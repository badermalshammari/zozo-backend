package com.zozo.app.controller

import com.zozo.app.model.OrderedItem
import com.zozo.app.service.OrderedItemService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderedItemController(private val service: OrderedItemService) {

    @PostMapping("/child/{childId}/item/{itemId}")
    fun orderItem(
        @PathVariable childId: Long,
        @PathVariable itemId: Long
    ): OrderedItem = service.orderItem(childId, itemId)

    @GetMapping("/child/{childId}")
    fun getChildOrders(@PathVariable childId: Long): List<OrderedItem> =
        service.getOrdersForChild(childId)

    @GetMapping
    fun getAllOrders(): List<OrderedItem> = service.getAllOrders()
}
