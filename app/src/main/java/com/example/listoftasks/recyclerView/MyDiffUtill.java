package com.example.listoftasks.recyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.listoftasks.models.TaskModel;

class MyDiffUtill {
    static final DiffUtil.ItemCallback<TaskModel> TASK_DIFF_UTIL = new DiffUtil.ItemCallback<TaskModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull TaskModel oldItem, @NonNull TaskModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskModel oldItem, @NonNull TaskModel newItem) {
            return oldItem.equals(newItem);
        }
    };
}
