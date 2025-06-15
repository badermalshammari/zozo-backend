package com.zozo.app.model

import jakarta.persistence.*


@Entity
data class StoreItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val itemId: Long = 0,
    val name: String,
    val description: String,
    val type: String,
    val age: Int,
    val costInPoints: Int
)