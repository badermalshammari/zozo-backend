package com.zozo.app.service

import com.zozo.app.model.ChildStoreItem
import com.zozo.app.repository.ChildStoreItemRepository
import org.springframework.stereotype.Service

@Service
class ChildStoreItemService(
    private val repository: ChildStoreItemRepository
) {

    fun getItemsForChild(childId: Long): List<ChildStoreItem> {
        return repository.findAllByChild_ChildId(childId)
    }

    fun toggleItemVisibility(childStoreItemId: Long): ChildStoreItem {
        val item = repository.findById(childStoreItemId).orElseThrow()
        item.isHidden = !item.isHidden
        return repository.save(item)
    }

}