package com.zaid.todolist.presentation.home_screen

import com.zaid.todolist.presentation.model.TodoLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


data class HomeScreenUiState(

    val snackBarMessage: String? = null,
    val getAllTodos: Flow<List<TodoLocal>> = flow { emptyList<TodoLocal>() },
    val todoLocal: TodoLocal = TodoLocal(id = -1, task = "", isImportant = false)

)
