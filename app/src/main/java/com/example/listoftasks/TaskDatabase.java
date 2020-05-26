package com.example.listoftasks;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = TaskModel.class, version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
//
//    public static synchronized TaskDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = Room
//                    .databaseBuilder(context.getApplicationContext(),
//                            TaskDatabase.class,
//                            "task_database")
//                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
//                    .build();
//        }
//        return instance;
//    }
//
//    static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
//    ExecutorService executorService = Executors.newSingleThreadExecutor();
//    TaskDao taskDao;
//
//    @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//
//            executorService.submit(() -> taskDao.insert(new TaskModel("Task 1")));
//            executorService.submit(() -> taskDao.insert(new TaskModel("Task 2")));
//            executorService.submit(() -> taskDao.insert(new TaskModel("Task 3")));
//            executorService.submit(() -> taskDao.insert(new TaskModel("Task 4")));
//        }
//    };
 }
