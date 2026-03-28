package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task

@Stable
class ProjectState(vararg projects: KanbanProject) {
    init {
        require(projects.isNotEmpty()) { "최소 하나의 칸반 프로젝트가 필요합니다. " }
    }
    private val _allProjects = mutableStateListOf(*projects)
    val allProjects: List<KanbanProject> get() = _allProjects.toList()

    private var currentProjectIndex: Int by mutableIntStateOf(0)

    val currentProject: KanbanProject get() = _allProjects[currentProjectIndex]

    fun createTask(task: Task) {
        _allProjects[currentProjectIndex] = _allProjects[currentProjectIndex].addTask(task)
    }

    fun selectProject(index: Int) {
        if (index !in _allProjects.indices) return
        currentProjectIndex = index
    }

    fun changeTaskStatus(task: Task, newStatus: Status) {
        _allProjects[currentProjectIndex] = _allProjects[currentProjectIndex].changeTaskStatus(task, newStatus)
    }
}
