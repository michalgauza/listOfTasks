package com.example.listoftasks;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class Converters {

    private Gson gson = new Gson();

    @TypeConverter
    public String taskStatusToJson(TaskStatus status) {
        return gson.toJson(status);
    }

    @TypeConverter
    public TaskStatus jsonToTaskStatus(String json) {
        return gson.fromJson(json, TaskStatus.class);
    }
}
