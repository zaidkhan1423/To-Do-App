package com.zaid.todolist.di

import android.content.Context
import androidx.room.Room
import com.zaid.todolist.data.local.TodoDao
import com.zaid.todolist.data.local.TodoDatabase
import com.zaid.todolist.data.repository.TodoRepositoryImpl
import com.zaid.todolist.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalDb(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(database: TodoDatabase): TodoDao = database.todoDao()


    @Provides
    @Singleton
    fun provideTodoRepository(todoRepositoryImpl: TodoRepositoryImpl): TodoRepository = todoRepositoryImpl
}