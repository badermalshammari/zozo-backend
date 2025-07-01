package com.zozo.app.model



import jakarta.persistence.*


@Entity
@Table(name = "task")
data class KidTask(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val taskId: Long = 0,

    @ManyToOne
    @JoinColumn(name = "parent_id")
    val parent: Parent,

    @ManyToOne
    @JoinColumn(name = "child_id")
    val child: Child,

    val title: String,
    val description: String,

    @Enumerated(EnumType.STRING)
    val type: TaskType,

    val points: Int? = if (type == TaskType.TASK) 50 else if (type == TaskType.QUIZ) 50 else 100,
    var gems: Int = 0,

    @ManyToOne
    @JoinColumn(name = "educationalcontent_id")
    val globalVideo: GlobalEducationalContent? = null
)

enum class TaskType {
    TASK,QUIZ,VIDEO
}