package woowacourse.kanban.board.app

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.App

@OptIn(ExperimentalTestApi::class)
class AppUiest {

    @Test
    fun `새 태스크 생성 버튼 클릭 시 다이얼로그가 노출된다`() = runComposeUiTest {
        setContent {
            App()
        }

        onNodeWithText("새 태스크 생성").performClick()
        onNode(isDialog()).assertIsDisplayed()
    }

    @Test
    fun `다이얼로그에서 취소 버튼 클릭 시 다이얼로그가 닫힌다`() = runComposeUiTest {
        setContent {
            App()
        }

        onNodeWithText("새 태스크 생성").performClick()
        onNodeWithText("취소").performClick()

        onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun `태스크 생성 완료 시 해당 상태 컬럼에 태스크가 노출된다`() = runComposeUiTest {
        setContent {
            App()
        }

        onNodeWithText("새 태스크 생성").performClick()
        onAllNodes(hasSetTextAction())[0].performTextInput("테스크")
        onNodeWithText("생성").performClick()

        onNodeWithText("테스크").assertIsDisplayed()
    }

    @Test
    fun `태스크 생성 완료 시 SnackBar가 노출된다`() = runComposeUiTest {
        setContent {
            App()
        }
        onNodeWithText("새 태스크 생성").performClick()
        onAllNodes(hasSetTextAction())[0].performTextInput("테스크")
        onNodeWithText("생성").performClick()

        onNodeWithText("새로운 태스크가 추가되었습니다.").assertIsDisplayed()
    }
}
