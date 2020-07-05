package com.thanosfisherman.data_network.models.response

data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Long,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
)