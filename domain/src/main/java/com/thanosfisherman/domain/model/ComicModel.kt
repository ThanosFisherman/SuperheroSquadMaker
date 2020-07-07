package com.thanosfisherman.domain.model

data class ComicModel(
    val id: Long,
    val title: String,
    val issueNumber: Int,
    val description: String,
    val pic: String
)