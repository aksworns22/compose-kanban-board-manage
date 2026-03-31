package woowacourse.kanban.board.ui.dialog.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.label_assignee
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.component.Label
import woowacourse.kanban.board.ui.component.UserProfile
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun AssigneeSection(modifier: Modifier = Modifier, managers: List<User>, selectedUser: User, onUserChange: (User) -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Label(stringResource(Res.string.label_assignee), true)
        managers.chunked(3).forEach { users ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                users.forEach { manager ->
                    AssigneeChip(
                        assignee = manager,
                        selected = manager == selectedUser,
                        onUserChange = { onUserChange(manager) },
                        modifier = Modifier.weight(1f),
                    )
                }
                repeat(3 - users.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun AssigneeChip(assignee: User, selected: Boolean, onUserChange: () -> Unit, modifier: Modifier = Modifier) {
    FilterChip(
        selected = selected,
        onClick = onUserChange,
        label = {
            UserProfile(user = assignee, Modifier.padding(vertical = 16.dp))
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.White,
            selectedContainerColor = CustomTheme.colors.blue.w50,
            selectedLabelColor = CustomTheme.colors.blue.w400,
        ),
        modifier = modifier,
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = CustomTheme.colors.gray.w400,
            selectedBorderColor = CustomTheme.colors.blue.w500,
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp,
        ),
    )
}

@Composable
@Preview(showBackground = true)
private fun AssigneePreview() {
    var selectedUser by remember { mutableStateOf(User("디노")) }

    val managers = listOf(
        User("디노"),
        User("제임스"),
        User("로미"),
        User("로미"),
        User("로미"),
    )

    AssigneeSection(
        managers = managers,
        selectedUser = selectedUser,
        onUserChange = { },
    )
}

@Composable
@Preview(showBackground = true)
private fun AssigneeChipPreview() {
    AssigneeChip(
        assignee = User("김철수"),
        selected = true,
        onUserChange = {},
    )
}
