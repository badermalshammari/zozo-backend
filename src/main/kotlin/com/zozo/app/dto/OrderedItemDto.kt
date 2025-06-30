package com.zozo.app.dto

import com.zozo.app.model.Child
import com.zozo.app.model.ChildStoreItem

data class OrderedItemDto(
    val orderId: Long,
    val childName: String,
    val itemName: String,
    val itemImageUrl: String,
    val gemsCost: Int,
    val status: String,
    val orderedAt: String
)