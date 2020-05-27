package com.example.listoftasks.recyclerView;

import com.example.listoftasks.models.TaskModel;

public interface RecyclerAdapterListener {

    void statusButtonClicked(TaskModel taskModel);

    void itemInserted();
}
