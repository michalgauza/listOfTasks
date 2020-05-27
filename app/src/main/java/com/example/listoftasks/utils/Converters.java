package com.example.listoftasks.utils;

import androidx.room.TypeConverter;

import com.example.listoftasks.models.TaskStatus;
import com.google.gson.Gson;

import static org.koin.java.KoinJavaComponent.get;

public class Converters {

    private Gson gson = get(Gson.class);

    @TypeConverter
    public String taskStatusToJson(TaskStatus status) {
        return gson.toJson(status);
    }

    @TypeConverter
    public TaskStatus jsonToTaskStatus(String json) {
        return gson.fromJson(json, TaskStatus.class);
    }
}
