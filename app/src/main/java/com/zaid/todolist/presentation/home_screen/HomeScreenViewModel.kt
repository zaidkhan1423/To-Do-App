package com.zaid.todolist.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaid.todolist.domain.model.Todo
import com.zaid.todolist.domain.repository.TodoRepository
import com.zaid.todolist.presentation.model.toLocal
import com.zaid.todolist.presentation.model.toLocalFlowList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _homeScreenUiState = MutableStateFlow(HomeScreenUiState())
    val homeUiState = _homeScreenUiState.asStateFlow()

    private var deletedTodo: Todo? = null

    init {
        getAllTodoTasks()
    }

    fun onMessageDisplayed() {
        _homeScreenUiState.update {
            it.copy(
                snackBarMessage = null
            )
        }
    }

    fun insertTodoTask(task: String, isImportant: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTodo(todo = Todo(task = task, isImportant = isImportant))
            _homeScreenUiState.update { uiState ->
                uiState.copy(
                    snackBarMessage = "Task Created Successfully"
                )
            }
        }
    }

    private fun getAllTodoTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            _homeScreenUiState.update { uiState ->
                uiState.copy(
                    getAllTodos = repository.getALlTodos().toLocalFlowList()
                )
            }
        }
    }

    fun updateTodoTask(id: Int, task: String, isImportant: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo = Todo(id = id, task = task, isImportant = isImportant))
            _homeScreenUiState.update { uiState ->
                uiState.copy(
                    snackBarMessage = "Task Updated Successfully"
                )
            }
        }
    }

    fun deleteTodo(id: Int, task: String, isImportant: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            deletedTodo = Todo(id = id, task = task, isImportant = isImportant)
            repository.deleteTodo(todo = Todo(id = id, task = task, isImportant = isImportant))
            _homeScreenUiState.update { uiState ->
                uiState.copy(
                    snackBarMessage = "Task Deleted Successfully"
                )
            }
        }
    }

    private fun undoDeletedTodo() {
        deletedTodo?.let { todo ->
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertTodo(todo = todo)
            }
        }
    }

    fun getTodoTaskById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _homeScreenUiState.update { uiState ->
                uiState.copy(
                    todoLocal = async { repository.getTodoById(id = id).toLocal() }.await()
                )
            }
        }
    }


}