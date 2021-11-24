package com.midterm.workout30days.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.midterm.workout30days.R;
import com.midterm.workout30days.model.Excercise;
import com.midterm.workout30days.modelview.DAO;

public class ExerciseDetailsActivity extends AppCompatActivity {

    TextView txtContentDetails, txtDepscribeDetails, txtTimerDetails;
    ImageView imageViewDetails;
    Button btnOk;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);
        txtContentDetails = findViewById(R.id.txt_content_details);
        txtDepscribeDetails = findViewById(R.id.txt_describe_details);
        txtTimerDetails = findViewById(R.id.txt_timer_details);
        imageViewDetails = findViewById(R.id.img_details);
        btnOk = findViewById(R.id.btn_ok);
        dao = new DAO();

        loadData();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

    }

    private void loadData() {
        Intent intent = getIntent();
        dao.getInstance().getDatabaseReference(this, "Excercise").child(intent.getStringExtra("id_key"))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Excercise excercise = snapshot.getValue(Excercise.class);
                        txtContentDetails.setText(excercise.getContent());
                        txtTimerDetails.setText("Số lần thực hiện : " + Integer.toString(excercise.getRep()));
                        Glide.with(ExerciseDetailsActivity.this).load(excercise.getGif()).into(imageViewDetails);
                        txtDepscribeDetails.setText(excercise.getDescribe());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


}