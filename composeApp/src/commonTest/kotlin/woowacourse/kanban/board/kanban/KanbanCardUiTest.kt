package woowacourse.kanban.board.kanban

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.domain.model.DefaultTodoTask
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tag
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.board.TaskCard
import woowacourse.kanban.board.ui.theme.CustomTheme

@OptIn(ExperimentalTestApi::class)
class KanbanCardUiTest {
    @Test
    fun `모든 필드가 있는 카드 - 제목 설명 태그 유저 모두 노출`() = runComposeUiTest {
        val title = "LazyColumn 컴포넌트 구현"
        val content = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다."
        val tags = Tags(listOf(Tag("컴포넌트"), Tag("성능")))
        val assignee = User.Assignee(name = "다이노")

        setContent {
            CustomTheme {
                TaskCard(DefaultTodoTask(title = title, description = content, tags = tags, user = assignee, status = Status.TODO))
            }
        }

        onNodeWithText(title).assertIsDisplayed()
        onNodeWithText(content).assertIsDisplayed()
        onNodeWithText("컴포넌트", useUnmergedTree = true).assertIsDisplayed()
        onNodeWithText("성능", useUnmergedTree = true).assertIsDisplayed()
        onNodeWithText(assignee.name).assertIsDisplayed()
    }
}
