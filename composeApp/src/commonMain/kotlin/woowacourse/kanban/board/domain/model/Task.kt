package woowacourse.kanban.board.domain.model

import java.util.Objects

abstract class Task(
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

    fun hasAssignee(): Boolean = user !is User.None

    override fun equals(other: Any?): Boolean {
        if (other is Task) {
            return this.id == other.id &&
                this.title == other.title &&
                this.description == other.description &&
                this.tags == other.tags &&
                this.user == other.user &&
                this.status == other.status
        }
        return false
    }

    override fun hashCode(): Int {
        return Objects.hash(id, title, description, tags, user, status)
    }

    abstract fun moveTo(status: Status): Task
}
