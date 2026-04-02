package woowacourse.kanban.board.ui.board

import androidx.compose.ui.test.ExperimentalTestApi
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
            KanbanBoardScreen(kanbanBoardState = KanbanBoardState(KanbanProjectState("테스트 프로젝트", toDoTaskWithUser)))
        }
        onNodeWithContentDescription("${Status.TODO}상태의 ${toDoTaskWithUser.title}태스크").performClick()

        // when
        onNodeWithTag("${Status.DONE} 버튼").performClick()
        onNodeWithTag("수정 버튼").performClick()

        // then
        onNodeWithContentDescription("${Status.TODO}상태의 ${toDoTaskWithUser.title}태스크").assertDoesNotExist()
        onNodeWithContentDescription("${Status.DONE}상태의 ${toDoTaskWithUser.title}태스크").assertExists()
    }

    private val toDoTaskWithUser = Task(
        title = "Todo 태스크",
        tags = Tags(emptyList()),
        user = User("사용자"),
        status = Status.TODO,
    )
}
