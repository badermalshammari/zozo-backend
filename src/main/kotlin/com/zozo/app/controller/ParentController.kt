package com.zozo.app.controller

import com.zozo.app.auth.AuthResponse
import com.zozo.app.auth.JwtService
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
    private val parentRepo: ParentRepository,
    private val jwtService: JwtService

) {


    @PostMapping("/register")
    fun registerParent(@RequestBody request: CreateParentRequest): ResponseEntity<out Any?> {

        val savedParent = parentService.createParent(
                name = request.name,
                username = request.username,
                password = request.password,
                phoneNumber = request.phoneNumber
            )

            val token = jwtService.generateToken(savedParent.username,  "PARENT")

            val response = AuthResponse(token = token, parent = savedParent)
            return ResponseEntity.ok(response)
    }
    @GetMapping("/me")
    fun getMe(@AuthenticationPrincipal user: UserDetails?): ResponseEntity<ParentDTO> {
        println("🔒 JWT user from token: $user")

        if (user == null) {
            println("❌ No user found in SecurityContext")
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val parent = parentRepo.findByUsername(user.username)
        if (parent == null) {
            println("❌ No parent found with username: ${user.username}")
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        println("✅ Found parent: ${parent.username}")
        val dto = ParentDTO(
            parentId = parent.parentId,
            name = parent.name,
            username = parent.username,
            phoneNumber = parent.phoneNumber
        )
        return ResponseEntity.ok(dto)
    }

    data class CreateParentRequest(
        val name: String,
        val username: String,
        val password: String,
        val phoneNumber: String
    )
    data class ParentDTO(
        val parentId: Long,
        val name: String,
        val username: String,
        val phoneNumber: String
    )
}