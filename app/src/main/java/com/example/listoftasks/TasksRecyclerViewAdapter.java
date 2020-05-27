package com.example.listoftasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listoftasks.databinding.TaskCardViewBinding;

import java.util.ArrayList;
import java.util.List;

public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DiffUtil.ItemCallback<TaskModel> TASK_DIFF_UTIL = new DiffUtil.ItemCallback<TaskModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull TaskModel oldItem, @NonNull TaskModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskModel oldItem, @NonNull TaskModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    private AsyncListDiffer<TaskModel> listDiffer = new AsyncListDiffer<>(this, TASK_DIFF_UTIL);

    private MainActivity mainActivity;

    public TasksRecyclerViewAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskCardViewBinding binding = TaskCardViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TaskViewHolder) {
            ((TaskViewHolder) holder).bind(listDiffer.getCurrentList().get(position));
        }
    }

    @Override
    public int getItemCount() {
        return listDiffer.getCurrentList().size();
    }

    void submitList(List<TaskModel> newList) {
        listDiffer.submitList(newList);
    }

    List<TaskModel> getList() {
        return listDiffer.getCurrentList();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private TaskCardViewBinding biding;

        public TaskViewHolder(@NonNull TaskCardViewBinding biding) {
            super(biding.getRoot());
            this.biding = biding;
        }

        void bind(TaskModel taskModel) {
            biding.setTaskModel(taskModel);
            biding.taskCardViewStatusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mainActivity != null) {
                        ((RecyclerAdapterListener) mainActivity).buttonClicked(taskModel);
                    }
                }
            });
            switch (taskModel.getStatus()) {
                case OPEN:
                    biding.taskCardViewConstraintLayout.setBackgroundColor(
                            ContextCompat.getColor(itemView.getContext(), R.color.openStatusColor));
                    biding.taskCardViewStatusButton.setText(R.string.button_text_start_travel);
                    break;
                case WORKING:
                    biding.taskCardViewConstraintLayout.setBackgroundColor(
                            ContextCompat.getColor(itemView.getContext(), R.color.workingStatusColor));
                    biding.taskCardViewStatusButton.setText(R.string.button_text_start_working);
                    break;
                case TRAVELING:
                    biding.taskCardViewConstraintLayout.setBackgroundColor(
                            ContextCompat.getColor(itemView.getContext(), R.color.travelingStatusColor));
                    biding.taskCardViewStatusButton.setText(R.string.button_text_stop);
                    break;
            }
        }
    }
}
