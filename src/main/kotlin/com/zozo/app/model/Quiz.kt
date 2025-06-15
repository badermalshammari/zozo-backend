package com.zozo.app.model

import jakarta.persistence.*


@Entity
data class Quiz(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val quizId: Long = 0,

    @OneToOne
    @JoinColumn(name = "task_id")
    val task: KidTask,

    val title: String
)