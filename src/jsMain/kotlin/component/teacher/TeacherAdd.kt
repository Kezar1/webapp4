package component.teacher

import component.template.EditAddProps
import react.FC
import react.dom.html.ReactHTML
import react.useState
import ru.altmanea.webapp.data.Teacher
import web.html.InputType

val CTeacherAdd = FC<EditAddProps<Teacher>>("TeacherAdd") { props ->
    var firstname by useState("")
    var surname by useState("")
    ReactHTML.span {
        ReactHTML.input {
            type = InputType.text
            value = firstname
            onChange = { firstname = it.target.value }
        }
        ReactHTML.input {
            type = InputType.text
            value = surname
            onChange = { surname = it.target.value }
        }
    }
    ReactHTML.button {
        +"✓"
        onClick = {
            props.saveElement(Teacher(firstname, surname))
        }
    }
}
