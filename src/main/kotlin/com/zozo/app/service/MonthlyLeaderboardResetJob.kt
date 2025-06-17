package com.zozo.app.service

import com.zozo.app.repository.MonthlyLeaderboardRepository
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
@EnableScheduling
class MonthlyLeaderboardResetJob (
    val monthlyLeaderboardRepository: MonthlyLeaderboardRepository)
{
    @Scheduled(cron = "0 0 0 1 * ?")
    fun resetMonthlyLeaderboard() {
        val allEntries = monthlyLeaderboardRepository.findAll()
        allEntries.forEach {
            it.rank = 0
            it.totalScore = 0
        }
        monthlyLeaderboardRepository.saveAll(allEntries)
    }
}