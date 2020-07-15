package com.github.imtiazea.mywallpapermgr.services

import com.github.imtiazea.mywallpapermgr.dtos.TaskDTO
import com.github.imtiazea.mywallpapermgr.models.Task
import com.github.imtiazea.mywallpapermgr.repo.TaskRepo
import org.springframework.stereotype.Service

@Service
class TaskService(private val taskRepo: TaskRepo, private val wallpaperImageService: WallpaperImageService) {
    fun createUpdateTask(taskDTO: TaskDTO): Task {

        val taskFromDTO = taskDTO.toTask()
        val imageLink = wallpaperImageService.getCombinedImageLinkParts(taskFromDTO)

        val task = taskFromDTO.copy(imageLink = imageLink)

        return taskRepo.save(task)
    }

    fun getTask(taskId: Long): Task {
        return taskRepo.findById(taskId)
                .orElseThrow { RuntimeException("Task Not Found By ID.") }
    }

    fun getTasks(): List<Task> {
        return taskRepo.findAll()
    }
}