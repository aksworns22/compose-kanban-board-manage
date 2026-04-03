package woowacourse.kanban.board.ui.board

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User

@OptIn(ExperimentalTestApi::class)
class KanbanBoardScreenTest {
    @Test
    fun `태스크 수정 다이어로그에서 Todo 상태면서 담당자가 있다면 Done 상태로 변경가능하다`() = runComposeUiTest {
        // given
        setContent {
            KanbanBoardScreen(kanbanBoardState = KanbanBoardState(KanbanProjectState("테스트 프로젝트", toDoTaskWithAssignee)))
        }
        onNodeWithContentDescription("${Status.TODO}상태의 ${toDoTaskWithAssignee.title}태스크").performClick()

        // when
        onNodeWithTag("${Status.DONE} 버튼").performClick()
        onNodeWithTag("수정 버튼").performClick()

        // then
        onNodeWithContentDescription("${Status.TODO}상태의 ${toDoTaskWithAssignee.title}태스크").assertDoesNotExist()
        onNodeWithContentDescription("${Status.DONE}상태의 ${toDoTaskWithAssignee.title}태스크").assertExists()
    }

    @Test
    fun `태스크 수정 다이어로그에서 Todo 상태면서 담당자가 없을 때 In progress를 클릭하면 첫 번째 담당자가 자동 선택된다`() = runComposeUiTest {
        // given
        val userDruid = User.Assignee("두루이드")
        setContent {
            KanbanBoardScreen(
                users = listOf(User.None, userDruid),
                kanbanBoardState = KanbanBoardState(KanbanProjectState("테스트 프로젝트", toDoTaskWithoutAssignee)),
            )
        }
        onNodeWithContentDescription("${Status.TODO}상태의 ${toDoTaskWithoutAssignee.title}태스크").performClick()

        // when
        onNodeWithTag("${User.None} 선택 버튼").assertIsSelected()
        onNodeWithTag("${Status.IN_PROGRESS} 버튼").performClick()

        // then
        onNodeWithTag("$userDruid 선택 버튼").assertIsSelected()
        onNodeWithTag("${Status.IN_PROGRESS} 버튼").assertIsSelected()
    }

    private val toDoTaskWithAssignee = Task(
        title = "Todo 태스크",
        tags = Tags(emptyList()),
        user = User.Assignee("사용자"),
        status = Status.TODO,
    )

    private val toDoTaskWithoutAssignee = Task(
        title = "Todo 태스크",
        tags = Tags(emptyList()),
        user = User.None,
        status = Status.TODO,
    )
}
