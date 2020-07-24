package com.example.bismillahyukbisayuk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button masuk, daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        masuk = findViewById(R.id.btn_masuk);
        daftar = findViewById(R.id.btn_daftar);

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveMasuk = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(moveMasuk);
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveDaftar = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(moveDaftar);
            }
        });

    }
}