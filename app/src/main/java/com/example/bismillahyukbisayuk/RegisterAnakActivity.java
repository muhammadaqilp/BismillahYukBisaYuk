package com.example.bismillahyukbisayuk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.PeriodType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class RegisterAnakActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText namaAnak, tempatLahir, tglLahir, beratBadan, tinggiAnak;
    Button pilihTgl, daftar;
    RadioGroup radioGroup;
    RadioButton radioButton;
    DatePickerDialog.OnDateSetListener dateSetListener1;
    String usia, date, usiaBulan;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    public static final String[] Months = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus",
            "September", "Oktober", "November", "Desember"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_anak);

        auth = FirebaseAuth.getInstance();

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

        final String username = getIntent().getStringExtra("username");
        final String password = getIntent().getStringExtra("password");
        final String nomorkk = getIntent().getStringExtra("nomorkk");
        final String namaayah = getIntent().getStringExtra("namaayah");
        final String namaibu = getIntent().getStringExtra("namaibu");
        final String alamat = getIntent().getStringExtra("alamat");
        final String nomortelp = getIntent().getStringExtra("nomortelp");

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int days = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(Calendar.getInstance().getTime());

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
//                String bln = Months[month-1];
                String date = days + "/" + month + "/" + year;
//                String date2 = days + " " + bln + " " + year;
                tglLahir.setText(date);
            }
        };

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(RegisterAnakActivity.this);
                pd.setMessage("Please Wait...");
                pd.show();

                String sDate = tglLahir.getText().toString();
                String eDate = date;
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date1 = simpleDateFormat1.parse(sDate);
                    Date date2 = simpleDateFormat1.parse(eDate);

                    long startDate = date1.getTime();
                    long endDate = date2.getTime();

                    if (startDate <= endDate) {
                        org.joda.time.Period period = new org.joda.time.Period(startDate, endDate, PeriodType.yearMonthDay());
                        int tahun = period.getYears();
                        int bulan = period.getMonths();
                        int hari = period.getDays();

                        usia = tahun + " Tahun " + bulan + " Bulan " + hari + " Hari";
                        int u = (tahun*12)+bulan;
                        usiaBulan = String.valueOf(u);

                    } else {
                        Toast.makeText(RegisterAnakActivity.this, "g", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String email = username+"@email.com";
                String namaanak = namaAnak.getText().toString();
                String tempatlahir = tempatLahir.getText().toString();
                String tanggallahir = tglLahir.getText().toString();
                String tinggi = tinggiAnak.getText().toString();
                String berat = beratBadan.getText().toString();
                String jeniskelamin = radioButton.getText().toString();
                String level = "user";
                register(email, username, password, nomorkk, namaayah, namaibu, alamat, nomortelp, namaanak, tempatlahir, tanggallahir, tinggi, berat, jeniskelamin, level, usia);
            }
        });
    }

    private void register(final String email, final String username, final String password, final String kk, final String ayah, final String ibu,
                          final String alamatrmh, final String telepon, final String anak, final String tempat, final String tanggal,
                          final String tinggi, final String berat, final String jeniskelamin, final String level, final String usia) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterAnakActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username.toLowerCase());
                            hashMap.put("nomorKK", kk);
                            hashMap.put("namaAyah", ayah);
                            hashMap.put("namaIbu", ibu);
                            hashMap.put("alamatRumah", alamatrmh);
                            hashMap.put("nomorTelepon", telepon);
                            hashMap.put("namaAnak", anak);
                            hashMap.put("tempatLahir", tempat);
                            hashMap.put("tanggalLahir", tanggal);
                            hashMap.put("usia", usia);
                            hashMap.put("tinggiBadan", tinggi);
                            hashMap.put("beratBadan", berat);
                            hashMap.put("jenisKelamin", jeniskelamin);
                            hashMap.put("level", level);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent = new Intent(RegisterAnakActivity.this, MainActivityPosyandu.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else{
                            pd.dismiss();
                            Toast.makeText(RegisterAnakActivity.this, "You can't register with this username or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);
    }
}