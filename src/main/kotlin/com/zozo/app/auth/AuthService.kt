package com.zozo.app.auth

import com.zozo.app.model.Child
import com.zozo.app.model.Parent
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtService: JwtService
) {
    fun generateToken(parent: Parent): String {
        val extraClaims = mapOf(
            "role" to "PARENT",
            "userId" to parent.parentId
        )
        return jwtService.generateToken(extraClaims, parent.username)
    }
    fun generateTokenForChild(child: Child): String {
        val extraClaims = mapOf(
            "role" to "CHILD",
            "userId" to child.childId
        )
        return jwtService.generateToken(extraClaims, child.username)
    }
}