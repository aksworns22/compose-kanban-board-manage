package woowacourse.kanban.board.ui.dialog

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.domain.validator.TaskValidator
import woowacourse.kanban.board.domain.validator.ValidationResult

class TaskCreateFormState(val assignees: List<User>) {
    var title by mutableStateOf("")
    val titleValidation: ValidationResult by derivedStateOf { TaskValidator.validateTitle(title) }

    var content by mutableStateOf("")

    var tag by mutableStateOf("")
    val tagValidation: ValidationResult by derivedStateOf { TaskValidator.validateTags(tag) }

    var selectedStatus by mutableStateOf(Status.TODO)
    var selectedAssignee by mutableStateOf(assignees.first())

    val canCreate by derivedStateOf { titleValidation is ValidationResult.Valid && tagValidation !is ValidationResult.Invalid }
    fun updateTitle(input: String) {
        title = input
    }

    fun updateContent(input: String) {
        content = input
    }

    fun updateTag(input: String) {
        tag = input
    }

    fun updateAssignee(user: User) {
        selectedAssignee = user
    }

    fun updateStatus(status: Status) {
        this.selectedStatus = status
    }
}
