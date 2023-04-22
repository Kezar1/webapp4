package ru.altmanea.webapp.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import ru.altmanea.webapp.command.AddStudentToLesson
import ru.altmanea.webapp.command.AddTeacherToLesson
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.repo.lessonsRepo
import ru.altmanea.webapp.repo.studentsRepo
import ru.altmanea.webapp.repo.teachersRepo

fun Route.lessonRoutes() {
    route(Config.lessonsPath) {
        repoRoutes(lessonsRepo)

        post (AddStudentToLesson.path) {
            val command = Json.decodeFromString(AddStudentToLesson.serializer(), call.receive())
            val lesson = lessonsRepo.read(listOf(command.lessonId)).getOrNull(0)
                ?: return@post call.respondText(
                    "No lesson with id ${command.lessonId}",
                    status = HttpStatusCode.NotFound
                )
            studentsRepo.read(listOf(command.studentId)).getOrNull(0)
                ?: return@post call.respondText(
                    "No student with id ${command.lessonId}",
                    status = HttpStatusCode.NotFound
                )
            if (lesson.elem.students.find { it.studentId == command.lessonId } != null)
                return@post call.respondText(
                    "Student already in lesson",
                    status = HttpStatusCode.BadRequest
                )
            if(command.version!=lesson.version){
                call.respondText(
                    "Lesson had updated on server",
                    status = HttpStatusCode.BadRequest
                )
            }
            val newLesson = lesson.elem.addStudent(command.studentId)
            if (lessonsRepo.update(Item(newLesson, command.lessonId, command.version))) {
                call.respondText(
                    "Student added correctly",
                    status = HttpStatusCode.OK
                )
            } else {
                call.respondText(
                    "Update error",
                    status = HttpStatusCode.BadRequest
                )
            }
        }
        post (AddTeacherToLesson.path) {
            val command = Json.decodeFromString(AddTeacherToLesson.serializer(), call.receive())
            val lesson = lessonsRepo.read(listOf(command.lessonId)).getOrNull(0)
                ?: return@post call.respondText(
                    "No lesson with id ${command.lessonId}",
                    status = HttpStatusCode.NotFound
                )
            teachersRepo.read(listOf(command.teacherId)).getOrNull(0)
                ?: return@post call.respondText(
                    "No teacher with id ${command.lessonId}",
                    status = HttpStatusCode.NotFound
                )

            if(command.version!=lesson.version){
                call.respondText(
                    "Lesson had updated on server",
                    status = HttpStatusCode.BadRequest
                )
            }

            val newLesson = lesson.elem.addTeacher(command.teacherId)
            if (lessonsRepo.update(Item(newLesson, command.lessonId, command.version))) {
                call.respondText(
                    "Teacher added correctly",
                    status = HttpStatusCode.OK
                )
            } else {
                call.respondText(
                    "Update error",
                    status = HttpStatusCode.BadRequest
                )
            }
        }
    }
}
