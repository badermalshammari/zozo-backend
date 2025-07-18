package com.zozo.app.repository

import com.zozo.app.model.Child
import org.springframework.data.jpa.repository.JpaRepository

interface ChildRepository : JpaRepository<Child, Long> {
    fun findByCivilId(civilId: String): Child?
    fun findByName(name: String): Child?
    fun findAllByParent_ParentId(parentId: Long): List<Child>
}