package woowacourse.kanban.board.ui.util

import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.error_invalid_user_about_status
import kanbanboard.composeapp.generated.resources.error_tag_count_exceeded
import kanbanboard.composeapp.generated.resources.error_tag_invalid_format
import kanbanboard.composeapp.generated.resources.error_tag_invalid_length
import kanbanboard.composeapp.generated.resources.error_title_blank
import kanbanboard.composeapp.generated.resources.status_done
import kanbanboard.composeapp.generated.resources.status_in_progress
import kanbanboard.composeapp.generated.resources.status_review
import kanbanboard.composeapp.generated.resources.status_todo
import org.jetbrains.compose.resources.StringResource
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.validator.ValidationError

fun ValidationError.toMessage(): StringResource = when (this) {
    ValidationError.TITLE_BLANK -> Res.string.error_title_blank
    ValidationError.TAG_INVALID_FORMAT -> Res.string.error_tag_invalid_format
    ValidationError.TAG_INVALID_LENGTH -> Res.string.error_tag_invalid_length
    ValidationError.TAG_COUNT_EXCEEDED -> Res.string.error_tag_count_exceeded
    ValidationError.INVALID_USER_ABOUT_STATUS -> Res.string.error_invalid_user_about_status
}

fun Status.toUiString(): StringResource = when (this) {
    Status.TODO -> Res.string.status_todo
    Status.IN_PROGRESS -> Res.string.status_in_progress
    Status.DONE -> Res.string.status_done
    Status.REVIEW -> Res.string.status_review
}
