package woowacourse.kanban.board.ui.dialog.editing

import woowacourse.kanban.board.domain.TaskCreator
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.domain.validator.TaskValidator
import woowacourse.kanban.board.domain.validator.ValidationResult
import woowacourse.kanban.board.ui.dialog.TaskDialogState

class TaskEditState(users: List<User>, task: Task) :
    TaskDialogState(users, task.title, task.description ?: "", task.tags.items.joinToString(", "), task.status, task.user) {
    val canEdit: Boolean
        get() =
            titleValidation is ValidationResult.Valid &&
                tagValidation !is ValidationResult.Invalid &&
                TaskValidator.validateUser(selectedStatus, selectedUser) !is ValidationResult.Invalid

    fun createEditedTask(): Result<Task> {
        return TaskCreator.create(
            title = title,
            description = content,
            tags = tags,
            user = selectedUser,
            status = selectedStatus,
        )
    }
}
