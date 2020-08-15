package com.example.bismillahyukbisayuk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bismillahyukbisayuk.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LihatDataDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    String profileid;

    TextView namaAnak, tempatTglLahir, jenisKelamin, nik, namaAyah, namaIbu, noTelp, noKK, alamat;

    public static final String[] Months = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus",
            "September", "Oktober", "November", "Desember"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_detail);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileid = getIntent().getStringExtra("profileid");

        namaAnak = findViewById(R.id.tv_nama_anak);
        tempatTglLahir = findViewById(R.id.tv_ttl);
        jenisKelamin = findViewById(R.id.tv_jk);
        nik = findViewById(R.id.nomor_nik);
        namaAyah = findViewById(R.id.nama_ayah);
        namaIbu = findViewById(R.id.nama_ibu);
        noTelp = findViewById(R.id.nomor_telp);
        noKK = findViewById(R.id.nomor_kk);
        alamat = findViewById(R.id.alamat_rumah);

        infoAnakDetail();
    }

    private void infoAnakDetail() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String tempat = user.getTempatLahir();
                String tgl = user.getTanggalLahir();

                String[] arr = tgl.split("/");
                String t = arr[0];
                String b = arr[1];
                int j = Integer.parseInt(b);
                String bN = Months[j-1];
                String y = arr[2];

                String tglFix = t+" "+bN+" "+y;

                namaAnak.setText(user.getNamaAnak());
                tempatTglLahir.setText(tempat+", "+tglFix);
                jenisKelamin.setText(user.getJenisKelamin());
                nik.setText(user.getUsername());
                namaAyah.setText(user.getNamaAyah());
                namaIbu.setText(user.getNamaIbu());
                noTelp.setText(user.getNomorTelepon());
                noKK.setText(user.getNomorKK());
                alamat.setText(user.getAlamatRumah());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}