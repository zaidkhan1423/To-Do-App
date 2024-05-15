package com.zaid.todolist.navigation

sealed class Screen(val route: String) {

    data object HomeScreen : Screen("home_screen")
    data object CreateTodoScreen : Screen("create_todo_screen")
    data object EditTodoScreen : Screen("edit_todo_screen/{id}") {
        fun passInId(id: Int): String =
            "edit_todo_screen/$id"
    }
}