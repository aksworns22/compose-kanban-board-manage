package woowacourse.kanban.board

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import woowacourse.kanban.board.ui.board.KanbanBoardScreen
import woowacourse.kanban.board.ui.board.KanbanBoardState
import woowacourse.kanban.board.ui.board.KanbanProjectState

@Composable
fun App() {
    MaterialTheme {
        KanbanBoardScreen(
            initialKanbanBoardState = KanbanBoardState(
                KanbanProjectState("A 프로젝트"),
                KanbanProjectState("B 프로젝트"),
                KanbanProjectState("C 프로젝트"),
            ),
        )
    }
}
