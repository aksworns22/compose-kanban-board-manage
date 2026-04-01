package woowacourse.kanban.board.ui.dialog

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import woowacourse.kanban.board.domain.TaskCreator
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.validator.ValidationResult

class TaskCreationState(initialTaskValidationState: TaskValidationState) {
    val validationState = initialTaskValidationState

    val canCreate by derivedStateOf {
        validationState.titleValidation is ValidationResult.Valid && validationState.tagValidation !is ValidationResult.Invalid
    }

    fun createTask(): Result<Task> {
        return TaskCreator.create(
            title = validationState.title,
            description = validationState.content,
            tags = validationState.tags,
            assignee = validationState.selectedAssignee,
            status = validationState.selectedStatus,
        )
    }
}
