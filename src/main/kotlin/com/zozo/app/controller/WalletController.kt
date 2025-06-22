package com.zozo.app.controller


import com.zozo.app.dto.ChildDto
import com.zozo.app.dto.WalletResponseDto
import com.zozo.app.model.Wallet
import com.zozo.app.service.WalletService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/wallet")
class WalletController(
    private val walletService: WalletService
) {

    @PostMapping("/create/{childId}")
    fun createWallet(@PathVariable childId: Long): ResponseEntity<Wallet> {
        val wallet = walletService.createWalletForChild(childId)
        return ResponseEntity.ok(wallet)
    }

    @PreAuthorize("hasRole('PARENT')")
    @GetMapping("/child/{childId}")
    fun getWalletByChildId(@PathVariable childId: Long): WalletResponseDto {
        val wallet = walletService.getWalletByChildId(childId)
        val child = wallet.child

        return WalletResponseDto(
            walletId = wallet.walletId,
            pointsBalance = wallet.pointsBalance,
            gems = wallet.gems,
            child = ChildDto(
                childId = child.childId,
                name = child.name,
                avatar = child.avatar,
                birthday = child.birthday,
                stats = child.stats
            )
        )
    }

//    @PreAuthorize("hasRole('PARENT')")
//    @PostMapping("/child/{childId}/add-balance")
//    fun addBalanceToChild(
//        @PathVariable childId: Long,
//        @RequestBody request: AddBalanceRequest
//    ): ResponseEntity<Wallet> {
//        val parentUsername = getAuthenticatedUsername() ?: return ResponseEntity.status(401).build()
//
//        return try {
////            val wallet = walletService.addToChildBalance(childId, request.amount, parentUsername)
////            ResponseEntity.ok(wallet)
//        } catch (e: IllegalAccessException) {
//            ResponseEntity.status(403).build()
//        } catch (e: Exception) {
//            ResponseEntity.badRequest().build()
//        }
//    }

    private fun getAuthenticatedUsername(): String? {
        return SecurityContextHolder.getContext().authentication?.name
    }
    @PreAuthorize("hasRole('PARENT')")
    @PostMapping("/child/{childId}/add-gems")
    fun addGemsToChild(
        @PathVariable childId: Long,
        @RequestBody request: AddGemsRequest
    ): ResponseEntity<Wallet> {
        val parentUsername = getAuthenticatedUsername() ?: return ResponseEntity.status(401).build()

        return try {
            val wallet = walletService.addGemsToChild(childId, request.gems, parentUsername)
            ResponseEntity.ok(wallet)
        } catch (e: IllegalAccessException) {
            ResponseEntity.status(403).build()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }
}

data class AddBalanceRequest(
    val amount: Double
)
data class AddGemsRequest(
    val gems: Int
)