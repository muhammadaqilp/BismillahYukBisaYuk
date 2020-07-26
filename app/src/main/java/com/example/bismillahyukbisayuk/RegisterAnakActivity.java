package com.example.bismillahyukbisayuk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

public class RegisterAnakActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText namaAnak, tempatLahir, tglLahir, beratBadan, tinggiAnak;
    Button pilihTgl, daftar;
    RadioGroup radioGroup;
    RadioButton radioButton;
    DatePickerDialog.OnDateSetListener dateSetListener1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_anak);

        toolbar = findViewById(R.id.toolbar);
        namaAnak = findViewById(R.id.namaanak);
        tempatLahir = findViewById(R.id.tempatlahir);
        tglLahir = findViewById(R.id.tgllahir);
        pilihTgl = findViewById(R.id.btnpicktgl);
        beratBadan = findViewById(R.id.beratbadan);
        tinggiAnak = findViewById(R.id.tinggibadan);
        radioGroup = findViewById(R.id.radioGroupNb);
        daftar = findViewById(R.id.btn_daftar_anak);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int days = calendar.get(Calendar.DAY_OF_MONTH);

        pilihTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterAnakActivity.this
                        , android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , dateSetListener1, year, month, days
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new
                        ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int days) {
                month = month + 1;
                String date = days + "/" + month + "/" + year;
                tglLahir.setText(date);
            }
        };

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regist = new Intent(RegisterAnakActivity.this, MainActivityPosyandu.class);
                startActivity(regist);
            }
        });
    }

    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton=findViewById(radioId);
    }
}