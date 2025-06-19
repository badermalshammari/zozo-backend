package com.zozo.app.auth

import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.ParentRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val parentRepo: ParentRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(authRequest: AuthRequest): AuthResponse {
        val username = authRequest.username
        val password = authRequest.password

        val parent = parentRepo.findByUsername(username)
        if (parent != null && passwordEncoder.matches(password, parent.password)) {
            val token = jwtService.generateToken(username, "PARENT")
            return AuthResponse(token, parent)
        }
        throw IllegalArgumentException("Invalid username or password")
    }
}