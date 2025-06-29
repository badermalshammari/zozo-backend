package com.zozo.app.controller

import com.zozo.app.dto.LeaderboardEntry
import com.zozo.app.model.OrderedItem
import com.zozo.app.model.TotalLeaderboardEntry
import com.zozo.app.service.WalletService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/leaderboard")
class LeaderboardController(
    private val walletService: WalletService,

) {
    @GetMapping("/top")
    @PreAuthorize("hasAnyRole('PARENT', 'CHILD')")
    fun getPointsLeaderboard(
        @RequestParam(defaultValue = "10") top: Int
    ): ResponseEntity<List<LeaderboardEntry>> {

        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name
        val role = auth.authorities.first().authority

        val leaderboard = when (role) {
            "ROLE_PARENT" -> walletService.getPointsLeaderboardForParent(username, top)
            "ROLE_CHILD" -> walletService.getPointsLeaderboardForChild(username, top)
            else -> throw IllegalAccessException("Unauthorized role")
        }

        return ResponseEntity.ok(leaderboard)
    }
    @GetMapping("/{childId}")
    @PreAuthorize("hasAnyRole('PARENT', 'CHILD')")
    fun getChildLeaderboard(
        @PathVariable childId: Long
    ): ResponseEntity<List<LeaderboardEntry>> {

        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name
        val role = auth.authorities.first().authority

        val leaderboard = when (role) {
            "ROLE_PARENT" -> walletService.getPointsLeaderboardForParent(username, 10)
            "ROLE_CHILD" -> walletService.getPointsLeaderboardForChild(username, 10)
            else -> throw IllegalAccessException("Unauthorized role")
        }

        return ResponseEntity.ok(leaderboard)

        }
}
