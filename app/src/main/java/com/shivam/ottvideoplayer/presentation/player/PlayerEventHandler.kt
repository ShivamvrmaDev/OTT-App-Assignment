package com.shivam.ottvideoplayer.presentation.player

import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import com.shivam.ottvideoplayer.domain.domain_model.PlayerEvent

class PlayerEventHandler(
    private val onEvent: (PlayerEvent) -> Unit
) : Player.Listener {

    private var isBuffering = false

    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            Player.STATE_READY -> {
                if (isBuffering) {
                    isBuffering = false
                    onEvent(PlayerEvent.BufferingEnded)
                }
                onEvent(PlayerEvent.PlayerReady)
            }
            Player.STATE_BUFFERING -> {
                isBuffering = true
                onEvent(PlayerEvent.BufferingStarted)
            }
            Player.STATE_ENDED -> {
                onEvent(PlayerEvent.PlaybackCompleted)
            }
            Player.STATE_IDLE -> {
                // Idle state
            }
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        if (isPlaying) {
            onEvent(PlayerEvent.PlaybackStarted)
        } else {
            onEvent(PlayerEvent.PlaybackPaused)
        }
    }

    override fun onPositionDiscontinuity(
        oldPosition: Player.PositionInfo,
        newPosition: Player.PositionInfo,
        reason: Int
    ) {
        if (reason == Player.DISCONTINUITY_REASON_SEEK) {
            onEvent(PlayerEvent.SeekPerformed(newPosition.positionMs))
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        val message = error.localizedMessage ?: "Unknown playback error"
        onEvent(PlayerEvent.PlaybackError(message))
    }
}
