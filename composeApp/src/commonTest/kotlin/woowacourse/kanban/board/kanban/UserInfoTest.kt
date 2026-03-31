package woowacourse.kanban.board.kanban

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import woowacourse.kanban.board.domain.model.User

class UserInfoTest {
    @Test
    fun `유저 이름이 빈 값이면 예외`() {
        val given = ""

        assertFails {
            User(
                name = given,
            )
        }
    }

    @Test
    fun `유저 이름이 공백이면 예외`() {
        val given = "  "

        assertFails {
            User(
                name = given,
            )
        }
    }

    @Test
    fun `유저 이름이 공백이 아니면 생성 성공`() {
        val given = "다이노"

        assertEquals(given, User(given).name)
    }
}
