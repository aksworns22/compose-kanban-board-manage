package woowacourse.kanban.board.ui.dialog.creation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.TaskCreator
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.domain.validator.TaskValidator
import woowacourse.kanban.board.domain.validator.ValidationResult

class TaskCreationState(private val users: List<User>) {
    var title by mutableStateOf("")
    private val assignees = users.filterIsInstance<User.Assignee>()
    val validUsers: List<User>
        get() = if (selectedStatus == Status.TODO) users else assignees
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
        if (TaskValidator.validateUser(status, selectedUser) is ValidationResult.Invalid) {
            selectedUser = assignees.first()
        }
        selectedStatus = status
    }

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
