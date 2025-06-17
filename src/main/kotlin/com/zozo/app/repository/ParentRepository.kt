package com.zozo.app.repository

import com.zozo.app.model.Parent
import org.springframework.data.jpa.repository.JpaRepository

interface ParentRepository : JpaRepository<Parent, Long> {
    fun findByUsername(username: String): Parent?
    fun findByParentId(parentId:Long):Parent?
}