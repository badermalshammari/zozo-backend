package com.zozo.app.dto

import com.zozo.app.model.AccountStats
import java.time.LocalDate

data class WalletResponseDto(
    val walletId: Long,
    val child: ChildDto,
    val pointsBalance: Int,
    val gems: Int
)

data class ChildDto(
    val childId: Long,
    val name: String,
    val avatar: String,
    val birthday: LocalDate,
    val stats: AccountStats
)

