package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User

fun interface StatusTransitionRule {
    fun isMovable(targetTask: Task, destinationStatus: Status): StatusTransitionResult
}

fun checkDefaultStatusTransition(targetTask: Task, destinationStatus: Status): StatusTransitionResult {
    val validTransitions = mapOf(
        Status.TODO to listOf(Status.IN_PROGRESS),
        Status.IN_PROGRESS to listOf(Status.TODO, Status.REVIEW),
        Status.REVIEW to listOf(Status.IN_PROGRESS, Status.DONE),
        Status.DONE to listOf(Status.TODO),
    )
    if (targetTask.status == Status.TODO && targetTask.user is User.None) return StatusTransitionResult.NoAssignee
    return if (validTransitions.getValue(targetTask.status).contains(destinationStatus)) {
        StatusTransitionResult.Success
    } else StatusTransitionResult.Failed
}

enum class StatusTransitionResult {
    Success,
    Failed,
    NoAssignee,
}
