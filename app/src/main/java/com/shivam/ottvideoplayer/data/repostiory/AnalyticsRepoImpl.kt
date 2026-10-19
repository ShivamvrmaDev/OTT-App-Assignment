package com.shivam.ottvideoplayer.data.repostiory

import android.util.Log
import com.shivam.ottvideoplayer.domain.domain_model.PlayerEvent
import com.shivam.ottvideoplayer.domain.repostiory.AnalyticsRepository

class AnalyticsRepoImpl : AnalyticsRepository {

    override fun logPlayerEvent(event: PlayerEvent) {
        val eventName = when (event) {
            is PlayerEvent.PlayerReady -> "PLAYER_READY"
            is PlayerEvent.PlaybackStarted -> "PLAYER_STARTED"
            is PlayerEvent.PlaybackPaused -> "PLAYER_PAUSED"
            is PlayerEvent.SeekPerformed -> "PLAYER_SEEKED (position=${event.positionMs}ms)"
            is PlayerEvent.BufferingStarted -> "BUFFERING_STARTED"
            is PlayerEvent.BufferingEnded -> "BUFFERING_ENDED"
            is PlayerEvent.PlaybackCompleted -> "PLAYER_COMPLETED"
            is PlayerEvent.PlaybackError -> "PLAYER_ERROR (${event.errorMessage})"
        }
        Log.d("OTTAnalytics", "[PLAYER_EVENT_ANALYTICS] -> $eventName")
    }
}
