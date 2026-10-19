package com.shivam.ottvideoplayer.domain.use_case

import com.shivam.ottvideoplayer.domain.domain_model.PlayerEvent
import com.shivam.ottvideoplayer.domain.repostiory.AnalyticsRepository

class LogPlayerEventUseCase(
    private val analyticsRepository: AnalyticsRepository
) {
    operator fun invoke(event: PlayerEvent) {
        analyticsRepository.logPlayerEvent(event)
    }
}
