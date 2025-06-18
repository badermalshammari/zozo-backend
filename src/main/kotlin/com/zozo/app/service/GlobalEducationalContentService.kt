package com.zozo.app.service

import com.zozo.app.model.GlobalEducationalContent
import com.zozo.app.repository.GlobalEducationalContentRepository
import org.springframework.stereotype.Service

@Service
class GlobalEducationalContentService(
    private val repo: GlobalEducationalContentRepository
) {
    fun getAllContent(): List<GlobalEducationalContent> = repo.findAll()
    fun getById(id: Long): GlobalEducationalContent = repo.findById(id).orElseThrow()
    fun addContent(content: GlobalEducationalContent): GlobalEducationalContent = repo.save(content)
}
