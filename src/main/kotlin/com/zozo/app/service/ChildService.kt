package com.zozo.app.service

import com.zozo.app.model.Child
import com.zozo.app.model.Parent
import com.zozo.app.repository.ChildRepository
import org.springframework.stereotype.Service

@Service
class ChildService(private val childRepository: ChildRepository) {

    fun getAllChildren(): List<Child> =
        childRepository.findAll()

    fun getChildById(id: Long): Child? =
        childRepository.findById(id).orElse(null)

    fun getChildrenByParentId(parentId: Long): List<Child> =
        childRepository.findAllByParent_ParentId(parentId)

    fun getChildByUsername(username: String): Child? =
        childRepository.findByUsername(username)

    fun createChildForParent(child: Child, parent: Parent): Child {
        val existing = childRepository.findByUsername(child.username)
        if (existing != null) throw IllegalArgumentException("Username already taken")

        return childRepository.save(child.copy(parent = parent))
    }
}