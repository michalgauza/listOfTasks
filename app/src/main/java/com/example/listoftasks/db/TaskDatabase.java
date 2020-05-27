package com.example.listoftasks.db;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.listoftasks.models.TaskModel;

import java.util.concurrent.Executors;

import static org.koin.java.KoinJavaComponent.get;

@Database(entities = TaskModel.class, version = TaskDatabase.DB_VERSION)
public abstract class TaskDatabase extends RoomDatabase {

    static final int DB_VERSION = 1;

    public static String DB_NAME = "task_database";

    public abstract TaskDao taskDao();

    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        private int sampleTasksQuantity = 20;
        private String sampleTaskName = "Sample task";

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            Executors.newSingleThreadExecutor().execute(() -> {
                TaskDao taskDao = get(TaskDao.class);
                for (int i = 0; i < sampleTasksQuantity; i++) {
                    taskDao.insert(new TaskModel(sampleTaskName + i));
                }
            });
        }
    };
}
