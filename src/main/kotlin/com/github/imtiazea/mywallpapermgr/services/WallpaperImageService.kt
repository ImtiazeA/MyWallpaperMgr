package com.github.imtiazea.mywallpapermgr.services

import com.github.imtiazea.mywallpapermgr.models.Task
import com.github.imtiazea.mywallpapermgr.repo.TaskRepo
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class WallpaperImageService(private val taskRepo: TaskRepo,
                            private val windowsNativeWallpaperService: WindowsNativeWallpaperService,
                            private val fakeImageRestTemplate: RestTemplate) {

    private val imageLinkRoot = "https://fakeimg.pl/1920x1080/?text="
    //    private val newLine = "%0A"
    private val newLine = "\n"
    private val imageLocation = "C:\\Library\\MyWallpaperMgr\\"


    public fun getCombinedImageLinkParts(task: Task): String {

        val (formattedTime, timeStringLength) = getTimeDescription(task)
        val (formattedDescription, descriptionLength) = getTaskDescription(task)

        val timeString = getAppendedLine(descriptionLength, formattedTime)
        val description = getAppendedLine(timeStringLength, formattedDescription)

        return getCombinedImageLinkParts(timeString, description)
    }

    private fun getCombinedImageLinkParts(timeString: String, description: String): String {
        val textParamString = timeString + newLine + description
        return imageLinkRoot + textParamString
    }

    private fun getTaskDescription(task: Task): Pair<String, Int> {
        // calculate description string length
        val description = task.description
        val descriptionLength = description.length
        return Pair(description, descriptionLength)
    }

    private fun getTimeDescription(task: Task): Pair<String, Int> {
        // calculate timeString string length
        val format = DateTimeFormatter.ofPattern("h:mm a")
        val startTime = task.startTime.format(format)
        val endTime = task.endTime.format(format)
        val timeString = "$startTime - $endTime"
        val timeStringLength = timeString.length
        return Pair(timeString, timeStringLength)
    }

    private fun getAppendedLine(stringLengthToMatch: Int, lineToAppend: String): String {

        val lineLength = lineToAppend.length

        return if (stringLengthToMatch > lineLength) {
            val spaceOnEachSide = (stringLengthToMatch - lineLength)
            wrapStringWithSpaces(lineToAppend, spaceOnEachSide)
        } else {
            lineToAppend
        }

    }

    private fun wrapStringWithSpaces(lineToAppend: String?, spaceCount: Int): String {
        val stringBuilder = StringBuilder()

        for (i in 1..spaceCount) {
            stringBuilder.append(" ")
        }

        stringBuilder.append(lineToAppend)

        for (i in 1..spaceCount) {
            stringBuilder.append(" ")
        }

        return stringBuilder.toString()
    }

    fun makeWallpaperImage() {

        val tasks = taskRepo.findAll()

        tasks.map { task -> downloadWalpaperImage(task) }

    }

    private fun downloadWalpaperImage(task: Task) {

        val responseEntity = fakeImageRestTemplate.exchange(task.imageLink, HttpMethod.GET, null, ByteArray::class.java)

        Files.write(Paths.get(imageLocation + task.id + ".jpg"), responseEntity.body)

    }

    public fun setImageAsWallpaper(imageFilePath: String) {
        windowsNativeWallpaperService.setWallpaper(imageFilePath)
    }

    @Scheduled(fixedRate = 3_00_000) // 3_00_000 ms -> 5 minutes
    private fun changeWallpaperForCurrentTask() {

        val tasks = taskRepo.findAll()

        try {
            val currentTask = tasks.first { task -> isCurrentTask(task) }
            setImageAsWallpaper(imageLocation + currentTask.id + ".jpg")
        } catch (e: Exception) {
            // TODO: Show Notification
        }


    }

    private fun isCurrentTask(task: Task): Boolean {
        val now = LocalTime.now()
        return task.startTime.isBefore(now) && task.endTime.isAfter(now)
    }

}