package com.example.listoftasks.mainActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.listoftasks.R;
import com.example.listoftasks.recyclerView.RecyclerAdapterListener;
import com.example.listoftasks.recyclerView.SwipeListener;
import com.example.listoftasks.recyclerView.SwipeToDeleteCallback;
import com.example.listoftasks.models.TaskModel;
import com.example.listoftasks.models.TaskStatus;
import com.example.listoftasks.recyclerView.TasksRecyclerViewAdapter;
import com.example.listoftasks.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.koin.java.KoinJavaComponent.get;

public class MainActivity extends AppCompatActivity implements SwipeListener, RecyclerAdapterListener {

    private MainActivityViewModel viewModel = get(MainActivityViewModel.class);

    private ActivityMainBinding binding;

    private TasksRecyclerViewAdapter tasksRecyclerViewAdapter;
    private RecyclerView recyclerView;

    private Observer<List<TaskModel>> tasksListObserver = tasksList -> {
        tasksList.stream().filter(taskModel -> taskModel.getStatus() != TaskStatus.OPEN)
                .findFirst()
                .ifPresent(notOpenTask -> tasksList.stream()
                        .filter(taskModel -> taskModel.getId() != notOpenTask.getId())
                        .forEach(taskModel -> taskModel.setCanStatusChanged(false)));
        tasksRecyclerViewAdapter.submitList(tasksList);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupRecyclerViewAdapter();
        setupItemTouchHelper();
        setupAddFab();

        viewModel.getTasksListLiveData().observe(this, tasksListObserver);
    }

    private void setupAddFab() {
        binding.mainActivityFab.setOnClickListener(v -> addTask());
    }

    private void setupItemTouchHelper() {
        new ItemTouchHelper(new SwipeToDeleteCallback(this))
                .attachToRecyclerView(recyclerView);
    }

    private void setupRecyclerViewAdapter() {
        tasksRecyclerViewAdapter = new TasksRecyclerViewAdapter(this);
        recyclerView = binding.mainActivityRecyclerView;
        recyclerView.setAdapter(tasksRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void itemSwiped(int adapterPosition) {
        viewModel.deleteTask(tasksRecyclerViewAdapter.getList().get(adapterPosition));
    }

    @Override
    public void statusButtonClicked(TaskModel taskModel) {
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

    @Override
    public void itemInserted() {
        int recyclerFirstPosition = 0;
        if (recyclerView.getLayoutManager() != null)
            recyclerView.getLayoutManager()
                    .smoothScrollToPosition(recyclerView, null, recyclerFirstPosition);
    }


    public void addTask() {
        createDialog();
    }

    public void createDialog() {

        EditText taskNameEditText = getEditText();

        LinearLayout layout = getLinearLayout(taskNameEditText);

        AlertDialog.Builder alertDialogBuilder = getDialogBuilder(taskNameEditText, layout);

        AlertDialog addTaskDialog = alertDialogBuilder.create();

        showKeyboard(addTaskDialog);

        alertDialogBuilder.show();
    }

    private void showKeyboard(AlertDialog addTaskDialog) {
        if (addTaskDialog.getWindow() != null)
            addTaskDialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @NotNull
    private AlertDialog.Builder getDialogBuilder(EditText taskNameEditText, LinearLayout layout) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        alertDialogBuilder.setView(layout);
        alertDialogBuilder.setTitle(R.string.new_task_dialog_title);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setNegativeButton(R.string.new_task_dialog_negative_button,
                (dialog, whichButton) -> dialog.cancel());
        alertDialogBuilder.setPositiveButton(R.string.new_task_dialog_positive_button,
                (dialog, which) -> setupPositiveButton(taskNameEditText));
        return alertDialogBuilder;
    }

    private void setupPositiveButton(EditText taskNameEditText) {
        String inputText = taskNameEditText.getText().toString();
        if (inputText.isEmpty()) {
            Snackbar.make(binding.mainActivityConstraintLayout, R.string.add_task_error_message,
                    Snackbar.LENGTH_SHORT).show();
        } else {
            viewModel.insertTask(new TaskModel(inputText));
        }
    }

    @NotNull
    private LinearLayout getLinearLayout(EditText taskNameEditText) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 20, 40, 20);
        layout.addView(taskNameEditText,
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        return layout;
    }

    @NotNull
    private EditText getEditText() {
        EditText editText = new EditText(this);
        editText.setTextColor(Color.WHITE);
        editText.requestFocus();
        return editText;
    }
}
