package com.zozo.app.controller

import com.zozo.app.dto.BankCardDto
import com.zozo.app.model.BankCard
import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.ParentRepository
import com.zozo.app.service.BankCardService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/cards")
class BankCardController(
    private val service: BankCardService,
    private val childRepo: ChildRepository,
    private val parentRepo: ParentRepository
) {

    @PostMapping("/child/{childId}")
    fun createChildCard(@PathVariable childId: Long): BankCard {
        val child = childRepo.findById(childId).orElseThrow { IllegalArgumentException("Child not found") }
        return service.createCardForChild(child.childId)
    }


    @PostMapping("/parent/{parentId}/new")
    fun createAdditionalParentCard(@PathVariable parentId: Long): BankCardDto {
        val parent = parentRepo.findById(parentId)
            .orElseThrow { IllegalArgumentException("Parent not found") }

        val savedCard = service.createCardForParent(parent.parentId)
        return service.mapToDto(savedCard)
    }

    @PostMapping("/topup")
    fun topUpCard(
        @RequestParam toCardId: Long,
        @RequestParam amount: BigDecimal
    ): BankCard {
        return service.topUp(toCardId, amount)
    }
    @GetMapping("/parent/{parentId}")
    fun getParentCards(@PathVariable parentId: Long): List<BankCardDto> {
        val cards = service.getParentCard(parentId)
        return cards.map { service.mapToDto(it) }
    }
    @GetMapping("/child/{childId}")
    fun getChildCard(
        @PathVariable childId: Long,
        @AuthenticationPrincipal userDetails: UserDetails
    ): BankCardDto {
        val parent = parentRepo.findByUsername(userDetails.username)
            ?: throw IllegalArgumentException("Parent not found")

        val card = service.getChildCardIfOwnedByParent(childId, parent.parentId)
        return service.mapToDto(card)
    }
}