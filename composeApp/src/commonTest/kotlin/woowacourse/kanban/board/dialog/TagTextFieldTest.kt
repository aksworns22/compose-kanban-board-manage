package woowacourse.kanban.board.dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.domain.validator.TaskValidator
import woowacourse.kanban.board.domain.validator.ValidationResult
import woowacourse.kanban.board.ui.dialog.section.TagSection
import woowacourse.kanban.board.ui.theme.CustomTheme

@OptIn(ExperimentalTestApi::class)
class TagTextFieldTest {

    @Test
    fun `글과 글 사이는 쉼표로 구분되고 공백이 있을 경우 에러 문구가 표시된다`() = runComposeUiTest {
        setContent {
            var tagText by remember { mutableStateOf("") }
            var validation by remember { mutableStateOf<ValidationResult>(ValidationResult.Initial) }

            CustomTheme {
                TagSection(
                    value = tagText,
                    onTagChange = {
                        tagText = it
                        validation = TaskValidator.validateTags(it)
                    },
                    validation = validation,
                )
            }
        }

        onNode(hasSetTextAction()).performTextInput(",,,,,,")

        onNodeWithText("태그 형식이 올바르지 않습니다.", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun `쉼표로 구분된 문자열이 5자 초과일 경우 에러 문구가 표시된다`() = runComposeUiTest {
        setContent {
            var tagText by remember { mutableStateOf("") }
            var validation by remember { mutableStateOf<ValidationResult>(ValidationResult.Initial) }

            CustomTheme {
                TagSection(
                    value = tagText,
                    onTagChange = {
                        tagText = it
                        validation = TaskValidator.validateTags(it)
                    },
                    validation = validation,
                )
            }
        }

        onNode(hasSetTextAction()).performTextInput("우아한테크코스")

        onNodeWithText("태그는 5자 이내로 5개까지만 등록할 수 있습니다.", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun `쉼표로 구분된 개수가 5개 초과일 경우 에러 문구가 표시된다`() = runComposeUiTest {
        setContent {
            var tagText by remember { mutableStateOf("") }
            var validation by remember { mutableStateOf<ValidationResult>(ValidationResult.Initial) }

            CustomTheme {
                TagSection(
                    value = tagText,
                    onTagChange = {
                        tagText = it
                        validation = TaskValidator.validateTags(it)
                    },
                    validation = validation,
                )
            }
        }

        onNode(hasSetTextAction()).performTextInput("우아한,테크,메가커피,아이폰,컴포즈,하이하이")

        onNodeWithText("태그는 5자 이내로 5개까지만 등록할 수 있습니다.", useUnmergedTree = true)
            .assertExists()
    }
}
