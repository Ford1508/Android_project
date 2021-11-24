package com.midterm.workout30days.modelview;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.midterm.workout30days.model.UserComplete;

import java.util.List;

@Dao
public interface UserRoomDatabaseDao {
    @Insert
    public void insert(UserComplete... userCompletes);
    @Query("SELECT * FROM UserComplete")
    public List<UserComplete> getAllUserComplete();
}
