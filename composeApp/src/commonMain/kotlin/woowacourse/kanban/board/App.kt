package woowacourse.kanban.board

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.ui.board.KanbanBoardScreen
import woowacourse.kanban.board.ui.board.ProjectState

@Composable
fun App() {
    MaterialTheme {
        KanbanBoardScreen(
            initialProjectState = ProjectState(
                KanbanProject(name = "두루두루 살펴보기", emptyList()),
                KanbanProject(name = "두루두루 공부하기", emptyList()),
                KanbanProject(name = "블랙핑크 - 뚜루뚜뚜뚜 뚜루뚜뚜뚜", emptyList()),
            ),
        )
    }
}
