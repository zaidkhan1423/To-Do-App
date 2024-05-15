package com.zaid.todolist.presentation.model

import com.zaid.todolist.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class TodoLocal(
    val id: Int,
    val task: String,
    val isImportant: Boolean
)

fun Todo.toLocal(): TodoLocal = TodoLocal(
    id = id,
    task = task,
    isImportant = isImportant
)
fun List<Todo>.toLocalList(): List<TodoLocal> {
    return map { todo -> todo.toLocal() }
}
fun Flow<List<Todo>>.toLocalFlowList(): Flow<List<TodoLocal>>{
    return map { listOfTodo -> listOfTodo.toLocalList() }
}

