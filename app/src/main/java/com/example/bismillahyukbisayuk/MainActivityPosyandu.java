package com.example.bismillahyukbisayuk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bismillahyukbisayuk.PushNotification.MainActivityPush;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityPosyandu extends AppCompatActivity {

    CardView menu1, menu2, menu3, menu4, menu5;
    TextView logout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_posyandu);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menu1 = findViewById(R.id.nav_menu1);
        menu2 = findViewById(R.id.nav_menu2);
        menu3 = findViewById(R.id.nav_menu3);
        menu4 = findViewById(R.id.nav_menu4);
        menu5 = findViewById(R.id.nav_menu5);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivityPosyandu.this, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

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

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notif = new Intent(MainActivityPosyandu.this, MainActivityPush.class);
                startActivity(notif);
            }
        });
    }
}