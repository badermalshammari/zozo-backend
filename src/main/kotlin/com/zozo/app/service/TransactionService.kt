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

    fun transfer(fromCardId: Long, toCardId: Long, amount: BigDecimal, description: String?): Transaction {
        val fromCard = bankCardRepo.findById(fromCardId).orElseThrow()
        val toCard = bankCardRepo.findById(toCardId).orElseThrow()

        if (fromCard.balnace!! < amount) throw IllegalArgumentException("Insufficient balance")

        fromCard.balnace = fromCard.balnace!!.minus(amount)
        toCard.balnace = toCard.balnace!!.plus(amount)

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