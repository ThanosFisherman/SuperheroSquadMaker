package com.thanosfisherman.data_network.models.response.characters

import com.thanosfisherman.data_network.models.response.characters.ItemXX

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXX>,
    val returned: Int
)