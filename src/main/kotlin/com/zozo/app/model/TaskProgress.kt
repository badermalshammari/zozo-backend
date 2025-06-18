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

    var status: TaskStatus? = TaskStatus.NOT_STARTED,
    var progressPercentage: Int,
    var currentTimeSeconds: Int,
    var earnedPoints: Int,
    var earnedGems: Int,
    var completedAt: LocalDateTime?,
    var type: TaskType
)
enum class TaskStatus {
    NOT_STARTED, IN_PROGRESS, FINISHED
}