package com.example.listoftasks.mainActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.listoftasks.models.TaskModel;
import com.example.listoftasks.db.TaskRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private TaskRepository repo;

    public MainActivityViewModel(TaskRepository repo) {
        this.repo = repo;
    }

    public void insertTask(TaskModel taskModel) {
        repo.insert(taskModel);
    }

    public void deleteTask(TaskModel taskModel) {
        repo.delete(taskModel);
    }

    public void updateTask(TaskModel taskModel) {
        repo.update(taskModel);
    }

    public LiveData<List<TaskModel>> getTasksListLiveData() {
        return repo.getAllTasks();
    }
}
