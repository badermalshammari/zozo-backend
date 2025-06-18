package com.zozo.app

import com.zozo.app.model.GlobalEducationalContent
import com.zozo.app.model.GlobalStoreItem
import com.zozo.app.model.ItemsTypes
import com.zozo.app.repository.GlobalEducationalContentRepository
import com.zozo.app.repository.GlobalStoreItemRepository
import com.zozo.app.repository.QuizRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataSeeder(
    private val globalStoreItemRepository: GlobalStoreItemRepository,
    private val globalEducationalRepository: GlobalEducationalContentRepository,
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
        val educationalContentList = listOf(
            GlobalEducationalContent(
                title = "The Circulatory System",
                description = "This video allows children to learn in a fun way about the circulatory system.",
                videoFilename = "circulatory_system.mp4",
                coverPicture = "default.png",
                time = 150
            ),
            GlobalEducationalContent(
                title = "The Reef Cup - Story about Friendship",
                description = "Video teaching kids about friendship, sportsmanship, loyalty, and sea ecosystems.",
                videoFilename = "reef_cup.mp4",
                coverPicture = "default.png",
                time = 589
            ),
            GlobalEducationalContent(
                title = "Learn Letter Thaa - Arabic",
                description = "Let's learn the letter Thaa and words with this letter in an entertaining way.",
                videoFilename = "learn_thaa_ar.mp4",
                coverPicture = "default.png",
                time = 188
            ),
            GlobalEducationalContent(
                title = "Multiplication & Division - Basic Math",
                description = "Learn more about multiplications and divisions with fun monster characters.",
                videoFilename = "multiplication_division.mp4",
                coverPicture = "default.png",
                time = 240
            ),
            GlobalEducationalContent(
                title = "Addition & Subtraction - Basic Math",
                description = "Fun math problems at Monster University with easy examples for kids.",
                videoFilename = "addition_subtraction.mp4",
                coverPicture = "default.png",
                time = 278
            ),
            GlobalEducationalContent(
                title = "Musical Notation - Notes",
                description = "Kids learn quarter, eighth, and sixteenth notes with interactive questions.",
                videoFilename = "musical_notation.mp4",
                coverPicture = "default.png",
                time = 225
            ),
            GlobalEducationalContent(
                title = "Learn Surah Al-Qadr",
                description = "Learn Surah Al-Qadr repeated 10 times in a fun way with Zakaria characters.",
                videoFilename = "surah_al_qadr.mp4",
                coverPicture = "default.png",
                time = 521
            ),
            GlobalEducationalContent(
                title = "Learn Arabic Numbers 1-20",
                description = "Learn numbers from 1 to 20 in Arabic with Zakaria and Zeeko.",
                videoFilename = "arabic_numbers.mp4",
                coverPicture = "default.png",
                time = 244
            ),
            GlobalEducationalContent(
                title = "Learn Colors - English & Arabic",
                description = "Learn the names of colors in English and Arabic in a fun way.",
                videoFilename = "colors_en_ar.mp4",
                coverPicture = "default.png",
                time = 318
            ),
            GlobalEducationalContent(
                title = "Memory Game - Fruits & Vegetables",
                description = "Play the memory card game with fruits and vegetables cartoon for kids.",
                videoFilename = "memory_game_fruits.mp4",
                coverPicture = "default.png",
                time = 209
            )
        )

        globalEducationalRepository.saveAll(educationalContentList)
        println("✅ 10 educational videos added successfully")
    }

}