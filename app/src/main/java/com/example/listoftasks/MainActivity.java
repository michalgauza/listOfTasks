package com.example.listoftasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getTasksListLiveData().observe(this, new Observer<List<TaskModel>>(){

            @Override
            public void onChanged(List<TaskModel> taskModels) {
                System.out.println(taskModels);
            }
        });

        //todo remove later
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.insertTask(new TaskModel("test"));
            }
        });
    }
}
