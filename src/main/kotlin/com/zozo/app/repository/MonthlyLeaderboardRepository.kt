package com.zozo.app.repository

import com.zozo.app.model.MonthlyLeaderboardEntry
import jakarta.inject.Named
import org.springframework.data.jpa.repository.JpaRepository

@Named
interface MonthlyLeaderboardRepository: JpaRepository<MonthlyLeaderboardEntry, Long> {
    fun findByChild_ChildId(childId: Long): MonthlyLeaderboardEntry?
    fun findAllByChildParentParentIdOrderByRank(parentId: Long): List<MonthlyLeaderboardEntry>
}
