package woowacourse.kanban.board.ui.board

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User.Assignee

class KanbanBoardStateTest {
    @Test
    fun `3개의 프로젝트가 주어졌을 때 0번 프로젝트에서 1번 프로젝트로 이동하면 현재 프로젝트는 1번 프로젝트가 된다`() {
        // given
        val projects = arrayOf(
            KanbanProjectState("0번 프로젝트", users = emptyList()),
            KanbanProjectState("1번 프로젝트", users = emptyList()),
            KanbanProjectState("2번 프로젝트", users = emptyList()),
        )
        val kanbanBoardState = KanbanBoardState(*projects)

        // when
        kanbanBoardState.currentProject.onSuccess { currentProject ->
            assertThat(currentProject).isEqualTo(projects[0])
        }
        kanbanBoardState.selectProject(1)

        // then
        kanbanBoardState.currentProject.onSuccess { currentProject ->
            assertThat(currentProject).isEqualTo(projects[1])
        }
    }

    @Test
    fun `1개의 프로젝트가 주어졌을 때 0번 프로젝트에서 범위를 넘어가는 2번 프로젝트로 이동하면 현재 프로젝트는 변하지 않는다`() {
        // given
        val project = KanbanProjectState("0번 프로젝트", users = emptyList())
        val kanbanBoardState = KanbanBoardState(project)

        // when
        kanbanBoardState.selectProject(2)

        // then
        kanbanBoardState.currentProject.onSuccess { currentProject ->
            assertThat(currentProject).isEqualTo(project)
        }
    }

    @Test
    fun `1개의 프로젝트가 주어졌을 때 0번 프로젝트에서 음수 번호의 프로젝트로 이동하면 현재 프로젝트는 변하지 않는다`() {
        // given
        val project = KanbanProjectState("0번 프로젝트", users = emptyList())
        val kanbanBoardState = KanbanBoardState(project)

        // when
        kanbanBoardState.selectProject(-1)

        // then
        kanbanBoardState.currentProject.onSuccess { currentProject ->
            assertThat(currentProject).isEqualTo(project)
        }
    }

    @Test
    fun `빈 프로젝트일 때 새로운 태스크를 1개 추가하면 전체 태스크 개수가 1개 늘어난다`() {
        // given
        val project = KanbanProjectState("0번 프로젝트", users = emptyList())

        // when
        assertThat(project.totalCount).isEqualTo(0)
        project.addTask(Task(title = "1번 태스크", tags = Tags(emptyList()), user = Assignee("dino"), status = Status.TODO))

        // then
        assertThat(project.totalCount).isEqualTo(1)
    }

    @Test
    fun `프로젝트에 TODO 상태의 태스크를 IN_PROGRESS 상태로 변경하면 해당 태스크의 상태가 IN_PROGRESS로 변경된다`() {
        // given
        val task = Task(title = "1번 태스크", tags = Tags(emptyList()), user = Assignee("dino"), status = Status.TODO)
        val project = KanbanProjectState(name = "0번 프로젝트", users = emptyList(), task)
        val expectedTask = task.copy(status = Status.IN_PROGRESS)

        // when
        assertThat(project.getTasks(Status.IN_PROGRESS)).doesNotContain(expectedTask)
        project.changeTaskStatus(task, Status.IN_PROGRESS)

        // then
        assertThat(project.getTasks(Status.TODO)).isEmpty()
        assertThat(project.getTasks(Status.IN_PROGRESS)).contains(expectedTask)
    }
}
