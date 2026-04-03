package woowacourse.kanban.board.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.dialog.section.AssigneeSection
import woowacourse.kanban.board.ui.dialog.section.DescriptionSection
import woowacourse.kanban.board.ui.dialog.section.StatusSection
import woowacourse.kanban.board.ui.dialog.section.TagSection
import woowacourse.kanban.board.ui.dialog.section.TitleSection

@Composable
fun TaskCreateForm(
    taskCreationState: TaskCreationState,
    header: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    footer: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        header()
        HorizontalDivider()
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),

        ) {
            TitleSection(
                value = taskCreationState.validationState.title,
                onTitleChange = {
                    taskCreationState.validationState.updateTitle(it)
                },
                validation = taskCreationState.validationState.titleValidation,
            )

            DescriptionSection(
                value = taskCreationState.validationState.content,
                onContentChange = {
                    taskCreationState.validationState.updateContent(it)
                },
            )

            TagSection(
                value = taskCreationState.validationState.tag,
                onTagChange = {
                    taskCreationState.validationState.updateTag(it)
                },
                validation = taskCreationState.validationState.tagValidation,
            )

            StatusSection(
                selectedStatus = taskCreationState.validationState.selectedStatus,
                onStatusChange = {
                    taskCreationState.validationState.updateStatus(it)
                },
            )

            AssigneeSection(
                userGroup = taskCreationState.validationState.validUsers,
                selectedUser = taskCreationState.validationState.selectedUser,
                onUserChange = {
                    taskCreationState.validationState.updateUser(it)
                },
            )
        }
        HorizontalDivider()
        footer()
    }
}

@Composable
@Preview(showBackground = true)
private fun TaskCreateFormPreview() {
    TaskCreateForm(
        taskCreationState =
        TaskCreationState(
            listOf(User.Assignee("디이노"), User.Assignee("제임스"), User.Assignee("본드")),
        ),
        header = { },
    ) {
        Text("footer 영역")
    }
}
