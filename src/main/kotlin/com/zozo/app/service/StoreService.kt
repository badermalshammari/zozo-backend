package com.zozo.app.service

import com.zozo.app.model.GlobalStoreItem
import com.zozo.app.model.OrderStatus
import com.zozo.app.model.OrderedItem
import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.ChildStoreItemRepository
import com.zozo.app.repository.OrderedItemRepository
import com.zozo.app.repository.GlobalStoreItemRepository
import com.zozo.app.repository.WalletRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StoreService(
    private val storeItemRepo: GlobalStoreItemRepository,
    private val orderedItemRepo: OrderedItemRepository,
    private val walletRepo: WalletRepository,
    private val childRepo: ChildRepository,
    private val childStoreItemRepo: ChildStoreItemRepository
) {
    //this function returns all store items (to show items to the child)
    fun getAllItems(): List<GlobalStoreItem> = storeItemRepo.findAll()

    //this function adds a new item to the store (by parent)
    fun addItem(item: GlobalStoreItem): GlobalStoreItem = storeItemRepo.save(item)


    //here the child orders an item from the store
    fun orderItem(childId: Long, childStoreItemId: Long): OrderedItem {
        val child = childRepo.findById(childId).orElseThrow { Exception("Child not found") }

        val childStoreItem = childStoreItemRepo.findById(childStoreItemId)
            .orElseThrow { Exception("Child store item not found") }

        if (childStoreItem.child.childId != childId)
            throw Exception("This item does not belong to this child")

        if (childStoreItem.isHidden)
            throw Exception("This item is currently hidden")

        val item = childStoreItem.globalItem

        val wallet = walletRepo.findByChild(child) ?: throw Exception("Wallet not found")

        if (wallet.gems < item.costInGems)
            throw Exception("Not enough gems")

        wallet.gems -= item.costInGems
        walletRepo.save(wallet)

        val order = OrderedItem(
            child = child,
            item = item,
            status = OrderStatus.COMPLETED,
            orderedAt = LocalDateTime.now(),
            gemsCost = item.costInGems
        )

        return orderedItemRepo.save(order)
    }

    fun getChildOrders(childId: Long): List<OrderedItem> {
        val child = childRepo.findById(childId).orElseThrow()
        return orderedItemRepo.findAll().filter { it.child == child }
    }
}
