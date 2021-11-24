package com.midterm.workout30days.modelview;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.midterm.workout30days.model.UserComplete;
import com.midterm.workout30days.view.MainActivity;

@Database(entities = {UserComplete.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {

    public abstract UserRoomDatabaseDao userRoomDatabaseDao();
    public static AppRoomDatabase instance;


    public static AppRoomDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context ,AppRoomDatabase.class, "UserCompletes").build();
        }
        return instance;
    }
}
