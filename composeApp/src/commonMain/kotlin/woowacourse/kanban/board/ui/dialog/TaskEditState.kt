package woowacourse.kanban.board.ui.dialog

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

class TaskEditState(private val users: List<User>, private val taskValidator: TaskValidator = TaskValidator(users), task: Task) {
    var title by mutableStateOf(task.title)

    private val assignees = users.filterIsInstance<User.Assignee>()
    val validUsers: List<User>
        get() = if (selectedStatus == Status.TODO) users else assignees

    val titleValidation: ValidationResult by derivedStateOf { taskValidator.validateTitle(title) }

    var content by mutableStateOf(task.description ?: "")

    var tag by mutableStateOf(task.tags.items.joinToString(", ") { it.content })
    val tags: List<String> get() = tag.split(",").filter { it.isNotEmpty() }.map { it.trim() }
    val tagValidation: ValidationResult by derivedStateOf { taskValidator.validateTags(tag) }

    var selectedStatus by mutableStateOf(task.status)
    var selectedUser by mutableStateOf(task.user)

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
        if (taskValidator.validateUser(status, selectedUser) is ValidationResult.Invalid) {
            selectedUser = validUsers.filterIsInstance<User.Assignee>().first()
        }
        selectedStatus = status
    }

    val canCreate: Boolean get() =
        titleValidation is ValidationResult.Valid &&
            tagValidation !is ValidationResult.Invalid &&
            taskValidator.validateUser(selectedStatus, selectedUser) !is ValidationResult.Invalid

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
