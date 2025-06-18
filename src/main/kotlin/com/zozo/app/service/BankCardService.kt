package com.zozo.app.service

import com.zozo.app.model.BankCard
import com.zozo.app.model.Child
import com.zozo.app.model.Parent
import com.zozo.app.repository.BankCardRepository
import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.ParentRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.random.Random

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
                child = child
            )

            return bankCardRepo.save(card)
        }
    fun createCardForParent(parentId: Long): BankCard {
        val parent = parentRepo.findById(parentId).orElseThrow { IllegalArgumentException("Parent not found") }

        val card = BankCard(
            cardHolderName = parent.name,
            expiryMonth = (1..12).random(),
            expiryYear = (2026..2030).random(),
            parent = parent
        )

        return bankCardRepo.save(card)
    }
     fun topUp(toCardId: Long, amount: BigDecimal): BankCard {
         val toCard = bankCardRepo.findById(toCardId).orElseThrow()
         toCard.balnace = toCard.balnace?.plus(amount)
         return bankCardRepo.save(toCard)
     }

 }