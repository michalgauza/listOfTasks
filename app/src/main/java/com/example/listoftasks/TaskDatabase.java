package com.example.listoftasks;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.koin.java.KoinJavaComponent.get;

@Database(entities = TaskModel.class, version = 2)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

    static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            Executors.newSingleThreadExecutor().execute(() -> {
                TaskDao taskDao = get(TaskDao.class);
                for (int i = 0; i < 5; i++) {
                    taskDao.insert(new TaskModel("Sample Task " + i));
                }
            });
        }
    };
}
