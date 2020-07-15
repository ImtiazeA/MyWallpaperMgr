package com.github.imtiazea.mywallpapermgr.repo

import com.github.imtiazea.mywallpapermgr.models.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TaskRepo : JpaRepository<Task, Long> {

//    @Query("")
//    fun findCurrentTask(): Task

}