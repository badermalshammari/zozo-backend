package com.zozo.app.service

import com.zozo.app.model.OrderedItem
import com.zozo.app.model.OrderStatus
import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.GlobalStoreItemRepository
import com.zozo.app.repository.OrderedItemRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderedItemService(
    private val orderedItemRepository: OrderedItemRepository,
    private val childRepository: ChildRepository,
    private val itemRepository: GlobalStoreItemRepository
) {
    fun orderItem(childId: Long, itemId: Long): OrderedItem {
        val child = childRepository.findById(childId).orElseThrow()
        val item = itemRepository.findById(itemId).orElseThrow()

        val order = OrderedItem(
            child = child,
            item = item,
            orderedAt = LocalDateTime.now(),
            status = OrderStatus.COMPLETED
        )
        return orderedItemRepository.save(order)
    }

    fun getOrdersForChild(childId: Long): List<OrderedItem> =
        orderedItemRepository.findAllByChildChildId(childId)

    fun getAllOrders(): List<OrderedItem> = orderedItemRepository.findAll()
}