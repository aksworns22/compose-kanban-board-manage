package woowacourse.kanban.board.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun Chip(
    content: String,
    modifier: Modifier = Modifier,
    textColor: Color = CustomTheme.colors.blue.w700,
    backgroundColor: Color = CustomTheme.colors.gray.w50,
) {
    Text(
        text = content,
        fontSize = 12.sp,
        fontWeight = FontWeight.W400,
        color = textColor,
        modifier = modifier
            .clip(shape = RoundedCornerShape(100.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
    )
}

@Preview
@Composable
private fun ChipPreview() {
    Chip("칩입니다")
}
