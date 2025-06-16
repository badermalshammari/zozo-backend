package com.zozo.app.repository

import com.zozo.app.model.Child
import com.zozo.app.model.OrderedItem
import com.zozo.app.model.StoreItem
import com.zozo.app.model.Wallet
import org.springframework.data.jpa.repository.JpaRepository

interface StoreItemRepository : JpaRepository<StoreItem, Long>

interface OrderedItemRepository : JpaRepository<OrderedItem, Long>

interface WalletRepository : JpaRepository<Wallet, Long> {
    fun findByChild(child: Child): Wallet?
}

