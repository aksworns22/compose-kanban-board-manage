package woowacourse.kanban.board.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import woowacourse.kanban.board.ui.dialog.section.Footer
import woowacourse.kanban.board.ui.dialog.section.TaskFormHeader

@Composable
fun TaskDialogContent(
    header: @Composable RowScope.() -> Unit,
    footer: @Composable RowScope.() -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    body: @Composable ColumnScope.() -> Unit,
) {
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
                header()
            }
            HorizontalDivider()
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                body()
            }
            HorizontalDivider()
            Footer {
                footer()
            }
        }
    }
}
