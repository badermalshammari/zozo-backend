package com.zozo.app.repository

import com.zozo.app.model.KidTask
import org.springframework.data.jpa.repository.JpaRepository

interface KidTaskRepository: JpaRepository<KidTask,Long> {
}



