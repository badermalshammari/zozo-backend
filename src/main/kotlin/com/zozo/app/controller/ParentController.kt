package com.zozo.app.controller

import com.zozo.app.model.Child
import com.zozo.app.model.Parent
import com.zozo.app.model.TaskType
import com.zozo.app.service.ParentService
import org.springframework.context.annotation.Description
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/parents")
class ParentController(
    private val parentService: ParentService
) {

    @PostMapping("/register")
    fun registerParent(@RequestBody request: CreateParentRequest): ResponseEntity<Parent> {
        return try {
            val savedParent = parentService.createParent(
                name = request.name,
                username = request.username,
                password = request.password,
                phoneNumber = request.phoneNumber
            )
            ResponseEntity(savedParent, HttpStatus.CREATED)
        } catch (_: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @PostMapping("/addToDoTask")
    fun addToDoTask(@RequestBody request: AddToDoTaskRequest): ResponseEntity<AddToDoTaskResponse>{

        return try {
            ResponseEntity.ok(parentService.addToDoTask(request))
        }catch (_: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

//    @PostMapping("addContant")
//    fun addContantToChild(@RequestBody request: AddContantToChildRequest): ResponseEntity<AddContantToChildResponse>{
//        return try {
//            ResponseEntity.ok(parentService.addContantToChild(request))
//        }catch (_: IllegalArgumentException) {
//            ResponseEntity.status(HttpStatus.CONFLICT).build()
//        }
//    }
}

data class CreateParentRequest(
    val name: String,
    val username: String,
    val password: String,
    val phoneNumber: String
)

data class AddToDoTaskRequest(
    val type: TaskType,
    val title: String,
    val description: String,
    val gems: Int,
    val points: Int,
    val parent: Long,
    val childId: Long,
    val coverPicture: String
)

data class AddToDoTaskResponse(
    val type: TaskType,
    val title: String,
    val description: String,
    val gems: Int,
    val points: Int,
    val parent: String,
    val childname: String,
    val coverPicture: String,
    val status: String
)

data class AddContantToChildRequest(
    val type: TaskType,
    val videoFilename: String,
    val title: String,
    val description: String,
    val gems: Int,
    val points: Int,
    val parent: Long,
    val childId: Long,
    val coverPicture: String
)

data class AddContantToChildResponse(
    val type: TaskType,
    val title: String,
    val videoFilename: String,
    val description: String,
    val gems: Int,
    val points: Int,
    val parent: String,
    val childname: String,
    val coverPicture: String,
    val status: String
)