package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable

@Serializable
class Teacher(
    val firstname: String,
    val surname: String
){
    fun fullname() =
        "$firstname $surname"
}