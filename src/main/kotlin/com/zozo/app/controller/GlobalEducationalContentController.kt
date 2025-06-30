package com.zozo.app.controller

import com.zozo.app.dto.GlobalEducationalContentDto
import com.zozo.app.model.GlobalEducationalContent
import com.zozo.app.service.GlobalEducationalContentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/content")
class GlobalEducationalContentController(
    private val service: GlobalEducationalContentService
) {

    @GetMapping
    fun getAll(): List<GlobalEducationalContentDto> {
        val content = service.getAllContent()
        println("Fetched ${content.size} videos, unique = ${content.distinctBy { it.videoId }.size}")
        return content.distinctBy { it.videoId }.map { it.toDto() }
    }

    @PostMapping
    fun addContent(@RequestBody content: GlobalEducationalContent): GlobalEducationalContent =
        service.addContent(content)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): GlobalEducationalContentDto =
        service.getById(id).toDto()

    private fun GlobalEducationalContent.toDto() = GlobalEducationalContentDto(
        id = this.videoId,
        title = this.title,
        description = this.description,
        videoFilename = this.videoFilename,
        coverPicture = this.coverPicture,
        time = this.time,
        youtubeUrl = this.youtubeUrl
    )
}