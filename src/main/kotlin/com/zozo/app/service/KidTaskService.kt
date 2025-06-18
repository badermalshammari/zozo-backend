package com.zozo.app.service

import com.zozo.app.model.KidTask
import com.zozo.app.model.Parent
import com.zozo.app.model.Quiz
import com.zozo.app.model.TaskProgress
import com.zozo.app.model.TaskStatus
import com.zozo.app.model.TaskType
import com.zozo.app.repository.ChildRepository
import com.zozo.app.repository.GlobalEducationalContentRepository
import com.zozo.app.repository.KidTaskRepository
import com.zozo.app.repository.ParentRepository
import com.zozo.app.repository.QuizRepository
import com.zozo.app.repository.TaskProgressRepository
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
        fun getTasksByChildId(childId: Long): List<KidTask> = kidtaskRepository.findAllByChildChildId(childId)
        fun getTasksByParentId(parentId: Long): List<KidTask> = kidtaskRepository.findAllByParentParentId(parentId)


        fun createTask(request: CreateTaskRequest): KidTask {
            val parent = parentRepo.findById(request.parentId).orElseThrow()
            val child = childRepo.findById(request.childId).orElseThrow()
            val video = request.educationalContentId?.let {
                educationalContentRepository.findById(it).orElse(null)
            }

            val task = KidTask(
                parent = parent,
                child = child,
                title = request.title,
                description = request.description,
                type = request.type,
                gems = request.gems,
                globalVideo = video
            )

            val savedTask = kidtaskRepository.save(task)

            val progress = TaskProgress(
                child = child,
                task = savedTask,
                status = TaskStatus.NOT_STARTED,
                progressPercentage = 0,
                currentTimeSeconds = 0,
                earnedPoints = 0,
                earnedGems = 0,
                completedAt = null,
                type = TaskType.TASK)

            taskProgressRepository.save(progress)
            when (task.type) {
                TaskType.QUIZ -> {
                    val quiz = Quiz(
                        task = savedTask,
                        questionText = request.quizQuestion ?: "",
                        optionA = request.optionA ?: "",
                        optionB = request.optionB ?: "",
                        optionC = request.optionC ?: "",
                        optionD = request.optionD ?: "",
                        correctOption = request.correctOption ?: "",
                        title = request.quizTitle,
                    )
                    progress.type = TaskType.QUIZ
                    taskProgressRepository.save(progress)
                    quizRepository.save(quiz)
                }
                TaskType.VIDEO -> {
                    progress.type = TaskType.VIDEO
                    taskProgressRepository.save(progress)
                }
                else -> {} //if it was task
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
