package com.farmsteq.global.ui

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.farmsteq.global.MainViewModel
import com.farmsteq.global.UiState
import com.farmsteq.global.ui.components.BrandMark
import com.farmsteq.global.ui.screen.AdvisoryScreen
import com.farmsteq.global.ui.screen.HomeScreen
import com.farmsteq.global.ui.screen.SolutionsScreen
import com.farmsteq.global.ui.screen.NotebookScreen
import com.farmsteq.global.ui.screen.OnboardingScreen
import com.farmsteq.global.ui.screen.SettingsScreen

private data class Destination(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

private val destinations = listOf(
    Destination("home", "Home", Icons.Filled.Home),
    Destination("notebook", "Notebook", Icons.Filled.EditNote),
    Destination("solutions", "Solutions", Icons.Filled.Layers),
    Destination("advisory", "Advisory", Icons.Filled.Insights),
)

@Composable
fun AppNavigation(state: UiState, viewModel: MainViewModel) {
    val context = LocalContext.current
    val exportJson by viewModel.exportJson.collectAsStateWithLifecycle()

    LaunchedEffect(exportJson) {
        exportJson?.let { json ->
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/json"
                putExtra(Intent.EXTRA_SUBJECT, "Farmerssteq field notes")
                putExtra(Intent.EXTRA_TEXT, json)
            }
            runCatching { context.startActivity(Intent.createChooser(intent, "Export field notes")) }
            viewModel.clearExport()
        }
    }

    when {
        state.loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { BrandMark() }
        !state.onboarded -> OnboardingScreen(onDone = viewModel::completeOnboarding)
        else -> MainContent(state, viewModel)
    }
}

@Composable
private fun MainContent(state: UiState, viewModel: MainViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                destinations.forEach { item ->
                    val selected = currentRoute?.hierarchy?.any { it.route == item.route } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.label) },
                    )
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding),
        ) {
            composable("home") {
                HomeScreen(
                    state = state,
                    viewModel = viewModel,
                    onOpenNotebook = { navController.navigate("notebook") },
                    onOpenAdvisory = { navController.navigate("advisory") },
                    onOpenSolutions = { navController.navigate("solutions") },
                    onOpenSettings = { navController.navigate("settings") },
                )
            }
            composable("notebook") { NotebookScreen(state, viewModel) }
            composable("solutions") {
                SolutionsScreen(
                    onOpenNotebook = { navController.navigate("notebook") },
                    onOpenAdvisory = { navController.navigate("advisory") },
                )
            }
            composable("advisory") { AdvisoryScreen(state, viewModel) }
            composable("settings") { SettingsScreen(state, viewModel) }
        }
    }
}
