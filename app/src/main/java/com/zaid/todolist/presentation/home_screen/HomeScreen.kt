package com.zaid.todolist.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zaid.todolist.R
import com.zaid.todolist.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeScreenUiState: HomeScreenUiState,
    navController: NavController,
    onShowSnackBar: suspend (message: String, actionLabel: String?, duration: SnackbarDuration) -> Boolean,
    onMessageDisplay: () -> Unit,
    deleteTodo: (id: Int, task: String, isImportant: Boolean) -> Unit
) {

    LaunchedEffect(key1 = homeScreenUiState) {
        if (homeScreenUiState.snackBarMessage != null) {
            onShowSnackBar(homeScreenUiState.snackBarMessage, null, SnackbarDuration.Short)
            onMessageDisplay()
        }
    }

    val todoList by homeScreenUiState.getAllTodos.collectAsState(initial = emptyList())
    val todoListReversed = todoList.reversed()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.matchParentSize()) {

            TopAppBar(title = { Text(text = "Todo App", fontWeight = FontWeight.Bold) })

            if (todoList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No Todo Tasks",
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                item {

                }
                items(todoList.size) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable {
                                        deleteTodo(
                                            todoListReversed[it].id,
                                            todoListReversed[it].task,
                                            todoListReversed[it].isImportant
                                        )
                                    }
                                    .padding(8.dp)
                            )
                            Text(
                                text = todoListReversed[it].task,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            )
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        if (todoListReversed[it].isImportant) {
                            Icon(
                                painter = painterResource(id = R.drawable.star_icon),
                                contentDescription = "Important",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                        }
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    navController.navigate(
                                        Screen.EditTodoScreen.passInId(todoListReversed[it].id)
                                    )
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(30.dp),
            onClick = {
                navController.navigate(Screen.CreateTodoScreen.route)
            }
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }

    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(
        homeScreenUiState = HomeScreenUiState(),
        navController = NavController(LocalContext.current),
        onShowSnackBar = { _, _, _ -> false },
        onMessageDisplay = {},
        deleteTodo = { _, _, _ -> }
    )
}