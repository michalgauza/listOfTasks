package com.example.listoftasks.models;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.listoftasks.utils.Converters;

@Entity(tableName = TaskModel.TABLE_NAME)
public class TaskModel {
    static final String TABLE_NAME = "task_table";

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @TypeConverters(Converters.class)
    private TaskStatus status;
    @Ignore
    private Boolean canStatusChanged;

    public TaskModel(String name) {
        this.name = name;
        this.status = TaskStatus.OPEN;
        canStatusChanged = true;
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
            TaskModel other = (TaskModel) obj;
            if (other.id != this.id) {
                return false;
            }
            if (other.status != this.status) {
                return false;
            }
            if (!other.name.equals(this.name)) {
                return false;
            }
            return other.canStatusChanged == this.canStatusChanged;
        } else {
            return false;
        }
    }

    public Boolean getCanStatusChanged() {
        return canStatusChanged;
    }

    public void setCanStatusChanged(Boolean canStatusChanged) {
        this.canStatusChanged = canStatusChanged;
    }
}
