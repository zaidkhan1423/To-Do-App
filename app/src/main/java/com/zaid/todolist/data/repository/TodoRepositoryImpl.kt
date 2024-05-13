package com.zaid.todolist.data.repository

import com.zaid.todolist.data.local.TodoDao
import com.zaid.todolist.domain.model.Todo
import com.zaid.todolist.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val toDoDao: TodoDao) : TodoRepository {
    override suspend fun insertTodo(todo: Todo) = toDoDao.insertToDo(todo = todo)
    override suspend fun getTodoById(id: Int): Todo = toDoDao.getTodoById(id = id)
    override suspend fun updateTodo(todo: Todo) = toDoDao.updateTodo(todo)
    override suspend fun deleteTodo(todo: Todo) = toDoDao.deleteToDo(todo = todo)
    override fun getALlTodos(): Flow<List<Todo>> = toDoDao.getAllTodos()
}