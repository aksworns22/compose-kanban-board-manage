package woowacourse.kanban.board.ui.dialog.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun AssigneeSection(modifier: Modifier = Modifier, userGroup: List<User>, selectedUser: User, onUserChange: (User) -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Label(stringResource(Res.string.label_assignee), true)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            userGroup.forEach { user ->
                UserChip(
                    user = user,
                    selected = user == selectedUser,
                    onUserChange = { onUserChange(user) },
                )
            }
        }
    }
}

@Composable
fun UserChip(user: User, selected: Boolean, onUserChange: () -> Unit, modifier: Modifier = Modifier) {
    FilterChip(
        selected = selected,
        onClick = onUserChange,
        label = {
            UserProfile(user = user, Modifier.padding(vertical = 16.dp))
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
    var selectedAssignee by remember { mutableStateOf(User.Assignee("디노")) }

    val users = listOf(
        User.Assignee("디노"),
        User.Assignee("제임스"),
        User.Assignee("로미"),
        User.Assignee("로미"),
        User.Assignee("로미"),
    )

    AssigneeSection(
        userGroup = users,
        selectedUser = selectedAssignee,
        onUserChange = { },
    )
}

@Composable
@Preview(showBackground = true)
private fun UserChipPreview() {
    UserChip(
        user = User.Assignee("김철수"),
        selected = true,
        onUserChange = {},
    )
}
