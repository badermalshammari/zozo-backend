package com.zozo.app.model

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
data class OrderedItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long = 0,

    @ManyToOne
    @JoinColumn(name = "child_id")
    val child: Child,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: GlobalStoreItem,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: OrderStatus =  OrderStatus.COMPLETED,
    val orderedAt: LocalDateTime,
    val gemsCost: Int
)
enum class OrderStatus{
    NOT_COMPLETED,
    COMPLETED,
}