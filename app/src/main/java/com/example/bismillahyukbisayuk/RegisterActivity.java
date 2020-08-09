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
    EditText username, password;
    Button next_ortu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbar);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        next_ortu = findViewById(R.id.btn_next_awal);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        next_ortu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

    }

    private void nextPage() {
        String usname = username.getText().toString();
        String pass = password.getText().toString();

        if (usname.isEmpty()){
            username.setError("Username is required");
            username.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        Intent nextOrtu = new Intent(RegisterActivity.this, RegisterOrtuActivity.class);
        nextOrtu.putExtra("username", usname);
        nextOrtu.putExtra("password", pass);
        startActivity(nextOrtu);
    }
}