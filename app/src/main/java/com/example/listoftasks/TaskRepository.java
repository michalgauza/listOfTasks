package com.example.listoftasks;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private TaskDao taskDao;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void insert(TaskModel taskModel) {
        executorService.submit(() -> taskDao.insert(taskModel));
    }

    public void update(TaskModel taskModel) {
        executorService.submit(() -> taskDao.update(taskModel));
    }

    public void delete(TaskModel taskModel) {
        executorService.submit(() -> taskDao.delete(taskModel));
    }

    public LiveData<List<TaskModel>> getAllTasks() {
        return taskDao.getAllTasks();
    }
}
