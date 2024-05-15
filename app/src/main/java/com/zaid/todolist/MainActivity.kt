package com.zaid.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.zaid.todolist.navigation.AppNavHost
import com.zaid.todolist.ui.theme.ToDoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val snackBarHostState = remember { SnackbarHostState() }
            ToDoListTheme {
                Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        AppNavHost(navHostController = rememberNavController(),
                            onShowSnackBar = { message, action, duration ->
                                snackBarHostState.showSnackbar(
                                    message = message,
                                    actionLabel = action,
                                    duration = duration,
                                    withDismissAction = duration == SnackbarDuration.Indefinite
                                ) == SnackbarResult.ActionPerformed
                            })
                    }
                }
            }
        }
    }
}

