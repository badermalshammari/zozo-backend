package com.zozo.app.model



import jakarta.persistence.*


@Entity
data class KidTask(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val taskId: Long = 0,

    @ManyToOne
    @JoinColumn(name = "parent_id")
    val parent: Parent,

    val title: String,
    val description: String,
    @Enumerated(EnumType.STRING)
    val type: TaskType, // Educational Video / Task
    val points: Int,
    val gems: Int,
    val videoFilename: String?,
    val coverPicture: String
)

enum class TaskType {
    VEDIO, TASK
}