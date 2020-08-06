package com.example.bismillahyukbisayuk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText email, password;
    Button masuk;
    TextView daftarsekarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        masuk = findViewById(R.id.btn_masuk);
//        daftarsekarang = findViewById(R.id.daftarsekarang);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        daftarsekarang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent daftar = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(daftar);
//            }
//        });

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveMain = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(moveMain);
            }
        });

    }


}