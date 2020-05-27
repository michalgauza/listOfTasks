package com.example.listoftasks.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listoftasks.R;
import com.example.listoftasks.models.TaskModel;
import com.example.listoftasks.databinding.TaskCardViewBinding;
import com.example.listoftasks.mainActivity.MainActivity;

import java.util.List;

public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.TaskViewHolder> {

    private AsyncListDiffer<TaskModel> listDiffer = new AsyncListDiffer<>(this, MyDiffUtill.TASK_DIFF_UTIL);

    private MainActivity mainActivity;

    public TasksRecyclerViewAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskCardViewBinding binding = TaskCardViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(listDiffer.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return listDiffer.getCurrentList().size();
    }

    public void submitList(List<TaskModel> newList) {
        List<TaskModel> oldList = listDiffer.getCurrentList();
        listDiffer.submitList(newList, () -> {
            if (oldList.size() < listDiffer.getCurrentList().size()) {
                if (mainActivity != null) {
                    mainActivity.itemInserted();
                }
            }
        });
    }

    public List<TaskModel> getList() {
        return listDiffer.getCurrentList();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private TaskCardViewBinding biding;

        TaskViewHolder(@NonNull TaskCardViewBinding biding) {
            super(biding.getRoot());
            this.biding = biding;
        }

        void bind(TaskModel taskModel) {
            biding.setTaskModel(taskModel);
            setupStatusButton(taskModel);
            setupStatusAndBackground(taskModel);
        }

        private void setupStatusButton(TaskModel taskModel) {
            if (!taskModel.getCanStatusChanged()) {
                biding.taskCardViewStatusButton.setVisibility(View.INVISIBLE);
            } else {
                biding.taskCardViewStatusButton.setVisibility(View.VISIBLE);
            }
            biding.taskCardViewStatusButton.setOnClickListener(v -> {
                if (mainActivity != null) {
                    ((RecyclerAdapterListener) mainActivity).statusButtonClicked(taskModel);
                }
            });
        }

        private void setupStatusAndBackground(TaskModel taskModel) {
            switch (taskModel.getStatus()) {
                case OPEN:
                    setupBackground(R.color.openStatusColor);
                    setupStatus(R.string.button_text_start_travel);
                    break;
                case TRAVELING:
                    setupBackground(R.color.travelingStatusColor);
                    setupStatus(R.string.button_text_start_working);
                    break;
                case WORKING:
                    setupBackground(R.color.workingStatusColor);
                    setupStatus(R.string.button_text_stop);
                    break;
            }
        }

        void setupBackground(@ColorRes int colorId) {
            biding.taskCardViewConstraintLayout.setBackgroundColor(
                    ContextCompat.getColor(itemView.getContext(), colorId));
        }

        void setupStatus(@StringRes int stringId) {
            biding.taskCardViewStatusButton.setText(stringId);
        }
    }
}
