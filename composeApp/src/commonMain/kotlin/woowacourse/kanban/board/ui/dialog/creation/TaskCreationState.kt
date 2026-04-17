package woowacourse.kanban.board.ui.dialog.creation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.TaskCreator
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.domain.validator.TaskValidator
import woowacourse.kanban.board.domain.validator.ValidationResult
import woowacourse.kanban.board.ui.dialog.TaskDialogState

class TaskCreationState(users: List<User>) : TaskDialogState(users, "", "", "", Status.TODO, users.first()) {
    val canCreate by derivedStateOf {
        titleValidation is ValidationResult.Valid &&
            tagValidation !is ValidationResult.Invalid &&
            TaskValidator.validateUser(
                selectedStatus,
                selectedUser,
            ) !is ValidationResult.Invalid
    }

    fun createTask(): Result<Task> {
        return TaskCreator.create(
            title = title,
            description = content,
            tags = tags,
            user = selectedUser,
            status = selectedStatus,
        )
    }
}
