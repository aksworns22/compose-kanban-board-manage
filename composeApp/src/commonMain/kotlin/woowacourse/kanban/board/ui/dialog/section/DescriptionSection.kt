package woowacourse.kanban.board.ui.dialog.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.description_placeholder
import kanbanboard.composeapp.generated.resources.label_description
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.ui.component.Label
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun DescriptionSection(modifier: Modifier = Modifier, value: String, onContentChange: (String) -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Label(stringResource(Res.string.label_description))
        OutlinedTextField(
            value = value,
            onValueChange = {
                onContentChange(it)
            },
            modifier = modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(Res.string.description_placeholder),
                    color = CustomTheme.colors.gray.w200,
                )
            },
            minLines = 6,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                unfocusedBorderColor = CustomTheme.colors.purple.w500,
                errorBorderColor = CustomTheme.colors.red.w50,
            ),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun DescriptionPreview() {
    DescriptionSection(
        value = "",
        onContentChange = {},
    )
}
