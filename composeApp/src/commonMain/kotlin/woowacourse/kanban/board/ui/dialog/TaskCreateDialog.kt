package woowacourse.kanban.board.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun TaskCreateDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (title: String, content: String, tags: List<String>, status: Status, assignee: User) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
        content = {
            TaskCreateForm(
                modifier = Modifier.fillMaxWidth(0.6f)
                    .fillMaxHeight(0.9f).clip(RoundedCornerShape(10.dp)).background(CustomTheme.colors.white),
                onDismiss = onDismissRequest,
                assignees = listOf(User("다이노"), User("다이노소어"), User("우우우")),
                onClickCreate = onConfirm,
            )
        },
    )
}

@Composable
@Preview(showBackground = true)
private fun TaskCreateDialogPreview() {
    TaskCreateDialog(
        onDismissRequest = {},
        onConfirm = { _, _, _, _, _ -> },
    )
}
