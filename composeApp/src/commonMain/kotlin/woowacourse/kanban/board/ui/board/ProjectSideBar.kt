package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.project_sidebar_subtitle
import kanbanboard.composeapp.generated.resources.project_sidebar_title
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.ui.theme.CustomTheme

@Composable
fun ProjectSideBar(
    kanbanBoardState: KanbanBoardState,
    onProjectSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    innerPadding: Dp = 16.dp,
) {
    Column(
        modifier = modifier,
    ) {
        SideBarHeader(modifier = Modifier.padding(innerPadding))
        HorizontalDivider(modifier = Modifier.height(1.dp).background(CustomTheme.colors.gray.w100))
        ProjectTabs(
            kanbanBoardState.allProjects,
            kanbanBoardState.currentProject,
            onProjectSelect,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun SideBarHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(Res.string.project_sidebar_title),
            color = CustomTheme.colors.gray.w600,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
        )
        Text(
            text = stringResource(Res.string.project_sidebar_subtitle),
            color = CustomTheme.colors.gray.w300,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
        )
    }
}

@Composable
private fun ProjectTabs(
    projects: List<KanbanProjectState>,
    selectedProject: KanbanProjectState,
    onProjectSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        projects.forEachIndexed { index, project ->
            val isSelected = (project == selectedProject)
            FilterChip(
                selected = isSelected,
                onClick = { onProjectSelect(index) },
                label = { Text(project.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                modifier = Modifier.fillMaxWidth().semantics { contentDescription = "${project.name} 전환 버튼" },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = CustomTheme.colors.white,
                    labelColor = CustomTheme.colors.blue.w700,
                    selectedContainerColor = CustomTheme.colors.blue.w100,
                    selectedLabelColor = CustomTheme.colors.purple.w50,
                ),
                border = null,
                elevation = FilterChipDefaults.elevatedFilterChipElevation(
                    elevation = if (isSelected) 2.dp else 0.dp,
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProjectSideBarPreview() {
    ProjectSideBar(
        modifier = Modifier.width(255.dp).fillMaxHeight(),
        kanbanBoardState = KanbanBoardState(
            KanbanProjectState("Compose1", ::isTaskMovable),
            KanbanProjectState("Compose2", ::isTaskMovable),
        ),
        onProjectSelect = {},
    )
}
