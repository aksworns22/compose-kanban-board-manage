package woowacourse.kanban.board.ui.dialog

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.domain.validator.TaskValidator
import woowacourse.kanban.board.domain.validator.ValidationResult

class TaskValidationState(private val users: List<User>) {
    var title by mutableStateOf("")

    val validUsers: List<User> get() = if (selectedStatus == Status.TODO) users else users.filterIsInstance<User.Assignee>()

    val titleValidation: ValidationResult by derivedStateOf { TaskValidator.validateTitle(title) }

    var content by mutableStateOf("")

    var tag by mutableStateOf("")
    val tags: List<String> get() = tag.split(",").filter { it.isNotEmpty() }.map { it.trim() }
    val tagValidation: ValidationResult by derivedStateOf { TaskValidator.validateTags(tag) }

    var selectedStatus by mutableStateOf(Status.TODO)
    var selectedUser by mutableStateOf(defaultUser)

    private val defaultUser: User get() = validUsers.first()

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
        val originalStatus = this.selectedStatus
        this.selectedStatus = status
        if (originalStatus == Status.TODO && selectedUser is User.None) {
            selectedUser = defaultUser
        }
    }
}
