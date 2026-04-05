package woowacourse.kanban.board.ui.dialog

import woowacourse.kanban.board.domain.model.Task

sealed class TaskDialogType {
    object CreateTask : TaskDialogType()
    data class EditTask(val task: Task) : TaskDialogType()
}
