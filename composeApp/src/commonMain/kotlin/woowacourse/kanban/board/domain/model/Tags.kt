package woowacourse.kanban.board.domain.model

class Tags(val items: List<Tag> = emptyList()) {
    init {
        require(items.size <= MAX_TAG_SIZE) { "태그는 최대 ${MAX_TAG_SIZE}개까지 입력 가능합니다." }
        require(items.distinct().size == items.size) { "중복된 태그가 존재합니다." }
    }

    companion object {
        const val MAX_TAG_SIZE = 5
    }
}
