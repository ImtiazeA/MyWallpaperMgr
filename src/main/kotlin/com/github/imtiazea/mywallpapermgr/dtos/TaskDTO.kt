package com.github.imtiazea.mywallpapermgr.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.LocalTime

data class TaskDTO(var id: Long?,
                   var description: String?,
                   var startTime: LocalTime?,
                   var endTime: LocalTime?,
                   var imageLink: String?
)