package woowacourse.kanban.board.ui.board

import kotlin.test.Test
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User

class KanbanProjectTest {
    @Test
    fun `빈 프로젝트가 주어질 때 새로운 태스크를 추가하면 프로젝트 태스크의 전체 개수가 하나 증가한다`() {
        // given
        val project = KanbanProjectState(name = "독도는 우리땅")

        // when
        project.addTask(
            Task(
                title = "우리땅!",
                tags = Tags(emptyList()),
                status = Status.TODO,
                user = User("두릅"),
            ),
        )

        // then
        assertThat(project.totalCount).isEqualTo(1)
    }

    @Test
    fun `TODO 상태의 태스크를 IN_PROGRESS로 변경하면 태스크의 상태가 IN_PROGRESS로 변경된다`() {
        // given
        val task =
            Task(title = "우리땅!", tags = Tags(emptyList()), status = Status.TODO, user = User("두릅"))
        val expectedTask = task.copy(status = Status.IN_PROGRESS)
        val project = KanbanProjectState(name = "독도는 우리땅", task)

        // when
        project.changeTaskStatus(task, Status.IN_PROGRESS)

        // then
        assertThat(project.getTasks(Status.IN_PROGRESS)).contains(expectedTask)
    }

    @Test
    fun `여러 상태의 태스크를 가진 프로젝트에서 DONE 상태의 태스크를 가져오면 DONE 상태의 태스크만 반환된다`() {
        // given
        val toDoTask =
            Task(title = "우리땅!", tags = Tags(emptyList()), status = Status.TODO, user = User("두릅"))
        val inProgressTask = Task(
            title = "우리땅!",
            tags = Tags(emptyList()),
            status = Status.IN_PROGRESS,
            user = User("부릅"),
        )
        val doneTask =
            Task(title = "우리땅!", tags = Tags(emptyList()), status = Status.DONE, user = User("꽈뚜릅"))
        val project = KanbanProjectState(name = "독도는 우리땅", toDoTask, inProgressTask, doneTask)

        // when
        val doneTasks = project.getTasks(Status.DONE)

        // then
        assertThat(doneTasks).contains(doneTask)
    }

    @Test
    fun `1개의 태스크를 가진 프로젝트라면 전체 태스크 개수는 1개이다`() {
        // given
        val project =
            KanbanProjectState(
                name = "독도는 우리땅",
                Task(
                    title = "우리땅!",
                    tags = Tags(emptyList()),
                    status = Status.TODO,
                    user = User("두릅"),
                ),
            )

        // then
        Assertions.assertThat(project.totalCount).isEqualTo(1)
    }

    @Test
    fun `프로젝트의 DONE 상태의 태스크가 1개라면 완료한 개수도 1개이다`() {
        // given
        val doneTask =
            Task(title = "우리땅!", tags = Tags(emptyList()), status = Status.DONE, user = User("꽈뚜릅"))
        val project = KanbanProjectState(name = "독도는 우리땅", doneTask)

        // then
        assertThat(project.completeCount).isEqualTo(1)
    }

    @Test
    fun `3개의 태스크 중 한개만 DONE 상태라면 완료율의 소수 둘째자리까지의 값은 0_33에 가깝다`() {
        // given
        val toDoTask =
            Task(title = "우리땅!", tags = Tags(emptyList()), status = Status.TODO, user = User("두릅"))
        val inProgressTask = Task(
            title = "우리땅!",
            tags = Tags(emptyList()),
            status = Status.IN_PROGRESS,
            user = User("부릅"),
        )
        val doneTask =
            Task(title = "우리땅!", tags = Tags(emptyList()), status = Status.DONE, user = User("꽈뚜릅"))
        val project = KanbanProjectState(name = "독도는 우리땅", toDoTask, inProgressTask, doneTask)

        // then
        assertThat(project.completeRatio).isCloseTo(0.33f, Offset.offset(0.01f))
    }

    @Test
    fun `프로젝트에 태스크가 없다면 완료율은 0이다`() {
        // given
        val project = KanbanProjectState(name = "독도는 우리땅")

        // then
        assertThat(project.completeRatio).isEqualTo(0f)
    }
}
