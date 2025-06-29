package com.zozo.app.service

import com.zozo.app.controller.CreateChildRequest
import com.zozo.app.model.AccountStats
import com.zozo.app.model.Child
import com.zozo.app.model.ChildStoreItem
import com.zozo.app.model.GenderType
import com.zozo.app.model.Parent
import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.ChildStoreItemRepository
import com.zozo.app.repository.ParentRepository
import com.zozo.app.repository.GlobalStoreItemRepository
import org.springframework.stereotype.Service

@Service
class ChildService(
    private val childRepo: ChildRepository,
    private val parentRepo: ParentRepository,
    private val walletService: WalletService,
    private val globalStoreItemRepository: GlobalStoreItemRepository,
    private val childStoreItemRepository: ChildStoreItemRepository,
    private val cardService: BankCardService
) {

    fun createChild(request: CreateChildRequest, parentUsername: String): Child {
        val parent: Parent = parentRepo.findByUsername(parentUsername)
            ?: throw IllegalArgumentException("Parent not found")

        if (childRepo.findByCivilId(request.civilId) != null) {
            throw IllegalArgumentException("Child Civil ID already exists")
        }

        val child = Child(
            name = request.name,
            civilId = request.civilId,
            birthday = request.birthday,
            gender = request.gender,
            avatar = if (request.gender == GenderType.ZAINAH) "zainah.png" else "zain.png",
            parent = parent
        )

        val savedChild = childRepo.save(child)

        val globalItems = globalStoreItemRepository.findAll()
        val childStoreItems = globalItems.map { globalItem ->
            ChildStoreItem(
                child = savedChild,
                globalItem = globalItem,
                isHidden = false,
                wishList = false
            )
        }
        cardService.createCardForChild(savedChild.childId)
        walletService.createWalletForChild(savedChild.childId)
        childStoreItemRepository.saveAll(childStoreItems)

        return savedChild
    }

    fun getChildrenForParent(parentUsername: String): List<Child> {
        val parent = parentRepo.findByUsername(parentUsername)
            ?: throw IllegalArgumentException("Parent not found")

        return childRepo.findAllByParent_ParentId(parent.parentId)
    }

    fun updateChild(child: Child, parentUsername: String): Child {
        verifyChildOwnership(child.childId, parentUsername)
        return childRepo.save(child)
    }

    fun getChildById(id: Long): Child? =
        childRepo.findById(id).orElse(null)

    fun changeChildStats(childId: Long, stats: AccountStats, parentUsername: String): Child {
        val child = childRepo.findById(childId).orElseThrow { IllegalArgumentException("Child not found") }
        verifyChildOwnership(childId, parentUsername)
        val updatedChild = child.copy(stats = stats)
        return childRepo.save(updatedChild)
    }

    private fun verifyChildOwnership(childId: Long, parentUsername: String) {
        val child = childRepo.findById(childId).orElseThrow { IllegalArgumentException("Child not found") }
        val parent = parentRepo.findByUsername(parentUsername)
            ?: throw IllegalArgumentException("Parent not found")

        if (child.parent.parentId != parent.parentId) {
            throw IllegalAccessException("Unauthorized access to this child")
        }
    }
}