package component.lesson


import invalidateRepoKey
import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import query.QueryError
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import ru.altmanea.webapp.command.AddTeacherToLesson
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.Lesson
import ru.altmanea.webapp.data.Teacher
import ru.altmanea.webapp.data.TeacherId
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import tools.fetchText
import web.html.HTMLInputElement
import web.html.HTMLSelectElement
import kotlin.js.json

external interface TeacherSelectProps : Props {
    var startName: String
    var onPick: (TeacherId) -> Unit
}

val CTeacherSelect = FC<TeacherSelectProps>("TeacherSelect") { props ->
    val selectQueryKey = arrayOf("TeacherSelect", props.startName).unsafeCast<QueryKey>()
    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = selectQueryKey,
        queryFn = {
            fetchText("${Config.teachersPath}ByStartName/${props.startName}")
        }
    )
    val selectRef = useRef<HTMLSelectElement>()
    val teachers: List<Item<Teacher>> =
        try {
            Json.decodeFromString(query.data ?: "")
        } catch (e: Throwable) {
            emptyList()
        }
    select {
        ref = selectRef
        teachers.map {
            option {
                +it.elem.fullname()
                value = it.id
            }
        }
    }
    button {
        +"Add Teacher"
        onClick = {
            selectRef.current?.value?.let {
                props.onPick(it)
            }
        }
    }
}

external interface AddTeacherProps : Props {
    var lesson: Item<Lesson>
}

val CAddTeacherToLesson = FC<AddTeacherProps>("AddTeacher") { props ->
    val queryClient = useQueryClient()
    val invalidateRepoKey = useContext(invalidateRepoKey)
    var input by useState("")
    val inputRef = useRef<HTMLInputElement>()

    val addTeacherMutation = useMutation<HTTPResult, Any, TeacherId, Any>(
        mutationFn = { teacherId ->
            fetch(
                
                "${Config.lessonsPath}/${AddTeacherToLesson.path}",
                jso {
                    method = "POST"
                    headers = json(
                        "Content-Type" to "application/json"
                    )
                    body = Json.encodeToString(
                        AddTeacherToLesson(
                            props.lesson.id,
                            teacherId,
                            props.lesson.version
                        )
                    )
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(invalidateRepoKey)
            }
        }
    )

    div {
        input {
            ref = inputRef
            list = "TeachersHint"
            onChange = { input = it.target.value }
        }
        CTeacherSelect {
            startName = input
            onPick = {
                addTeacherMutation.mutateAsync(it, null)
            }
        }
    }
}
