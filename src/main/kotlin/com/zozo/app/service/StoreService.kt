package com.zozo.app.service

import com.zozo.app.model.GlobalStoreItem
import com.zozo.app.model.OrderStatus
import com.zozo.app.model.OrderedItem
import com.zozo.app.repository.ChildRepository
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
    private val childRepo: ChildRepository
) {
    //this function returns all store items (to show items to the child)
    fun getAllItems(): List<GlobalStoreItem> = storeItemRepo.findAll()

    //this function adds a new item to the store (by parent)
    fun addItem(item: GlobalStoreItem): GlobalStoreItem = storeItemRepo.save(item)


    //here the child orders an item from the store
    fun orderItem(childId: Long, itemId: Long): OrderedItem {
        val child = childRepo.findById(childId).orElseThrow { Exception("Child not found") } // Get the child who is making the order
        val item = storeItemRepo.findById(itemId).orElseThrow { Exception("Item not found") } // Get the item the child wants to order
        val wallet = walletRepo.findByChild(child) ?: throw Exception("Wallet not found") // Get the child’s wallet to check how many gems they have

        //check if the child has enough gems to buy the item
        if (wallet.gems < item.costInGems)
            throw Exception("Not enough gems")

        wallet.gems -= item.costInGems
        walletRepo.save(wallet) //wallet with fewer gems

        val order = OrderedItem(
            child = child,
            item = item,
            status = OrderStatus.COMPLETED,
            orderedAt = LocalDateTime.now(),
            gemsCost =  item.costInGems
        )
        return orderedItemRepo.save(order)
    }

    fun getChildOrders(childId: Long): List<OrderedItem> {
        val child = childRepo.findById(childId).orElseThrow()
        return orderedItemRepo.findAll().filter { it.child == child }
    }
}
