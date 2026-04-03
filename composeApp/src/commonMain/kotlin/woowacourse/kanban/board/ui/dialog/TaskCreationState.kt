package woowacourse.kanban.board.ui.dialog

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import woowacourse.kanban.board.domain.TaskCreator
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.domain.validator.ValidationResult

class TaskCreationState(users: List<User>) {
    val validationState = TaskValidationState(users)

    val canCreate by derivedStateOf {
        validationState.titleValidation is ValidationResult.Valid && validationState.tagValidation !is ValidationResult.Invalid
    }

    fun initialize(task: Task): TaskCreationState {
        validationState.updateTitle(task.title)
        validationState.updateContent(task.description ?: "")
        validationState.updateTag(task.tags.items.joinToString(", ") { it.content })
        validationState.updateStatus(task.status)
        validationState.updateUser(task.user)
        return this
    }

    fun createTask(): Result<Task> {
        return TaskCreator.create(
            title = validationState.title,
            description = validationState.content,
            tags = validationState.tags,
            user = validationState.selectedUser,
            status = validationState.selectedStatus,
        )
    }
}
