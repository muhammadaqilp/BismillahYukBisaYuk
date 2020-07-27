package com.example.bismillahyukbisayuk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bismillahyukbisayuk.KumpulanResep.Resep1;
import com.example.bismillahyukbisayuk.KumpulanResep.Resep2;
import com.example.bismillahyukbisayuk.KumpulanResep.Resep3;

public class ResepActivity extends AppCompatActivity {

    Toolbar toolbar;
    CardView menu1, menu2, menu3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resep);

        toolbar = findViewById(R.id.toolbar);
        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu3 = findViewById(R.id.menu3);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah1 = new Intent(ResepActivity.this, Resep1.class);
                startActivity(pindah1);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah2 = new Intent(ResepActivity.this, Resep2.class);
                startActivity(pindah2);
            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah3 = new Intent(ResepActivity.this, Resep3.class);
                startActivity(pindah3);
            }
        });
    }
}