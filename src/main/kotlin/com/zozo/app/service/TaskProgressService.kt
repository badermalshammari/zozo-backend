package com.zozo.app.service

import com.zozo.app.model.TaskProgress
import com.zozo.app.model.TaskStatus
import com.zozo.app.repository.KidTaskRepository
import com.zozo.app.repository.ParentRepository
import com.zozo.app.repository.TaskProgressRepository
import com.zozo.app.repository.WalletRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TaskProgressService(
    private val repo: TaskProgressRepository,
    private val kidtaskRepository: KidTaskRepository,
    private val parentRepo: ParentRepository,
    private val walletRepo: WalletRepository,
) {

    fun getByChildId(childId: Long): List<TaskProgress> =
        repo.findByChildChildId(childId)

    fun updateProgress(progress: TaskProgress): TaskProgress {
        when {
            progress.progressPercentage <= 0 -> {
                progress.status = TaskStatus.NOT_STARTED
            }
            progress.progressPercentage in 1..99 -> {
                progress.status = TaskStatus.IN_PROGRESS
            }
            progress.progressPercentage >= 100 -> {
                progress.status = TaskStatus.FINISHED
                progress.completedAt = LocalDateTime.now()
            }
        }
        return repo.save(progress)
    }

    fun getByChildAndTask(childId: Long, taskId: Long): TaskProgress? =
        repo.findByChildChildIdAndTaskTaskId(childId, taskId)

    fun create(progress: TaskProgress): TaskProgress = repo.save(progress)

    fun completeTask(childId: Long, taskId: Long): TaskProgress {
        val task = kidtaskRepository.findById(taskId)
            .orElseThrow { IllegalArgumentException("Task not found") }

        val childWallet = walletRepo.findById(childId)
            .orElseThrow { IllegalArgumentException("Wallet not found") }

        val existingProgress = repo.findByChildChildIdAndTaskTaskId(childId, taskId)
            ?: throw IllegalArgumentException("Task progress not found for this child and task.")
        if (existingProgress.status == TaskStatus.FINISHED) {
            throw IllegalStateException("Task has already been completed.")
        }

        existingProgress.status = TaskStatus.FINISHED
        existingProgress.progressPercentage = 100
        existingProgress.completedAt = LocalDateTime.now()
        existingProgress.earnedGems = task.gems
        existingProgress.earnedPoints = task.points ?: 0

        childWallet.gems += task.gems
        task.points?.let { childWallet.pointsBalance += it }

        walletRepo.save(childWallet)
        return repo.save(existingProgress)
    }
}