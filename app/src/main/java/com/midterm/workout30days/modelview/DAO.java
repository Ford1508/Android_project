package com.midterm.workout30days.modelview;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAO {
    public static DAO instance;
    public static DAO getInstance(){
        if (instance == null)
        {
            instance = new DAO();
        }
        return instance;
    }
    public DatabaseReference getDatabaseReference(Context context, String path){
        FirebaseApp.initializeApp(context);
        DatabaseReference myRef;
        myRef = FirebaseDatabase.getInstance().getReference(path);
        return myRef;
    }
}
