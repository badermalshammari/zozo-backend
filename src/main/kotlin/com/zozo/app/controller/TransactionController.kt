package com.zozo.app.controller

import com.zozo.app.model.Transaction
import com.zozo.app.repository.TransactionRepository
import com.zozo.app.service.TransactionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val transactionService: TransactionService,
    private val transactionRepository: TransactionRepository
) {
    @PostMapping("/transfer")
    fun transfer(
        @RequestParam fromCardId: Long,
        @RequestParam toCardId: Long,
        @RequestParam amount: BigDecimal,
        @RequestParam description: String
    ): Transaction {
        return transactionService.transfer(fromCardId, toCardId, amount, description)
    }

    @GetMapping("/card/{cardId}")
    fun getTransactionsForCard(@PathVariable cardId: Long): List<Transaction> {
        return transactionRepository.findByFromCardCardIdOrToCardCardId(cardId, cardId)
    }
}