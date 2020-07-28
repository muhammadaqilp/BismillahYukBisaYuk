package com.example.bismillahyukbisayuk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class parentsProfil extends AppCompatActivity {
    private String title="Profil Keluarga";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_profil_parents);
        setActionBarTitle(title);

        ImageButton backhome = findViewById(R.id.btn_back);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(parentsProfil.this, MainActivity.class);
                startActivity(home);
            }
        });

        Button keluar = findViewById(R.id.keluar);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(parentsProfil.this, StartActivity.class);
                startActivity(home);
            }
        });
    }

    private void setActionBarTitle(String title)
    {
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle(title);
        }
    }
}
