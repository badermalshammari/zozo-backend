package com.zozo.app.service

import com.zozo.app.dto.LeaderboardEntry
import com.zozo.app.model.Wallet
import com.zozo.app.model.Child
import com.zozo.app.model.Parent
import com.zozo.app.repository.BankCardRepository
import com.zozo.app.repository.WalletRepository
import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.ParentRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class WalletService(
    private val walletRepo: WalletRepository,
    private val childRepo: ChildRepository,
    private val parentRepo: ParentRepository,
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

fun getPointsLeaderboardForParent(parentUsername: String, top: Int): List<LeaderboardEntry> {
    val parent: Parent = parentRepo.findByUsername(parentUsername)
        ?: throw IllegalArgumentException("Parent not found")

    val children = childRepo.findAllByParent_ParentId(parent.parentId)

    return children.mapNotNull { child ->
        walletRepo.findByChild(child)?.let { wallet ->
            LeaderboardEntry(
                childId = child.childId,
                name = child.name,
                avatar = child.avatar,
                points = wallet.pointsBalance
            )
        }
    }.sortedByDescending { it.points }
        .take(top)
}
    fun getPointsLeaderboardForChild(childUsername: String, top: Int): List<LeaderboardEntry> {
        val child = childRepo.findByCivilId(childUsername)
            ?: throw IllegalArgumentException("Child not found")

        val siblings = childRepo.findAllByParent_ParentId(child.parent.parentId)

        return siblings.mapNotNull { sibling ->
            walletRepo.findByChild(sibling)?.let { wallet ->
                LeaderboardEntry(
                    childId = sibling.childId,
                    name = sibling.name,
                    avatar = sibling.avatar,
                    points = wallet.pointsBalance
                )
            }
        }.sortedByDescending { it.points }
            .take(top)
    }

    fun addGemsToChild(childId: Long, gems: Int, parentUsername: String): Wallet {
        if (gems <= 0) {
            throw IllegalArgumentException("Number of gems must be a positive value")
        }

        val child = getChildIfOwnedByParent(childId, parentUsername)

        val wallet = walletRepo.findByChild(child)
            ?: throw Exception("Wallet not found for this child")

        val card = bankCardRepo.findByChild_ChildId(childId)
            ?: throw Exception("Child does not have a bank card")

        val conversionRate = BigDecimal(1000)
        val requiredBalance = BigDecimal(gems).divide(conversionRate)

        if (card.balance < requiredBalance) {
            throw IllegalStateException("Not enough balance in card to convert to gems")
        }

        // Deduct from card, add gems
        card.balance -= requiredBalance
        wallet.gems += gems

        // Save updates
        bankCardRepo.save(card)
        return walletRepo.save(wallet)
    }

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