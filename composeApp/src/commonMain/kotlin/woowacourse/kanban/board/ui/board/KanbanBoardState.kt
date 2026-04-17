package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue

class KanbanBoardState(vararg kanbanProjectStates: KanbanProjectState) {
    private val _allProjects = mutableStateListOf(*kanbanProjectStates)
    val allProjects: List<KanbanProjectState> get() = _allProjects.toList()
    val allProjectNames: List<String> get() = _allProjects.map { it.name }

    private var currentProjectIndex: Int by mutableIntStateOf(0)

    val currentProject: Result<KanbanProjectState> get() = runCatching { _allProjects[currentProjectIndex] }

    fun selectProject(index: Int) {
        if (index !in _allProjects.indices) return
        currentProjectIndex = index
    }
}
