package com.zozo.app.auth

import com.zozo.app.repository.ParentRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtService: JwtService,
    private val accessDeniedHandler: CustomAccessDeniedHandler
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager

    @Bean
    fun filterChain(http: HttpSecurity, userDetailsService: UserDetailsService): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/auth/**", "/api/auth/**").permitAll()
                    .requestMatchers("/api/children/create").hasRole("PARENT")
                    .requestMatchers("/api/child/**").hasRole("CHILD")
                    .anyRequest().authenticated()
            }
            .exceptionHandling {
                it.accessDeniedHandler(accessDeniedHandler)
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(JwtAuthFilter(jwtService, userDetailsService), UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun userDetailsService(userRepository: ParentRepository): UserDetailsService {
        return UserDetailsService { username ->
            val user = userRepository.findByUsername(username)
                ?: throw UsernameNotFoundException("User not found: $username") as Throwable
            org.springframework.security.core.userdetails.User(
                user.username,
                user.password,
                listOf(SimpleGrantedAuthority("ROLE_PARENT"))
            )
        }
    }
}