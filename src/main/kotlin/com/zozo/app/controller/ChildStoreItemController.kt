package com.zozo.app.controller

import com.zozo.app.model.ChildStoreItem
import com.zozo.app.service.ChildStoreItemService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/children/{childId}")
class ChildStoreItemController(
    private val service: ChildStoreItemService
) {

    @GetMapping("/store")
    fun getItems(@PathVariable childId: Long): List<ChildStoreItem> {
        return service.getItemsForChild(childId)
    }

    @PostMapping("/item/{childStoreItemId}/toggle")
    fun toggleVisibility(@PathVariable childStoreItemId: Long): ChildStoreItem {
        return service.toggleItemVisibility(childStoreItemId)
    }

    @PostMapping("/item/{childStoreItemId}/wishlist/toggle")
    fun toggleWishList(@PathVariable childStoreItemId: Long): ChildStoreItem {
        return service.toggleItemWishList(childStoreItemId)
    }
}