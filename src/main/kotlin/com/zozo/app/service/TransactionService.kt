package com.zozo.app.service

import com.zozo.app.model.Transaction
import com.zozo.app.repository.BankCardRepository
import com.zozo.app.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class TransactionService(
    private val transactionRepo: TransactionRepository,
    private val bankCardRepo: BankCardRepository
) {

    fun transfer(fromCardNumber: String, toCardNumber: String, amount: BigDecimal, description: String?): Transaction {
        val fromCard = bankCardRepo.findByCardNumber(fromCardNumber)
            ?: throw IllegalArgumentException("From card not found")

        val toCard = bankCardRepo.findByCardNumber(toCardNumber)
            ?: throw IllegalArgumentException("To card not found")

        if (amount <= BigDecimal.ZERO) {
            throw IllegalArgumentException("Amount must be positive")
        }

        if (fromCard.balance < amount) {
            throw IllegalArgumentException("Insufficient balance")
        }

        fromCard.balance = fromCard.balance.minus(amount)
        toCard.balance = toCard.balance.plus(amount)

        bankCardRepo.save(fromCard)
        bankCardRepo.save(toCard)

        val transaction = Transaction(
            fromCard = fromCard,
            toCard = toCard,
            amount = amount,
            timestamp = LocalDateTime.now(),
            description = description
        )

        return transactionRepo.save(transaction)
    }

    fun getAll(): List<Transaction> = transactionRepo.findAll()


}