package woowacourse.kanban.board.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.button_cancel
import kanbanboard.composeapp.generated.resources.button_delete
import kanbanboard.composeapp.generated.resources.button_edit
import kanbanboard.composeapp.generated.resources.edit_dialog_title
import org.jetbrains.compose.resources.stringResource
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
fun TaskEditDialogScreen(
    task: Task,
    users: List<User>,
    onDismiss: () -> Unit,
    onEditResult: (Result<Task>) -> Unit,
    onClickDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val taskCreationState = remember { TaskEditState(users, task = task) }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
    ) {
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
                    value = taskCreationState.title,
                    onTitleChange = {
                        taskCreationState.updateTitle(it)
                    },
                    validation = taskCreationState.titleValidation,
                )

                DescriptionSection(
                    value = taskCreationState.content,
                    onContentChange = {
                        taskCreationState.updateContent(it)
                    },
                )

                TagSection(
                    value = taskCreationState.tag,
                    onTagChange = {
                        taskCreationState.updateTag(it)
                    },
                    validation = taskCreationState.tagValidation,
                )

                StatusSection(
                    selectedStatus = taskCreationState.selectedStatus,
                    onStatusChange = {
                        taskCreationState.updateStatus(it)
                    },
                )

                AssigneeSection(
                    userGroup = taskCreationState.validUsers,
                    selectedUser = taskCreationState.selectedUser,
                    onUserChange = {
                        taskCreationState.updateUser(it)
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
                    onClick = onClickDelete,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomTheme.colors.red.w50,
                        contentColor = CustomTheme.colors.white,
                    ),
                    modifier = Modifier.testTag("삭제 버튼"),
                ) {
                    Text(
                        text = stringResource(Res.string.button_delete),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                    )
                }
                KanbanBoardButton(
                    onClick = { onEditResult(taskCreationState.createEditedTask()) },
                    enabled = taskCreationState.canCreate,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomTheme.colors.purple.w100,
                        contentColor = CustomTheme.colors.white,
                        disabledContainerColor = CustomTheme.colors.purple.w400,
                        disabledContentColor = CustomTheme.colors.white,
                    ),
                    modifier = Modifier.testTag("수정 버튼"),
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
}
