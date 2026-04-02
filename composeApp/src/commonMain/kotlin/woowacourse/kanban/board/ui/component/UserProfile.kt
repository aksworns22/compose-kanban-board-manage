package woowacourse.kanban.board.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun UserProfile(user: User, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (user) {
            is User.Assignee -> {
                Box(
                    modifier = Modifier
                        .size(24.dp).clip(CircleShape)
                        .background(color = CustomTheme.colors.white)
                        .border(width = 2.dp, color = CustomTheme.colors.red.w100, shape = CircleShape),
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "profile image",
                        tint = CustomTheme.colors.red.w100,
                        modifier = Modifier.clip(CircleShape).requiredSize(size = 33.dp),
                    )
                }
                Text(
                    text = user.name,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    color = CustomTheme.colors.blue.w700,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            User.None -> {
                Text(
                    text = "없음",
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    color = CustomTheme.colors.blue.w700,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserPreView() {
    UserProfile(User.Assignee("다이노"))
}
