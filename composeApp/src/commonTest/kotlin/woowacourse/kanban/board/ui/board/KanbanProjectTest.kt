package woowacourse.kanban.board.ui.board

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import woowacourse.kanban.board.domain.model.DefaultDoneTask
import woowacourse.kanban.board.domain.model.DefaultInProgressTask
import woowacourse.kanban.board.domain.model.DefaultReviewTask
import woowacourse.kanban.board.domain.model.DefaultTodoTask
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User

class KanbanProjectTest {
    @Test
    fun `새로운 태스크를 추가하면 프로젝트 태스크의 전체 개수가 하나 증가한다`() {
        // given
        val project = KanbanProjectState(name = "독도는 우리땅", users = users, toDoTask, inProgressTask, doneTask)
        val beforeProjectSize = project.totalCount

        // when
        project.addTask(
            DefaultTodoTask(
                title = "tft 공략은? 두루루~",
                tags = Tags(emptyList()),
                status = Status.TODO,
                user = users[2],
            ),
        )

        // then
        assertThat(project.totalCount).isEqualTo(beforeProjectSize + 1)
    }

    @Test
    fun `TODO 상태의 태스크를 IN_PROGRESS로 변경하면 태스크의 상태가 IN_PROGRESS로 변경된다`() {
        // given
        val project = KanbanProjectState(name = "독도는 우리땅", users, toDoTask, inProgressTask, doneTask)
        val expectedTask = toDoTask.moveTo(status = Status.IN_PROGRESS)

        // when
        project.changeTaskStatus(toDoTask, Status.IN_PROGRESS)

        // then
        assertThat(project.getTasks(Status.IN_PROGRESS)).contains(expectedTask)
    }

    @Test
    fun `DONE 상태의 태스크를 가져오면 DONE 상태의 태스크만 반환된다`() {
        // given
        val project = KanbanProjectState(name = "독도는 우리땅", users, toDoTask, inProgressTask, doneTask)

        // when
        val doneTasks = project.getTasks(Status.DONE)

        // then
        assertThat(doneTasks).contains(doneTask)
    }

    @Test
    fun `3개의 태스크를 가진 프로젝트라면 전체 태스크 개수는 3개이다`() {
        // given
        val project = KanbanProjectState(name = "독도는 우리땅", users, toDoTask, inProgressTask, doneTask)

        // then
        assertThat(project.totalCount).isEqualTo(3)
    }

    @Test
    fun `프로젝트의 DONE 상태의 태스크가 1개라면 완료한 개수도 1개이다`() {
        // given
        val project = KanbanProjectState(name = "독도는 우리땅", users, toDoTask, inProgressTask, doneTask)

        // then
        assertThat(project.completeCount).isEqualTo(1)
    }

    @Test
    fun `3개의 태스크 중 한개만 DONE 상태라면 완료율의 소수 둘째자리까지의 값은 0_33에 가깝다`() {
        // given
        val project = KanbanProjectState(name = "독도는 우리땅", users, toDoTask, inProgressTask, doneTask)

        // then
        assertThat(project.completeRatio).isCloseTo(0.33f, Offset.offset(0.01f))
    }

    @Test
    fun `프로젝트에 태스크가 없다면 완료율은 0이다`() {
        // given
        val emptyProject = KanbanProjectState(name = "독도는 우리땅", users = users)

        // then
        assertThat(emptyProject.completeRatio).isEqualTo(0f)
    }

    @Test
    fun `review와 done 상태의 태스크를 삭제해도 제거되지 않는다`() {
        // given
        val project = KanbanProjectState(name = "독도는 우리땅", users = users, reviewTask, doneTask)

        // when
        project.deleteTask(reviewTask)
        project.deleteTask(doneTask)

        // then
        assertThat(project.getTasks(Status.DONE)).contains(doneTask)
        assertThat(project.getTasks(Status.REVIEW)).contains(reviewTask)
    }

//    @Test
//    fun `전이 규칙이 success면 태스크의 상태가 변경된다`() {
//        // given
//        val onlySuccessRule = StatusTransitionRule { _, _ -> StatusTransitionResult.Success }
//        val project = KanbanProjectState(name = "독도는 우리땅", users = users, toDoTask)
//        val expectedTask = toDoTask.copy(status = Status.DONE)
//
//        // when
//        project.changeTaskStatus(toDoTask, Status.DONE, onlySuccessRule)
//
//        // then
//        assertThat(project.getTasks(Status.DONE)).contains(expectedTask)
//    }
//
//    @Test
//    fun `전이 규칙이 NoAssignee면 태스크의 상태가 변경되지 않는다`() {
//        // given
//        val onlyNoAssigneeRule = StatusTransitionRule { _, _ -> StatusTransitionResult.NoAssignee }
//        val project = KanbanProjectState(name = "독도는 우리땅", users = users, toDoTask)
//
//        // when
//        project.changeTaskStatus(toDoTask, Status.DONE, onlyNoAssigneeRule)
//
//        // then
//        assertThat(project.getTasks(Status.TODO)).contains(toDoTask)
//    }
//
//    @Test
//    fun `전이 규칙이 Failed면 태스크의 상태가 변경되지 않는다`() {
//        // given
//        val onlyFailedRule = StatusTransitionRule { _, _ -> StatusTransitionResult.Failed }
//        val project = KanbanProjectState(name = "독도는 우리땅", users = users, toDoTask)
//
//        // when
//        project.changeTaskStatus(toDoTask, Status.IN_PROGRESS, onlyFailedRule)
//
//        // then
//        assertThat(project.getTasks(Status.TODO)).contains(toDoTask)
//    }

    private val users = listOf(
        User.Assignee("부릅"),
        User.Assignee("스마일"),
        User.Assignee("두릅"),
        User.Assignee("꽈뚜릅"),
    )
    private val toDoTask: Task = DefaultTodoTask(title = "우리땅!", tags = Tags(emptyList()), status = Status.TODO, user = users[2])
    private val inProgressTask: Task = DefaultInProgressTask(
        title = "우리땅!",
        tags = Tags(emptyList()),
        status = Status.IN_PROGRESS,
        user = users[0],
    )
    private val doneTask: Task = DefaultDoneTask(title = "우리땅!", tags = Tags(emptyList()), status = Status.DONE, user = users[3])
    private val reviewTask: Task = DefaultReviewTask(
        title = "Review Task",
        tags = Tags(emptyList()),
        status = Status.REVIEW,
        user = users[1],
    )
}
