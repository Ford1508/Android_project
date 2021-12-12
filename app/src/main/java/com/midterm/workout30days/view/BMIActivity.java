package com.midterm.workout30days.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.midterm.workout30days.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Timestamp;
import java.util.Date;
import java.util.Scanner;

public class BMIActivity extends AppCompatActivity {

    private EditText txtHeight, txtWeight;
    private Button btnOk, btnClear, btnSave, btnHistory;
    private TextView txtBMIShow, txtBMIHistory;
    float height, weight, bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);
        txtHeight = findViewById(R.id.txt_height);
        txtWeight = findViewById(R.id.txt_weight);
        btnOk = findViewById(R.id.btn_bmi_show);
        btnClear = findViewById(R.id.btn_clear);
        btnSave = findViewById(R.id.btn_bmi_save);
        btnHistory = findViewById(R.id.btn_bmi_history);
        txtBMIShow = findViewById(R.id.txt_bmi_show);
        txtBMIHistory = findViewById(R.id.txt_bmi_history);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHeight.setText(null);
                txtWeight.setText(null);
                txtBMIShow.setText(null);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    height = Float.parseFloat(txtHeight.getText().toString().trim());
                    weight = Float.parseFloat(txtWeight.getText().toString().trim());
                    bmi = weight / ((height) / 50);
                    if (bmi < 18.5) {
                        txtBMIShow.setText(Float.toString(bmi) + " - " + "GẦY");
                    }
                    if (bmi >= 18 && bmi <= 24.9) {
                        txtBMIShow.setText(Float.toString(bmi) + " - " + "BÌNH THƯỜNG");
                    }
                    if (bmi >= 25 && bmi <= 29.9) {
                        txtBMIShow.setText(Float.toString(bmi) + " - " + "TĂNG CÂN");
                    }
                    if (bmi >= 30) {
                        txtBMIShow.setText(Float.toString(bmi) + " - " + "BÉO PHÌ");
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Dữ liệu trống hoặc sai định dạng !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtBMIShow.getText() != null) {
                    saveBMI();
                    Toast.makeText(getApplicationContext(), "Đã lưu kết quả", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readBMIHistory();
            }
        });
    }

    public void saveBMI() {
        try {
            File pathCacheDir = getCacheDir();
            String strCacheFileName = "myBMICache.cache";
            String strFileContents = txtBMIShow.getText() + "";
            File newCacheFile = new
                    File(pathCacheDir, strCacheFileName);
            newCacheFile.createNewFile();
            FileOutputStream foCache =
                    new FileOutputStream(
                            newCacheFile.getAbsolutePath());
            foCache.write(strFileContents.getBytes());
            foCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readBMIHistory() {
        try {
            File pathCacheDir = getCacheDir();
            String strCacheFileName = "myBMICache.cache";
            File newCacheFile = new
                    File(pathCacheDir, strCacheFileName);
            Scanner sc = new Scanner(newCacheFile);
            String data = "";
            while (sc.hasNext()) {
                data += sc.nextLine() + "\n";
            }
            txtBMIHistory.setText(data);
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void backbtn(View view) {
        startActivity(new Intent(BMIActivity.this, MainActivity.class));
    }
}