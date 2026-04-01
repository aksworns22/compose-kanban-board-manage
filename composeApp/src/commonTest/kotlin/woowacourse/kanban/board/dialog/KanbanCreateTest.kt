package woowacourse.kanban.board.dialog

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.dialog.TaskCreateForm
import woowacourse.kanban.board.ui.dialog.TaskCreationState
import woowacourse.kanban.board.ui.dialog.TaskValidationState
import woowacourse.kanban.board.ui.theme.CustomTheme

@OptIn(ExperimentalTestApi::class)
class KanbanCreateTest {

    @Test
    fun `제목과 태그에 오류가 없고 제목이 공백이 아닐 경우 활성화된다`() = runComposeUiTest {
        setContent {
            CustomTheme {
                TaskCreateForm(
                    taskCreationState = TaskCreationState(TaskValidationState(listOf(User("우테코"), User("테코")))),
                    onDismiss = {},
                    onClickCreate = {},
                )
            }
        }
        onAllNodes(hasSetTextAction())[0]
            .performTextInput("할 일")

        onAllNodes(hasSetTextAction())[1]
            .performTextInput("우테코,테코")

        onNodeWithText("생성")
            .assertIsEnabled()
    }

    @Test
    fun `제목 에러 발생시 비활성화된다`() = runComposeUiTest {
        setContent {
            CustomTheme {
                TaskCreateForm(
                    taskCreationState = TaskCreationState(
                        TaskValidationState(
                            listOf(User("우테코"), User("테코")),
                        ),
                    ),
                    onDismiss = {},
                    onClickCreate = { },
                )
            }
        }

        onAllNodes(hasSetTextAction())[0]
            .performTextInput("할 일")

        onAllNodes(hasSetTextAction())[0]
            .performTextClearance()

        onNodeWithText("생성")
            .assertIsNotEnabled()
    }

    @Test
    fun `태그 에러 발생시 비활성화된다`() = runComposeUiTest {
        setContent {
            CustomTheme {
                TaskCreateForm(
                    onDismiss = {},
                    taskCreationState = TaskCreationState(
                        TaskValidationState(
                            listOf(User("우테코"), User("테코")),
                        ),
                    ),
                    onClickCreate = { },
                )
            }
        }

        onAllNodes(hasSetTextAction())[1]
            .performTextInput("우아한테크코스")

        onNodeWithText("생성")
            .assertIsNotEnabled()
    }
}
