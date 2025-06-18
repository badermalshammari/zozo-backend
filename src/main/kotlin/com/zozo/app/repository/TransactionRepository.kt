package com.zozo.app.repository

import com.zozo.app.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<Transaction, Long> {
    fun findByFromCardCardIdOrToCardCardId(fromId: Long, toId: Long): List<Transaction>}