package com.zozo.app.controller

import com.zozo.app.dto.KidTaskDto
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
        return service.getTasksByChildId(childId).map {
            KidTaskDto(
                taskId = it.taskId,
                title = it.title,
                description = it.description,
                type = it.type.name,
                points = it.points,
                gems = it.gems,
                childName = it.child.name,
                videoTitle = it.globalVideo?.title
            )
        }
    }

    @GetMapping("/parent/{parentId}")
    fun getByParent(@PathVariable parentId: Long): List<KidTaskDto> {
        return service.getTasksByParentId(parentId).map {
            KidTaskDto(
                taskId = it.taskId,
                title = it.title,
                description = it.description,
                type = it.type.name,
                points = it.points,
                gems = it.gems,
                childName = it.child.name,
                videoTitle = it.globalVideo?.title
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
            videoTitle = task.globalVideo?.title
        )
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): KidTaskDto {
        val task = service.getById(id)
        return KidTaskDto(
            taskId = task.taskId,
            title = task.title,
            description = task.description,
            type = task.type.name,
            points = task.points,
            gems = task.gems,
            childName = task.child.name,
            videoTitle = task.globalVideo?.title
        )
    }

    @PostMapping("/complete")
    fun markTaskAsFinished(
        @RequestParam childId: Long,
        @RequestParam taskId: Long
    ): ResponseEntity<TaskProgress> {
        val updated = taskProgressService.completeTask(childId, taskId)
        return ResponseEntity.ok(updated)
    }
}