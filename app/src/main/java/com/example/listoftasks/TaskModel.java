package com.example.listoftasks;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task_table")
public class TaskModel {
    @PrimaryKey
    private Long id;
    private String name;
//    private TaskStatus status;

    public TaskModel(String name) {
        this.id = new Date().getTime();
        this.name = name;
//        this.status = TaskStatus.OPEN;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public TaskStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(TaskStatus status) {
//        this.status = status;
//    }
}
