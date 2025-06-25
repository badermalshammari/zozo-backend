package com.zozo.app.service

import com.zozo.app.model.*
import com.zozo.app.repository.*
import org.springframework.stereotype.Service

@Service
class KidTaskService(
    private val kidtaskRepository: KidTaskRepository,
    private val parentRepo: ParentRepository,
    private val childRepo: ChildRepository,
    private val educationalContentRepository: GlobalEducationalContentRepository,
    private val quizRepository: QuizRepository,
    private val taskProgressRepository: TaskProgressRepository
) {

    fun getTasksByChildId(childId: Long): List<KidTask> =
        kidtaskRepository.findAllByChildChildId(childId)

    fun getTasksByParentId(parentId: Long): List<KidTask> =
        kidtaskRepository.findAllByParentParentId(parentId)

    fun createTask(request: CreateTaskRequest): KidTask {
        val parent = parentRepo.findById(request.parentId).orElseThrow()
        val child = childRepo.findById(request.childId).orElseThrow()

        // Handle video task content if applicable
        val video = request.educationalContentId?.let {
            educationalContentRepository.findById(it).orElse(null)
        }

        // Use video title/description if it's a VIDEO task
        val finalTitle = if (request.type == TaskType.VIDEO) {
            video?.title ?: throw IllegalArgumentException("Video not found")
        } else {
            request.title
        }

        val finalDescription = if (request.type == TaskType.VIDEO) {
            video?.description ?: ""
        } else {
            request.description
        }

        // Save the task
        val task = KidTask(
            parent = parent,
            child = child,
            title = finalTitle,
            description = finalDescription,
            type = request.type,
            gems = request.gems,
            globalVideo = video
        )

        val savedTask = kidtaskRepository.save(task)

        // Save progress
        val progress = TaskProgress(
            child = child,
            task = savedTask,
            status = TaskStatus.NOT_STARTED,
            progressPercentage = 0,
            currentTimeSeconds = 0,
            earnedPoints = 0,
            earnedGems = 0,
            completedAt = null,
            type = request.type
        )
        taskProgressRepository.save(progress)

        // Special logic for QUIZ
        if (request.type == TaskType.QUIZ) {
            val quiz = Quiz(
                task = savedTask,
                questionText = request.quizQuestion ?: "",
                optionA = request.optionA ?: "",
                optionB = request.optionB ?: "",
                optionC = request.optionC ?: "",
                optionD = request.optionD ?: "",
                correctOption = request.correctOption ?: "",
                title = request.quizTitle
            )
            quizRepository.save(quiz)
        }

        return savedTask
    }

    fun getById(id: Long): KidTask = kidtaskRepository.findById(id).orElseThrow()

    data class CreateTaskRequest(
        val parentId: Long,
        val childId: Long,
        val title: String,
        val description: String,
        val type: TaskType,
        val gems: Int,
        val educationalContentId: Long? = null,
        val quizQuestion: String? = null,
        val optionA: String? = null,
        val optionB: String? = null,
        val optionC: String? = null,
        val optionD: String? = null,
        val correctOption: String? = null,
        val quizTitle: String? = null
    )
}