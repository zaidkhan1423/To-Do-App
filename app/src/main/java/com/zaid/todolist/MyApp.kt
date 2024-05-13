package com.zaid.todolist

import android.app.Application
import androidx.room.Insert
import com.zaid.todolist.domain.repository.TodoRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp: Application()