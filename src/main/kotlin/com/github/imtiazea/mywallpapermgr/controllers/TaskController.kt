package com.github.imtiazea.mywallpapermgr.controllers

import com.github.imtiazea.mywallpapermgr.dtos.TaskDTO
import com.github.imtiazea.mywallpapermgr.services.TaskService
import com.github.imtiazea.mywallpapermgr.services.WallpaperImageService
import com.github.imtiazea.mywallpapermgr.services.toTaskDTO
import org.springframework.web.bind.annotation.*
import java.time.LocalTime
import javax.annotation.PostConstruct

@RestController
class TaskController(private val taskService: TaskService, private val wallpaperImageService: WallpaperImageService) {

    @PostMapping("task")
    fun createUpdateTask(@RequestBody taskDTO: TaskDTO): TaskDTO {

        val createUpdateTask = taskService.createUpdateTask(taskDTO)

        return createUpdateTask.toTaskDTO()
    }

    @GetMapping("task")
    fun getTask(@RequestBody taskDTO: TaskDTO, @RequestParam taskId: Long): TaskDTO {

        val task = taskService.getTask(taskId)

        return task.toTaskDTO()
    }

    @GetMapping("tasks")
    fun getTask(): List<TaskDTO> {

        val tasks = taskService.getTasks()

        val taskDTOS = tasks
                .map { task -> task.toTaskDTO() }
                .toList()

        return taskDTOS
    }

    @PostMapping("tasks/images")
    fun createWallpapersForTasks() {

        wallpaperImageService.makeWallpaperImage()

    }

    @PostMapping("tasks/images/set")
    fun setWallpapersForTasks() {

        val imageFilePath = "C:\\Library\\MyWallpaperMgr\\1.jpg"

        wallpaperImageService.setImageAsWallpaper(imageFilePath)

    }

//    @PostConstruct
//    fun addTask() {
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//        taskService.createUpdateTask(TaskDTO(null, "Task Description", LocalTime.now(), LocalTime.now(), ""))
//    }
}