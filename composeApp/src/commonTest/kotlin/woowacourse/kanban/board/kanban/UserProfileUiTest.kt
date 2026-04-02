package woowacourse.kanban.board.kanban

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.component.UserProfile
import woowacourse.kanban.board.ui.theme.CustomTheme

@OptIn(ExperimentalTestApi::class)
class UserProfileUiTest {

    @Test
    fun `전달된 유저 이름 표시`() = runComposeUiTest {
        val given = "다이노"
        setContent {
            CustomTheme {
                UserProfile(
                    User.Assignee(given),
                )
            }
        }

        onNodeWithText(given).assertIsDisplayed()
    }
}
