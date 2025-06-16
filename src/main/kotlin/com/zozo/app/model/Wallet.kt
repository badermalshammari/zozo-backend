package com.zozo.app.model

import jakarta.persistence.*

@Entity
data class Wallet(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val walletId: Long = 0,

    @OneToOne
    @JoinColumn(name = "child_id")
    val child: Child,

    var balance: Double,
    var pointsBalance: Int,
    var gems: Int
)