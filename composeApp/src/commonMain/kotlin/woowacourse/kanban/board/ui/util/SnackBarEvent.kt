package woowacourse.kanban.board.ui.util

import org.jetbrains.compose.resources.StringResource

data class SnackBarEvent(val id: Long = System.currentTimeMillis(), val strRes: StringResource? = null, val message: String? = null)
