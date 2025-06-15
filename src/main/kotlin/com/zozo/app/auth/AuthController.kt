package com.zozo.app.auth

import com.zozo.app.service.ChildService
import com.zozo.app.service.ParentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val authenticationManager: AuthenticationManager,
    private val parentService: ParentService,
    private val childService: ChildService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        val authToken = UsernamePasswordAuthenticationToken(request.username, request.password)
        val authentication: Authentication = authenticationManager.authenticate(authToken)
        SecurityContextHolder.getContext().authentication = authentication

        val parent = parentService.getParentByUsername(request.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val token = authService.generateToken(parent)
        return ResponseEntity.ok(AuthResponse(token))
    }
    @PostMapping("/child-login")
    fun childLogin(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        val authToken = UsernamePasswordAuthenticationToken(request.username, request.password)
        val authentication = try {
            authenticationManager.authenticate(authToken)
        } catch (ex: Exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        SecurityContextHolder.getContext().authentication = authentication

        val child = childService.getChildByUsername(request.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val token = authService.generateTokenForChild(child)
        return ResponseEntity.ok(AuthResponse(token))
    }
}