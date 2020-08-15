package com.example.bismillahyukbisayuk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bismillahyukbisayuk.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.PeriodType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MonitoringActivity2 extends AppCompatActivity {

    Toolbar toolbar;
    EditText namaAnak, tempatTglLahir, tanggalRekam, beratBadan, tinggiBadan, namaVitamin, namaImunisasi;
    Button simpan;
    String profileid;
    String date;
    String usia, usiaBulan;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring2);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        namaAnak = findViewById(R.id.namaanak);
        tempatTglLahir = findViewById(R.id.tv_tgl_lahir);
        tanggalRekam = findViewById(R.id.tv_tgl_rekam);
        beratBadan = findViewById(R.id.tv_berat_badan);
        tinggiBadan = findViewById(R.id.tv_tinggi_badan);
        namaVitamin = findViewById(R.id.tv_vitamin);
        namaImunisasi = findViewById(R.id.tv_lingkar_lengan);
        simpan = findViewById(R.id.simpan);

        profileid = getIntent().getStringExtra("profileid");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(Calendar.getInstance().getTime());

        dataRekam();

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(MonitoringActivity2.this);
                pd.setMessage("Menambahkan Data...");
                pd.show();

                String sDate = tempatTglLahir.getText().toString();
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
                        int u = (tahun * 12) + bulan;
                        usiaBulan = String.valueOf(u);

                    } else {
                        Toast.makeText(MonitoringActivity2.this, "g", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String bb = beratBadan.getText().toString();
                String tb = tinggiBadan.getText().toString();
                String vit = namaVitamin.getText().toString();
                String imun = namaImunisasi.getText().toString();

//                addHistori(bb, tb, vit, imun);
                updateInfo(bb, tb, vit, imun, usia, usiaBulan);
            }
        });
    }

    private void updateInfo(final String bb, final String tb, final String vit, final String imun, final String usia, final String usiaBulan) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("usia", usia);
        hashMap.put("usiaBulan", usiaBulan);
        hashMap.put("beratBadan", bb);
        hashMap.put("tinggiBadan", tb);
        hashMap.put("namaImunisasi", vit);
        hashMap.put("namaVitamin", imun);

        reference.updateChildren(hashMap);

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Histori");

        final String historiid = reference1.push().getKey();

        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("historiid", historiid);
        hashMap1.put("publisher", profileid);
        hashMap1.put("tanggalrekam", date);
        hashMap1.put("berat", bb);
        hashMap1.put("tinggi", tb);
        hashMap1.put("statusgizi", "-");
        hashMap1.put("imunisasi", vit);
        hashMap1.put("vitamin", imun);

        FirebaseDatabase.getInstance().getReference().child("Saves").child(profileid)
                .child(historiid).setValue(true);

        reference1.child(historiid).setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(profileid)
                            .child(historiid).setValue(true);

                    pd.dismiss();
                    Intent intent = new Intent(MonitoringActivity2.this, MainActivityPosyandu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

    }

    private void addHistori(final String bb, final String tb, final String vit, final String imun) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Histori");

        final String historiid = reference.push().getKey();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("historiid", historiid);
        hashMap.put("publisher", profileid);
        hashMap.put("tanggalrekam", date);
        hashMap.put("berat", bb);
        hashMap.put("tinggi", tb);
        hashMap.put("statusgizi", "-");
        hashMap.put("imunisasi", vit);
        hashMap.put("vitamin", imun);

        reference.child(historiid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(profileid)
                            .child(historiid).setValue(true);

                    pd.dismiss();
                    Intent intent = new Intent(MonitoringActivity2.this, MainActivityPosyandu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private void dataRekam() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                namaAnak.setText(user.getNamaAnak());
                tempatTglLahir.setText(user.getTanggalLahir());
                tanggalRekam.setText(date);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}