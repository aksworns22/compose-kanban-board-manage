package woowacourse.kanban.board.ui.dialog

import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User

class TaskEditState(originalTask: Task, assignees: List<User>, val validationState: TaskValidationState = TaskValidationState(assignees)) {
    private val creationState = TaskCreationState(validationState)

    init {
        validationState.updateTitle(originalTask.title)
        validationState.updateContent(originalTask.description ?: "")
        validationState.updateTag(originalTask.tags.items.joinToString(", ") { it.content })
        validationState.updateStatus(originalTask.status)
        validationState.updateAssignee(originalTask.user)
    }

    fun createEditedTask(): Result<Task> {
        return creationState.createTask()
    }
}
