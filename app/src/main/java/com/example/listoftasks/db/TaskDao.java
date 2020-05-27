package com.example.listoftasks.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listoftasks.models.TaskModel;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskModel taskModel);

    @Update
    void update(TaskModel taskModel);

    @Delete
    void delete(TaskModel taskModel);

    @Query("SELECT * FROM task_table ORDER BY id DESC")
    LiveData<List<TaskModel>> getAllTasks();
}
