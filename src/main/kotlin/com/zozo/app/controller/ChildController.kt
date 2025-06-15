package com.zozo.app.controller

import com.zozo.app.model.AccountStats
import com.zozo.app.model.Child
import com.zozo.app.model.GenderType
import com.zozo.app.service.ChildService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/children")
class ChildController(
    private val childService: ChildService
) {

    @PreAuthorize("hasRole('PARENT')")
    @PostMapping("/create")
    fun createChild(@RequestBody request: CreateChildRequest): ResponseEntity<Child> {
        val parentUsername = getAuthenticatedUsername() ?: return ResponseEntity.status(401).build()

        return try {
            val child = childService.createChild(request, parentUsername)
            ResponseEntity.ok(child)
        } catch (ex: IllegalArgumentException) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @PreAuthorize("hasRole('PARENT')")
    @GetMapping
    fun getChildren(): ResponseEntity<List<Child>> {
        val parentUsername = getAuthenticatedUsername() ?: return ResponseEntity.status(401).build()

        return try {
            val children = childService.getChildrenForParent(parentUsername)
            ResponseEntity.ok(children)
        } catch (ex: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PreAuthorize("hasRole('PARENT')")
    @PostMapping("/{id}/enable")
    fun enableChildAccount(@PathVariable id: Long): ResponseEntity<Child> {
        val parentUsername = getAuthenticatedUsername() ?: return ResponseEntity.status(401).build()

        return try {
            val updatedChild = childService.changeChildStats(id, AccountStats.ENABLED, parentUsername)
            ResponseEntity.ok(updatedChild)
        } catch (e: IllegalAccessException) {
            ResponseEntity.status(403).build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PreAuthorize("hasRole('PARENT')")
    @PostMapping("/{id}/disable")
    fun disableChildAccount(@PathVariable id: Long): ResponseEntity<Child> {
        val parentUsername = getAuthenticatedUsername() ?: return ResponseEntity.status(401).build()

        return try {
            val updatedChild = childService.changeChildStats(id, AccountStats.DISABLED, parentUsername)
            ResponseEntity.ok(updatedChild)
        } catch (e: IllegalAccessException) {
            ResponseEntity.status(403).build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PreAuthorize("hasRole('PARENT')")
    @PostMapping("/{id}/avatar")
    fun updateAvatar(
        @PathVariable id: Long,
        @RequestBody request: AvatarRequest
    ): ResponseEntity<Child> {
        val parentUsername = getAuthenticatedUsername() ?: return ResponseEntity.status(401).build()

        return try {
            val child = childService.getChildById(id) ?: return ResponseEntity.notFound().build()
            val updatedChild = child.copy(avatar = request.avatar)
            val saved = childService.updateChild(updatedChild, parentUsername)
            ResponseEntity.ok(saved)
        } catch (e: IllegalAccessException) {
            ResponseEntity.status(403).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    private fun getAuthenticatedUsername(): String? {
        return SecurityContextHolder.getContext().authentication?.name
    }
}

data class AvatarRequest(val avatar: String)

data class CreateChildRequest(
    val name: String,
    val civilId: String,
    val birthday: LocalDate,
    val username: String,
    val password: String,
    val gender: GenderType
)