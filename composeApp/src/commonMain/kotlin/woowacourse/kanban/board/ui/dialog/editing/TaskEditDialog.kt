package woowacourse.kanban.board.ui.dialog.editing

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.button_cancel
import kanbanboard.composeapp.generated.resources.button_delete
import kanbanboard.composeapp.generated.resources.button_edit
import kanbanboard.composeapp.generated.resources.edit_dialog_title
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.component.KanbanBoardButton
import woowacourse.kanban.board.ui.dialog.TaskDialogContent
import woowacourse.kanban.board.ui.dialog.section.AssigneeSection
import woowacourse.kanban.board.ui.dialog.section.DescriptionSection
import woowacourse.kanban.board.ui.dialog.section.StatusSection
import woowacourse.kanban.board.ui.dialog.section.TagSection
import woowacourse.kanban.board.ui.dialog.section.TitleSection
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun TaskEditDialog(
    task: Task,
    users: List<User>,
    onDismiss: () -> Unit,
    onEditResult: (Result<Task>) -> Unit,
    onClickDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val taskEditState = remember { TaskEditState(users, task = task) }
    TaskDialogContent(
        onDismiss = onDismiss,
        modifier = modifier,
        header = {
            Text(
                text = stringResource(Res.string.edit_dialog_title),
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = CustomTheme.colors.gray.w600,
            )
        },
        footer = {
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
                onClick = { onEditResult(taskEditState.createEditedTask()) },
                enabled = taskEditState.canEdit,
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
        },
    ) {
        TitleSection(
            value = taskEditState.title,
            onTitleChange = {
                taskEditState.updateTitle(it)
            },
            validation = taskEditState.titleValidation,
        )

        DescriptionSection(
            value = taskEditState.content,
            onContentChange = {
                taskEditState.updateContent(it)
            },
        )

        TagSection(
            value = taskEditState.tag,
            onTagChange = {
                taskEditState.updateTag(it)
            },
            validation = taskEditState.tagValidation,
        )

        StatusSection(
            selectedStatus = taskEditState.selectedStatus,
            onStatusChange = {
                taskEditState.updateStatus(it)
            },
        )

        AssigneeSection(
            userGroup = taskEditState.validUsers,
            selectedUser = taskEditState.selectedUser,
            onUserChange = {
                taskEditState.updateUser(it)
            },
        )
    }
}
