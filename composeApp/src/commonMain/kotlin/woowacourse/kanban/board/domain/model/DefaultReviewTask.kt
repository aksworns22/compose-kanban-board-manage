package woowacourse.kanban.board.domain.model

class DefaultReviewTask(
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
            Status.TODO -> throw IllegalArgumentException("TODO로는 이동할 수 없습니다")
            Status.IN_PROGRESS -> DefaultInProgressTask(
                id = id,
                title = title,
                description = description,
                tags = tags,
                user = user,
                status = status,
            )

            Status.REVIEW -> this
            Status.DONE -> DefaultDoneTask(id = id, title = title, description = description, tags = tags, user = user, status = status)
        }
    }
}
