package com.thanosfisherman.data_network.models.response.characters

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)