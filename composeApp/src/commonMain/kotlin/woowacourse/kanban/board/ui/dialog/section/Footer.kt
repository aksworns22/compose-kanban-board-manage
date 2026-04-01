package woowacourse.kanban.board.ui.dialog.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.button_cancel
import kanbanboard.composeapp.generated.resources.button_create
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun Footer(modifier: Modifier = Modifier, onClickCancel: () -> Unit, onClickConfirm: () -> Unit, enabled: Boolean = true) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
    ) {
        CancelButton(onClickCancel = onClickCancel)
        ConfirmButton(
            onClickConfirm = onClickConfirm,
            enabled = enabled,
        )
    }
}

@Composable
fun CancelButton(modifier: Modifier = Modifier, onClickCancel: () -> Unit, enabled: Boolean = true) {
    Button(
        modifier = modifier,
        onClick = {
            onClickCancel()
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = CustomTheme.colors.blue.w700,
        ),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
    ) {
        Text(
            text = stringResource(Res.string.button_cancel),
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
        )
    }
}

@Composable
fun ConfirmButton(modifier: Modifier = Modifier, onClickConfirm: () -> Unit, enabled: Boolean = true) {
    Button(
        modifier = modifier,
        onClick = onClickConfirm,
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CustomTheme.colors.purple.w100,
            contentColor = CustomTheme.colors.white,
            disabledContainerColor = CustomTheme.colors.purple.w200,
            disabledContentColor = CustomTheme.colors.white,
        ),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
    ) {
        Text(
            text = stringResource(Res.string.button_create),
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun FooterPreview() {
    Footer(
        onClickCancel = {},
        onClickConfirm = {},
    )
}
