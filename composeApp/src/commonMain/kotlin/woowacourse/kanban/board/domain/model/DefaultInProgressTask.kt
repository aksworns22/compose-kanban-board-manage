package woowacourse.kanban.board.domain.model

class DefaultInProgressTask(
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
            Status.REVIEW -> DefaultReviewTask(id = id, title = title, description = description, tags = tags, user = user, status = status)
            Status.IN_PROGRESS -> this
            Status.DONE -> throw IllegalArgumentException("DONE으로는 이동할 수 없습니다")
        }
    }
}
