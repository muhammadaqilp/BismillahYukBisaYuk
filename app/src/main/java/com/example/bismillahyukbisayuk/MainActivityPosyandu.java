package com.example.bismillahyukbisayuk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivityPosyandu extends AppCompatActivity {

    CardView menu1, menu2;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_posyandu);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menu1 = findViewById(R.id.nav_menu1);
        menu2 = findViewById(R.id.nav_menu2);

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registUser = new Intent(MainActivityPosyandu.this, RegisterActivity.class);
                startActivity(registUser);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}