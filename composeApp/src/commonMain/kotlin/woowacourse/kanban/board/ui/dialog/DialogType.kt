package woowacourse.kanban.board.ui.dialog

import woowacourse.kanban.board.domain.model.Task

sealed class DialogType {
    object CreateTask : DialogType()
    data class EditTask(val task: Task) : DialogType()
}
