package woowacourse.kanban.board.domain.model

@JvmInline
value class Tag(val content: String) {
    init {
        require(content.length in MIN_TAG_LENGTH..MAX_TAG_LENGTH) { "최대 $MAX_TAG_LENGTH 글자까지 입력 가능합니다." }
    }

    companion object {
        const val MIN_TAG_LENGTH = 1
        const val MAX_TAG_LENGTH = 5
    }
}
