package com.zozo.app.repository

import com.zozo.app.model.Child
import org.springframework.data.jpa.repository.JpaRepository

interface ChildRepository : JpaRepository<Child, Long> {
    fun findByUsername(username: String): Child?
    fun findAllByParent_ParentId(parentId: Long): List<Child>
}