package com.zozo.app.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "child")
data class Child(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val childId: Long = 0,

    val name: String,
    val civilId: String,
    val birthday: LocalDate,

    @Enumerated(EnumType.STRING)
    val gender: GenderType? = GenderType.ZAIN,

    val avatar: String = "",

    @ManyToOne
    @JoinColumn(name = "parent_id")
    val parent: Parent,

    @Enumerated(EnumType.STRING)
    val role: RoleType = RoleType.CHILD,

    @Enumerated(EnumType.STRING)
    val stats: AccountStats = AccountStats.ENABLED
)

