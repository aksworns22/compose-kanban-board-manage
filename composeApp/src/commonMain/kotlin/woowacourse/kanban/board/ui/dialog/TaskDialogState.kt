package woowacourse.kanban.board.ui.dialog

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.domain.validator.TaskValidator
import woowacourse.kanban.board.domain.validator.ValidationResult

abstract class TaskDialogState(
    private val users: List<User>,
    initialTitle: String,
    initialContent: String,
    initialTags: String,
    initialSelectedStatus: Status,
    initialSelectedUser: User,
) {
    var title by mutableStateOf(initialTitle)
        private set
    private val assignees = users.filterIsInstance<User.Assignee>()
    val validUsers: List<User>
        get() = if (selectedStatus == Status.TODO) users else assignees
    val titleValidation: ValidationResult by derivedStateOf { TaskValidator.validateTitle(title) }

    var content by mutableStateOf(initialContent)
        private set

    var tag by mutableStateOf(initialTags)
        private set

    val tags: List<String> get() = tag.split(",").filter { it.isNotEmpty() }.map { it.trim() }
    val tagValidation: ValidationResult by derivedStateOf { TaskValidator.validateTags(tag) }

    var selectedStatus by mutableStateOf(initialSelectedStatus)
        private set
    var selectedUser by mutableStateOf(initialSelectedUser)
        private set

    fun updateTitle(input: String) {
        title = input
    }

    fun updateContent(input: String) {
        content = input
    }

    fun updateTag(input: String) {
        tag = input
    }

    fun updateUser(user: User) {
        selectedUser = user
    }

    fun updateStatus(status: Status) {
        if (TaskValidator.validateUser(status, selectedUser) is ValidationResult.Invalid) {
            selectedUser = assignees.first()
        }
        selectedStatus = status
    }
}
