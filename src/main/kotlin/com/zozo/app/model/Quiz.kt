package com.zozo.app.model

import jakarta.persistence.*


@Entity
data class Quiz(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val quizId: Long = 0,

    @OneToOne
    @JoinColumn(name = "task_id")
    val task: KidTask,

    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOption: String,

    @Enumerated(EnumType.STRING)
    val status: TaskStatus = TaskStatus.NOT_STARTED,

    val title: String? = "${task.title}"
)