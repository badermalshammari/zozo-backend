package com.zozo.app.model

import jakarta.persistence.*

@Entity
@Table(name = "monthly_leaderboard_entry")
data class MonthlyLeaderboardEntry(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @OneToOne
    @JoinColumn(name = "child_id")
    val child: Child,

    val name: String,
    var totalScore: Int,
    var rank: Int
)