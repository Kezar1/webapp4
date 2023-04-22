package ru.altmanea.webapp.repo

import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.data.*

val studentsRepo = ListRepo<Student>()
val lessonsRepo = ListRepo<Lesson>()
val teachersRepo = ListRepo<Teacher>()


fun createTestData() {
    listOf(
        Student("Sheldon", "Cooper"),
        Student("Leonard", "Hofstadter"),
        Student("Howard", "Wolowitz"),
        Student("Penny", "Hofstadter"),
    ).apply {
        map {
            studentsRepo.create(it)
        }
    }

    listOf(
        Lesson("Math"),
        Lesson("Phys"),
        Lesson("Story"),
    ).apply {
        map {
            lessonsRepo.create(it)
        }
    }

    listOf(
        Teacher("May", "Legacy"),
        Teacher("Tiny", "Large")
    ).apply {
        map {
            teachersRepo.create(it)
        }
    }


    val students = studentsRepo.read()
    val lessons = lessonsRepo.read()
    val sheldon = students.findLast { it.elem.firstname == "Sheldon" }
    check(sheldon != null)
    val leonard = students.findLast { it.elem.firstname == "Leonard" }
    check(leonard != null)
    val math = lessons.findLast { it.elem.name =="Math" }
    check(math != null)
    val newMath = Lesson(
        math.elem.name,
        arrayOf(
            GradeInfo(sheldon.id, Grade.A),
            GradeInfo(leonard.id, Grade.B)
        ))
    lessonsRepo.update(Item(newMath, math.id, math.version))
}
