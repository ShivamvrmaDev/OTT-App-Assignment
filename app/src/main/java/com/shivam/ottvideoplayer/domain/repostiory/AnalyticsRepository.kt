package com.shivam.ottvideoplayer.domain.repostiory

import com.shivam.ottvideoplayer.domain.domain_model.PlayerEvent

interface AnalyticsRepository {
    fun logPlayerEvent(event: PlayerEvent)
}
