package woowacourse.kanban.board.ui.board

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.domain.model.DefaultDoneTask
import woowacourse.kanban.board.domain.model.DefaultTodoTask
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.User

@OptIn(ExperimentalTestApi::class)
class KanbanBoardScreenTest {
    private val userDruid = User.Assignee("두루이드")
    private val users = listOf(User.None, userDruid)

    @Test
    fun `프로젝트가 하나도 존재하지 않는 경우 에러 메시지가 화면에 표시된다`() = runComposeUiTest {
        // given 프로젝트가 없는 보드 상태가 주어진다
        setContent {
            KanbanBoardScreen(kanbanBoardState = KanbanBoardState())
        }

        // then
        onNodeWithTag("빈 프로젝트 에러").assertIsDisplayed()
    }

    @Test
    fun `수정 다이어로그에서 제목이 비어있다면 태스크 수정 버튼이 비활성화된다`() = runComposeUiTest {
        // given 태스크 수정 다이어로그
        val kanbanProjectState = KanbanProjectState("테스트 프로젝트", users, toDoTaskWithAssignee)
        setContent {
            KanbanBoardScreen(kanbanBoardState = KanbanBoardState(KanbanProjectState("테스트 프로젝트", users, toDoTaskWithAssignee)))
        }
        onNodeWithContentDescription("${Status.TODO}상태의 ${toDoTaskWithAssignee.title}태스크").performClick()

        // when 제목 텍스트를 모두 지운다
        onNodeWithTag("수정 버튼").assertIsEnabled()
        onNodeWithTag("제목 입력폼").performTextClearance()

        // then 태스크 수정 버튼이 비활성화된다
        onNodeWithTag("수정 버튼").assertIsNotEnabled()
    }

    @Test
    fun `태스크 수정 다이어로그에서 Todo 상태면서 담당자가 있다면 Done 상태로 변경가능하다`() = runComposeUiTest {
        // given to-do 상태면서 담당자가 선택되어 있다
        setContent {
            KanbanBoardScreen(kanbanBoardState = KanbanBoardState(KanbanProjectState("테스트 프로젝트", users, toDoTaskWithAssignee)))
        }
        onNodeWithContentDescription("${Status.TODO}상태의 ${toDoTaskWithAssignee.title}태스크").performClick()

        // when done 상태를 클릭한 후 수정 버튼을 클릭한다
        onNodeWithTag("${Status.DONE} 버튼").performClick()
        onNodeWithTag("수정 버튼").performClick()

        // then 태스크가 done 상태로 변경된다
        onNodeWithContentDescription("${Status.TODO}상태의 ${toDoTaskWithAssignee.title}태스크").assertDoesNotExist()
        onNodeWithContentDescription("${Status.DONE}상태의 ${toDoTaskWithAssignee.title}태스크").assertExists()
    }

    @Test
    fun `태스크 수정 다이어로그에서 Todo 상태면서 담당자가 없을 때 In progress를 클릭하면 첫 번째 담당자가 자동 선택된다`() = runComposeUiTest {
        // given to-do 상태면서 담당자 없음이 선택되어 있다
        setContent {
            KanbanBoardScreen(
                kanbanBoardState = KanbanBoardState(KanbanProjectState("테스트 프로젝트", users, toDoTaskWithoutAssignee)),
            )
        }
        onNodeWithContentDescription("${Status.TODO}상태의 ${toDoTaskWithoutAssignee.title}태스크").performClick()

        // when in-progress 상태를 클릭한다
        onNodeWithTag("${User.None} 선택 버튼").assertIsSelected()
        onNodeWithTag("${Status.IN_PROGRESS} 버튼").performClick()

        // then 첫 번째 담당자가 자동으로 선택되고 In Progress 상태로 바뀐다
        onNodeWithTag("$userDruid 선택 버튼").assertIsSelected()
        onNodeWithTag("${Status.IN_PROGRESS} 버튼").assertIsSelected()
    }

    @Test
    fun `Todo 상태면서 담당자가 지정되지 않은 태스크카드를 In Progress 상태로 이동시키면 Todo 상태로 남아있다`() = runComposeUiTest {
        // given to-do 상태가 선택되어 있고 담당자가 없음 상태인 태스크
        setContent {
            KanbanBoardScreen(
                kanbanBoardState = KanbanBoardState(KanbanProjectState("테스트 프로젝트", users, toDoTaskWithoutAssignee)),
            )
        }

        // when 해당 태스크를 In Progress 상태로 드래그앤드롭한다
        val baseTouchOffset =
            onNodeWithContentDescription("${toDoTaskWithoutAssignee.status}상태의 ${toDoTaskWithoutAssignee.title}태스크")
                .fetchSemanticsNode().boundsInWindow.center
        val targetTouchOffset = onNodeWithContentDescription("${Status.IN_PROGRESS} 태스크 목록")
            .fetchSemanticsNode().boundsInWindow.center
        onNodeWithContentDescription("${toDoTaskWithoutAssignee.status}상태의 ${toDoTaskWithoutAssignee.title}태스크")
            .performTouchInput {
                down(center)
                moveTo(targetTouchOffset - baseTouchOffset)
                up()
            }

        // then 태스크가 이동하지 않고 to-do 상태로 남아있다
        onNodeWithContentDescription("${toDoTaskWithoutAssignee.status}상태의 ${toDoTaskWithoutAssignee.title}태스크")
            .assertIsDisplayed()
    }

    @Test
    fun `Todo 상태면서 담당자가 지정된 태스크카드를 In Progress 상태로 이동시키면 상태가 In Progress로 변경된다`() = runComposeUiTest {
        // Given to-do 상태가 선택되어 있고 담당자가 존재하는 태스크
        setContent {
            KanbanBoardScreen(
                kanbanBoardState = KanbanBoardState(
                    KanbanProjectState(name = "A 프로젝트", users = users, toDoTaskWithAssignee),
                    KanbanProjectState(name = "B 프로젝트", users = users),
                ),
            )
        }

        // When 해당 태스크를 In Progress 상태로 드래그앤드롭한다
        val baseTouchOffset = onNodeWithContentDescription("${toDoTaskWithAssignee.status}상태의 ${toDoTaskWithAssignee.title}태스크")
            .fetchSemanticsNode().boundsInWindow.center
        val targetTouchOffset = onNodeWithContentDescription("${Status.IN_PROGRESS} 태스크 목록")
            .fetchSemanticsNode().boundsInWindow.center
        onNodeWithContentDescription("${toDoTaskWithAssignee.status}상태의 ${toDoTaskWithAssignee.title}태스크").performTouchInput {
            down(center)
            moveTo(targetTouchOffset - baseTouchOffset)
            up()
        }

        // Then 태스크가 In Progress 상태로 변경된다
        onNodeWithContentDescription("${Status.IN_PROGRESS}상태의 ${toDoTaskWithAssignee.title}태스크").assertExists()
    }

    @Test
    fun `to do 상태의 태스크를 삭제하면 화면에서 표시되지 않는다`() = runComposeUiTest {
        // Given to-do 상태의 태스크에 대한 다이어로그가 표시된 상태
        setContent {
            KanbanBoardScreen(
                kanbanBoardState = KanbanBoardState(
                    KanbanProjectState("A 프로젝트", users, toDoTaskWithAssignee),
                    KanbanProjectState("B 프로젝트", users),
                ),
            )
        }
        onNodeWithContentDescription("${toDoTaskWithAssignee.status}상태의 ${toDoTaskWithAssignee.title}태스크").performClick()

        // When 삭제 버튼을 클릭한다
        onNodeWithTag("삭제 버튼").performClick()

        // Then 태스크 카드가 화면에 표시되지 않는다
        onNodeWithContentDescription("${toDoTaskWithAssignee.status}상태의 ${toDoTaskWithAssignee.title}태스크").assertDoesNotExist()
    }

    @Test
    fun `done 상태의 태스크를 삭제하려 시도하면 실패하여 화면에 그대로 표시된다`() = runComposeUiTest {
        // Given done 상태의 태스크에 대한 다이어로그가 표시된 상태
        setContent {
            KanbanBoardScreen(
                kanbanBoardState = KanbanBoardState(KanbanProjectState("A 프로젝트", users, doneTask), KanbanProjectState("B 프로젝트", users)),
            )
        }
        onNodeWithContentDescription("${doneTask.status}상태의 ${doneTask.title}태스크").performClick()

        // When 삭제 버튼을 클릭한다
        onNodeWithTag("삭제 버튼").performClick()

        // Then 태스크 카드가 화면에 그대로 표시된다
        onNodeWithContentDescription("${doneTask.status}상태의 ${doneTask.title}태스크").assertIsDisplayed()
    }

    private val toDoTaskWithAssignee = DefaultTodoTask(
        title = "Todo 태스크",
        tags = Tags(emptyList()),
        user = User.Assignee("사용자"),
        status = Status.TODO,
    )

    private val toDoTaskWithoutAssignee = DefaultTodoTask(
        title = "Todo 태스크",
        tags = Tags(emptyList()),
        user = User.None,
        status = Status.TODO,
    )

    private val doneTask = DefaultDoneTask(
        title = "Done 태스크",
        tags = Tags(emptyList()),
        user = User.Assignee("사용자"),
        status = Status.DONE,
    )
}
