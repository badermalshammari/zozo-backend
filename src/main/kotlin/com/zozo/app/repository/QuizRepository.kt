package com.zozo.app.repository

import com.zozo.app.model.Quiz
import org.springframework.data.jpa.repository.JpaRepository

interface QuizRepository : JpaRepository<Quiz, Long> {
    fun findByTaskTaskId(taskId: Long): Quiz?
}