package woowacourse.kanban.board.domain.model

data class Task(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val description: String? = null,
    val tags: Tags,
    val user: User,
    val status: Status,
) {
    init {
        require(title.isNotBlank()) { "제목이 비어있습니다." }
    }
}
