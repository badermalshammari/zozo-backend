package com.zozo.app.model

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
data class TaskProgress(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val taskProgressId: Long = 0,

    @ManyToOne
    @JoinColumn(name = "child_id")
    val child: Child,

    @ManyToOne
    @JoinColumn(name = "task_id")
    val task: KidTask,

    val status: String,
    val progressPercentage: Int,
    val currentTimeSeconds: Int,
    val earnedPoints: Int,
    val earnedGems: Int,
    val completedAt: LocalDateTime?
)