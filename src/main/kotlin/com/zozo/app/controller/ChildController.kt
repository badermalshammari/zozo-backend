package com.zozo.app.controller

import com.zozo.app.model.AccountStats
import com.zozo.app.model.Child
import com.zozo.app.model.GenderType
import com.zozo.app.model.Parent
import com.zozo.app.service.ChildService
import com.zozo.app.service.ParentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/children")
class ChildController(
    private val childService: ChildService,
    private val parentService: ParentService,
    private val passwordEncoder: PasswordEncoder
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

        val hashedPassword = passwordEncoder.encode(childRequest.password)

        val child = Child(
            name = childRequest.name,
            civilId = childRequest.civilId,
            birthday = childRequest.birthday,
            username = childRequest.username,
            password = hashedPassword,
            gender = childRequest.gender,
            avatar = childRequest.avatar,
            parent = parent
        )

        val savedChild = childService.createChildForParent(child, parent)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedChild)
    }
    @PostMapping("/{id}/enable")
    fun enableChildAccount(@PathVariable id: Long): ResponseEntity<Child> {
        return try {
            val updatedChild = childService.changeChildStats(id, AccountStats.ENABLED)
            ResponseEntity.ok(updatedChild)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }
    @PostMapping("/{id}/disable")
    fun disableChildAccount(@PathVariable id: Long): ResponseEntity<Child> {
        return try {
            val updatedChild = childService.changeChildStats(id, AccountStats.DISABLED)
            ResponseEntity.ok(updatedChild)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/{id}/avatar")
    fun updateAvatar(
        @PathVariable id: Long,
        @RequestBody request: AvatarRequest
    ): ResponseEntity<Child> {
        return try {
            val child = childService.getChildById(id) ?: return ResponseEntity.notFound().build()
            val updatedChild = child.copy(avatar = request.avatar)
            val saved = childService.updateChild(updatedChild)
            ResponseEntity.ok(saved)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}

data class AvatarRequest(val avatar: String)

data class ChildRequest(
    val name: String,
    val civilId: String,
    val birthday: LocalDate,
    val username: String,
    val password: String,
    val gender: GenderType,
    val avatar: String,
)
