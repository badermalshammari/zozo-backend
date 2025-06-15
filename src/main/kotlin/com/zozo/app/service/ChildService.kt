package com.zozo.app.service

import com.zozo.app.model.AccountStats
import com.zozo.app.model.Child
import com.zozo.app.model.Parent
import com.zozo.app.repository.ChildRepository
import org.springframework.stereotype.Service

@Service
class ChildService(private val childRepository: ChildRepository) {

    fun updateChild(child: Child): Child {
        return childRepository.save(child)
    }

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

    fun changeChildStats(childId: Long, stats: AccountStats): Child {
        val child = childRepository.findById(childId).orElseThrow { IllegalArgumentException("Child not found") }
            val updatedChild = child.copy(stats = stats)
        return childRepository.save(updatedChild)
    }
}