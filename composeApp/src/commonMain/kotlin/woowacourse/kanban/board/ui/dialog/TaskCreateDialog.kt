package woowacourse.kanban.board.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun TaskCreateDialog(
    users: List<User>,
    taskCreationState: TaskCreationState = remember { TaskCreationState(users) },
    onDismiss: () -> Unit = { },
    onResult: (Result<Task>) -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
    ) {
        TaskCreateForm(
            taskCreationState,
            modifier = Modifier.fillMaxWidth(0.6f)
                .fillMaxHeight(0.9f).clip(RoundedCornerShape(10.dp)).background(CustomTheme.colors.white),
            onDismiss = onDismiss,
            onClickCreate = {
                val result = taskCreationState.createTask()
                onResult(result)
            },
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TaskCreateDialogPreview() {
    TaskCreateDialog(users = listOf(User.None, User.Assignee("손흥민"), User.Assignee("봉준호"), User.Assignee("BTS"), User.Assignee("스마일")))
}
