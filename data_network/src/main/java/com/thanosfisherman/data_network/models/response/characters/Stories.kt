package com.thanosfisherman.data_network.models.response.characters

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)