package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.model.DefaultDoneTask
import woowacourse.kanban.board.domain.model.DefaultInProgressTask
import woowacourse.kanban.board.domain.model.DefaultReviewTask
import woowacourse.kanban.board.domain.model.DefaultTodoTask
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tag
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User

object TaskCreator {
    fun create(title: String, description: String, tags: List<String>, user: User, status: Status): Result<Task> {
        return runCatching {
            when (status) {
                Status.TODO -> DefaultTodoTask(
                    title = title, description = description,
                    tags = Tags(
                        tags.map {
                            Tag(it)
                        },
                    ),
                    user = user, status = status,
                )
                Status.IN_PROGRESS -> DefaultInProgressTask(
                    title = title, description = description,
                    tags = Tags(
                        tags.map {
                            Tag(it)
                        },
                    ),
                    user = user, status = status,
                )
                Status.REVIEW -> DefaultReviewTask(
                    title = title, description = description,
                    tags = Tags(
                        tags.map {
                            Tag(it)
                        },
                    ),
                    user = user, status = status,
                )
                Status.DONE -> DefaultDoneTask(
                    title = title, description = description,
                    tags = Tags(
                        tags.map {
                            Tag(it)
                        },
                    ),
                    user = user, status = status,
                )
            }
        }
    }
}
