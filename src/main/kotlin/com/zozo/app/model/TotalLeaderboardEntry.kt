package com.zozo.app.model

import jakarta.persistence.*

@Entity
@Table(name = "total_leaderboard_entry")
data class TotalLeaderboardEntry(
    val name: String,
    val totalScore: Int,
    val rank: Int,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    @JoinColumn(name = "child_id")
    val child: Child
)