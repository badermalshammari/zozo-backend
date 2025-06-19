package com.zozo.app.repository

import com.zozo.app.model.Wallet
import com.zozo.app.model.Child
import org.springframework.data.jpa.repository.JpaRepository

interface WalletRepository : JpaRepository<Wallet, Long> {
    fun findByChild(child: Child): Wallet?
    fun findAllByChild_Parent_Username(username: String): List<Wallet>
}