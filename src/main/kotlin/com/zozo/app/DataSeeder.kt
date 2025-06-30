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
                GlobalStoreItem(name = "Giraffe Inflatable Pool", description = "Colorful kiddie pool for summer fun", age = 3, costInGems = 6000, type = ItemsTypes.SUMMER, photo = "toy_1"),
                GlobalStoreItem(name = "Basketball Hoop Set", description = "Shoot hoops indoors or outdoors", age = 6, costInGems = 5500, type = ItemsTypes.OTHER, photo = "toy_2"),
                GlobalStoreItem(name = "Disney Princess Wardrobe", description = "Unbox magical surprises", age = 5, costInGems = 5000, type = ItemsTypes.GIRLS, photo = "toy_3"),
                GlobalStoreItem(name = "Magnetic Dartboard", description = "Safe and fun dart throwing", age = 6, costInGems = 3500, type = ItemsTypes.OTHER, photo = "toy_4"),
                GlobalStoreItem(name = "Electronic Keyboard", description = "Play and learn music", age = 6, costInGems = 50, type = ItemsTypes.OTHER, photo = "toy_5"),
                GlobalStoreItem(name = "Stitch Slime Kit", description = "Stretchy fun in Stitch design", age = 5, costInGems = 4500, type = ItemsTypes.OTHER, photo = "toy_6"),
                GlobalStoreItem(name = "Geomag Magnetic Set", description = "Build structures with magnets", age = 5, costInGems = 5500, type = ItemsTypes.OTHER, photo = "toy_7"),
                GlobalStoreItem(name = "Toy Microphone", description = "Sing your favorite songs", age = 3, costInGems = 3000, type = ItemsTypes.GIRLS, photo = "toy_8"),
                GlobalStoreItem(name = "Mini Piano Toy", description = "Colorful keys with sounds", age = 3, costInGems = 2500, type = ItemsTypes.OTHER, photo = "toy_9"),
                GlobalStoreItem(name = "Dino World Book", description = "Adventure book with puzzles", age = 5, costInGems = 4000, type = ItemsTypes.BOYS, photo = "toy_10"),
                GlobalStoreItem(name = "Bitzee Disney Edition", description = "Collect 30 digital characters", age = 5, costInGems = 6000, type = ItemsTypes.OTHER, photo = "toy_11"),
                GlobalStoreItem(name = "3D Puzzle Blocks", description = "Solve shapes and challenges", age = 6, costInGems = 5000, type = ItemsTypes.OTHER, photo = "toy_12"),
                GlobalStoreItem(name = "Inflatable Bouncer", description = "Jump and slide safely", age = 4, costInGems = 7000, type = ItemsTypes.BEACH, photo = "toy_13"),
                GlobalStoreItem(name = "Eraser Making Kit", description = "Craft your own cute erasers", age = 6, costInGems = 4000, type = ItemsTypes.GIRLS, photo = "toy_14"),
                GlobalStoreItem(name = "X-Shot Insanity Blaster", description = "Rapid-fire foam blaster", age = 8, costInGems = 6500, type = ItemsTypes.GUNS, photo = "toy_15"),
                GlobalStoreItem(name = "Bildits Construction Set", description = "Build a mini cottage", age = 7, costInGems = 6000, type = ItemsTypes.OTHER, photo = "toy_16"),
                GlobalStoreItem(name = "Disney Bitzee Game", description = "Digital pet with surprises", age = 5, costInGems = 5000, type = ItemsTypes.OTHER, photo = "toy_17"),
                GlobalStoreItem(name = "Playhouse Slide Set", description = "Climb and slide playground", age = 4, costInGems = 7000, type = ItemsTypes.BEACH, photo = "toy_18"),
                GlobalStoreItem(name = "Arabic Story Game", description = "Interactive learning through play", age = 6, costInGems = 3500, type = ItemsTypes.OTHER, photo = "toy_19"),
                GlobalStoreItem(name = "Monster RC Truck", description = "All-terrain remote control", age = 7, costInGems = 6500, type = ItemsTypes.CARS, photo = "toy_20"),
                GlobalStoreItem(name = "Duck Target Gun", description = "Shoot foam balls at the duck", age = 6, costInGems = 4500, type = ItemsTypes.GUNS, photo = "toy_21"),
                GlobalStoreItem(name = "Outdoor Kitchen Playhouse", description = "Pretend cooking outdoors", age = 4, costInGems = 6000, type = ItemsTypes.OTHER, photo = "toy_22"),
                GlobalStoreItem(name = "Baby Balance Walker", description = "Push and play safely", age = 2, costInGems = 3500, type = ItemsTypes.OTHER, photo = "toy_23"),
                GlobalStoreItem(name = "Boxing Arena Set", description = "Duel with battling boxers", age = 6, costInGems = 5000, type = ItemsTypes.BOYS, photo = "toy_24"),
                GlobalStoreItem(name = "Kids Smartwatch", description = "Tracks time, steps, and fun features", age = 6, costInGems = 6500, type = ItemsTypes.OTHER, photo = "toy_25"),
                GlobalStoreItem(name = "Eggy Wawa Bath Toy", description = "Water play with lights and music", age = 3, costInGems = 3000, type = ItemsTypes.SUMMER, photo = "toy_26"),
                )
            globalStoreItemRepository.saveAll(items)
        }
        val educationalContentList = listOf(
            GlobalEducationalContent(
                title = "The Circulatory System",
                description = "This video allows children to learn in a fun way about the circulatory system.",
                videoFilename = "circulatory_system.mp4",
                coverPicture = "default.png",
                time = 150,
                youtubeUrl = "https://www.youtube.com/watch?v=TmcXm-8H-ks&list=PLZS3MUjYqjUGwX3lYR7_OKKnSnqfQkqmQ&index=3"
            ),
            GlobalEducationalContent(
                title = "The Reef Cup - Story about Friendship",
                description = "Video teaching kids about friendship, sportsmanship, loyalty, and sea ecosystems.",
                videoFilename = "reef_cup.mp4",
                coverPicture = "default.png",
                time = 589,
                youtubeUrl = "https://www.youtube.com/watch?v=rtqLwqbw30Q&list=PLZS3MUjYqjUGwX3lYR7_OKKnSnqfQkqmQ&index=13"
            ),
            GlobalEducationalContent(
                title = "Learn Letter Thaa - Arabic",
                description = "Let's learn the letter Thaa and words with this letter in an entertaining way.",
                videoFilename = "learn_thaa_ar.mp4",
                coverPicture = "default.png",
                time = 188,
                youtubeUrl = "https://www.youtube.com/watch?v=8hKC6rGMaP8"
            ),
            GlobalEducationalContent(
                title = "Multiplication & Division - Basic Math",
                description = "Learn more about multiplications and divisions with fun monster characters.",
                videoFilename = "multiplication_division.mp4",
                coverPicture = "default.png",
                time = 240,
                youtubeUrl = "https://www.youtube.com/watch?v=BNk-yaFguNs&list=PLZS3MUjYqjUGwX3lYR7_OKKnSnqfQkqmQ&index=21"
            ),
            GlobalEducationalContent(
                title = "Addition & Subtraction - Basic Math",
                description = "Fun math problems at Monster University with easy examples for kids.",
                videoFilename = "addition_subtraction.mp4",
                coverPicture = "default.png",
                time = 278,
                youtubeUrl = "https://www.youtube.com/watch?v=LIUAnh2b0fA&list=PLZS3MUjYqjUGwX3lYR7_OKKnSnqfQkqmQ&index=19"
            ),
            GlobalEducationalContent(
                title = "Musical Notation - Notes",
                description = "Kids learn quarter, eighth, and sixteenth notes with interactive questions.",
                videoFilename = "musical_notation.mp4",
                coverPicture = "default.png",
                time = 225,
                youtubeUrl = "https://www.youtube.com/watch?v=2bJjigIRo4c&list=PLZS3MUjYqjUGwX3lYR7_OKKnSnqfQkqmQ&index=12"
            ),
            GlobalEducationalContent(
                title = "Learn Surah Al-Qadr",
                description = "Learn Surah Al-Qadr repeated 10 times in a fun way with Zakaria characters.",
                videoFilename = "surah_al_qadr.mp4",
                coverPicture = "default.png",
                time = 521,
                youtubeUrl = "https://www.youtube.com/watch?v=ctNkoRgFBmg"
            ),
            GlobalEducationalContent(
                title = "Learn Arabic Numbers 1-20",
                description = "Learn numbers from 1 to 20 in Arabic with Zakaria and Zeeko.",
                videoFilename = "arabic_numbers.mp4",
                coverPicture = "default.png",
                time = 244,
                youtubeUrl = "https://www.youtube.com/watch?v=2xgyEC9WCA4"
            ),
            GlobalEducationalContent(
                title = "Learn Colors - English & Arabic",
                description = "Learn the names of colors in English and Arabic in a fun way.",
                videoFilename = "colors_en_ar.mp4",
                coverPicture = "default.png",
                time = 318,
                youtubeUrl = "https://www.youtube.com/watch?v=IB72L8OXSBs"
            ),
            GlobalEducationalContent(
                title = "Memory Game - Fruits & Vegetables",
                description = "Play the memory card game with fruits and vegetables cartoon for kids.",
                videoFilename = "memory_game_fruits.mp4",
                coverPicture = "default.png",
                time = 209,
                youtubeUrl = "https://www.youtube.com/watch?v=f4WYX9m3FGg"
            )
        )
        globalEducationalRepository.saveAll(educationalContentList)

    }

}