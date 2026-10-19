package com.shivam.ottvideoplayer.domain.use_case

import com.shivam.ottvideoplayer.domain.domain_model.DomainOTTDataModel
import com.shivam.ottvideoplayer.domain.repostiory.DomainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHomeContentUseCase(
    private val repository: DomainRepository
) {
    operator fun invoke(): Flow<DomainOTTDataModel?> = flow {
        emit(repository.getOTTData())
    }
}
