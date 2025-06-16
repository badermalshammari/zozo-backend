package com.zozo.app.model

import jakarta.persistence.*


@Entity
data class GlobalStoreItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val itemId: Long = 0,
    val name: String,
    val description: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: ItemsTypes? = ItemsTypes.OTHER,

    val age: Int,
    val costInGems: Int,
    val photo: String? = "defualt.png"
)
enum class ItemsTypes{
    BOYS, GIRLS, OTHER, GUNS, TOYS, SUMMER, BEACH, CARS
}
