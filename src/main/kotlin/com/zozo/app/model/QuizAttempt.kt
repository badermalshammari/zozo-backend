package com.zozo.app.model

import jakarta.persistence.*
import java.time.LocalDateTime



@Entity
data class QuizAttempt(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val attemptId: Long = 0,

    @ManyToOne
    @JoinColumn(name = "child_id")
    val child: Child,

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    val quiz: Quiz,

    val score: Int,
    val attemptedAt: LocalDateTime
)