package com.zozo.app.service

import com.zozo.app.model.OrderedItem
import com.zozo.app.model.StoreItem
import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.OrderedItemRepository
import com.zozo.app.repository.StoreItemRepository
import com.zozo.app.repository.WalletRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StoreService(
    private val storeItemRepo: StoreItemRepository,
    private val orderedItemRepo: OrderedItemRepository,
    private val walletRepo: WalletRepository,
    private val childRepo: ChildRepository
) {
    //this function returns all store items (to show items to the child)
    fun getAllItems(): List<StoreItem> = storeItemRepo.findAll()

    //this function adds a new item to the store (by parent)
    fun addItem(item: StoreItem): StoreItem = storeItemRepo.save(item)


    //here the child orders an item from the store
    fun orderItem(childId: Long, itemId: Long): OrderedItem {
        val child = childRepo.findById(childId).orElseThrow { Exception("Child not found") } // Get the child who is making the order
        val item = storeItemRepo.findById(itemId).orElseThrow { Exception("Item not found") } // Get the item the child wants to order
        val wallet = walletRepo.findByChild(child) ?: throw Exception("Wallet not found") // Get the child’s wallet to check how many gems they have

        //check if the child has enough gems to buy the item
        if (wallet.gems < item.costInGems)
            throw Exception("Not enough gems")

        //deduct the items gem cost from the wallet
        wallet.gems -= item.costInGems
        walletRepo.save(wallet) //wallet with fewer gems

        //now we create a new OrderedItem  to record this order
        //this links the child and the item they chose, sets the status to "ordered" and saves the current time so we know exactly when the order was made
        val order = OrderedItem(
            child = child,
            item = item,
            status = "ordered",
            orderedAt = LocalDateTime.now()
        )
        return orderedItemRepo.save(order)
    }

    //this function is used to get all orders made by a specific child
    fun getChildOrders(childId: Long): List<OrderedItem> {
        //first find the child
        val child = childRepo.findById(childId).orElseThrow()
        //then return the orders that belongs to this child
        return orderedItemRepo.findAll().filter { it.child == child }
    }
}
