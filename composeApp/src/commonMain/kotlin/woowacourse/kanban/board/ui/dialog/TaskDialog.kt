package woowacourse.kanban.board.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.snackbar_create_new_task
import kanbanboard.composeapp.generated.resources.snackbar_delete_task
import kanbanboard.composeapp.generated.resources.snackbar_edit_task
import kanbanboard.composeapp.generated.resources.snackbar_task_delete_error
import kanbanboard.composeapp.generated.resources.snackbar_task_error
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.ui.board.KanbanProjectState
import woowacourse.kanban.board.ui.theme.CustomTheme
import woowacourse.kanban.board.ui.util.SnackBarEvent

@Composable
fun TaskDialog(
    kanbanProjectState: KanbanProjectState,
    taskDialogType: TaskDialogType,
    onDismiss: () -> Unit,
    showSnackBar: (SnackBarEvent) -> Unit,
) {
    val dialogModifier = Modifier.fillMaxWidth(0.6f)
        .fillMaxHeight(0.9f).clip(RoundedCornerShape(10.dp)).background(CustomTheme.colors.white)
    when (taskDialogType) {
        TaskDialogType.CreateTask -> {
            TaskCreateDialog(
                users = kanbanProjectState.users,
                onDismiss = onDismiss,
                onCreateResult = { result ->
                    handleTaskCreationResult(
                        result = result,
                        projectState = kanbanProjectState,
                        showSnackBar = showSnackBar,
                        onCloseDialog = onDismiss,
                    )
                },
                modifier = dialogModifier,
            )
        }

        is TaskDialogType.EditTask -> TaskEditDialogScreen(
            users = kanbanProjectState.users,
            onDismiss = onDismiss,
            task = taskDialogType.task,
            onEditResult = { result ->
                handleTaskEditResult(
                    originalTask = taskDialogType.task,
                    result = result,
                    projectState = kanbanProjectState,
                    showSnackBar = showSnackBar,
                    onCloseDialog = onDismiss,
                )
            },
            onClickDelete = {
                if (kanbanProjectState.deleteTask(taskDialogType.task)) {
                    showSnackBar(SnackBarEvent(strRes = Res.string.snackbar_delete_task))
                } else {
                    showSnackBar(SnackBarEvent(strRes = Res.string.snackbar_task_delete_error))
                }
                onDismiss()
            },
            modifier = dialogModifier,
        )
    }
}

private fun handleTaskEditResult(
    originalTask: Task,
    result: Result<Task>,
    projectState: KanbanProjectState,
    showSnackBar: (SnackBarEvent) -> Unit,
    onCloseDialog: () -> Unit,
) {
    result.onSuccess { newTask ->
        projectState.editTask(originalTask, newTask)
        onCloseDialog()
        showSnackBar(
            SnackBarEvent(
                strRes = Res.string.snackbar_edit_task,
            ),
        )
    }.onFailure { exception ->
        showSnackBar(
            SnackBarEvent(
                strRes = Res.string.snackbar_task_error,
                message = exception.message,
            ),
        )
    }
}

private fun handleTaskCreationResult(
    result: Result<Task>,
    projectState: KanbanProjectState,
    showSnackBar: (SnackBarEvent) -> Unit,
    onCloseDialog: () -> Unit,
) {
    result.onSuccess { newTask ->
        projectState.addTask(newTask)
        onCloseDialog()
        showSnackBar(
            SnackBarEvent(
                strRes = Res.string.snackbar_create_new_task,
            ),
        )
    }.onFailure { exception ->
        showSnackBar(
            SnackBarEvent(
                strRes = Res.string.snackbar_task_error,
                message = exception.message,
            ),
        )
    }
}
