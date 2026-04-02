package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.mutableStateListOf
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task

class KanbanProjectState(val name: String, vararg tasks: Task) {

    private val tasks: MutableList<Task> = mutableStateListOf(*tasks)

    val totalCount: Int get() = tasks.size
    val completeCount: Int get() = tasks.count { it.status == Status.DONE }
    val completeRatio: Float get() = if (totalCount == 0) 0f else completeCount.toFloat() / totalCount.toFloat()
    fun getTasks(status: Status): List<Task> = tasks.filter { it.status == status }
    fun addTask(task: Task) {
        tasks.add(task)
    }
    fun changeTaskStatus(task: Task, newStatus: Status) {
        tasks[tasks.indexOf(task)] = task.copy(status = newStatus)
    }

    fun editTask(originalTask: Task, newTask: Task) {
        tasks[tasks.indexOf(originalTask)] = newTask
    }

    fun deleteTask(task: Task): Boolean {
        if (task.status == Status.DONE) return false
        tasks.remove(task)
        return true
    }
}
