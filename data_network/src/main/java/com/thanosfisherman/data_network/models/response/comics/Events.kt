package com.thanosfisherman.data_network.models.response.comics

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)