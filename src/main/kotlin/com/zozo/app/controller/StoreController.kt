package com.zozo.app.controller

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
    fun orderItem(@RequestParam childId: Long, @RequestParam itemId: Long): ResponseEntity<OrderedItem> =
        ResponseEntity.ok(storeService.orderItem(childId, itemId))

    @GetMapping("/orders/{childId}")
    fun getOrders(@PathVariable childId: Long): ResponseEntity<List<OrderedItem>> =
        ResponseEntity.ok(storeService.getChildOrders(childId))

}

