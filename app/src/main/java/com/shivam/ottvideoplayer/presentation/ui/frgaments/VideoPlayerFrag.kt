package com.shivam.ottvideoplayer.presentation.ui.frgaments

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.OptIn
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.shivam.ottvideoplayer.R
import com.shivam.ottvideoplayer.databinding.FragmentVideoPlayerBinding
import com.shivam.ottvideoplayer.presentation.player.PlayerEventHandler
import com.shivam.ottvideoplayer.presentation.ui.utils.MyViewModel

class VideoPlayerFrag : Fragment() {

    private val viewModel: MyViewModel by activityViewModels()
    private lateinit var binding: FragmentVideoPlayerBinding
    private lateinit var player: ExoPlayer
    private var playerEventHandler: PlayerEventHandler? = null

    private var playbackPosition: Long = 0L
    private var playWhenReadyState: Boolean = true

    companion object {
        private const val KEY_PLAYBACK_POSITION = "key_playback_position"
        private const val KEY_PLAY_WHEN_READY = "key_play_when_ready"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(KEY_PLAYBACK_POSITION, 0L)
            playWhenReadyState = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY, true)
        }
        initPlayer()
        setupOrientationControls()
        setupBackPressHandler()
    }

    private fun setupBackPressHandler() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                    if (isLandscape) {
                        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        )
    }

    @OptIn(UnstableApi::class)
    private fun initPlayer() {
        val selectedItem = viewModel.selectedItem.value
        val videoUrl = selectedItem?.videoUrl

        if (selectedItem != null) {
            binding.tvPlayerTitle.text = selectedItem.title
            binding.tvPlayerDesc.text = selectedItem.description
        }

        if (videoUrl.isNullOrEmpty()) {
            Log.e("VideoPlayerFrag", "Video URL is null or empty")
            return
        }

        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent("Mozilla/5.0 (Linux; Android 11; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Mobile Safari/537.36")
            .setAllowCrossProtocolRedirects(true)

        val mediaSourceFactory = DefaultMediaSourceFactory(requireContext())
            .setDataSourceFactory(httpDataSourceFactory)

        player = ExoPlayer.Builder(requireContext())
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        binding.playerView.player = player

        playerEventHandler = PlayerEventHandler { playerEvent ->
            viewModel.onPlayerEvent(playerEvent)
        }

        playerEventHandler?.let { handler ->
            player.addListener(handler)
        }

        val mediaItem = MediaItem.fromUri(videoUrl)
        player.setMediaItem(mediaItem)

        if (playbackPosition > 0) {
            player.seekTo(playbackPosition)
        }

        player.prepare()
        player.playWhenReady = playWhenReadyState
    }

    private fun setupOrientationControls() {
        val toggleListener = View.OnClickListener {
            toggleOrientation()
        }
        binding.btnOrientation.setOnClickListener(toggleListener)
        binding.btnSwitchMode.setOnClickListener(toggleListener)

        updateOrientationUI()
    }

    private fun toggleOrientation() {
        val currentOrientation = resources.configuration.orientation
        activity?.requestedOrientation = if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    private fun updateOrientationUI() {
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        if (isLandscape) {
            binding.btnOrientation.setIconResource(R.drawable.ic_fullscreen_exit)
            binding.btnSwitchMode.setIconResource(R.drawable.ic_fullscreen_exit)
            binding.btnSwitchMode.text = getString(R.string.portrait_mode)
            binding.cvPlayingInfo.visibility = View.GONE

            val params = binding.cvPlayerFrame.layoutParams as ConstraintLayout.LayoutParams
            params.height = ConstraintLayout.LayoutParams.MATCH_PARENT
            params.setMargins(0, 0, 0, 0)
            binding.cvPlayerFrame.layoutParams = params
            binding.cvPlayerFrame.radius = 0f
        } else {
            binding.btnOrientation.setIconResource(R.drawable.ic_fullscreen)
            binding.btnSwitchMode.setIconResource(R.drawable.ic_fullscreen)
            binding.btnSwitchMode.text = getString(R.string.landscape_mode)
            binding.cvPlayingInfo.visibility = View.VISIBLE

            val params = binding.cvPlayerFrame.layoutParams as ConstraintLayout.LayoutParams
            val marginInPx = (12 * resources.displayMetrics.density).toInt()
            val heightInPx = (250 * resources.displayMetrics.density).toInt()
            params.height = heightInPx
            params.setMargins(marginInPx, marginInPx, marginInPx, marginInPx)
            binding.cvPlayerFrame.layoutParams = params
            binding.cvPlayerFrame.radius = 12 * resources.displayMetrics.density
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateOrientationUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::player.isInitialized) {
            outState.putLong(KEY_PLAYBACK_POSITION, player.currentPosition)
            outState.putBoolean(KEY_PLAY_WHEN_READY, player.playWhenReady)
        } else {
            outState.putLong(KEY_PLAYBACK_POSITION, playbackPosition)
            outState.putBoolean(KEY_PLAY_WHEN_READY, playWhenReadyState)
        }
    }

    override fun onPause() {
        super.onPause()
        if (::player.isInitialized) {
            playbackPosition = player.currentPosition
            playWhenReadyState = player.playWhenReady
            player.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (::player.isInitialized && player.playbackState == ExoPlayer.STATE_READY) {
            player.play()
        }
    }

    override fun onStop() {
        super.onStop()
        if (::player.isInitialized) {
            playbackPosition = player.currentPosition
            playWhenReadyState = player.playWhenReady
            playerEventHandler?.let { handler ->
                player.removeListener(handler)
            }
            player.release()
        }
    }
}
