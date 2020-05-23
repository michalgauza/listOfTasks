package com.example.listoftasks;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<TaskModel>> tasksListLiveData;

    public TaskRepository(Application application) {
        TaskDatabase taskDatabase = TaskDatabase.getInstance(application);
        taskDao = taskDatabase.taskDao();
        tasksListLiveData = taskDao.getAllTasks();
    }

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void insert(TaskModel taskModel) {
//        new InsertTaskAsyncTask(taskDao).execute(taskModel);
        executorService.submit(() -> taskDao.insert(taskModel));
    }

    public void update(TaskModel taskModel) {

    }

    public void delete(TaskModel taskModel) {

    }

    public LiveData<List<TaskModel>> getAllTasks() {
        return tasksListLiveData;
    }


    private static class InsertTaskAsyncTask extends AsyncTask<TaskModel, Void, Void> {
        private TaskDao taskDao;

        private InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskModel... taskModels) {
            taskDao.insert(taskModels[0]);
            return null;
        }
    }
}
