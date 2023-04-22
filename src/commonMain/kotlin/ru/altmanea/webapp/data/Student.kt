package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.altmanea.webapp.common.ItemId

@Serializable
class Student(
    val firstname: String,
    val surname: String
){
    fun fullname() =
        "$firstname $surname"
}

typealias StudentId = ItemId
typealias TeacherId = ItemId

val Student.json
    get() = Json.encodeToString(this)

val Teacher.json
    get() = Json.encodeToString(this)


