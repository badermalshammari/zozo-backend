package com.zozo.app.repository

import com.zozo.app.model.QuizAttempt
import org.springframework.data.jpa.repository.JpaRepository

interface QuizAttemptRepository : JpaRepository<QuizAttempt, Long> {
    fun findByChildChildId(childId: Long): List<QuizAttempt>
    fun findByQuizQuizIdAndChildChildId(quizId: Long, childId: Long): QuizAttempt?
}