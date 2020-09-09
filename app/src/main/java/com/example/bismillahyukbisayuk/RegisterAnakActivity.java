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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    List<double[]> values = new ArrayList<>();
    int u;

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
                        u = (tahun*12)+bulan;
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
                String statusGizi = initStatus(berat, u);
                register(email, username, password, nomorkk, namaayah, namaibu, alamat, nomortelp, namaanak, tempatlahir, tanggallahir, tinggi, berat, jeniskelamin, level, usia, usiaBulan, statusGizi);
            }
        });
    }

    private String initStatus(String beratnya, int u) {
        String gizi = null;
        double beratb = Double.parseDouble(beratnya);

        double element0[] = new double[]{2.1, 2.6, 2.9, 3.9, 4.5, 5};
        double element1[] = new double[]{2.9, 3.4, 3.9, 5.1, 5.7, 6.4};
        double element2[] = new double[]{3.8, 4.4, 4.9, 6.4, 7.2, 7.9};
        double element3[] = new double[]{4.5, 5, 5.7, 7.2, 8, 8.8};
        double element4[] = new double[]{4.9, 5.6, 6.3, 7.8, 8.7, 9.6};
        double element5[] = new double[]{5.3, 6, 6.7, 8.5, 9.4, 10.4};
        double element6[] = new double[]{5.7, 6.4, 7.2, 8.8, 9.8, 10.9};
        double element7[] = new double[]{5.9, 6.7, 7.5, 9.3, 10.4, 11.4};
        double element8[] = new double[]{6.3, 6.9, 7.8, 9.6, 10.7, 11.8};
        double element9[] = new double[]{6.4, 7.1, 8, 9.9, 11, 12.3};
        double element10[] = new double[]{6.6, 7.3, 8.3, 10.3, 11.4, 12.5};
        double element11[] = new double[]{6.8, 7.5, 8.5, 10.5, 11.7, 12.9};
        double element12[] = new double[]{6.9, 7.7, 8.7, 10.8, 12, 13.3};
        double element13[] = new double[]{7.1, 7.9, 8.9, 11, 12.3, 13.5};
        double element14[] = new double[]{7.3, 8.1, 9, 11.3, 12.6, 13.9};
        double element15[] = new double[]{7.4, 8.3, 9.2, 11.5, 12.9, 14.2};
        double element16[] = new double[]{7.5, 8.5, 9.5, 11.8, 13.1, 14.5};
        double element17[] = new double[]{7.7, 8.6, 9.7, 12, 13.4, 14.8};
        double element18[] = new double[]{7.9, 8.8, 9.8, 12.2, 13.7, 15.1};
        double element19[] = new double[]{8, 8.9, 10, 12.5, 13.9, 15.4};
        double element20[] = new double[]{8.1, 9.1, 10.2, 12.7, 14.3, 15.7};
        double element21[] = new double[]{8.3, 9.2, 10.4, 12.9, 14.5, 16};
        double element22[] = new double[]{8.4, 9.4, 10.5, 13.2, 14.8, 16.3};
        double element23[] = new double[]{8.5, 9.5, 10.7, 13.4, 15, 16.7};
        double element24[] = new double[]{8.6, 9.6, 10.8, 13.6, 15.2, 16.9};

        values.add(element0);
        values.add(element1);
        values.add(element2);
        values.add(element3);
        values.add(element4);
        values.add(element5);
        values.add(element6);
        values.add(element7);
        values.add(element8);
        values.add(element9);
        values.add(element10);
        values.add(element11);
        values.add(element12);
        values.add(element13);
        values.add(element14);
        values.add(element15);
        values.add(element16);
        values.add(element17);
        values.add(element18);
        values.add(element19);
        values.add(element20);
        values.add(element21);
        values.add(element22);
        values.add(element23);
        values.add(element24);

        double[] temp = values.get(u);
        if (beratb > temp[0] && beratb <= temp[1]){
            gizi = "Gizi Buruk";
        }
        else if(beratb > temp[1] && beratb <= temp[2]){
            gizi = "Gizi Kurang";
        }
        else if(beratb > temp[2] && beratb <= temp[3]){
            gizi = "Normal";
        }
        else if(beratb > temp[3] && beratb <= temp[4]){
            gizi = "Overweight";
        }
        else if(beratb > temp[4] && beratb <= temp[5]){
            gizi = "Obesitas";
        }
        else if (beratb > temp[5]){
            gizi = "Obesitas";
        }
        else {
            gizi = "Gizi Buruk";
        }

        return gizi;
    }

    private void register(final String email, final String username, final String password, final String kk, final String ayah, final String ibu,
                          final String alamatrmh, final String telepon, final String anak, final String tempat, final String tanggal,
                          final String tinggi, final String berat, final String jeniskelamin, final String level, final String usia, final String usiaBulan,
                          final String statusGizi) {
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
                            hashMap.put("usiaBulan", usiaBulan);
                            hashMap.put("statusGizi", statusGizi);
                            hashMap.put("tinggiBadan", tinggi);
                            hashMap.put("beratBadan", berat);
                            hashMap.put("jenisKelamin", jeniskelamin);
                            hashMap.put("namaImunisasi", "-");
                            hashMap.put("namaVitamin", "-");
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