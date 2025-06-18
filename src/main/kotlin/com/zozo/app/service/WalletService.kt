package com.zozo.app.service

import com.zozo.app.model.Wallet
import com.zozo.app.model.Child
import com.zozo.app.repository.BankCardRepository
import com.zozo.app.repository.WalletRepository
import com.zozo.app.repository.ChildRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class WalletService(
    private val walletRepo: WalletRepository,
    private val childRepo: ChildRepository,
    private val bankCardRepo: BankCardRepository,
) {
    fun createWalletForChild(childId: Long): Wallet {
        val child = childRepo.findById(childId).orElseThrow {
            Exception("Child not found with ID: $childId")
        }

        if (walletRepo.findByChild(child) != null) {
            throw Exception("Wallet already exists for this child")
        }

        val wallet = Wallet(
            child = child,
            pointsBalance = 0,
            gems = 0
        )
        return walletRepo.save(wallet)
    }

    fun getWalletByChildId(childId: Long): Wallet {
        val child = childRepo.findById(childId).orElseThrow {
            Exception("Child not found with ID: $childId")
        }

        return walletRepo.findByChild(child)
            ?: throw Exception("Wallet not found for child ID: $childId")
    }

    fun getWalletIfOwnedByParent(childId: Long, parentUsername: String): Wallet {
        val child = getChildIfOwnedByParent(childId, parentUsername)
        return walletRepo.findByChild(child)
            ?: throw Exception("Wallet not found for this child")
    }

//    fun addToChildBalance(childId: Long, amount: Double, parentUsername: String): Wallet {
//        val wallet = getWalletIfOwnedByParent(childId, parentUsername)
//        val amountBD = BigDecimal.valueOf(amount)
//        wallet.balance = wallet.balance?.add(amountBD)
//
//        wallet.card?.let {
//            it.balance = it.balance?.add(amountBD)
//        }
//
//        return walletRepo.save(wallet)
//    }

    fun addGemsToChild(childId: Long, gems: Int, parentUsername: String): Wallet {
        val wallet = getWalletIfOwnedByParent(childId, parentUsername)
        val updatedWallet = wallet.copy(gems = wallet.gems + gems)
        return walletRepo.save(updatedWallet)
    }

    // ✅ helper method to avoid repetition
    private fun getChildIfOwnedByParent(childId: Long, parentUsername: String): Child {
        val child = childRepo.findById(childId).orElseThrow {
            Exception("Child not found with ID: $childId")
        }

        if (child.parent.username != parentUsername) {
            throw IllegalAccessException("Unauthorized access to this child's wallet")
        }

        return child
    }
}