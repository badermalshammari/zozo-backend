package com.zozo.app.service

import com.zozo.app.model.TaskProgress
import com.zozo.app.model.TaskStatus
import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.KidTaskRepository
import com.zozo.app.repository.ParentRepository
import com.zozo.app.repository.TaskProgressRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TaskProgressService(
    private val repo: TaskProgressRepository,
    private val kidtaskRepository: KidTaskRepository,
    private val parentRepo: ParentRepository,
    private val taskProgressRepository: TaskProgressRepository
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
    
    fun completeTaskByParent(parentId: Long, childId: Long, taskId: Long): TaskProgress {
        val parent = parentRepo.findById(parentId).orElseThrow { IllegalArgumentException("Parent not found") }
        val task = kidtaskRepository.findById(taskId).orElseThrow { IllegalArgumentException("Task not found") }

        if (task.parent.parentId != parent.parentId || task.child.childId != childId) {
            throw IllegalAccessException("Unauthorized: Task does not belong to this parent or child")
        }

        val existingProgress = taskProgressRepository.findByChildChildIdAndTaskTaskId(childId, taskId)
            ?: throw IllegalArgumentException("Task progress not found for this child and task.")

        existingProgress.status = TaskStatus.FINISHED
        existingProgress.progressPercentage = 100
        existingProgress.completedAt = java.time.LocalDateTime.now()

        return taskProgressRepository.save(existingProgress)
    }
}