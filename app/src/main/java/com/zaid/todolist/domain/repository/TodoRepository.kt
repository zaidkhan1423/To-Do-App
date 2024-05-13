package com.zaid.todolist.domain.repository

import com.zaid.todolist.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)
    suspend fun getTodoById(id: Int): Todo
    suspend fun updateTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
    fun getALlTodos(): Flow<List<Todo>>

}