package woowacourse.kanban.board.ui.dialog.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.button_cancel
import kanbanboard.composeapp.generated.resources.button_create
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.ui.component.KanbanBoardButton
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun Footer(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
        content = content,
    )
}

@Composable
@Preview(showBackground = true)
private fun FooterPreview() {
    Footer {
        KanbanBoardButton(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = CustomTheme.colors.blue.w700,
            ),
        ) {
            Text(
                text = stringResource(Res.string.button_cancel),
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
            )
        }
        KanbanBoardButton(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomTheme.colors.purple.w100,
                contentColor = CustomTheme.colors.white,
                disabledContainerColor = CustomTheme.colors.purple.w400,
                disabledContentColor = CustomTheme.colors.white,
            ),
        ) {
            Text(
                text = stringResource(Res.string.button_create),
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
            )
        }
    }
}
