package com.zozo.app.dto

data class GlobalEducationalContentDto(
    val id: Long,
    val title: String,
    val description: String,
    val videoFilename: String?,
    val coverPicture: String?,
    val time: Int?,
    val youtubeUrl: String?
)