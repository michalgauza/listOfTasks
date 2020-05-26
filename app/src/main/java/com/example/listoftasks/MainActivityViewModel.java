package com.example.listoftasks;

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

    public void updateTask(TaskModel taskModel){
        repo.update(taskModel);
    }

    public LiveData<List<TaskModel>> getTasksListLiveData() {
        return tasksListLiveData;
    }
}
