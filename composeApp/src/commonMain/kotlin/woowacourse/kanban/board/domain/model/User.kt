package woowacourse.kanban.board.domain.model

sealed class User {
    data class Assignee(val name: String, val profileImg: String? = null) : User() {
        init {
            require(name.isNotBlank()) {
                "이름은 공백일 수 없습니다."
            }
        }
    }

    object None : User()
}
