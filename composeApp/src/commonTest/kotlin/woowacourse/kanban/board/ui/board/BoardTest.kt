package woowacourse.kanban.board.ui.board

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tag
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.theme.CustomTheme

@OptIn(ExperimentalTestApi::class)
class BoardTest {

    @Test
    fun `사용자가 프로젝트 선택하면 해당하는 해당 프로젝트 화면으로 전환된다`() = runComposeUiTest {

        // Given 현재 프로젝트가 A프로젝트
        val state = KanbanBoardState(KanbanProjectState("A 프로젝트"), KanbanProjectState("B 프로젝트"))

        // When B 프로젝트를 선택한다.
        setContent {
            CustomTheme {
                KanbanBoardScreen(
                    kanbanBoardState = state,
                )
            }
        }
        onNode(isHeading()).assertTextEquals("A 프로젝트")
        onNodeWithContentDescription(label = "B 프로젝트 전환 버튼").performClick()

        // Then B 프로젝트 화면으로 이등한다
        onNode(isHeading()).assertTextEquals("B 프로젝트")
    }

    @Test
    fun `태스크박스가 아닌 곳에 드래그앤드롭 할 경우 상태가 바뀌지 않는다`() = runComposeUiTest {
        // Given to-do 상태의 A 태스크가 있다
        val task = Task(
            title = "A 태스크",
            tags = Tags(listOf(Tag("웃지마"))),
            user = User.Assignee("정준하"),
            status = Status.TODO,
        )
        val state = KanbanBoardState(KanbanProjectState("A 프로젝트", task), KanbanProjectState("B 프로젝트"))

        // When 사용자가 태스크를 드래그앤드롭한다
        setContent {
            CustomTheme {
                KanbanBoardScreen(
                    kanbanBoardState = state,
                )
            }
        }
        val targetArea = onNodeWithContentDescription("Project SideBar").fetchSemanticsNode().boundsInWindow.center
        onNodeWithContentDescription("${task.status}상태의 ${task.title}태스크").performTouchInput {
            down(center)
            moveTo(targetArea)
            up()
        }
        // Then  to-do 상태의 A 태스크가 있다
        onNodeWithContentDescription("${task.status}상태의 ${task.title}태스크").assertExists()
    }

    @Test
    fun `동일한 상태의 태스크박스에 드롭할 경우 상태가 바뀌지 않는다`() = runComposeUiTest {
        // Given to-do 상태의 A 태스크가 있다
        val task = Task(
            title = "A 태스크",
            tags = Tags(listOf(Tag("웃지마"))),
            user = User.Assignee("정준하"),
            status = Status.TODO,
        )
        val state = KanbanBoardState(KanbanProjectState("A 프로젝트", task), KanbanProjectState("B 프로젝트"))

        // When 사용자가 태스크를 드래그앤드롭한다
        setContent {
            CustomTheme {
                KanbanBoardScreen(
                    kanbanBoardState = state,
                )
            }
        }
        val baseTouchOffset = onNodeWithContentDescription("${task.status}상태의 ${task.title}태스크").fetchSemanticsNode().boundsInWindow.center
        val targetTouchOffset = onNodeWithContentDescription("${Status.TODO} 태스크 목록").fetchSemanticsNode().boundsInWindow.center

        onNodeWithContentDescription("${task.status}상태의 ${task.title}태스크").performTouchInput {
            down(center)
            moveTo(targetTouchOffset - baseTouchOffset)
            up()
        }
        // Then to-do상태 태스크 박스에 A태스크가 표시된다
        onNodeWithContentDescription("${Status.TODO}상태의 ${task.title}태스크").assertExists()
    }

    @Test
    fun `다른 상태의 태스크박스에 드롭할 경우 해당 상태로 변경한다`() = runComposeUiTest {
        // Given to-do상태의 A 태스크를 in-progress상태의 태스크 박스로 드래그한다
        val task = Task(
            title = "A 태스크",
            tags = Tags(listOf(Tag("웃지마"))),
            user = User.Assignee("정준하"),
            status = Status.TODO,
        )
        val state = KanbanBoardState(KanbanProjectState("A 프로젝트", task), KanbanProjectState("B 프로젝트"))

        // When 사용자가 태스크를 드롭한다
        setContent {
            CustomTheme {
                KanbanBoardScreen(
                    kanbanBoardState = state,
                )
            }
        }
        val baseTouchOffset = onNodeWithContentDescription("${task.status}상태의 ${task.title}태스크").fetchSemanticsNode().boundsInWindow.center
        val targetTouchOffset = onNodeWithContentDescription("${Status.IN_PROGRESS} 태스크 목록").fetchSemanticsNode().boundsInWindow.center
        onNodeWithContentDescription("${task.status}상태의 ${task.title}태스크").performTouchInput {
            down(center)
            moveTo(targetTouchOffset - baseTouchOffset)
            up()
        }
        // Then to-do상태 태스크 박스에서 A태스크가 사라지고, in-progress상태 태스크 박스에 A태스크가 표시된다
        onNodeWithContentDescription("${Status.IN_PROGRESS}상태의 ${task.title}태스크").assertExists()
    }
}
