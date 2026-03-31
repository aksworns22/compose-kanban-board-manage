package woowacourse.kanban.board

import androidx.compose.runtime.Composable
import woowacourse.kanban.board.ui.board.KanbanBoardScreen
import woowacourse.kanban.board.ui.board.KanbanBoardState
import woowacourse.kanban.board.ui.board.KanbanProjectState
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun App() {
    CustomTheme {
        KanbanBoardScreen(
            initialKanbanBoardState = KanbanBoardState(
                KanbanProjectState("A 프로젝트"),
                KanbanProjectState("B 프로젝트"),
                KanbanProjectState("C 프로젝트"),
            ),
        )
    }
}
