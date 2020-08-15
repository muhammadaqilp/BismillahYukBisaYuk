package com.example.bismillahyukbisayuk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.EditText;

import com.example.bismillahyukbisayuk.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MonitoringActivity2 extends AppCompatActivity {

    Toolbar toolbar;
    EditText namaAnak, tempatTglLahir, tanggalRekam, beratBadan, tinggiBadan, namaVitamin, namaImunisasi;
    String profileid;
    String date;

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

        profileid = getIntent().getStringExtra("profileid");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(Calendar.getInstance().getTime());

        dataRekam();
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