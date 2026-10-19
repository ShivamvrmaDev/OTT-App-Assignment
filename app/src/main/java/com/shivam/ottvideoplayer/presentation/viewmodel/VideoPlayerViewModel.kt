package com.shivam.ottvideoplayer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.shivam.ottvideoplayer.data.repostiory.AnalyticsRepoImpl
import com.shivam.ottvideoplayer.domain.domain_model.PlayerEvent
import com.shivam.ottvideoplayer.domain.use_case.LogPlayerEventUseCase

class VideoPlayerViewModel(
    private val logPlayerEventUseCase: LogPlayerEventUseCase = LogPlayerEventUseCase(AnalyticsRepoImpl())
) : ViewModel() {

    fun onPlayerEvent(event: PlayerEvent) {
        logPlayerEventUseCase(event)
    }
}
