package com.example.listoftasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import kotlin.Lazy;

import static org.koin.java.KoinJavaComponent.get;

public class MainActivity extends AppCompatActivity implements SwipeListener {


    private MainActivityViewModel viewModel = get(MainActivityViewModel.class);
    private TasksRecyclerViewAdapter tasksRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksRecyclerViewAdapter = new TasksRecyclerViewAdapter();
        RecyclerView recyclerView = findViewById(R.id.main_activity_recycler_view);
        recyclerView.setAdapter(tasksRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        viewModel.getTasksListLiveData().observe(this, new Observer<List<TaskModel>>() {

            @Override
            public void onChanged(List<TaskModel> taskModels) {
                tasksRecyclerViewAdapter.submitList(taskModels);
            }
        });

    }

    @Override
    public void itemSwiped(int adapterPosition) {
        viewModel.deleteTask(tasksRecyclerViewAdapter.getList().get(adapterPosition));
    }
}
