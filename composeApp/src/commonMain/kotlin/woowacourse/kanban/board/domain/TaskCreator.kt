package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tag
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User

object TaskCreator {

    fun create(title: String, description: String, tags: List<String>, assignee: User, status: Status): Result<Task> {
        return try {
            val task = Task(title = title, description = description, tags = Tags(tags.map { Tag(it) }), user = assignee, status = status)
            Result.success(task)
        } catch (e: IllegalArgumentException) {
            Result.failure(e)
        }
    }
}
