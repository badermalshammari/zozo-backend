package com.zozo.app.controller


import com.zozo.app.model.Child
import com.zozo.app.model.GenderType
import com.zozo.app.service.ChildService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/children")
class ChildController(
    private val childService: ChildService
) {

    @PostMapping("/create")
    fun createChild(@RequestBody request: CreateChildRequest): ResponseEntity<Child> {
        val auth = SecurityContextHolder.getContext().authentication
        val parentUsername = auth?.name ?: return ResponseEntity.status(401).build()

        return try {
            val child = childService.createChild(request, parentUsername)
            ResponseEntity.ok(child)
        } catch (ex: IllegalArgumentException) {
            ResponseEntity.badRequest().body(null)
        }
    }
    @GetMapping
    fun getChildren(): ResponseEntity<List<Child>> {
        val auth = SecurityContextHolder.getContext().authentication
        val parentUsername = auth?.name ?: return ResponseEntity.status(401).build()

        return try {
            val children = childService.getChildrenForParent(parentUsername)
            ResponseEntity.ok(children)
        } catch (ex: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}



data class CreateChildRequest(
    val name: String,
    val civilId: String,
    val birthday: LocalDate,
    val username: String,
    val password: String,
    val gender: GenderType
)