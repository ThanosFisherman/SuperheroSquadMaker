package com.thanosfisherman.data_network.models.response

import com.thanosfisherman.data_network.models.response.ItemXX

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXX>,
    val returned: Int
)