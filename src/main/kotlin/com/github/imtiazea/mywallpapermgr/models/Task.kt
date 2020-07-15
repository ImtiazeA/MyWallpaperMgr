package com.github.imtiazea.mywallpapermgr.models

import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "tasks")
data class Task(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_id_seq")
        @SequenceGenerator(name = "tasks_id_seq", sequenceName = "tasks_id_seq", allocationSize = 1)
        var id: Long?,
        var description: String,
        var startTime: LocalTime,
        var endTime: LocalTime,
        var imageLink: String
)