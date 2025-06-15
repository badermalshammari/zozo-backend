package com.zozo.app.auth

import com.zozo.app.model.Parent
import com.zozo.app.repository.ParentRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val parentRepository: ParentRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val parent: Parent = parentRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Parent not found with username: $username")

        return object : UserDetails {
            override fun getUsername(): String = parent.username
            override fun getPassword(): String = parent.password

            override fun getAuthorities(): Collection<GrantedAuthority> =
                listOf(SimpleGrantedAuthority("ROLE_PARENT"))

            override fun isAccountNonExpired(): Boolean = true
            override fun isAccountNonLocked(): Boolean = true
            override fun isCredentialsNonExpired(): Boolean = true
            override fun isEnabled(): Boolean = true
        }
    }
}