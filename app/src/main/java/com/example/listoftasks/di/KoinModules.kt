package com.example.listoftasks.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.listoftasks.mainActivity.MainActivityViewModel
import com.example.listoftasks.db.TaskDao
import com.example.listoftasks.db.TaskDatabase
import com.example.listoftasks.db.TaskRepository
import com.google.gson.Gson
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
                TaskDatabase.DB_NAME
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

val gsonModule = module {
    single {Gson()}
}

fun startMyKoin(context: Context) {
    startKoin {
        androidContext(context)
        modules(listOf(viewModelModules, databaseModule, gsonModule))
    }
}