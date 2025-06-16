package com.zozo.app.controller

import com.zozo.app.model.OrderedItem
import com.zozo.app.model.StoreItem
import com.zozo.app.service.StoreService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/store")

class StoreController(private val storeService: StoreService) {

    @GetMapping("/items")
    fun getItems(): ResponseEntity<List<StoreItem>> =
        ResponseEntity.ok(storeService.getAllItems())

    @PostMapping("/items")
    fun addItem(@RequestBody item: StoreItem): ResponseEntity<StoreItem> =
        ResponseEntity.ok(storeService.addItem(item))

    @PostMapping("/order")
    fun orderItem(@RequestParam childId: Long, @RequestParam itemId: Long): ResponseEntity<OrderedItem> =
        ResponseEntity.ok(storeService.orderItem(childId, itemId))

    @GetMapping("/orders/{childId}")
    fun getOrders(@PathVariable childId: Long): ResponseEntity<List<OrderedItem>> =
        ResponseEntity.ok(storeService.getChildOrders(childId))

}

