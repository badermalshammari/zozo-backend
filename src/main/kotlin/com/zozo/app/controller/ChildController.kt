package com.zozo.app.controller

import com.zozo.app.model.Child
import com.zozo.app.service.ChildService
import com.zozo.app.service.ParentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/children")
class ChildController(
    private val childService: ChildService,
    private val parentService: ParentService
) {

    @GetMapping
    fun getMyChildren(authentication: Authentication): ResponseEntity<List<Child>> {
        val parentUsername = authentication.name
        val parent = parentService.getParentByUsername(parentUsername)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val children = childService.getChildrenByParentId(parent.parentId)
        return ResponseEntity.ok(children)
    }

    @GetMapping("/{id}")
    fun getChildById(@PathVariable id: Long): ResponseEntity<Child> {
        val child = childService.getChildById(id)
        return if (child != null) ResponseEntity.ok(child)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createChild(
        @RequestBody childRequest: ChildRequest,
        authentication: Authentication
    ): ResponseEntity<Child> {
        val parentUsername = authentication.name
        val parent = parentService.getParentByUsername(parentUsername)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val child = Child(
            name = childRequest.name,
            civilId = childRequest.civilId,
            birthday = childRequest.birthday,
            username = childRequest.username,
            password = childRequest.password,
            gender = childRequest.gender,
            avatar = childRequest.avatar,
            parent = parent
        )

        val savedChild = childService.createChildForParent(child, parent)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedChild)
    }
}

data class ChildRequest(
    val name: String,
    val civilId: String,
    val birthday: LocalDate,
    val username: String,
    val password: String,
    val gender: String,
    val avatar: String
)