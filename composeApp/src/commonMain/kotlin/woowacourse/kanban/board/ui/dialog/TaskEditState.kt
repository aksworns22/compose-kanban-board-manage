package woowacourse.kanban.board.ui.dialog

import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User

class TaskEditState(originalTask: Task, users: List<User>) {
    private val creationState = TaskCreationState(users)
    val validationState = creationState.validationState
    init {
        validationState.updateTitle(originalTask.title)
        validationState.updateContent(originalTask.description ?: "")
        validationState.updateTag(originalTask.tags.items.joinToString(", ") { it.content })
        validationState.updateStatus(originalTask.status)
        validationState.updateUser(originalTask.user)
    }

    fun createEditedTask(): Result<Task> {
        return creationState.createTask()
    }
}
