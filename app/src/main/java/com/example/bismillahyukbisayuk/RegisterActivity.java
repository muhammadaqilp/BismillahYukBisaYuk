package com.example.bismillahyukbisayuk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText nama, email, password;
    Button daftar;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView masuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbar);
        nama = findViewById(R.id.namalengkap);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        daftar = findViewById(R.id.btn_daftar);
        radioGroup = findViewById(R.id.radioGroupNb);
        masuk = findViewById(R.id.masuksekarang);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent masuk = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(masuk);
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton=findViewById(radioId);
    }
}