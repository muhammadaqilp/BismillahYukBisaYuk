package com.example.bismillahyukbisayuk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    }

    private void setActionBarTitle(String title)
    {
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle(title);
        }
    }
}
