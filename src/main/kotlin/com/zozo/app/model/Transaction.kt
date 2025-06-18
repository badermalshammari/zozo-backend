package com.zozo.app.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val transactionId: Long = 0,

    @ManyToOne
    @JoinColumn(name = "from_card_id")
    val fromCard: BankCard? = null,

    @ManyToOne
    @JoinColumn(name = "to_card_id")
    val toCard: BankCard? = null,

    val amount: BigDecimal,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val description: String? = null
)