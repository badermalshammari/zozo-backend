package com.zozo.app.model

import jakarta.persistence.*

@Entity
@Table(name = "parent")
data class Parent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val parentId: Long = 0,

    val name: String,
    val username: String,
    val password: String,

    @Enumerated(EnumType.STRING)
    val status: AccountStatus? = AccountStatus.ACTIVE

)

enum class AccountStatus {
    ACTIVE,
    INACTIVE
}