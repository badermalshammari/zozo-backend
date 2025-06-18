package com.zozo.app.repository

import com.zozo.app.model.OrderedItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderedItemRepository : JpaRepository<OrderedItem, Long> {
    fun findAllByChildChildId(childId: Long): List<OrderedItem>
}