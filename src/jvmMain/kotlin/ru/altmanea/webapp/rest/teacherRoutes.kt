package ru.altmanea.webapp.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.repo.lessonsRepo
import ru.altmanea.webapp.repo.teachersRepo

fun Route.teacherRoutes() {
    route(Config.teachersPath) {
        repoRoutes(teachersRepo)
        get("{idS}/lessons") {
            val idS = call.parameters["idS"] ?: return@get call.respondText(
                "Missing or malformed teacher id",
                status = HttpStatusCode.BadRequest
            )
            val lessons = lessonsRepo.read().map {
                if (it.elem.teacherId != idS) it.elem.name else null
            }
            if (lessons.isEmpty()) {
                call.respondText("No lessons found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(lessons)
            }
        }
        get("ByStartName/{startName}") {
            val startName =
                call.parameters["startName"] ?: return@get call.respondText(
                    "Missing or malformed startName",
                    status = HttpStatusCode.BadRequest
                )
            val teacher = teachersRepo.read().filter {
                it.elem.firstname.startsWith(startName)
            }

            if (teacher.isEmpty()) {
                call.respondText("No teacher found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(teacher)
            }
        }
    }
}