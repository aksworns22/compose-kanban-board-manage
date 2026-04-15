package woowacourse.kanban.board.domain.model

class DefaultTodoTask(
    id: Long = System.currentTimeMillis(),
    title: String,
    description: String? = null,
    tags: Tags,
    user: User,
    status: Status,
) : Task(id, title, description, tags, user, status) {
    override fun moveTo(status: Status): Task {
        return when (status) {
            Status.TODO -> this
            Status.IN_PROGRESS -> DefaultInProgressTask(
                id = id,
                title = title,
                description = description,
                tags = tags,
                user = user,
                status = status,
            )

            Status.REVIEW,
            Status.DONE,
            -> throw IllegalArgumentException("REVIEW와 DONE으로는 이동할 수 없습니다")
        }
    }
}
