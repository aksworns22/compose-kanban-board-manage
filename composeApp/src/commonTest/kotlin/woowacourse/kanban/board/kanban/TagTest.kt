package woowacourse.kanban.board.kanban

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import woowacourse.kanban.board.domain.model.Tag

class TagTest {
    @Test
    fun `태그 글자 수가 5자 초과이면 예외`() {
        val given = "너무너무긴태그"

        assertFails {
            Tag(given)
        }
    }

    @Test
    fun `태그 글자 수가 1자 미만이면 예외`() {
        val given = ""

        assertFails {
            Tag(
                given,
            )
        }
    }

    @Test
    fun `태그 글자 수가 1자 이상이면 생성 성공`() {
        val given = "태"

        assertEquals(given, Tag(given).content)
    }

    @Test
    fun `태그 글자 수가 5자 이상이면 생성 성공`() {
        val given = "태그다섯자"

        assertEquals(given, Tag(given).content)
    }
}
