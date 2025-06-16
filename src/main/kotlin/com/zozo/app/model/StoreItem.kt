package com.zozo.app.model

import jakarta.persistence.*


@Entity
data class StoreItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val itemId: Long = 0,
    val name: String,
    val description: String,
    val type: ItemsTypes? = ItemsTypes.OTHER,
    val age: Int,
    val costInGems: Int,
    val hide: Hide? = Hide.DISABLE
)
enum class ItemsTypes{
    BOYS, GIRLS, OTHER, GUNS, TOYS, SUMMER, BEACH, CARS
}
enum class Hide{
    ENABLE,
    DISABLE
}