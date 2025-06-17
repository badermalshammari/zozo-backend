package com.zozo.app.service

import com.zozo.app.controller.AddContantToChildRequest
import com.zozo.app.controller.AddContantToChildResponse
import com.zozo.app.controller.AddToDoTaskRequest
import com.zozo.app.controller.AddToDoTaskResponse
import com.zozo.app.model.*
import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.KidTaskRepository
import com.zozo.app.repository.ParentRepository
import com.zozo.app.repository.TaskProgressRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ParentService(
    private val parentRepo: ParentRepository,
    private val passwordEncoder: PasswordEncoder,
    private val childRepository: ChildRepository,
    private val kidTaskRepository: KidTaskRepository,
    private val taskProgressRepository: TaskProgressRepository
) {

    fun createParent(name: String, username: String, password: String, phoneNumber: String): Parent {
        if (parentRepo.findByUsername(username) != null) {
            throw IllegalArgumentException("Username already exists")
        }

        val encodedPassword = passwordEncoder.encode(password)

        val parent = Parent(
            name = name,
            username = username,
            password = encodedPassword,
            phoneNumber = phoneNumber
        )

        return parentRepo.save(parent)
    }

    fun addToDoTask(request: AddToDoTaskRequest): AddToDoTaskResponse{

       val child= childRepository.findByChildId(request.childId) ?: throw IllegalArgumentException("Child Not found")
        val parent =parentRepo.findByParentId(request.parent) ?: throw IllegalArgumentException("Parent Not found")
        if (child.parent == parent )
        {
            val task = KidTask(
                parent = parent,
                title = request.title,
                description = request.description,
                type = TaskType.TASK,
                points = request.points,
                gems = request.gems,
                videoFilename = "",
                coverPicture = request.coverPicture
            )
            kidTaskRepository.save(task)

            val taskProgress = TaskProgress(
                child = child,
                task = task,
                status = StatusType.NEW,
                progressPercentage = 0,
                currentTimeSeconds = 0 ,
                earnedGems = 0,
                earnedPoints = 0,
                completedAt = LocalDateTime.now()
            )
            taskProgressRepository.save(taskProgress)
            val response = AddToDoTaskResponse(
                type = request.type,
                title = request.title,
                description = request.description,
                points = request.points,
                gems = request.gems,
                parent = parent.name,
                childname = child.name,
                coverPicture = request.coverPicture,
                status = StatusType.NEW.toString()

            )
            return response

        }else{
            throw IllegalArgumentException("Your not his parent")
        }
    }

//    fun addContantToChild(request: AddContantToChildRequest): AddContantToChildResponse{
//
//    }
}