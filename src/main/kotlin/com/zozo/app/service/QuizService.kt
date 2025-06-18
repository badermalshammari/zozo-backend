package com.zozo.app.service

import com.zozo.app.model.*
import com.zozo.app.repository.*
import org.springframework.stereotype.Service

@Service
class QuizService(
    private val quizRepo: QuizRepository,
    private val quizAttemptRepo: QuizAttemptRepository,
    private val childRepo: ChildRepository,
    private val kidTaskRepository: KidTaskRepository,
    private val taskProgressRepository: TaskProgressRepository,
    private val taskProgressService: TaskProgressService,
    private val walletService: WalletService
) {

    fun getQuizByTaskId(taskId: Long): Quiz? =
        quizRepo.findByTaskTaskId(taskId)

    fun createQuizFromRequest(request: CreateQuizRequest): Quiz {
        val task = kidTaskRepository.findById(request.taskId).orElseThrow()

        val quiz = Quiz(
            task = task,
            questionText = request.questionText,
            optionA = request.optionA,
            optionB = request.optionB,
            optionC = request.optionC,
            optionD = request.optionD,
            correctOption = request.correctOption,
            title = request.title
        )

        return quizRepo.save(quiz)
    }

    fun submitQuizAttempt(childId: Long, quizId: Long, selectedAnswer: String): QuizAttempt {
        val existingAttempt = quizAttemptRepo.findByQuizQuizIdAndChildChildId(childId, quizId)
        if (existingAttempt != null) {
            throw IllegalStateException("Child has already attempted this quiz.")
        }

        val child = childRepo.findById(childId).orElseThrow()
        val quiz = quizRepo.findById(quizId).orElseThrow()
        val task = quiz.task

        val isCorrect = selectedAnswer == quiz.correctOption

        val earnedPoints = if (isCorrect) task.points ?: 0 else 0
        val earnedGems = if (isCorrect) task.gems else 0


        walletService.getWalletByChildId(childId).pointsBalance += earnedPoints
        walletService.getWalletByChildId(childId).gems += earnedGems

        childRepo.save(child)

        val progress = taskProgressRepository.findByChildChildIdAndTaskTaskId(childId, quiz.task.taskId)
        if (progress != null) {
            progress.earnedPoints += earnedPoints
            progress.earnedGems += earnedGems
            if (isCorrect) {
                progress.status = TaskStatus.FINISHED
                progress.progressPercentage = 100
                taskProgressService.completeTask(childId, progress.task.taskId)
            }
            taskProgressRepository.save(progress)
        }

        val attempt = QuizAttempt(
            child = child,
            quiz = quiz,
            score = if (isCorrect) 100 else 0
        )

        return quizAttemptRepo.save(attempt)
    }

    fun getAttemptsForChild(childId: Long): List<QuizAttempt> =
        quizAttemptRepo.findByChildChildId(childId)
        }

data class CreateQuizRequest(
    val taskId: Long,
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOption: String,
    val title: String
)