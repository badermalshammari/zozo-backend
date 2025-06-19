package com.zozo.app.auth

import com.zozo.app.model.Parent
import com.zozo.app.repository.ParentRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val parentRepository: ParentRepository,
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        return try {
            val response = authService.login(request)
            ResponseEntity.ok(response)
        } catch (ex: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

}
    data class AuthRequest(val username: String, val password: String)
    data class AuthResponse(val token: String, val parent: Parent)

