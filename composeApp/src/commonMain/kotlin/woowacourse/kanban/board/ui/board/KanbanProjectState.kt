package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.mutableStateListOf
import woowacourse.kanban.board.domain.StatusTransitionResult
import woowacourse.kanban.board.domain.StatusTransitionRule
import woowacourse.kanban.board.domain.checkDefaultStatusTransition
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User

class KanbanProjectState(val name: String, val users: List<User>, vararg tasks: Task) {

    private val tasks: MutableList<Task> = mutableStateListOf(*tasks)

    val totalCount: Int get() = tasks.size
    val completeCount: Int get() = tasks.count { it.status == Status.DONE }
    val completeRatio: Float get() = if (totalCount == 0) 0f else completeCount.toFloat() / totalCount.toFloat()
    fun getTasks(status: Status): List<Task> = tasks.filter { it.status == status }
    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun changeTaskStatus(
        task: Task,
        newStatus: Status,
        rule: StatusTransitionRule = StatusTransitionRule(::checkDefaultStatusTransition),
    ): StatusTransitionResult {
        val movementResult = rule.isMovable(task, newStatus)
        if (movementResult == StatusTransitionResult.Success) {
            tasks[tasks.indexOf(task)] = task.copy(status = newStatus)
        }
        return movementResult
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
