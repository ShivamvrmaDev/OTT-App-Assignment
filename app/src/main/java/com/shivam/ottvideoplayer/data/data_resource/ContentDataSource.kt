package com.shivam.ottvideoplayer.data.data_resource

import com.shivam.ottvideoplayer.data.model.OTTDataModelClass

interface ContentDataSource {
    suspend fun getHomeData(): OTTDataModelClass?
}
