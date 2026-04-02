package woowacourse.kanban.board.ui.dialog.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.label_status
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.ui.component.Label
import woowacourse.kanban.board.ui.theme.CustomTheme
import woowacourse.kanban.board.ui.util.toUiString

@Composable
fun StatusSection(modifier: Modifier = Modifier, selectedStatus: Status = Status.TODO, onStatusChange: (Status) -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Label(stringResource(Res.string.label_status), true)
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Status.entries.forEach { status ->
                StatusChip(
                    status = status,
                    selectedStatus = selectedStatus,
                    onStatusChange = onStatusChange,
                    modifier = Modifier.weight(1f).testTag("$status 버튼"),
                )
            }
        }
    }
}

@Composable
fun StatusChip(modifier: Modifier = Modifier, status: Status, selectedStatus: Status, onStatusChange: (Status) -> Unit) {
    FilterChip(
        selected = selectedStatus == status,
        onClick = {
            onStatusChange(status)
        },
        label = {
            Text(
                stringResource(status.toUiString()),
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 14.dp),
                textAlign = TextAlign.Center,
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.White,
            selectedContainerColor = CustomTheme.colors.blue.w50,
            selectedLabelColor = CustomTheme.colors.blue.w400,
        ),
        modifier = modifier,
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selectedStatus == status,
            borderColor = CustomTheme.colors.gray.w400,
            selectedBorderColor = CustomTheme.colors.blue.w500,
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp,
        ),
    )
}

@Composable
@Preview(showBackground = true)
private fun StatusPreview() {
    StatusSection(
        selectedStatus = Status.TODO,
        onStatusChange = { print(it) },
    )
}

@Composable
@Preview(showBackground = true)
private fun StatusChipPreview() {
    var status by remember { mutableStateOf(Status.TODO) }
    StatusChip(
        selectedStatus = status,
        status = Status.TODO,
        onStatusChange = {
            status = it
        },
    )
}
