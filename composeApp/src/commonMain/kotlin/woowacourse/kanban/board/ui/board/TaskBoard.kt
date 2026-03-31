package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun TaskBoard(
    kanbanBoardState: KanbanBoardState,
    modifier: Modifier = Modifier,
    getIsDropTarget: (Status) -> Boolean = { false },
    onBoundsChanged: (Rect, Status) -> Unit = { _, _ -> },
    onTaskDragStart: (Task) -> Unit = {},
    onTaskDragChange: (Offset) -> Unit = {},
    onTaskDragEnd: () -> Unit = {},
    onTaskDragCancel: () -> Unit = {},
    onClickCreate: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth().fillMaxHeight().background(CustomTheme.colors.gray.w50),
    ) {
        KanbanHeader(
            title = kanbanBoardState.currentProject.name,
            onClickCreate = onClickCreate,
            totalCount = kanbanBoardState.currentProject.totalCount,
            completeCount = kanbanBoardState.currentProject.completeCount,
            completeRatio = kanbanBoardState.currentProject.completeRatio,
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Status.entries.forEach { status ->
                TaskBox(
                    modifier = Modifier.weight(1f, fill = false).widthIn(max = 320.dp).fillMaxHeight()
                        .semantics { contentDescription = "$status 태스크 목록" },
                    status = status,
                    tasks = kanbanBoardState.currentProject.getTasks(status),
                    boxColor = status.getBoxColor(),
                    getIsDropTarget = { getIsDropTarget(status) },
                    onBoundsChanged = { rect -> onBoundsChanged(rect, status) },
                    onTaskDragStart = onTaskDragStart,
                    onTaskDragChange = onTaskDragChange,
                    onTaskDragEnd = onTaskDragEnd,
                    onTaskDragCancel = onTaskDragCancel,
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 800)
@Composable
private fun TaskBoardPreview() {
    TaskBoard(kanbanBoardState = KanbanBoardState(KanbanProjectState(name = "스마일은 천재인가?")))
}
