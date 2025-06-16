package com.zozo.app.repository

import com.zozo.app.model.ChildStoreItem
import org.springframework.data.jpa.repository.JpaRepository

interface ChildStoreItemRepository : JpaRepository<ChildStoreItem, Long> {
    fun findAllByChild_ChildId(childId: Long): List<ChildStoreItem>
}