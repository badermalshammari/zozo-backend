package com.zozo.app.dto

data class CreateParentCardRequest(
    val parentId: Long,
    val cardDesign: String
)