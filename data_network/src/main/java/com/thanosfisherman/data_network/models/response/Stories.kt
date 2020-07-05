package com.thanosfisherman.data_network.models.response

import com.thanosfisherman.data_network.models.response.ItemXXX

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXXX>,
    val returned: Int
)