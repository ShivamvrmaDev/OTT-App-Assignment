package com.shivam.ottvideoplayer.data.repostiory

import android.content.Context
import com.shivam.ottvideoplayer.data.data_resource.ContentDataSource
import com.shivam.ottvideoplayer.data.data_resource.LocalDataSource
import com.shivam.ottvideoplayer.data.model.toDomain
import com.shivam.ottvideoplayer.domain.domain_model.DomainOTTDataModel
import com.shivam.ottvideoplayer.domain.repostiory.DomainRepository

class DomainRepoImpl(
    private val contentDataSource: ContentDataSource
) : DomainRepository {

    constructor(context: Context) : this(LocalDataSource(context))

    override suspend fun getOTTData(): DomainOTTDataModel? {
        return contentDataSource.getHomeData()?.toDomain()
    }
}
