package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.domain.model.DefaultTodoTask
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tag
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.component.Chip
import woowacourse.kanban.board.ui.component.UserProfile
import woowacourse.kanban.board.ui.theme.CustomTheme

private const val TITLE_MAX_LINE = 1
private const val CONTENT_MAX_LINE = 2

@Composable
fun TaskCard(
    task: Task,
    modifier: Modifier = Modifier,
    onTaskClick: () -> Unit = {},
    onDragStart: () -> Unit = {},
    onDragChange: (Offset) -> Unit = {},
    onDragEnd: () -> Unit = {},
    onDragCancel: () -> Unit = {},
) {
    var cardWindowPosition by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(CustomTheme.colors.white)
            .border(width = 1.dp, shape = RoundedCornerShape(10.dp), color = CustomTheme.colors.gray.w100)
            .clickable { onTaskClick() }
            .onGloballyPositioned { cardWindowPosition = it.positionInWindow() }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { onDragStart() },
                    onDrag = { change, _ ->
                        change.consume()
                        onDragChange(cardWindowPosition + change.position)
                    },
                    onDragEnd = { onDragEnd() },
                    onDragCancel = { onDragCancel() },
                )
            }
            .padding(17.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TaskTitle(task.title)
        task.description?.let { content -> TaskDescription(content) }
        if (task.tags.items.isNotEmpty()) TaskTags(task.tags)
        if (task.user is User.Assignee) {
            Box {
                HorizontalDivider(color = CustomTheme.colors.gray.w50, thickness = 1.dp)
                UserProfile(task.user, Modifier.padding(10.dp))
            }
        }
    }
}

@Composable
private fun TaskTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.W500,
        color = CustomTheme.colors.gray.w600,
        maxLines = TITLE_MAX_LINE,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun TaskDescription(description: String) {
    Text(
        text = description,
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
        color = CustomTheme.colors.blue.w600,
        maxLines = CONTENT_MAX_LINE,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun TaskTags(tags: Tags) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        tags.items.forEach { tag ->
            Chip(tag.content)
        }
    }
}

class CardPreviewParameterProvider : PreviewParameterProvider<Task> {
    override val values = sequenceOf(
        DefaultTodoTask(
            title = "LazyColumn 컴포넌트 구현",
            description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
            tags = Tags(listOf(Tag("컴포넌트"), Tag("성능"))),
            user = User.Assignee(name = "다이노"),
            status = Status.TODO,
        ),
        DefaultTodoTask(
            title = "LazyColumn 컴포넌트 구현",
            tags = Tags(listOf(Tag("컴포넌트"), Tag("성능"))),
            user = User.Assignee(name = "다이노"),
            status = Status.TODO,
        ),
        DefaultTodoTask(
            title = "LazyColumn 컴포넌트 구현",
            description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
            user = User.Assignee(name = "다이노"),
            tags = Tags(emptyList()),
            status = Status.TODO,
        ),
        DefaultTodoTask(
            title = "LazyColumn 컴포넌트 구현",
            user = User.Assignee(name = "다이노"),
            tags = Tags(emptyList()),
            status = Status.TODO,
        ),
        DefaultTodoTask(
            title = "LazyColumn 컴포넌트 구현",
            description = "너무너무너무 긴 설명은 두 줄까지만 노출하고 말줄임표로 처리합니다 두 줄까지만 노출하고 말줄임표로 처리합니다",
            tags = Tags(listOf(Tag("너무너무"), Tag("긴 태그"), Tag("최대로"), Tag("5자까지"), Tag("5개제한임"))),
            user = User.Assignee(name = "너무너무너무 긴 담당자도 한 줄 너무너무너무 긴 담당자도 한 줄"),
            status = Status.TODO,
        ),
    )
}

@Composable
@Preview
private fun TaskCardPreview(@PreviewParameter(CardPreviewParameterProvider::class) card: Task) {
    TaskCard(card)
}
