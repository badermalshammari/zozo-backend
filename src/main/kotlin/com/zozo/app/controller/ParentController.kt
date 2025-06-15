package com.zozo.app.controller

import com.zozo.app.model.Parent
import com.zozo.app.service.ParentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ParentController(private val parentService: ParentService) {

    @GetMapping("parents/getAll")
    fun getAllParents(): ResponseEntity<List<Parent>> =
        ResponseEntity.ok(parentService.getAllParents())

    @GetMapping("parents/{id}")
    fun getParentById(@PathVariable id: Long): ResponseEntity<Parent> {
        val parent = parentService.getParentById(id)
        return if (parent != null) ResponseEntity.ok(parent)
        else ResponseEntity.notFound().build()
    }

    @PostMapping("auth/register")
    fun createParent(@RequestBody parent: Parent): ResponseEntity<Parent> =
        ResponseEntity.ok(parentService.createParent(parent))

    @DeleteMapping("parents/{id}")
    fun deleteParent(@PathVariable id: Long): ResponseEntity<Void> {
        parentService.deleteParent(id)
        return ResponseEntity.noContent().build()
    }
}