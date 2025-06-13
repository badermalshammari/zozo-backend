package com.zozo.app.controller

import com.zozo.app.model.Parent
import com.zozo.app.service.ParentService
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class RegisterController(
    private val parentService: ParentService,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun registerParent(@RequestBody parent: Parent): ResponseEntity<Parent> {
        val existing = parentService.getParentByUsername(parent.username)
        if (existing != null) {
            return ResponseEntity.badRequest().body(null) // username taken
        }

        val encodedParent = parent.copy(password = passwordEncoder.encode(parent.password))
        val savedParent = parentService.createParent(encodedParent)

        return ResponseEntity.ok(savedParent)
    }
}