package ru.altmanea.webapp.command

import kotlinx.serialization.Serializable
import ru.altmanea.webapp.data.LessonId
import ru.altmanea.webapp.data.StudentId

@Serializable
class AddStudentToLesson(
    val lessonId: LessonId,
    val studentId: StudentId,
    val version: Long
){
    companion object {
        const val path="addStudent"
    }
}