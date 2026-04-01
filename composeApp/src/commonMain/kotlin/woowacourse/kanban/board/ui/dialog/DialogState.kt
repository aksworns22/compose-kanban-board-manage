package woowacourse.kanban.board.ui.dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DialogState(initialDialogType: DialogType? = null) {
    var dialogType: DialogType? by mutableStateOf(initialDialogType)
        private set

    fun openDialog(dialogType: DialogType) {
        this.dialogType = dialogType
    }

    fun closeDialog() {
        this.dialogType = null
    }
}
