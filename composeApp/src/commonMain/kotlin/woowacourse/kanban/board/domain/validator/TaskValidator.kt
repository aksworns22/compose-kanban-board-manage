package woowacourse.kanban.board.domain.validator

import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tag.Companion.MAX_TAG_LENGTH
import woowacourse.kanban.board.domain.model.Tags.Companion.MAX_TAG_SIZE
import woowacourse.kanban.board.domain.model.User

class TaskValidator(users: List<User>) {
    fun validateTitle(input: String): ValidationResult {
        if (input.isBlank()) return ValidationResult.Invalid(ValidationError.TITLE_BLANK)

        return ValidationResult.Valid
    }

    fun validateTags(input: String): ValidationResult {
        if (input.isEmpty()) return ValidationResult.Initial

        val formatted = input.split(",").map { it.trim() }
        if (formatted.any { it.isBlank() }) return ValidationResult.Invalid(ValidationError.TAG_INVALID_FORMAT)
        if (formatted.any { it.length > MAX_TAG_LENGTH }) return ValidationResult.Invalid(ValidationError.TAG_INVALID_LENGTH)
        if (formatted.size > MAX_TAG_SIZE) return ValidationResult.Invalid(ValidationError.TAG_COUNT_EXCEEDED)

        return ValidationResult.Valid
    }

    fun validateUser(status: Status, user: User): ValidationResult {
        if (status in setOf(Status.IN_PROGRESS, Status.DONE, Status.REVIEW) && user is User.None) {
            return ValidationResult.Invalid(ValidationError.INVALID_USER_ABOUT_STATUS)
        }
        return ValidationResult.Valid
    }
}
