package com.shivam.ottvideoplayer.domain.domain_model

sealed class PlayerEvent {
    object PlayerReady : PlayerEvent()
    object PlaybackStarted : PlayerEvent()
    object PlaybackPaused : PlayerEvent()
    data class SeekPerformed(val positionMs: Long) : PlayerEvent()
    object BufferingStarted : PlayerEvent()
    object BufferingEnded : PlayerEvent()
    object PlaybackCompleted : PlayerEvent()
    data class PlaybackError(val errorMessage: String) : PlayerEvent()
}
