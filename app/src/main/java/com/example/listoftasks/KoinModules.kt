package com.example.listoftasks

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { MainActivityViewModel(get()) }
}

val databaseModule = module {
    fun provideDatabase(application: Application): TaskDatabase {
        return Room.databaseBuilder(
                application,
                TaskDatabase::class.java,
                "task_database"
        ).fallbackToDestructiveMigration()
                .addCallback(TaskDatabase.roomCallback)
                .build()
    }

    fun provideDao(database: TaskDatabase): TaskDao = database.taskDao()
    fun provideRepository(dao: TaskDao): TaskRepository =
            TaskRepository(dao)

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
    single { provideRepository(get()) }
}

fun startMyKoin(context: Context) {
    startKoin {
        androidContext(context)
        modules(listOf(viewModelModules, databaseModule))
    }
}