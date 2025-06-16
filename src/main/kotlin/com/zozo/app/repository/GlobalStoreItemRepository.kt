package com.zozo.app.repository

import com.zozo.app.model.GlobalStoreItem
import org.springframework.data.jpa.repository.JpaRepository

interface GlobalStoreItemRepository : JpaRepository<GlobalStoreItem, Long>


