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

    @OneToOne
    val card: BankCard? = null,

    var pointsBalance: Int,
    var gems: Int
) {
    @get:Transient
    val balance: BigDecimal?
        get() = card?.balance
}