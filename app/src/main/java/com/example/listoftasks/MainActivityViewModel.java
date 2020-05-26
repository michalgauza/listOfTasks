package com.example.listoftasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private TaskRepository repo;
    private LiveData<List<TaskModel>> tasksListLiveData;

    public MainActivityViewModel(TaskRepository repo) {
        this.repo = repo;
        tasksListLiveData = repo.getAllTasks();
    }

    public void insertTask(TaskModel taskModel) {
        repo.insert(taskModel);
    }

    public void deleteTask(TaskModel taskModel){
        repo.delete(taskModel);
    }

    public LiveData<List<TaskModel>> getTasksListLiveData() {
        return tasksListLiveData;
    }
}
