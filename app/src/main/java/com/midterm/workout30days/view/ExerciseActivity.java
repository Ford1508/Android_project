package com.midterm.workout30days.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.midterm.workout30days.R;
import com.midterm.workout30days.model.Excercise;
import com.midterm.workout30days.modelview.DAO;
import com.midterm.workout30days.modelview.ExcerciseAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

    TextView txtDayExercise, txtCheckComplete;
    RecyclerView recyclerView;
    Button btnStart;
    ExcerciseAdapter excerciseAdapter;
    ArrayList<Excercise> arrayList;
    DAO dao;

    public String day_exercises;

    private static final int PLAY_ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        txtDayExercise = findViewById(R.id.txt_day_exercise);
        recyclerView = findViewById(R.id.rv_exercise);
        btnStart = findViewById(R.id.btn_start);
        txtCheckComplete = findViewById(R.id.txt_check_complete);
        arrayList = new ArrayList<Excercise>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        excerciseAdapter = new ExcerciseAdapter(arrayList);
        recyclerView.setAdapter(excerciseAdapter);
        dao = new DAO();
        loadData();
        //practiseAdapter.notifyDataSetChanged();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExerciseActivity.this, PlayActivity.class);
                intent.putExtra("day_exercises", day_exercises);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivityForResult(intent, PLAY_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PLAY_ACTIVITY_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                boolean check = data.getExtras().getBoolean("check");
                if(check == true)
                {
                    createCache();
                    finish();
                }
            }
        }
    }

    private void createCache() {
        Bundle bundle = getIntent().getExtras();
        try {
            File pathCacheDir = getCacheDir();
            String strCacheFileName = "myCacheFile.cache";
            int day_id_complete = bundle.getInt("day_id");
            File newCacheFile = new
                    File(pathCacheDir, strCacheFileName);
            newCacheFile.createNewFile();
            FileOutputStream foCache =
                    new FileOutputStream(
                            newCacheFile.getAbsolutePath());
            foCache.write(Integer.toString(day_id_complete).getBytes());
            foCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        Bundle bundle = getIntent().getExtras();
        txtDayExercise.setText(bundle.getString("day_content"));
        day_exercises = bundle.getString("day_excercises");
        if(bundle.getBoolean("day_completed") == true)
        {
            txtCheckComplete.setText("Đã hoàn thành");
            //btnStart.setText("Đã hoàn thành");
            btnStart.setEnabled(false);
        }
        if(day_exercises != null){
            String[] words = day_exercises.split(",");
            for(String word : words)
            {
                dao.getInstance().getDatabaseReference(this, "Excercise").child(word)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Excercise excercise = snapshot.getValue(Excercise.class);
                                arrayList.add(excercise);
                                excerciseAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        }
    }
}