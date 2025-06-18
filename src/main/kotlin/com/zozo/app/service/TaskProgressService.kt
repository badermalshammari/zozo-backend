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
    private val taskProgressRepository: TaskProgressRepository,
    private val walletRepo: WalletRepository,

    ) {
    fun getByChildId(childId: Long): List<TaskProgress> = repo.findByChildChildId(childId)

    fun updateProgress(progress: TaskProgress): TaskProgress {
        if (progress.progressPercentage <= 0) {
            progress.status = TaskStatus.NOT_STARTED
    } else if (progress.progressPercentage > 100) {
            progress.completedAt = LocalDateTime.now()
            progress.status = TaskStatus.IN_PROGRESS
        }else if (progress.progressPercentage == 100){
            progress.completedAt
            progress.status = TaskStatus.FINISHED
        }
        return repo.save(progress)
    }

    fun getByChildAndTask(childId: Long, taskId: Long): TaskProgress? =
        repo.findByChildChildIdAndTaskTaskId(childId, taskId)

    fun create(progress: TaskProgress): TaskProgress = repo.save(progress)

    fun completeTask(childId: Long, taskId: Long): TaskProgress {
        val task = kidtaskRepository.findById(taskId).orElseThrow { IllegalArgumentException("Task not found") }
        var childwallet = walletRepo.findById(childId).orElseThrow { IllegalArgumentException("Wallet not found") }

        val existingProgress = taskProgressRepository.findByChildChildIdAndTaskTaskId(childId, taskId)
            ?: throw IllegalArgumentException("Task progress not found for this child and task.")

        existingProgress.status = TaskStatus.FINISHED
        existingProgress.progressPercentage = 100
        existingProgress.completedAt = LocalDateTime.now()
        existingProgress.earnedGems = task.gems
        childwallet.gems =+ task.gems
        task.points?.let { childwallet.pointsBalance =+ it }
        existingProgress.earnedPoints = task.points!!

        walletRepo.save(childwallet)
        return taskProgressRepository.save(existingProgress)
    }

}