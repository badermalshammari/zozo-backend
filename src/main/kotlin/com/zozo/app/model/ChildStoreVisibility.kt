package com.zozo.app.model

import jakarta.persistence.*


@Entity
data class ChildStoreVisibility(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "child_id")
    val child: Child,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: StoreItem,

    val isHidden: Boolean
)