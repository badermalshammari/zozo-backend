package com.zozo.app.dto

data class KidTaskDto(
    val taskId: Long,
    val title: String,
    val description: String,
    val type: String,
    val points: Int?,
    val gems: Int? = null,
    val childName: String,
    val videoTitle: String? = null,
    val youtubeUrl: String? = null,
    val status: String
)