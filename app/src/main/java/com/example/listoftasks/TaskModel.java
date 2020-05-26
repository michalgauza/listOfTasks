package com.example.listoftasks;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "task_table")
public class TaskModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @TypeConverters(Converters.class)
    private TaskStatus status;

    public TaskModel(String name) {
        this.name = name;
        this.status = TaskStatus.OPEN;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof TaskModel) {
            if (((TaskModel) obj).status != this.status) {
                return false;
            }
            if (!((TaskModel) obj).name.equals(this.name)) {
                return false;
            }
            if (((TaskModel) obj).id != this.id) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
}
