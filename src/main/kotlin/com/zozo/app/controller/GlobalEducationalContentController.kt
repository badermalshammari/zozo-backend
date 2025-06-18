package com.zozo.app.controller

import com.zozo.app.model.GlobalEducationalContent
import com.zozo.app.service.GlobalEducationalContentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/content")
class GlobalEducationalContentController(
    private val service: GlobalEducationalContentService
) {
    @GetMapping
    fun getAll(): List<GlobalEducationalContent> = service.getAllContent()

    @PostMapping
    fun addContent(@RequestBody content: GlobalEducationalContent): GlobalEducationalContent =
        service.addContent(content)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): GlobalEducationalContent = service.getById(id)
}