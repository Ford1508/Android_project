package com.midterm.workout30days.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.midterm.workout30days.R;
import com.midterm.workout30days.model.Day;
import com.midterm.workout30days.model.UserComplete;
import com.midterm.workout30days.modelview.AppRoomDatabase;
import com.midterm.workout30days.modelview.DAO;
import com.midterm.workout30days.modelview.DaysAdapter;
import com.midterm.workout30days.modelview.UserRoomDatabaseDao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton exitApp;
    DaysAdapter daysAdapter;
    public ArrayList<Day> arrayList;
    AppRoomDatabase appRoomDatabase;
    UserRoomDatabaseDao userRoomDatabaseDao;
    View newView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_day);
        exitApp = findViewById(R.id.btn_exit_app);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        recyclerView.setLayoutManager(gridLayoutManager);
        arrayList = new ArrayList<Day>();
        daysAdapter = new DaysAdapter(arrayList);
        recyclerView.setAdapter(daysAdapter);
        appRoomDatabase = AppRoomDatabase.getInstance(this);
        userRoomDatabaseDao = appRoomDatabase.userRoomDatabaseDao();
        loadData();
        daysAdapter.notifyDataSetChanged();
        readCacheData();
        exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogExitEvent();
            }
        });
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                daysAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBackPressed() {
        dialogExitEvent();
    }

    private void dialogExitEvent() {
        AlertDialog.Builder altdial = new AlertDialog.Builder(this);
        altdial.setMessage("Bạn chắc chắn muốn đóng ứng dụng ?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        exitApp();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = altdial.create();
        alertDialog.setTitle("Dialog Header");
        alertDialog.show();
    }

    private void exitApp() {
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        startActivity(intent);

        // Tao su kien ket thuc app
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
        finish();
        addUserData();
    }

    public void addUserData() {
        try {
            String data ="";
            File pathCacheDir = getCacheDir();
            String strCacheFileName = "myCacheFile.cache";
            File newCacheFile = new
                    File(pathCacheDir, strCacheFileName);
            Scanner sc=new Scanner(newCacheFile);
            while(sc.hasNext())
            {
                data+=sc.nextLine()+"";
            }
           // newCacheFile.delete();
            if(data != "")
            {
                String finalData = data;
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        userRoomDatabaseDao.insert(new UserComplete(Integer.parseInt(finalData), Integer.parseInt(finalData)));
                    }
                });
            }
            if(data == "")
                return;
            newCacheFile.delete();
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void loadData() {
        DAO.getInstance().getDatabaseReference(this, "Day")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot item : snapshot.getChildren())
                        {
                            Day day = item.getValue(Day.class);
                            arrayList.add(day);
                        }
                        daysAdapter.notifyDataSetChanged();



                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                if(userRoomDatabaseDao.getAllUserComplete() != null)
                                {
                                    for (Day day : arrayList) {
                                        for (UserComplete userComplete : userRoomDatabaseDao.getAllUserComplete()) {
                                            if (day.getId() == userComplete.getDay_complete()) {
                                                day.setComplete(true);
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void readCacheData(){
        try {
            File pathCacheDir = getCacheDir();
            String strCacheFileName = "myCacheFile.cache";
            File newCacheFile = new
                    File(pathCacheDir, strCacheFileName);
            Scanner sc=new Scanner(newCacheFile);
            String data="";
            while(sc.hasNext())
            {
                data+=sc.nextLine()+"";
            }
            for(Day day : arrayList)
            {
                if (day.getId() == Integer.parseInt(data))
                {
                    day.setComplete(true);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}