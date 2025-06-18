package com.zozo.app.controller

import com.zozo.app.model.KidTask
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
    fun getByChild(@PathVariable childId: Long): List<KidTask> = service.getTasksByChildId(childId)

    @GetMapping("/parent/{parentId}")
    fun getByParent(@PathVariable parentId: Long): List<KidTask> = service.getTasksByParentId(parentId)

    @PostMapping
    fun create(@RequestBody request: KidTaskService.CreateTaskRequest): KidTask {
        return service.createTask(request)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): KidTask = service.getById(id)


    @PostMapping("/complete")
    fun markTaskAsFinished(
        @RequestParam childId: Long,
        @RequestParam taskId: Long
    ): ResponseEntity<TaskProgress> {
        val updated = taskProgressService.completeTask(childId, taskId)
        return ResponseEntity.ok(updated)
    }
}

