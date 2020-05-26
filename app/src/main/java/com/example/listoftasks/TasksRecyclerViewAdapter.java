package com.example.listoftasks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listoftasks.databinding.TaskCardViewBinding;

import java.util.List;


public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    DiffUtil.ItemCallback<TaskModel> TASK_DIFF_UTIL = new DiffUtil.ItemCallback<TaskModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull TaskModel oldItem, @NonNull TaskModel newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskModel oldItem, @NonNull TaskModel newItem) {
            return false;
        }
    };

    private AsyncListDiffer<TaskModel> listDiffer = new AsyncListDiffer<>(this, TASK_DIFF_UTIL);

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
        }
    }
}
