package woowacourse.kanban.board.kanban

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import org.assertj.core.api.Assertions.assertThatThrownBy
import woowacourse.kanban.board.domain.model.DefaultDoneTask
import woowacourse.kanban.board.domain.model.DefaultInProgressTask
import woowacourse.kanban.board.domain.model.DefaultReviewTask
import woowacourse.kanban.board.domain.model.DefaultTodoTask
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tag
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.User

class CardTest {
    @Test
    fun `todo 상태가 아닌 경우 담당자가 지정되지 않으면 예외를 반환한다`() {
        Status.entries.filter { it != Status.TODO }.forEach { status ->
            assertThatThrownBy {
                when (status) {
                    Status.TODO -> Unit
                    Status.IN_PROGRESS -> DefaultInProgressTask(
                        title = "타이틀",
                        description = "내용",
                        tags = Tags(emptyList()),
                        user = User.None,
                        status = status,
                    )
                    Status.REVIEW -> DefaultReviewTask(
                        title = "타이틀",
                        description = "내용",
                        tags = Tags(emptyList()),
                        user = User.None,
                        status = status,
                    )
                    Status.DONE -> DefaultDoneTask(
                        title = "타이틀",
                        description = "내용",
                        tags = Tags(emptyList()),
                        user = User.None,
                        status = status,
                    )
                }
            }
        }
    }

    @Test
    fun `태그 개수가 5개 초과이면 예외`() {
        assertFails {
            Tags(listOf(Tag("컴포넌트"), Tag("성능"), Tag("컴포즈"), Tag("테스트"), Tag("안드로이드"), Tag("코틀린")))
        }
    }

    @Test
    fun `카드에 유저를 제외한 필드가 모두 비어있으면 예외`() {
        assertFails {
            DefaultTodoTask(
                title = "",
                description = "",
                tags = Tags(),
                user = User.Assignee("테스트"),
                status = Status.TODO,
            )
        }
    }

    @Test
    fun `카드에 타이틀만 있어도 생성 성공`() {
        val given = "타이틀"
        assertEquals(given, DefaultTodoTask(title = given, tags = Tags(), user = User.Assignee("테스트"), status = Status.TODO).title)
    }

    @Test
    fun `카드에 내용만 있어도 생성 성공`() {
        val given = "내용"
        assertEquals(
            given,
            DefaultTodoTask(
                title = "타이틀",
                description = given,
                tags = Tags(),
                user = User.Assignee("테스트"),
                status = Status.TODO,
            ).description,
        )
    }

    @Test
    fun `카드에 태그가 5개 이하면 생성 성공`() {
        val given = Tags(listOf(Tag("컴포넌트"), Tag("성능"), Tag("컴포즈"), Tag("테스트"), Tag("안드로이드")))
        DefaultTodoTask(title = "타이틀", tags = given, user = User.Assignee("테스트"), status = Status.TODO)
    }

    @Test
    fun `카드에 모든 필드가 있으면 생성 성공`() {
        val givenTitle = "타이틀"
        val givenContent = "내용"
        val givenTags = Tags(listOf(Tag("컴포넌트"), Tag("성능")))
        val givenAssignee = User.Assignee("다이노")

        val card = DefaultTodoTask(
            title = givenTitle,
            description = givenContent,
            tags = givenTags,
            user = givenAssignee,
            status = Status.TODO,
        )
        assertEquals(givenTitle, card.title)
        assertEquals(givenContent, card.description)
        assertEquals(givenTags.items.toSet(), card.tags.items.toSet())
        assertEquals(givenAssignee, card.user)
    }
}
