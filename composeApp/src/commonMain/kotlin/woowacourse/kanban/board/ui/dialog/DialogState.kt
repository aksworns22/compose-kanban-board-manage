package woowacourse.kanban.board.ui.dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DialogState<T : Any>(initialDialog: T? = null) {
    var currentDialog: T? by mutableStateOf(initialDialog)
        private set

    fun openDialog(dialog: T) {
        currentDialog = dialog
    }

    fun closeDialog() {
        currentDialog = null
    }
}
