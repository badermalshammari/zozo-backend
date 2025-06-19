package com.zozo.app.dto

data class LeaderboardEntry(
    val childId: Long,
    val name: String,
    val avatar: String,
    val points: Int
)