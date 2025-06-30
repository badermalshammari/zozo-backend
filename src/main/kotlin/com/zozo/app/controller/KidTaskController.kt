package com.zozo.app.controller

import com.zozo.app.dto.KidTaskDto
import com.zozo.app.dto.TaskProgressDto
import com.zozo.app.model.TaskProgress
import com.zozo.app.service.KidTaskService
import com.zozo.app.service.TaskProgressService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class KidTaskController(
    private val service: KidTaskService,
    private val taskProgressService: TaskProgressService
) {

    @GetMapping("/child/{childId}")
    fun getByChild(@PathVariable childId: Long): List<KidTaskDto> {
        val tasks = service.getTasksByChildId(childId)
        val progressMap = taskProgressService.getByChildId(childId)
            .associateBy { it.task.taskId }

        return tasks.map { task ->
            val progress = progressMap[task.taskId]
            KidTaskDto(
                taskId = task.taskId,
                title = task.title,
                description = task.description,
                type = task.type.name,
                points = task.points,
                gems = task.gems,
                childName = task.child.name,
                videoTitle = task.globalVideo?.title,
                youtubeUrl = task.globalVideo?.youtubeUrl, // ✅ added
                status = progress?.status?.name ?: "NOT_STARTED"
            )
        }
    }

    @GetMapping("/parent/{parentId}")
    fun getByParent(@PathVariable parentId: Long): List<KidTaskDto> {
        val tasks = service.getTasksByParentId(parentId)
        val progressList = tasks.mapNotNull { task ->
            taskProgressService.getByChildAndTask(task.child.childId, task.taskId)
        }
        val progressMap = progressList.associateBy { it.task.taskId }

        return tasks.map { task ->
            val progress = progressMap[task.taskId]
            KidTaskDto(
                taskId = task.taskId,
                title = task.title,
                description = task.description,
                type = task.type.name,
                points = task.points,
                gems = task.gems,
                childName = task.child.name,
                videoTitle = task.globalVideo?.title,
                youtubeUrl = task.globalVideo?.youtubeUrl, // ✅ added
                status = progress?.status?.name ?: "NOT_STARTED"
            )
        }
    }

    @PostMapping
    fun create(@RequestBody request: KidTaskService.CreateTaskRequest): KidTaskDto {
        val task = service.createTask(request)
        return KidTaskDto(
            taskId = task.taskId,
            title = task.title,
            description = task.description,
            type = task.type.name,
            points = task.points,
            gems = task.gems,
            childName = task.child.name,
            videoTitle = task.globalVideo?.title,
            youtubeUrl = task.globalVideo?.youtubeUrl, // ✅ added
            status = "NOT_STARTED"
        )
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): KidTaskDto {
        val task = service.getById(id)
        val progress = taskProgressService.getByChildAndTask(task.child.childId, id)

        return KidTaskDto(
            taskId = task.taskId,
            title = task.title,
            description = task.description,
            type = task.type.name,
            points = task.points,
            gems = task.gems,
            childName = task.child.name,
            videoTitle = task.globalVideo?.title,
            youtubeUrl = task.globalVideo?.youtubeUrl, // ✅ added
            status = progress?.status?.name ?: "NOT_STARTED"
        )
    }

    @PostMapping("/complete")
    fun markTaskAsFinished(
        @RequestParam childId: Long,
        @RequestParam taskId: Long
    ): ResponseEntity<TaskProgressDto> {
        val updated = taskProgressService.completeTask(childId, taskId)

        val dto = TaskProgressDto(
            taskProgressId = updated.taskProgressId,
            taskId = updated.task.taskId,
            childId = updated.child.childId,
            status = updated.status?.name ?: "UNKNOWN",
            progressPercentage = updated.progressPercentage,
            earnedPoints = updated.earnedPoints,
            earnedGems = updated.earnedGems,
            completedAt = updated.completedAt?.toString()
        )

        return ResponseEntity.ok(dto)
    }
}