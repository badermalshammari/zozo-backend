package com.zozo.app.controller

import com.zozo.app.model.Transaction
import com.zozo.app.repository.TransactionRepository
import com.zozo.app.service.TransactionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import org.springframework.web.bind.annotation.RequestBody



@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val transactionService: TransactionService,
    private val transactionRepository: TransactionRepository
) {

    @PostMapping("/transfer")
    fun transfer(@RequestBody request: TransferRequest): Transaction {
        return transactionService.transfer(
            fromCardNumber = request.fromCardNumber,
            toCardNumber = request.toCardNumber,
            amount = request.amount,
            description = request.description
        )
    }

    @GetMapping("/card-number/{cardNumber}")
    fun getTransactionsForCardByNumber(@PathVariable cardNumber: String): List<Transaction> {
        return transactionRepository.findByFromCard_CardNumberOrToCard_CardNumber(cardNumber, cardNumber)
    }
}


data class TransferRequest(
    val fromCardNumber: String,
    val toCardNumber: String,
    val amount: BigDecimal,
    val description: String?
)