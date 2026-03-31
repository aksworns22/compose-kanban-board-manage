package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.theme.CustomTheme
import woowacourse.kanban.board.ui.util.toUiString

@Composable
fun TaskBox(
    status: Status,
    tasks: List<Task>,
    boxColor: TaskBoxColor,
    modifier: Modifier = Modifier,
    getIsDropTarget: () -> Boolean = { false },
    onBoundsChanged: (Rect) -> Unit = {},
    onTaskDragStart: (Task) -> Unit = {},
    onTaskDragChange: (Offset) -> Unit = {},
    onTaskDragEnd: () -> Unit = {},
    onTaskDragCancel: () -> Unit = {},
) {
    val isDropTarget by remember { derivedStateOf { getIsDropTarget() } }
    val lastBoundsHolder = remember { mutableStateOf<Rect?>(null) }
    Column(
        modifier = modifier.clip(shape = RoundedCornerShape(10.dp))
            .background(boxColor.background)
            .onGloballyPositioned {
                val newBounds = it.boundsInWindow()
                if (newBounds != lastBoundsHolder.value) {
                    lastBoundsHolder.value = newBounds
                    onBoundsChanged(newBounds)
                }
            }
            .then(
                if (isDropTarget) modifier.border(2.dp, CustomTheme.colors.red.w50, RoundedCornerShape(12.dp)) else modifier,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(boxColor.headerBackground)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(stringResource(status.toUiString()), color = CustomTheme.colors.white, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(
                tasks.size.toString(),
                modifier = Modifier.clip(RoundedCornerShape(100.dp)).background(CustomTheme.colors.white)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                color = CustomTheme.colors.black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().border(
                width = 1.dp,
                color = boxColor.border,
                shape = RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp),
            ),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(tasks, key = { it.id }) {
                TaskCard(
                    task = it,
                    onDragStart = { onTaskDragStart(it) },
                    onDragChange = onTaskDragChange,
                    onDragEnd = onTaskDragEnd,
                    onDragCancel = onTaskDragCancel,
                    modifier = Modifier.fillMaxWidth().semantics { contentDescription = "${it.status}상태의 ${it.title}태스크" },
                )
            }
        }
    }
}

data class TaskBoxColor(val background: Color, val headerBackground: Color, val border: Color)

@Composable
fun Status.getBoxColor(): TaskBoxColor = when (this) {
    Status.TODO -> TaskBoxColor(
        background = CustomTheme.colors.blue.w50,
        headerBackground = CustomTheme.colors.blue.w300,
        border = CustomTheme.colors.blue.w200,
    )

    Status.IN_PROGRESS -> TaskBoxColor(
        background = CustomTheme.colors.orange.w50,
        headerBackground = CustomTheme.colors.orange.w200,
        border = CustomTheme.colors.orange.w100,
    )

    Status.DONE -> TaskBoxColor(
        background = CustomTheme.colors.green.w50,
        headerBackground = CustomTheme.colors.green.w200,
        border = CustomTheme.colors.green.w100,
    )
}

@Preview
@Composable
private fun TaskBoxPreview() {
    TaskBox(
        status = Status.DONE,
        tasks = listOf(
            Task(title = "Task 1", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 2", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 3", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 1", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 2", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 3", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 1", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 2", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 3", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
        ),
        boxColor = Status.DONE.getBoxColor(),
    )
}
