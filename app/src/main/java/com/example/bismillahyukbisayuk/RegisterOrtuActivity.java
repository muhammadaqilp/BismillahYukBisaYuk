package com.example.bismillahyukbisayuk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;

public class RegisterOrtuActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText nomorKK, namaAyah, namaIbu, alamatRumah;
    Button next_anak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ortu);

        toolbar = findViewById(R.id.toolbar);
        nomorKK = findViewById(R.id.nomorkk);
        namaAyah = findViewById(R.id.namaayah);
        namaIbu = findViewById(R.id.namaibu);
        alamatRumah = findViewById(R.id.alamatrumah);
        next_anak = findViewById(R.id.btn_next_ortu);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        next_anak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveAnak = new Intent(RegisterOrtuActivity.this, RegisterAnakActivity.class);
                startActivity(moveAnak);
            }
        });
    }
}