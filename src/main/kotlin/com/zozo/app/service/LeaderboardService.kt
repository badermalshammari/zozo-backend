package com.zozo.app.service

import com.zozo.app.repository.MonthlyLeaderboardRepository
import org.springframework.stereotype.Service

@Service
class LeaderboardService(
    private val monthlyLeaderboardRepository: MonthlyLeaderboardRepository,
)
{

    fun getLeaderBoardforParent(parentId: Long): List<ChildLeaderboardDTO> {
        return monthlyLeaderboardRepository.findAllByChildParentParentIdOrderByRank(parentId)
            .map {
                ChildLeaderboardDTO(
                    rank = it.rank,
                    name = it.name,
                    points = it.totalScore
                )
            }
    }

    fun getLeaderboardForChild(childId: Long): List<ChildLeaderboardDTO> {
        val entry = monthlyLeaderboardRepository.findByChild_ChildId(childId)
            ?: throw IllegalArgumentException("child not found")

        val parentId = entry.child.parent.parentId

        return monthlyLeaderboardRepository.findAllByChildParentParentIdOrderByRank(parentId)
            .map {
                ChildLeaderboardDTO(
                    rank = it.rank,
                    name = it.name,
                    points = it.totalScore
                )
            }
    }
}

data class ChildLeaderboardDTO(
    val rank: Int,
    val name: String,
    val points: Int
)