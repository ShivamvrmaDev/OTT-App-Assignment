package com.shivam.ottvideoplayer.data.model

import com.shivam.ottvideoplayer.domain.domain_model.DomainCategory
import com.shivam.ottvideoplayer.domain.domain_model.DomainItem
import com.shivam.ottvideoplayer.domain.domain_model.DomainOTTDataModel

data class OTTDataModelClass(
    val heroBanner: Item,
    val categories: List<Category>
)

fun OTTDataModelClass.toDomain(): DomainOTTDataModel {
    return DomainOTTDataModel(
        mainCard = DomainItem(
            id = this.heroBanner.id,
            title = this.heroBanner.title,
            description = this.heroBanner.description,
            thumbnailUrl = this.heroBanner.thumbnailUrl,
            bannerUrl = this.heroBanner.bannerUrl,
            videoUrl = this.heroBanner.videoUrl
        ),
        categoriesData = this.categories.map { category ->
            DomainCategory(
                id = category.id,
                name = category.name,
                items = category.items.map { item ->
                    DomainItem(
                        id = item.id,
                        title = item.title,
                        description = item.description,
                        thumbnailUrl = item.thumbnailUrl,
                        bannerUrl = item.bannerUrl,
                        videoUrl = item.videoUrl
                    )
                }
            )
        }
    )
}
