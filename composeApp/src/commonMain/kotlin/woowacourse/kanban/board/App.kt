package woowacourse.kanban.board

import androidx.compose.runtime.Composable
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.board.KanbanBoardScreen
import woowacourse.kanban.board.ui.board.KanbanBoardState
import woowacourse.kanban.board.ui.board.KanbanProjectState
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun App() {
    CustomTheme {
        val users = listOf(
            User.None,
            User.Assignee("손흥민"),
            User.Assignee("봉준호"),
            User.Assignee("BTS"),
            User.Assignee("스마일"),
            User.Assignee("렛츠 고!"),
        )
        KanbanBoardScreen(
            kanbanBoardState = KanbanBoardState(
                KanbanProjectState(name = "A 프로젝트", users = users),
                KanbanProjectState(name = "B 프로젝트", users = users),
                KanbanProjectState(name = "C 프로젝트", users = users),
            ),
        )
    }
}
