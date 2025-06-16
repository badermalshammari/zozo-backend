package com.zozo.app

import com.zozo.app.model.GlobalStoreItem
import com.zozo.app.model.ItemsTypes
import com.zozo.app.repository.GlobalStoreItemRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataSeeder(
    private val globalStoreItemRepository: GlobalStoreItemRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        if (globalStoreItemRepository.count() == 0L) {
            val items = listOf(
                GlobalStoreItem(
                    name = "Puzzle Game",
                    description = "Enhances logic and problem-solving skills",
                    age = 6,
                    costInGems = 100,
                    type = ItemsTypes.BOYS,
                    photo = "puzzle.png"
                ),
                GlobalStoreItem(
                    name = "Princess Doll",
                    description = "A beautiful doll for storytelling fun",
                    age = 5,
                    costInGems = 80,
                    type = ItemsTypes.GIRLS,
                    photo = "princess_doll.png"
                ),
                GlobalStoreItem(
                    name = "Water Gun",
                    description = "Summer fun with safe water play",
                    age = 6,
                    costInGems = 90,
                    type = ItemsTypes.GUNS,
                    photo = "water_gun.png"
                ),
                GlobalStoreItem(
                    name = "Toy Car",
                    description = "Mini racing car with great speed",
                    age = 4,
                    costInGems = 60,
                    type = ItemsTypes.CARS,
                    photo = "toy_car.png"
                ),
                GlobalStoreItem(
                    name = "Math Book",
                    description = "Helps kids learn basic math",
                    age = 7,
                    costInGems = 50,
                    type = ItemsTypes.OTHER,
                    photo = "math_book.png"
                ),
                GlobalStoreItem(
                    name = "Color Set",
                    description = "Boosts creativity with drawing tools",
                    age = 5,
                    costInGems = 40,
                    type = ItemsTypes.OTHER,
                    photo = "color_set.png"
                ),
                GlobalStoreItem(
                    name = "Beach Ball",
                    description = "Perfect for beach games",
                    age = 5,
                    costInGems = 30,
                    type = ItemsTypes.BEACH,
                    photo = "beach_ball.png"
                ),
                GlobalStoreItem(
                    name = "Sunglasses",
                    description = "Cool shades for summer days",
                    age = 6,
                    costInGems = 25,
                    type = ItemsTypes.SUMMER,
                    photo = "sunglasses.png"
                )
            )
            globalStoreItemRepository.saveAll(items)
            println("✅ Sample items successfully seeded.")
        }
    }
}