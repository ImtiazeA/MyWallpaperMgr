package com.github.imtiazea.mywallpapermgr.services

import com.github.imtiazea.mywallpapermgr.dtos.TaskDTO
import com.github.imtiazea.mywallpapermgr.models.Task
import java.time.LocalTime

fun Task.toTaskDTO() = TaskDTO(id, description, startTime, endTime, imageLink)
fun TaskDTO.toTask() = Task(
        id,
        description ?: "Task Description",
        startTime ?: LocalTime.now(),
        endTime ?: LocalTime.now(),
        imageLink ?: ""
)