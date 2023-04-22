package component.teacher

import component.template.ElementInListProps
import react.FC
import react.dom.html.ReactHTML
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.data.Teacher

val CTeacherInList = FC<ElementInListProps<Item<Teacher>>>("TeacherInList") { props ->
    ReactHTML.span {
        +props.element.elem.fullname()
//            to = props.element.id

    }
}