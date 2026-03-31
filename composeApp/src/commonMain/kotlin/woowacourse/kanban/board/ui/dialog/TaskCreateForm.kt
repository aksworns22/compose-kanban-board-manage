package woowacourse.kanban.board.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.dialog.section.AssigneeSection
import woowacourse.kanban.board.ui.dialog.section.DescriptionSection
import woowacourse.kanban.board.ui.dialog.section.Footer
import woowacourse.kanban.board.ui.dialog.section.Header
import woowacourse.kanban.board.ui.dialog.section.StatusSection
import woowacourse.kanban.board.ui.dialog.section.TagSection
import woowacourse.kanban.board.ui.dialog.section.TitleSection

@Composable
fun TaskCreateForm(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    assignees: List<User>,
    onClickCreate: (title: String, content: String, tags: List<String>, status: Status, assignee: User) -> Unit,
) {
    val uiState = remember { TaskCreateFormState(assignees) }

    Column(
        modifier = modifier,
    ) {
        Header(
            onDismiss = onDismiss,
        )
        HorizontalDivider()
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),

        ) {
            TitleSection(
                value = uiState.title,
                onTitleChange = {
                    uiState.updateTitle(it)
                },
                validation = uiState.titleValidation,
            )

            DescriptionSection(
                value = uiState.content,
                onContentChange = {
                    uiState.updateContent(it)
                },
            )

            TagSection(
                value = uiState.tag,
                onTagChange = {
                    uiState.updateTag(it)
                },
                validation = uiState.tagValidation,
            )

            StatusSection(
                selectedStatus = uiState.selectedStatus,
                onStatusChange = {
                    uiState.updateStatus(it)
                },
            )

            AssigneeSection(
                managers = uiState.assignees,
                selectedUser = uiState.selectedAssignee,
                onUserChange = {
                    uiState.updateAssignee(it)
                },
            )
        }
        HorizontalDivider()
        Footer(
            onClickCancel = onDismiss,
            onClickConfirm = {
                onClickCreate(
                    uiState.title,
                    uiState.content,
                    uiState.tag.split(",").filter { it.isNotEmpty() }.map { it.trim() },
                    uiState.selectedStatus,
                    uiState.selectedAssignee,
                )
            },
            enabled = uiState.canCreate,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TaskCreateFormPreview() {
    TaskCreateForm(
        onDismiss = {},
        assignees = listOf(User("다이노"), User("다이노소어"), User("우우우")),
        onClickCreate = { _, _, _, _, _ -> },
    )
}
