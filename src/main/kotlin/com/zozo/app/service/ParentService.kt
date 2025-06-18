package com.zozo.app.service

import com.zozo.app.model.Parent
import com.zozo.app.repository.ParentRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class ParentService(
    private val parentRepo: ParentRepository,
    private val passwordEncoder: PasswordEncoder,
    private val cardService: BankCardService

) {

    fun createParent(name: String, username: String, password: String, phoneNumber: String): Parent {
        if (parentRepo.findByUsername(username) != null) {
            throw IllegalArgumentException("Username already exists")
        }

        val encodedPassword = passwordEncoder.encode(password)

        val savedParent = parentRepo.save(
            Parent(
                name = name,
                username = username,
                password = encodedPassword,
                phoneNumber = phoneNumber
            )
        )
        cardService.createCardForParent(savedParent.parentId)

        return savedParent
    }
}