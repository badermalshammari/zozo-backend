package com.zozo.app.auth

import com.zozo.app.service.ParentService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val parentService: ParentService
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val parent = parentService.getParentByUsername(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        return User(
            parent.username,
            parent.password,
            listOf(org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_PARENT"))
        )
    }
}