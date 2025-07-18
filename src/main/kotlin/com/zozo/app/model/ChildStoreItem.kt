package com.zozo.app.model

import jakarta.persistence.*

@Entity
data class ChildStoreItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "child_id")
    val child: Child,

    @ManyToOne
    @JoinColumn(name = "global_item_id")
    val globalItem: GlobalStoreItem,

    var isHidden: Boolean = false,


    @Column(nullable = false)
    var wishList: Boolean = false

)