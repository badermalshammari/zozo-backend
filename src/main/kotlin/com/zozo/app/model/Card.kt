package com.zozo.app.model


import jakarta.persistence.*
import java.math.BigDecimal


@Entity
@Table(name = "bankcard")
data class BankCard(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cardId: Long = 0,


    val accountNumber: Long? = (100_000_000L..999_999_999L).random(),
    val cardNumber: String? = "53" + (1..14).joinToString("") { (0..9).random().toString() },
    val cardHolderName: String,
    val expiryMonth: Int,
    val expiryYear: Int,
    val cvv: String? = (100..999).random().toString(),
    var balnace: BigDecimal? = BigDecimal.ZERO,
    val cardDesign: String? = "default.png",
    val isActive: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = true)
    val child: Child? = null,

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    val parent: Parent? = null
)