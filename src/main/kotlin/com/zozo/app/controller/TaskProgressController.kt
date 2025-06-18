package com.zozo.app.controller

import com.zozo.app.model.TaskProgress
import com.zozo.app.service.TaskProgressService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/task-progress")
class TaskProgressController(
    private val service: TaskProgressService
) {
    @GetMapping("/child/{childId}")
    fun getByChild(@PathVariable childId: Long): List<TaskProgress> = service.getByChildId(childId)

    @PostMapping
    fun create(@RequestBody progress: TaskProgress): TaskProgress = service.create(progress)

    @PutMapping
    fun update(@RequestBody progress: TaskProgress): TaskProgress = service.updateProgress(progress)

    @GetMapping("/child/{childId}/task/{taskId}")
    fun getByChildAndTask(@PathVariable childId: Long, @PathVariable taskId: Long): TaskProgress? =
        service.getByChildAndTask(childId, taskId)
}
