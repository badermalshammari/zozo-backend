package com.zozo.app.service

import com.zozo.app.model.Parent
import com.zozo.app.repository.ParentRepository
import org.springframework.stereotype.Service

@Service
class ParentService(private val parentRepository: ParentRepository) {

    fun getAllParents(): List<Parent> = parentRepository.findAll()

    fun getParentById(id: Long): Parent? = parentRepository.findById(id).orElse(null)

    fun getParentByUsername(username: String): Parent? = parentRepository.findByUsername(username)

    fun createParent(parent: Parent): Parent = parentRepository.save(parent)

    fun deleteParent(id: Long) = parentRepository.deleteById(id)
}