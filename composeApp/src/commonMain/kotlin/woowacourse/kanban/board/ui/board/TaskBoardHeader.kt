package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.button_create_new_task
import kanbanboard.composeapp.generated.resources.format_completion_rate
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.ui.theme.Purple

@Composable
fun KanbanHeader(
    title: String,
    totalCount: Int,
    completeCount: Int,
    completeRatio: Float,
    modifier: Modifier = Modifier,
    onClickCreate: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth().background(Color.White).border(width = 1.dp, color = Color(0xffE5E7EB))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = title,
                    color = Color(0xff101828),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.semantics { heading() },
                )
                CompletionRateText(completeRatio, completeCount, totalCount)
            }
            CreateNewTaskButton(onClickCreate = onClickCreate)
        }
        TaskProgressBar(completeRatio = completeRatio)
    }
}

@Composable
private fun CompletionRateText(completeRatio: Float, completeCount: Int, totalCount: Int) {
    Text(stringResource(Res.string.format_completion_rate, (completeRatio * 100).toInt(), completeCount, totalCount))
}

@Composable
private fun CreateNewTaskButton(onClickCreate: () -> Unit) {
    Button(
        onClick = {
            onClickCreate()
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple,
            contentColor = Color.White,
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "add",
        )
        Text(
            text = stringResource(Res.string.button_create_new_task),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
private fun TaskProgressBar(modifier: Modifier = Modifier, completeRatio: Float) {
    LinearProgressIndicator(
        progress = { completeRatio },
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp),
        color = Color(0xFF4F39F6),
        trackColor = Color(0xffE5E7EB),
        drawStopIndicator = {},
        gapSize = 0.dp,
    )
}

@Preview(showBackground = true, widthDp = 800)
@Composable
private fun KanbanHeaderPreview() {
    KanbanHeader(
        title = "안녕하세요 스마일입니다",
        totalCount = 30,
        completeCount = 20,
        completeRatio = 0.6f,
    )
}
