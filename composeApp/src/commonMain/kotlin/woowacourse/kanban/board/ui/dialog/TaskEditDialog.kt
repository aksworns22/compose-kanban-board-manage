package woowacourse.kanban.board.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.button_cancel
import kanbanboard.composeapp.generated.resources.button_edit
import kanbanboard.composeapp.generated.resources.edit_dialog_title
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.component.KanbanBoardButton
import woowacourse.kanban.board.ui.dialog.section.AssigneeSection
import woowacourse.kanban.board.ui.dialog.section.DescriptionSection
import woowacourse.kanban.board.ui.dialog.section.Footer
import woowacourse.kanban.board.ui.dialog.section.StatusSection
import woowacourse.kanban.board.ui.dialog.section.TagSection
import woowacourse.kanban.board.ui.dialog.section.TaskFormHeader
import woowacourse.kanban.board.ui.dialog.section.TitleSection
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun TaskEditDialogScreen(task: Task, onDismiss: () -> Unit, onResult: (Result<Task>) -> Unit = { }) {
    val taskEditState = remember { TaskEditState(task, listOf(User("다이노"), User("다이노소어"), User("우우우"))) }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
    ) {
        TaskEditDialogContent(
            taskEditState = taskEditState,
            onDismiss = onDismiss,
            onClickEdit = { onResult(taskEditState.createEditedTask()) },
            modifier = Modifier.fillMaxWidth(0.6f)
                .fillMaxHeight(0.9f).clip(RoundedCornerShape(10.dp)).background(CustomTheme.colors.white),
        )
    }
}

@Composable
fun TaskEditDialogContent(taskEditState: TaskEditState, modifier: Modifier = Modifier, onDismiss: () -> Unit, onClickEdit: () -> Unit) {
    Column(
        modifier = modifier,
    ) {
        TaskFormHeader(
            onDismiss = onDismiss,
        ) {
            Text(
                text = stringResource(Res.string.edit_dialog_title),
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = CustomTheme.colors.gray.w600,
            )
        }
        HorizontalDivider()
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),

        ) {
            TitleSection(
                value = taskEditState.validationState.title,
                onTitleChange = {
                    taskEditState.validationState.updateTitle(it)
                },
                validation = taskEditState.validationState.titleValidation,
            )

            DescriptionSection(
                value = taskEditState.validationState.content,
                onContentChange = {
                    taskEditState.validationState.updateContent(it)
                },
            )

            TagSection(
                value = taskEditState.validationState.tag,
                onTagChange = {
                    taskEditState.validationState.updateTag(it)
                },
                validation = taskEditState.validationState.tagValidation,
            )

            StatusSection(
                selectedStatus = taskEditState.validationState.selectedStatus,
                onStatusChange = {
                    taskEditState.validationState.updateStatus(it)
                },
            )

            AssigneeSection(
                managers = taskEditState.validationState.assignees,
                selectedUser = taskEditState.validationState.selectedAssignee,
                onUserChange = {
                    taskEditState.validationState.updateAssignee(it)
                },
            )
        }
        HorizontalDivider()
        Footer {
            KanbanBoardButton(
                onClick = onDismiss,
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
                onClick = onClickEdit,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomTheme.colors.purple.w100,
                    contentColor = CustomTheme.colors.white,
                    disabledContainerColor = CustomTheme.colors.purple.w200,
                    disabledContentColor = CustomTheme.colors.white,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.button_edit),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 672,
)
@Composable
private fun TaskEditContentPreview() {
    CustomTheme {
        TaskEditDialogContent(
            taskEditState =
            TaskEditState(
                originalTask = Task(
                    title = "제목",
                    tags = Tags(emptyList()),
                    user = User("구루루"),
                    status = Status.TODO,
                ),
                assignees = listOf(User("구루루")),
            ),
            onDismiss = {},
            onClickEdit = {},
        )
    }
}
