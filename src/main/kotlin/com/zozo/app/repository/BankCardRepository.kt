package com.zozo.app.repository

import com.zozo.app.model.BankCard
import org.springframework.data.jpa.repository.JpaRepository

interface BankCardRepository : JpaRepository<BankCard, Long>{
    fun findByChild_ChildId(childId: Long): BankCard?
    fun findAllByParent_ParentId(parentId: Long): List<BankCard>
}