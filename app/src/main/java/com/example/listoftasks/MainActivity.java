package com.example.listoftasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TasksRecyclerViewAdapter tasksRecyclerViewAdapter = new TasksRecyclerViewAdapter();
        RecyclerView recyclerView = findViewById(R.id.main_activity_recycler_view);
        recyclerView.setAdapter(tasksRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this.getApplicationContext()));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getTasksListLiveData().observe(this, new Observer<List<TaskModel>>() {

            @Override
            public void onChanged(List<TaskModel> taskModels) {
                tasksRecyclerViewAdapter.submitList(taskModels);
            }
        });

    }
}
