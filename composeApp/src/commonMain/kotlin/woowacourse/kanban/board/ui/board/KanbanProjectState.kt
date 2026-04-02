package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.mutableStateListOf
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User

fun isTaskMovable(targetTask: Task, destinationStatus: Status): Boolean {
    val validTransitions = mapOf(
        Status.TODO to listOf(Status.IN_PROGRESS),
        Status.IN_PROGRESS to listOf(Status.TODO, Status.REVIEW),
        Status.REVIEW to listOf(Status.IN_PROGRESS, Status.DONE),
        Status.DONE to listOf(Status.TODO),
    )
    if (targetTask.status == Status.TODO && targetTask.user is User.None) return false
    return validTransitions.getValue(targetTask.status).contains(destinationStatus)
}

class KanbanProjectState(val name: String, val isTaskMovable: (Task, Status) -> Boolean, vararg tasks: Task) {

    private val tasks: MutableList<Task> = mutableStateListOf(*tasks)

    val totalCount: Int get() = tasks.size
    val completeCount: Int get() = tasks.count { it.status == Status.DONE }
    val completeRatio: Float get() = if (totalCount == 0) 0f else completeCount.toFloat() / totalCount.toFloat()
    fun getTasks(status: Status): List<Task> = tasks.filter { it.status == status }
    fun addTask(task: Task) {
        tasks.add(task)
    }
    fun changeTaskStatus(task: Task, newStatus: Status) {
        if (!isTaskMovable(task, newStatus)) return
        tasks[tasks.indexOf(task)] = task.copy(status = newStatus)
    }

    fun editTask(originalTask: Task, newTask: Task) {
        tasks[tasks.indexOf(originalTask)] = newTask
    }

    fun deleteTask(task: Task): Boolean {
        if (task.status in listOf(Status.DONE, Status.REVIEW)) return false
        tasks.remove(task)
        return true
    }
}
