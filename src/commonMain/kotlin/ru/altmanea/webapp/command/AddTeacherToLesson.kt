package ru.altmanea.webapp.command

import kotlinx.serialization.Serializable
import ru.altmanea.webapp.data.LessonId
import ru.altmanea.webapp.data.TeacherId

@Serializable
class AddTeacherToLesson(
    val lessonId: LessonId,
    val teacherId: TeacherId,
    val version: Long
){
    companion object {
        const val path="addTeacher"
    }
}