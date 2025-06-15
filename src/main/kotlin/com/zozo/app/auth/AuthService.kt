package com.zozo.app.auth

import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.ParentRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val parentRepo: ParentRepository,
    private val childRepo: ChildRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(authRequest: AuthRequest): AuthResponse {
        val username = authRequest.username
        val password = authRequest.password

        // 1️⃣ Try parent login
        val parent = parentRepo.findByUsername(username)
        if (parent != null && passwordEncoder.matches(password, parent.password)) {
            val token = jwtService.generateToken(username, "PARENT")
            return AuthResponse(token)
        }

        // 2️⃣ Try child login
        val child = childRepo.findByUsername(username)
        if (child != null && passwordEncoder.matches(password, child.password)) {
            val token = jwtService.generateToken(username, "CHILD")
            return AuthResponse(token)
        }

        // ❌ If both fail
        throw IllegalArgumentException("Invalid username or password")
    }
}