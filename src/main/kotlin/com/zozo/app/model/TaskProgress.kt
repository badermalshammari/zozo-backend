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

    @Enumerated(EnumType.STRING)
    val status: StatusType,
    val progressPercentage: Int,
    val currentTimeSeconds: Int,
    val earnedPoints: Int,
    val earnedGems: Int,
    val completedAt: LocalDateTime?
)

enum class StatusType {
    NEW, INPROGRESS ,FINISHED
}