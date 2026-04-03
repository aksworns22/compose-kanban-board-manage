package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User

class StatusTransitionRuleTest {
    private val defaultStatusTransitionRule: StatusTransitionRule = StatusTransitionRule(::checkDefaultStatusTransition)

    @Test
    fun `To Do 상태에서는 In Progress로 전이가 가능하다`() {
        // when
        val result = this@StatusTransitionRuleTest.defaultStatusTransitionRule.isMovable(toDoTask, Status.IN_PROGRESS)

        // then
        assertThat(result).isEqualTo(StatusTransitionResult.Success)
    }

    @Test
    fun `To Do 상태에서 담당자가 없다면 In Progress로 전이에 대해 담당자 없음을 반환한다`() {
        // when
        val result = this@StatusTransitionRuleTest.defaultStatusTransitionRule.isMovable(toDoTaskWithoutAssignee, Status.IN_PROGRESS)

        // then
        assertThat(result).isEqualTo(StatusTransitionResult.NoAssignee)
    }

    @Test
    fun `To Do 상태에서는 In Progress를 제외한 상태로 전이가 불가능하다`() {
        for (status in listOf(Status.TODO, Status.REVIEW, Status.DONE)) {
            // when
            val result = this@StatusTransitionRuleTest.defaultStatusTransitionRule.isMovable(toDoTask, status)

            // then
            assertThat(result).isEqualTo(StatusTransitionResult.Failed)
        }
    }

    @Test
    fun `In Progress 상태에서는 To Do와 Review로 전이가 가능하다`() {
        for (status in listOf(Status.TODO, Status.REVIEW)) {
            // when
            val result = this@StatusTransitionRuleTest.defaultStatusTransitionRule.isMovable(inProgressTask, status)

            // then
            assertThat(result).isEqualTo(StatusTransitionResult.Success)
        }
    }

    @Test
    fun `In Progress 상태에서는 In progress와 done으로 전이가 불가능하다`() {
        for (status in listOf(Status.IN_PROGRESS, Status.DONE)) {
            // when
            val result = this@StatusTransitionRuleTest.defaultStatusTransitionRule.isMovable(inProgressTask, status)

            // then
            assertThat(result).isEqualTo(StatusTransitionResult.Failed)
        }
    }

    @Test
    fun `Review 상태에서는 In Progress와 Done으로 전이가 가능하다`() {
        for (status in listOf(Status.IN_PROGRESS, Status.DONE)) {
            // when
            val result = this@StatusTransitionRuleTest.defaultStatusTransitionRule.isMovable(reviewTask, status)

            // then
            assertThat(result).isEqualTo(StatusTransitionResult.Success)
        }
    }

    @Test
    fun `Review 상태에서는 Todo와 Review로 전이가 불가능하다`() {
        for (status in listOf(Status.TODO, Status.REVIEW)) {
            // when
            val result = this@StatusTransitionRuleTest.defaultStatusTransitionRule.isMovable(reviewTask, status)

            // then
            assertThat(result).isEqualTo(StatusTransitionResult.Failed)
        }
    }

    @Test
    fun `Done 상태에서 To Do로 전이가 가능하다`() {
        // when
        val result = this@StatusTransitionRuleTest.defaultStatusTransitionRule.isMovable(doneTask, Status.TODO)

        // then
        assertThat(result).isEqualTo(StatusTransitionResult.Success)
    }

    @Test
    fun `Done 상태에서 To Do를 제외한 상태로 전이가 불가능하다`() {
        for (status in listOf(Status.IN_PROGRESS, Status.REVIEW, Status.DONE)) {
            // when
            val result = this@StatusTransitionRuleTest.defaultStatusTransitionRule.isMovable(doneTask, status)

            // then
            assertThat(result).isEqualTo(StatusTransitionResult.Failed)
        }
    }

    private val toDoTask: Task = Task(
        title = "우리땅!",
        tags = Tags(emptyList()),
        status = Status.TODO,
        user = User.Assignee("두릅"),
    )
    private val toDoTaskWithoutAssignee =
        Task(title = "우리땅!", tags = Tags(emptyList()), status = Status.TODO, user = User.None)
    private val inProgressTask: Task = Task(
        title = "우리땅!",
        tags = Tags(emptyList()),
        status = Status.IN_PROGRESS,
        user = User.Assignee("부릅"),
    )
    private val doneTask: Task = Task(
        title = "우리땅!",
        tags = Tags(emptyList()),
        status = Status.DONE,
        user = User.Assignee("꽈뚜릅"),
    )
    private val reviewTask: Task = Task(
        title = "Review Task",
        tags = Tags(emptyList()),
        status = Status.REVIEW,
        user = User.Assignee("스마일"),
    )
}
