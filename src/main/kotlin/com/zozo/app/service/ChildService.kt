package com.zozo.app.service

import com.zozo.app.controller.CreateChildRequest
import com.zozo.app.model.Child
import com.zozo.app.model.Parent
import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.ParentRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class ChildService(
    private val childRepo: ChildRepository,
    private val parentRepo: ParentRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun createChild(request: CreateChildRequest, parentUsername: String): Child {
        val parent: Parent = parentRepo.findByUsername(parentUsername)
            ?: throw IllegalArgumentException("Parent not found")

        if (childRepo.findByUsername(request.username) != null) {
            throw IllegalArgumentException("Child username already exists")
        }

        val encodedPassword = passwordEncoder.encode(request.password)

        val child = Child(
            name = request.name,
            civilId = request.civilId,
            birthday = request.birthday,
            username = request.username,
            password = encodedPassword,
            gender = request.gender,
            parent = parent
        )

        return childRepo.save(child)
    }
    fun getChildrenForParent(parentUsername: String): List<Child> {
        val parent = parentRepo.findByUsername(parentUsername)
            ?: throw IllegalArgumentException("Parent not found")

        return childRepo.findAllByParent_ParentId(parent.parentId)
    }
}