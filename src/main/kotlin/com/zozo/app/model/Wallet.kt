package com.zozo.app.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class Wallet(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val walletId: Long = 0,

    @OneToOne
    @JoinColumn(name = "child_id", unique = true)
    val child: Child,

    var pointsBalance: Int,
    var gems: Int
)