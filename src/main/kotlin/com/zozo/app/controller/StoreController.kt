package com.zozo.app.controller

import com.zozo.app.dto.OrderedItemDto
import com.zozo.app.model.GlobalStoreItem
import com.zozo.app.model.OrderedItem
import com.zozo.app.service.StoreService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/store")
class StoreController(private val storeService: StoreService) {

    @GetMapping("/items")
    fun getItems(): ResponseEntity<List<GlobalStoreItem>> =
        ResponseEntity.ok(storeService.getAllItems())

    @PostMapping("/items/add")
    fun addItem(@RequestBody item: GlobalStoreItem): ResponseEntity<GlobalStoreItem> =
        ResponseEntity.ok(storeService.addItem(item))

    @PostMapping("/items/order")
    fun orderItem(
        @RequestParam childId: Long,
        @RequestParam childStoreItemId: Long
    ): ResponseEntity<OrderedItemDto> {
        val order = storeService.orderItem(childId, childStoreItemId)
        return ResponseEntity.ok(order.toDto())

    }

    @GetMapping("/orders/{childId}")
    fun getOrders(@PathVariable childId: Long): ResponseEntity<List<OrderedItemDto>> {
        val orders = storeService.getChildOrders(childId)
        return ResponseEntity.ok(orders.map { it.toDto() })
    }
}


fun OrderedItem.toDto(): OrderedItemDto {
    return OrderedItemDto(
        orderId = this.orderId,
        itemName = this.item.name,
        itemImageUrl = this.item.photo.toString(),
        childName = this.child.name,
        gemsCost = this.gemsCost,
        status = this.status.toString(),
        orderedAt = this.orderedAt.toString()
    )
}

