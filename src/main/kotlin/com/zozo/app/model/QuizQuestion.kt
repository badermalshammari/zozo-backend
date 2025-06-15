package com.zozo.app.model

import jakarta.persistence.*


@Entity
data class QuizQuestion(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val questionId: Long = 0,

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    val quiz: Quiz,

    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val correctOption: String
)