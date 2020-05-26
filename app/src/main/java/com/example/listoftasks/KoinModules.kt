package com.example.listoftasks

import android.app.Application
import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
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

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideRepository(dao: TaskDao): TaskRepository =
            TaskRepository(dao)

    single { provideRepository(get()) }
}

fun startMyKoin(context: Context) {
    startKoin {
        androidContext(context)
        modules(listOf(viewModelModules, databaseModule, repositoryModule))
    }
}

class MainActivityHolder : KoinComponent {
    val mainActivityViewModel: MainActivityViewModel by inject()
}