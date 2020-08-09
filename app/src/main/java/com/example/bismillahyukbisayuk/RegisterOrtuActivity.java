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
    EditText nomorKK, namaAyah, namaIbu, alamatRumah, nomorTelp;
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
        nomorTelp = findViewById(R.id.notelp);
        next_anak = findViewById(R.id.btn_next_ortu);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String username = getIntent().getStringExtra("username");
        final String password = getIntent().getStringExtra("password");

        next_anak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage(username, password);
            }
        });
    }

    private void nextPage(String username, String password) {
        String nokk = nomorKK.getText().toString();
        String namaayah = namaAyah.getText().toString();
        String namaibu = namaIbu.getText().toString();
        String alamatrumah = alamatRumah.getText().toString();
        String nomortelp = nomorTelp.getText().toString();

        if (nokk.isEmpty()){
            nomorKK.setError("Nomor KK is required");
            nomorKK.requestFocus();
            return;
        }

        if (namaayah.isEmpty()){
            namaAyah.setError("Nama Ayah is required");
            namaAyah.requestFocus();
            return;
        }

        if (namaibu.isEmpty()){
            namaIbu.setError("Nama Ibu is required");
            namaIbu.requestFocus();
            return;
        }

        if (alamatrumah.isEmpty()){
            alamatRumah.setError("Alamat Rumah is required");
            alamatRumah.requestFocus();
            return;
        }

        if (nomortelp.isEmpty()){
            nomorTelp.setError("Nomor Telepon is required");
            nomorTelp.requestFocus();
            return;
        }

        Intent moveAnak = new Intent(RegisterOrtuActivity.this, RegisterAnakActivity.class);
        moveAnak.putExtra("username", username);
        moveAnak.putExtra("password", password);
        moveAnak.putExtra("nomorkk", nokk);
        moveAnak.putExtra("namaayah", namaayah);
        moveAnak.putExtra("namaibu", namaibu);
        moveAnak.putExtra("alamat", alamatrumah);
        moveAnak.putExtra("nomortelp", nomortelp);
        startActivity(moveAnak);
    }
}