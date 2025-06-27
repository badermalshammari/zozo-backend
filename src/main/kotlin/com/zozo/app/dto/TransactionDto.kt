package com.zozo.app.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionDto(
    val transactionId: Long,
    val fromCardNumber: String,
    val toCardNumber: String,
    val amount: BigDecimal,
    val timestamp: LocalDateTime,
    val description: String?
)