package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.snackbar_create_new_task
import kanbanboard.composeapp.generated.resources.snackbar_delete_task
import kanbanboard.composeapp.generated.resources.snackbar_edit_task
import kanbanboard.composeapp.generated.resources.snackbar_move_general_error
import kanbanboard.composeapp.generated.resources.snackbar_move_no_assignee_error
import kanbanboard.composeapp.generated.resources.snackbar_move_task
import kanbanboard.composeapp.generated.resources.snackbar_task_delete_error
import kanbanboard.composeapp.generated.resources.snackbar_task_error
import kanbanboard.composeapp.generated.resources.snackbar_unknown_error
import org.jetbrains.compose.resources.getString
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.dialog.DialogState
import woowacourse.kanban.board.ui.dialog.DialogType
import woowacourse.kanban.board.ui.dialog.TaskCreateDialog
import woowacourse.kanban.board.ui.dialog.TaskEditDialogScreen
import woowacourse.kanban.board.ui.theme.CustomTheme
import woowacourse.kanban.board.ui.util.SnackBarEvent

@Composable
fun KanbanBoardScreen(kanbanBoardState: KanbanBoardState) {
    val kanbanBoardState = remember { kanbanBoardState }
    val dialogState = remember { DialogState() }
    val snackBarHostState = remember { SnackbarHostState() }
    var snackBarEvent: SnackBarEvent? by remember { mutableStateOf(null) }

    var draggedTask by remember { mutableStateOf<Task?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<Status, Rect>() }

    val resetDrag: () -> Unit = {
        currentDragPosition = null
        draggedTask = null
    }

    val showSnackBar: (SnackBarEvent) -> Unit = { snackBarEvent = it }

    LaunchedEffect(snackBarEvent?.id) {
        snackBarEvent?.let {
            snackBarHostState.showSnackbar(
                message = when {
                    it.message != null -> it.message
                    it.strRes != null -> getString(it.strRes)
                    else -> getString(Res.string.snackbar_unknown_error)
                },
                withDismissAction = true,
            )
        }
        snackBarEvent = null
    }

    Box {
        val dialogType = dialogState.dialogType
        if (dialogType != null) {
            TaskDialogScreen(
                kanbanProjectState = kanbanBoardState.currentProject,
                dialogType = dialogType,
                onDismiss = dialogState::closeDialog,
                showSnackBar = showSnackBar,
            )
        }
        Row {
            ProjectSideBar(
                kanbanBoardState = kanbanBoardState,
                onProjectSelect = kanbanBoardState::selectProject,
                modifier = Modifier
                    .width(255.dp)
                    .fillMaxHeight()
                    .background(CustomTheme.colors.white)
                    .semantics { contentDescription = "Project SideBar" },
            )
            VerticalDivider(modifier = Modifier.width(1.dp).background(CustomTheme.colors.gray.w100))
            TaskBoard(
                kanbanBoardState = kanbanBoardState,
                getIsDropTarget = { status ->
                    currentDragPosition?.let { columnBounds[status]?.contains(it) } ?: false
                },
                onBoundsChanged = { rect, status -> columnBounds[status] = rect },
                onTaskDragStart = { task -> draggedTask = task },
                onTaskDragChange = { pos -> currentDragPosition = pos },
                onTaskDragEnd = {
                    val dropPosition = currentDragPosition ?: return@TaskBoard
                    val targetStatus = columnBounds.entries
                        .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                    draggedTask?.let { task ->
                        if (targetStatus != null && task.status != targetStatus) {
                            val movementResult = kanbanBoardState.currentProject.changeTaskStatus(task = task, newStatus = targetStatus)
                            when (movementResult) {
                                MovementResult.Success -> {
                                    snackBarEvent = SnackBarEvent(
                                        strRes = Res.string.snackbar_move_task,
                                    )
                                }
                                MovementResult.Failed -> {
                                    snackBarEvent = SnackBarEvent(
                                        strRes = Res.string.snackbar_move_general_error,
                                    )
                                }

                                MovementResult.NoAssignee -> {
                                    snackBarEvent = SnackBarEvent(
                                        strRes = Res.string.snackbar_move_no_assignee_error,
                                    )
                                }
                            }
                        }
                    }
                    resetDrag()
                },
                onTaskDragCancel = resetDrag,
                onTaskClick = { task ->
                    dialogState.openDialog(DialogType.EditTask(task))
                },
                onClickCreate = { dialogState.openDialog(DialogType.CreateTask) },
            )
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun TaskDialogScreen(
    kanbanProjectState: KanbanProjectState,
    dialogType: DialogType,
    onDismiss: () -> Unit,
    showSnackBar: (SnackBarEvent) -> Unit,
) {
    // 월드 클래스 담당자들
    val users =
        listOf(User.None, User.Assignee("손흥민"), User.Assignee("봉준호"), User.Assignee("BTS"), User.Assignee("스마일"), User.Assignee("렛츠 고!"))
    when (dialogType) {
        DialogType.CreateTask -> {
            TaskCreateDialog(
                users = users,
                onDismiss = onDismiss,
                onResult = { result ->
                    handleTaskCreationResult(
                        result = result,
                        projectState = kanbanProjectState,
                        showSnackBar = showSnackBar,
                        onCloseDialog = onDismiss,
                    )
                },
            )
        }

        is DialogType.EditTask -> TaskEditDialogScreen(
            users = users,
            onDismiss = onDismiss,
            task = dialogType.task,
            onResult = { result ->
                handleTaskEditResult(
                    originalTask = dialogType.task,
                    result = result,
                    projectState = kanbanProjectState,
                    showSnackBar = showSnackBar,
                    onCloseDialog = onDismiss,
                )
            },
            onClickDelete = {
                if (kanbanProjectState.deleteTask(dialogType.task)) {
                    showSnackBar(SnackBarEvent(strRes = Res.string.snackbar_delete_task))
                } else {
                    showSnackBar(SnackBarEvent(strRes = Res.string.snackbar_task_delete_error))
                }
                onDismiss()
            },
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

@Composable
@Preview
private fun KanbanBoardScreenPreview() {
    KanbanBoardScreen(kanbanBoardState = KanbanBoardState(KanbanProjectState(name = "허닛은 바보인가?")))
}
