package com.zozo.app.controller

import com.zozo.app.model.Quiz
import com.zozo.app.service.CreateQuizRequest
import com.zozo.app.service.QuizService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quiz")
class QuizController(
    private val quizService: QuizService
) {

    @PostMapping("/create")
    fun createQuiz(@RequestBody request: CreateQuizRequest): ResponseEntity<Quiz> {
        val quiz = quizService.createQuizFromRequest(request)
        return ResponseEntity.ok(quiz)
    }

    @GetMapping("/task/{taskId}")
    fun getQuizByTask(@PathVariable taskId: Long): ResponseEntity<Quiz?> {
        return ResponseEntity.ok(quizService.getQuizByTaskId(taskId))
    }

    @GetMapping("/attempts/{childId}")
    fun getAttemptsForChild(@PathVariable childId: Long): ResponseEntity<Any> {
        return ResponseEntity.ok(quizService.getAttemptsForChild(childId))
    }

    @PostMapping("/submit")
    fun submitAttempt(
        @RequestParam childId: Long,
        @RequestParam quizId: Long,
        @RequestParam score: String
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(quizService.submitQuizAttempt(childId, quizId, score))
    }
}
