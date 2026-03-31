package woowacourse.kanban.board.domain.validator

import woowacourse.kanban.board.domain.model.Tag.Companion.MAX_TAG_LENGTH
import woowacourse.kanban.board.domain.model.Tags.Companion.MAX_TAG_SIZE

object TaskValidator {
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
}
