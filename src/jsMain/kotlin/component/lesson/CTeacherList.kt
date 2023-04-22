package component.lesson

import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.tbody

external interface TeacherProps : Props {
    var teacher: String
}

val CTeacherList = FC<TeacherProps>("TeacherEdit") { props ->
    tbody {
        className = ClassName("lesson")
        div {
            className = ClassName("teacher")
            +"Teacher of this lesson: "
            +props.teacher
        }
    }
}