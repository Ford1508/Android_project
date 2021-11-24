package com.midterm.workout30days.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.midterm.workout30days.R;
import com.midterm.workout30days.model.Excercise;
import com.midterm.workout30days.modelview.DAO;

public class PlayActivity extends AppCompatActivity {

    TextView txtContent, txtTimer;
    ImageView imageView;
    Button btnOk;
    DAO dao;
    int i;
    String[] words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        txtContent = findViewById(R.id.txt_content_play);
        txtTimer = findViewById(R.id.txt_timer_play);
        imageView = findViewById(R.id.img_play);
        btnOk = findViewById(R.id.btn_ok_play);
        dao = new DAO();
        i = 0;
        loadData(0);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i < (words.length - 1))
                {
                    Intent intent = new Intent(PlayActivity.this, ExerciseBreakActivity.class);
                    startActivity(intent);
                }
                if(i == (words.length - 1))
                {
                    Toast.makeText(getApplicationContext(), "Đã hoàn thành tất cả các bài tập !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("check", true);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        i = i + 1;
                        loadData(i);
                    }
                }, 1000);
            }
        });
    }


    private void loadData(int i) {
        Intent intent = getIntent();
        String day_excercises = intent.getStringExtra("day_exercises");
        if(day_excercises != null){
            words = day_excercises.split(",");
            if(i < words.length)
            {
                dao.getInstance().getDatabaseReference(this, "Excercise").child(words[i])
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Excercise excercise = snapshot.getValue(Excercise.class);
                                txtContent.setText(excercise.getContent());
                                txtTimer.setText("Số lần thực hiện : " + Integer.toString(excercise.getRep()));
                                Glide.with(PlayActivity.this).load(excercise.getGif()).into(imageView);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
            if (i >= words.length)
            {
                i = words.length;
            }
        }
    }
}