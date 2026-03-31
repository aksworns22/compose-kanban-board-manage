package woowacourse.kanban.board.ui.dialog.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.label_tag
import kanbanboard.composeapp.generated.resources.tag_placeholder
import kanbanboard.composeapp.generated.resources.tag_supporting_text
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.model.Tag.Companion.MAX_TAG_LENGTH
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.validator.ValidationResult
import woowacourse.kanban.board.ui.component.Label
import woowacourse.kanban.board.ui.component.SingleLineTextField
import woowacourse.kanban.board.ui.util.toMessage

@Composable
fun TagSection(
    modifier: Modifier = Modifier,
    value: String,
    onTagChange: (String) -> Unit,
    validation: ValidationResult = ValidationResult.Initial,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Label(stringResource(Res.string.label_tag))
        SingleLineTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { onTagChange(it) },
            isError = validation is ValidationResult.Invalid,
            placeHolder = stringResource(Res.string.tag_supporting_text, MAX_TAG_LENGTH, Tags.MAX_TAG_SIZE),
            supportingText = (validation as? ValidationResult.Invalid)?.error?.let {
                stringResource(
                    it.toMessage(),
                    MAX_TAG_LENGTH,
                    Tags.MAX_TAG_SIZE,
                )
            } ?: stringResource(Res.string.tag_placeholder),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TagPreview() {
    TagSection(
        value = "",
        onTagChange = {},
    )
}
