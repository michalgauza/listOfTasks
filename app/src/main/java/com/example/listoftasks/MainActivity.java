package com.example.listoftasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.listoftasks.databinding.ActivityMainBinding;

import static org.koin.java.KoinJavaComponent.get;

public class MainActivity extends AppCompatActivity implements SwipeListener, StatusButtonListener {

    private MainActivityViewModel viewModel = get(MainActivityViewModel.class);
    private ActivityMainBinding binding;
    private TasksRecyclerViewAdapter tasksRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        tasksRecyclerViewAdapter = new TasksRecyclerViewAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.main_activity_recycler_view);
        recyclerView.setAdapter(tasksRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        binding.mainActivityFab.setOnClickListener(v -> addTask()
        );

        viewModel.getTasksListLiveData().observe(this, taskModels -> {
            tasksRecyclerViewAdapter.submitList(taskModels);
        });

    }

    @Override
    public void itemSwiped(int adapterPosition) {
        viewModel.deleteTask(tasksRecyclerViewAdapter.getList().get(adapterPosition));
    }


    @Override
    public void buttonClicked(TaskModel taskModel) {
        TaskModel newTask = new TaskModel(taskModel.getName());
        newTask.setId(taskModel.getId());
        switch (taskModel.getStatus()) {
            case OPEN:
                newTask.setStatus(TaskStatus.TRAVELING);
                break;
            case TRAVELING:
                newTask.setStatus(TaskStatus.WORKING);
                break;
            case WORKING:
                newTask.setStatus(TaskStatus.OPEN);
                break;
        }
        viewModel.updateTask(newTask);
    }

    public void addTask() {
        viewModel.insertTask(new TaskModel(("new task")));
    }
}
