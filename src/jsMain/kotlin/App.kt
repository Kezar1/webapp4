import component.lesson.CLessonAdd
import component.lesson.CLessonEditContainer
import component.lesson.CLessonInList
import component.student.CSelectedStudent
import component.student.CStudentAdd
import component.student.CStudentEdit
import component.student.CStudentInList
import component.teacher.CTeacherAdd
import component.teacher.CTeacherEdit
import component.teacher.CTeacherInList
import component.template.RestContainerChildProps
import component.template.restContainer
import component.template.restList
import react.FC
import react.Props
import react.create
import react.createContext
import react.dom.client.createRoot
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter
import react.router.dom.Link
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.Lesson
import ru.altmanea.webapp.data.Student
import ru.altmanea.webapp.data.Teacher
import tanstack.query.core.QueryClient
import tanstack.query.core.QueryKey
import tanstack.react.query.QueryClientProvider
import tanstack.react.query.devtools.ReactQueryDevtools
import web.dom.document

val invalidateRepoKey = createContext<QueryKey>()

fun main() {
    val container = document.getElementById("root")!!
    createRoot(container).render(app.create())
}

val app = FC<Props>("App") {
    HashRouter {
        QueryClientProvider {
            client = QueryClient()
            ul {
                listOf("Students", "Lessons", "Teachers").map { tag ->
                    li {
                        Link {
                            +tag
                            to = tag.lowercase()
                        }
                    }
                }
            }

            Routes {
                Route {
                    path = "lessons"
                    val list: FC<RestContainerChildProps<Lesson>> =
                        restList(
                            CLessonInList,
                            CLessonAdd,
                            CLessonEditContainer
                        )
                    element = restContainer(
                        Config.lessonsPath,
                        list,
                        "lessons"
                    ).create()
                }
                Route {
                    path = "students"
                    val list: FC<RestContainerChildProps<Student>> =
                        restList(
                            CStudentInList,
                            CStudentAdd,
                            CStudentEdit
                        )
                    element = restContainer(
                        Config.studentsPath,
                        list,
                        "students"
                    ).create()
                }
                Route {
                    path = "teachers"
                    val list: FC<RestContainerChildProps<Teacher>> =
                        restList(
                            CTeacherInList,
                            CTeacherAdd,
                            CTeacherEdit
                        )
                    element = restContainer(
                        Config.teachersPath,
                        list,
                        "teachers"
                    ).create()
                }
                Route{
                    path = Config.studentsPath + ":name"
                    element = CSelectedStudent.create()
                }
            }
            ReactQueryDevtools { }
        }
    }
}