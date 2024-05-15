package com.zaid.todolist.presentation.edit_todo_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zaid.todolist.presentation.home_screen.HomeScreenUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTodoScreen(
    navController: NavController,
    uiState: HomeScreenUiState,
    onShowSnackBar: suspend (message: String, actionLabel: String?, duration: SnackbarDuration) -> Boolean,
    onMessageDisplay: () -> Unit,
    id: String?,
    getTodoTaskById: (id: Int) -> Unit,
    updateTodoTask: (id: Int, task: String, isImportant: Boolean) -> Unit
) {
    id?.let { getTodoTaskById(it.toInt()) }
    val context = LocalContext.current
    var todoTask by remember { mutableStateOf("") }
    var isImportant: Boolean by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = uiState) {
        if (uiState.snackBarMessage != null) {
            onShowSnackBar(uiState.snackBarMessage, null, SnackbarDuration.Short)
            onMessageDisplay()
        }
    }
    LaunchedEffect(key1 = uiState) {
        if (uiState.todoLocal.task != "") {
            todoTask = uiState.todoLocal.task
            isImportant = uiState.todoLocal.isImportant
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text(text = "Edit Todo Task", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .clickable { navController.popBackStack() }
                            .padding(10.dp)
                    )
                })
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    .fillMaxWidth()
                    .wrapContentHeight(),
                value = todoTask,
                onValueChange = { todoTask = it },
                colors = outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                ),
                placeholder = {
                    Text(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        text = "Enter Todo Task"
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isImportant, onCheckedChange = { isImportant = !isImportant })
                Text(text = "Important", fontSize = MaterialTheme.typography.bodyLarge.fontSize)
            }
        }


        FloatingActionButton(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(30.dp),
            onClick = {
                if (todoTask != "") {
                    updateTodoTask(id!!.toInt(), todoTask, isImportant)
                    navController.popBackStack()
                } else {
                    Toast.makeText(context, "Fill Todo Task", Toast.LENGTH_SHORT).show()
                }
            }) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EditTodoScreenPreview() {
    EditTodoScreen(
        uiState = HomeScreenUiState(),
        onShowSnackBar = { _, _, _ -> false },
        onMessageDisplay = {},
        navController = NavController(LocalContext.current),
        id = "",
        getTodoTaskById = { _ -> },
        updateTodoTask = { _, _, _ -> }
    )
}
