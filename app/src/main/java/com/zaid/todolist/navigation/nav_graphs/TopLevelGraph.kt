package com.zaid.todolist.navigation.nav_graphs

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.zaid.todolist.navigation.NavGraphRoutes
import com.zaid.todolist.navigation.Screen
import com.zaid.todolist.navigation.sharedViewModel
import com.zaid.todolist.presentation.create_todo_screen.CreateTodoScreen
import com.zaid.todolist.presentation.edit_todo_screen.EditTodoScreen
import com.zaid.todolist.presentation.home_screen.HomeScreen
import com.zaid.todolist.presentation.home_screen.HomeScreenViewModel

fun NavGraphBuilder.topLevelGraph(
    navController: NavHostController,
    onShowSnackBar: suspend (message: String, actionLabel: String?, duration: SnackbarDuration) -> Boolean
) {
    navigation(startDestination = Screen.HomeScreen.route, route = NavGraphRoutes.TOP_LEVEL) {

        composable(route = Screen.HomeScreen.route) { entry ->

            val homeScreenViewModel: HomeScreenViewModel =
                entry.sharedViewModel(navHostController = navController)
            val homeScreenUiState by homeScreenViewModel.homeUiState.collectAsStateWithLifecycle()

            HomeScreen(
                navController = navController,
                homeScreenUiState = homeScreenUiState,
                onShowSnackBar = onShowSnackBar,
                onMessageDisplay = homeScreenViewModel::onMessageDisplayed,
                deleteTodo = homeScreenViewModel::deleteTodo
                )

        }
        composable(
            route = Screen.CreateTodoScreen.route
        ) { entry ->

            val homeScreenViewModel: HomeScreenViewModel =
                entry.sharedViewModel(navHostController = navController)
            val homeScreenUiState by homeScreenViewModel.homeUiState.collectAsStateWithLifecycle()

            CreateTodoScreen(
                uiState = homeScreenUiState,
                insertTodo = homeScreenViewModel::insertTodoTask,
                onShowSnackBar = onShowSnackBar,
                onMessageDisplay = homeScreenViewModel::onMessageDisplayed,
                navController = navController
            )
        }

        composable(
            route = Screen.EditTodoScreen.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = null
                nullable = true
            })
        ) { entry ->

            val homeScreenViewModel: HomeScreenViewModel = entry.sharedViewModel(navHostController = navController)
            val homeScreenUiState by homeScreenViewModel.homeUiState.collectAsStateWithLifecycle()
            val id = entry.arguments?.getString("id")

            EditTodoScreen(
                navController = navController,
                uiState = homeScreenUiState,
                onShowSnackBar = onShowSnackBar,
                onMessageDisplay = homeScreenViewModel::onMessageDisplayed,
                id = id,
                getTodoTaskById = homeScreenViewModel::getTodoTaskById,
                updateTodoTask = homeScreenViewModel::updateTodoTask
            )
        }

    }
}