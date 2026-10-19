package com.shivam.ottvideoplayer.domain.domain_model

data class DomainCategory(
    val id: String,
    val name: String,
    val items: List<DomainItem>
)
