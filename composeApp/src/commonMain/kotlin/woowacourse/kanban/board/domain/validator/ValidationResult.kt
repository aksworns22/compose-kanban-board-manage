package woowacourse.kanban.board.domain.validator

sealed class ValidationResult {
    data object Initial : ValidationResult()
    data object Valid : ValidationResult()
    data class Invalid(val error: ValidationError) : ValidationResult()
}
