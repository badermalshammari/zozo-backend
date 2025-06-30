package com.zozo.app.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "educationalcontent")
data class GlobalEducationalContent(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val videoId: Long = 0,

    val title: String,
    val description: String,
    val videoFilename: String?,
    val coverPicture: String? = "default.png",
    val time: Int?,
    val youtubeUrl: String? = null
)