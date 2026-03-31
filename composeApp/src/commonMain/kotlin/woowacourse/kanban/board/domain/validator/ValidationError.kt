package woowacourse.kanban.board.domain.validator

enum class ValidationError {
    TITLE_BLANK,
    TAG_INVALID_FORMAT,
    TAG_INVALID_LENGTH,
    TAG_COUNT_EXCEEDED,
}
