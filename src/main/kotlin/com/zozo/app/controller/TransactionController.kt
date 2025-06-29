package com.zozo.app.controller

import com.zozo.app.dto.TransactionDto
import com.zozo.app.service.TransactionService
import com.zozo.app.repository.TransactionRepository
import com.zozo.app.service.toDto
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val transactionService: TransactionService,
    private val transactionRepository: TransactionRepository
) {

    @PostMapping("/transfer")
    fun transfer(@RequestBody request: TransferRequest): TransactionDto {
        val transaction = transactionService.transfer(
            fromAccountNumber = request.fromAccountNumber,
            toAccountNumber = request.toAccountNumber,
            amount = request.amount,
            description = request.description
        )
        return transaction.toDto()
    }

    @GetMapping("/card-number/{cardNumber}")
    fun getTransactionsForCardByNumber(@PathVariable cardNumber: String): List<TransactionDto> {
        return transactionRepository
            .findByFromCard_CardNumberOrToCard_CardNumber(cardNumber, cardNumber)
            .map { it.toDto() }
    }
}

data class TransferRequest(
    val fromAccountNumber: Long,
    val toAccountNumber: Long,
    val amount: BigDecimal,
    val description: String? = null
)