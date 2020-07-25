package com.example.bismillahyukbisayuk;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class parentsProfil extends AppCompatActivity {
    private String title="Profil Keluarga";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_profil_parents);
        setActionBarTitle(title);
    }
    private void setActionBarTitle(String title)
    {
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle(title);
        }
    }
}
