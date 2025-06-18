package com.zozo.app.repository

import com.zozo.app.model.TaskProgress
import org.springframework.data.jpa.repository.JpaRepository

interface TaskProgressRepository : JpaRepository<TaskProgress, Long> {
    fun findByChildChildId(childId: Long): List<TaskProgress>
    fun findByChildChildIdAndTaskTaskId(childId: Long, taskId: Long): TaskProgress?
}