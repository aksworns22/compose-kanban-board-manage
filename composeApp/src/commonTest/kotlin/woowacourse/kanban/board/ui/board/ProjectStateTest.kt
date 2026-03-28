package woowacourse.kanban.board.ui.board

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User

class ProjectStateTest {
    @Test
    fun `프로젝트가 하나도 없다면 예외를 발생시킨다`() {
        assertThatThrownBy {
            ProjectState()
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `3개의 프로젝트 중 0번 프로젝트에서 1번 프로젝트로 이동하면 현재 프로젝트는 1번 프로젝트가 된다`() {
        val projects = arrayOf(
            KanbanProject("0번 프로젝트", emptyList()),
            KanbanProject("1번 프로젝트", emptyList()),
            KanbanProject("2번 프로젝트", emptyList()),
        )
        val projectState = ProjectState(*projects)

        assertThat(projectState.currentProject).isEqualTo(projects[0])
        projectState.selectProject(1)

        assertThat(projectState.currentProject).isEqualTo(projects[1])
    }

    @Test
    fun `1개의 프로젝트 중 0번 프로젝트에서 범위를 넘어가는 2번 프로젝트로 이동하면 현재 프로젝트는 변하지 않는다`() {
        val project = KanbanProject("0번 프로젝트", emptyList())
        val projectState = ProjectState(project)

        projectState.selectProject(2)
        assertThat(projectState.currentProject).isEqualTo(project)
    }

    @Test
    fun `0번 프로젝트에서 음수 번호의 프로젝트로 이동하면 현재 프로젝트는 변하지 않는다`() {
        val project = KanbanProject("0번 프로젝트", emptyList())
        val projectState = ProjectState(project)

        projectState.selectProject(-1)
        assertThat(projectState.currentProject).isEqualTo(project)
    }

    @Test
    fun `프로젝트에 새로운 태스크를 1개 추가하면 해당 프로젝트의 전체 태스크 개수가 1개 늘어난다`() {
        val project = KanbanProject("0번 프로젝트", emptyList())
        val projectState = ProjectState(project)

        assertThat(projectState.currentProject.totalCount).isEqualTo(0)
        projectState.createTask(Task(title = "1번 태스크", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO))

        assertThat(projectState.currentProject.totalCount).isEqualTo(1)
    }

    @Test
    fun `프로젝트에 TODO 상태의 태스크를 IN_PROGRESS 상태로 변경하면 해당 태스크의 상태가 IN_PROGRESS로 변경된다`() {
        val project = KanbanProject("0번 프로젝트", emptyList())
        val projectState = ProjectState(project)
        val startTask = Task(title = "1번 태스크", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO)
        val expectedTask = startTask.copy(status = Status.IN_PROGRESS)

        projectState.createTask(startTask)
        assertThat(projectState.currentProject.getTasks(Status.IN_PROGRESS)).doesNotContain(expectedTask)
        projectState.changeTaskStatus(startTask, Status.IN_PROGRESS)

        assertThat(projectState.currentProject.getTasks(Status.TODO)).isEmpty()
        assertThat(projectState.currentProject.getTasks(Status.IN_PROGRESS)).contains(expectedTask)
    }
}
