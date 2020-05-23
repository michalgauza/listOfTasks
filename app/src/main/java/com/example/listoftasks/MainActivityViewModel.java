package com.example.listoftasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private TaskRepository repo;
    private LiveData<List<TaskModel>> tasksListLiveData;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        repo = new TaskRepository(application);
        tasksListLiveData = repo.getAllTasks();
    }

    public void insertTask(TaskModel taskModel){
        repo.insert(taskModel);
    }

    public LiveData<List<TaskModel>> getTasksListLiveData() {
        return tasksListLiveData;
    }
}
