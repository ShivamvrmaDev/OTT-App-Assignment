package com.shivam.ottvideoplayer.domain.repostiory

import com.shivam.ottvideoplayer.domain.domain_model.DomainOTTDataModel

interface DomainRepository {
    suspend fun getOTTData(): DomainOTTDataModel?
}
