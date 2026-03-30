package woowacourse.kanban.board.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.ui.theme.CustomTheme

private const val REQUIRED_MARK = "*"

@Composable
fun Label(label: String, required: Boolean = false) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            color = CustomTheme.colors.blue.w700,
        )
        if (required) {
            Text(
                text = REQUIRED_MARK,
                color = CustomTheme.colors.blue.w700,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun LabelPreview() {
    Label("로미", true)
}
