package com.zozo.app.model

import jakarta.persistence.*

@Entity
@Table(name = "monthly_leaderboard_entry")
data class MonthlyLeaderboardEntry(
    @Id
    @OneToOne
    @JoinColumn(name = "child_id")
    val child: Child,

    val name: String,
    val totalScore: Int,
    val rank: Int
)