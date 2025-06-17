package com.zozo.app.controller

import com.zozo.app.service.ChildLeaderboardDTO
import com.zozo.app.service.LeaderboardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/leaderboard")
class LeaderboardController (
    private val leaderboardService: LeaderboardService,
){

    @GetMapping("/parent/{parentId}")
    fun getParentLeaderboard(
        @PathVariable parentId: Long,
    ): List<ChildLeaderboardDTO>
    {
        return leaderboardService.getLeaderBoardforParent(parentId)
    }

    @GetMapping("/child/{childId}")
    fun getChildLeaderboard(
        @PathVariable childId: Long
    ): List<ChildLeaderboardDTO>
    {
     return leaderboardService.getLeaderboardForChild(childId)
    }
}