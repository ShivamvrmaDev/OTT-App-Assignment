package com.shivam.ottvideoplayer.domain.use_case

import android.content.Context
import com.shivam.ottvideoplayer.domain.domain_model.DomainOTTDataModel
import com.shivam.ottvideoplayer.domain.repostiory.DomainRepository
import kotlinx.coroutines.flow.Flow

class HomeDataUseCase(
    private val repository: DomainRepository
) {
    private val getHomeContentUseCase = GetHomeContentUseCase(repository)

    operator fun invoke(context: Context? = null): Flow<DomainOTTDataModel?> {
        return getHomeContentUseCase()
    }
}
