package woowacourse.kanban.board.domain.model

data class KanbanProject(val name: String, val tasks: List<Task>) {
    val totalCount: Int get() = tasks.size
    val completeCount: Int get() = tasks.count { it.status == Status.DONE }
    val completeRatio: Float get() = if (totalCount == 0) 0f else completeCount.toFloat() / totalCount.toFloat()
    fun getTasks(status: Status): List<Task> = tasks.filter { it.status == status }
    fun addTask(task: Task): KanbanProject = copy(tasks = tasks + task)
    fun changeTaskStatus(task: Task, newStatus: Status) = copy(tasks = tasks.filter { it != task } + task.copy(status = newStatus))
}
