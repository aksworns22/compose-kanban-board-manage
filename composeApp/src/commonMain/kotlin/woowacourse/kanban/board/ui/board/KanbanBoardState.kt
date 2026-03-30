package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue

class KanbanBoardState(vararg kanbanProjectStates: KanbanProjectState) {
    init {
        require(kanbanProjectStates.isNotEmpty()) { "최소 하나의 칸반 프로젝트가 필요합니다. " }
    }
    private val _allProjects = mutableStateListOf(*kanbanProjectStates)
    val allProjects: List<KanbanProjectState> get() = _allProjects.toList()

    private var currentProjectIndex: Int by mutableIntStateOf(0)

    val currentProject: KanbanProjectState get() = _allProjects[currentProjectIndex]

    fun selectProject(index: Int) {
        if (index !in _allProjects.indices) return
        currentProjectIndex = index
    }
}
