package com.zozo.app.auth

import com.zozo.app.service.ChildService
import com.zozo.app.service.ParentService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val parentService: ParentService,
    private val childService: ChildService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val parent = parentService.getParentByUsername(username)
        if (parent != null) {
            return User(
                parent.username,
                parent.password,
                listOf(SimpleGrantedAuthority("ROLE_PARENT"))
            )
        }

        val child = childService.getChildByUsername(username)
        if (child != null) {
            return User(
                child.username,
                child.password,
                listOf(SimpleGrantedAuthority("ROLE_CHILD"))
            )
        }

        throw UsernameNotFoundException("User not found: $username")
    }
}