package com.midterm.workout30days.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.midterm.workout30days.R;

public class ExerciseBreakActivity extends AppCompatActivity {

    TextView txtTimerBreak;
    Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_break);
        txtTimerBreak = findViewById(R.id.txt_time_break);
        btnSkip = findViewById(R.id.btn_time_skip);
        loadTimmer();
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void loadTimmer() {
        CountDownTimer w = new CountDownTimer(30000, 1000) {
            public void onTick(long mil) {
                txtTimerBreak.setText(String.valueOf(mil / 1000));
            }
            public void onFinish() {
                finish();
            }
        }.start();
    }
}