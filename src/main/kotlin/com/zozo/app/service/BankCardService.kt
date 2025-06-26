package com.zozo.app.service

import com.zozo.app.dto.BankCardDto
import com.zozo.app.model.BankCard
import com.zozo.app.repository.BankCardRepository
import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.ParentRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BankCardService(
    private val bankCardRepo: BankCardRepository,
    private val childRepo: ChildRepository,
    private val parentRepo: ParentRepository
) {

    fun createCardForChild(childId: Long): BankCard {
        val child = childRepo.findById(childId).orElseThrow { IllegalArgumentException("Child not found") }

        val card = BankCard(
            cardHolderName = child.name,
            expiryMonth = (1..12).random(),
            expiryYear = (2026..2030).random(),
            child = child,
            parent = child.parent,
            isParent = false
        )

        return bankCardRepo.save(card)
    }
    fun createCardForParent(parentId: Long): BankCard {
        val parent = parentRepo.findById(parentId).orElseThrow { IllegalArgumentException("Parent not found") }

        val card = BankCard(
            cardHolderName = parent.name,
            expiryMonth = (1..12).random(),
            expiryYear = (2026..2030).random(),
            parent = parent,
            isParent = true

        )

        return bankCardRepo.save(card)
    }
    fun topUp(toCardId: Long, amount: BigDecimal): BankCard {
        val toCard = bankCardRepo.findById(toCardId).orElseThrow()
        toCard.balance = toCard.balance?.plus(amount)!!
        return bankCardRepo.save(toCard)
    }
    fun getParentCard(parentId: Long): List<BankCard> {
        return bankCardRepo.findAllByParent_ParentId(parentId)
    }
    fun getChildCardIfOwnedByParent(childId: Long, parentId: Long): BankCard {
        val card = bankCardRepo.findByChild_ChildId(childId)
            ?: throw IllegalArgumentException("Card not found for childId: $childId")

        if (card.child?.parent?.parentId != parentId) {
            throw IllegalAccessException("Access denied: This child does not belong to you")
        }

        return card
    }
    fun mapToDto(card: BankCard): BankCardDto {
        return BankCardDto(
            cardId = card.cardId,
            cardNumber = card.cardNumber,
            accountNumber = card.accountNumber,
            cardHolderName = card.cardHolderName,
            expiryMonth = card.expiryMonth,
            expiryYear = card.expiryYear,
            cvv = card.cvv,
            balance = card.balance,
            cardDesign = card.cardDesign,
            isParentCard = card.child == null,
            childId = card.child?.childId        )
    }


}