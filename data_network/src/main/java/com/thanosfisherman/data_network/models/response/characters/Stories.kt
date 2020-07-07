package com.thanosfisherman.data_network.models.response.characters

import com.thanosfisherman.data_network.models.response.characters.ItemXXX

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXXX>,
    val returned: Int
)