package com.zozo.app.repository

import com.zozo.app.model.StoreItem
import org.springframework.data.jpa.repository.JpaRepository

interface StoreItemRepository : JpaRepository<StoreItem, Long>


