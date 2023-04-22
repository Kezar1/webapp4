package component.teacher

import component.template.EditItemProps
import react.FC
import react.dom.html.ReactHTML
import react.useState
import ru.altmanea.webapp.data.Teacher
import web.html.InputType

val CTeacherEdit = FC<EditItemProps<Teacher>>("TeacherEdit") { props ->
    var firstname by useState(props.item.elem.firstname)
    var surname by useState(props.item.elem.surname)
    ReactHTML.table {
        ReactHTML.td {
            ReactHTML.input {
                type = InputType.text
                value = firstname
                onChange = { firstname = it.target.value }
            }
        }
        ReactHTML.td {
            ReactHTML.input {
                type = InputType.text
                value = surname
                onChange = { surname = it.target.value }
            }
        }
        ReactHTML.td {
            ReactHTML.button {
                +"✓"
                onClick = {
                    props.saveElement(Teacher(firstname, surname))
                }
            }
        }
    }
}