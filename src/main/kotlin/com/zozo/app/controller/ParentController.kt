package com.zozo.app.controller

import com.zozo.app.model.Parent
import com.zozo.app.repository.ParentRepository
import com.zozo.app.service.ParentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/auth")
class ParentController(
    private val parentService: ParentService,
    private val parentReop: ParentRepository
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

    @GetMapping("/me")
    fun getMe(@AuthenticationPrincipal user: UserDetails): ResponseEntity<Parent> {
        val parent = parentReop.findByUsername(user.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        return ResponseEntity.ok(parent)
    }

    data class CreateParentRequest(
        val name: String,
        val username: String,
        val password: String,
        val phoneNumber: String
    )
}