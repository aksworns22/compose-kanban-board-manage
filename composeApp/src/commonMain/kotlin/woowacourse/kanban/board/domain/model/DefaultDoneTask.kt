package woowacourse.kanban.board.domain.model

class DefaultDoneTask(
    id: Long = System.currentTimeMillis(),
    title: String,
    description: String? = null,
    tags: Tags,
    user: User,
    status: Status,
) : Task(id, title, description, tags, user, status) {
    init {
        if (!hasAssignee()) throw NoAssigneeException()
    }
    override fun moveTo(status: Status): Task {
        return when (status) {
            Status.TODO -> DefaultTodoTask(id = id, title = title, description = description, tags = tags, user = user, status = status)
            Status.IN_PROGRESS,
            Status.REVIEW,
            -> throw IllegalArgumentException("IN_PROGRESS와 REVIEW로는 이동할 수 없습니다")
            Status.DONE -> this
        }
    }
}
