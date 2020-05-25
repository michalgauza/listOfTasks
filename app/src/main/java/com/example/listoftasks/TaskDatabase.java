package com.example.listoftasks;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = TaskModel.class, version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    private static TaskDatabase instance;

    public abstract TaskDao taskDao();

    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class,
                            "task_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTack(instance).execute();

        }
    };

    private static class PopulateDatabaseAsyncTack extends AsyncTask<Void, Void, Void>{
        private TaskDao taskDao;

        private PopulateDatabaseAsyncTack(TaskDatabase taskDatabase) {
            taskDao = taskDatabase.taskDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.insert(new TaskModel("task 1"));
            taskDao.insert(new TaskModel("task 2"));
            taskDao.insert(new TaskModel("task 3"));
            return null;
        }
    }
}