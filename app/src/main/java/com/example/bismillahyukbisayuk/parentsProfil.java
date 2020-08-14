package com.example.bismillahyukbisayuk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.bismillahyukbisayuk.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class parentsProfil extends AppCompatActivity {

    TextView namaKeluarga, namaAnak, namaAyah, namaIbu, nomorTelp, nomorKK, alamatRumah;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    String profileid;
    Toolbar toolbar;

    private String title="Profil Keluarga";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_profil_parents);
//        setActionBarTitle(title);

        namaKeluarga = findViewById(R.id.tv_nama_parents);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        namaAnak = findViewById(R.id.nama_anak);
        namaAyah = findViewById(R.id.nama_ayah);
        namaIbu = findViewById(R.id.nama_ibu);
        nomorTelp = findViewById(R.id.nomor_telp);
        nomorKK = findViewById(R.id.nomor_kk);
        alamatRumah = findViewById(R.id.alamat_rumah);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        profileid = getIntent().getStringExtra("profileid");

        userInfo();

        Button keluar = findViewById(R.id.keluar);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(parentsProfil.this, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private void userInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                namaKeluarga.setText("Keluarga "+user.getNamaAyah());
                namaAnak.setText(user.getNamaAnak());
                namaAyah.setText(user.getNamaAyah());
                namaIbu.setText(user.getNamaIbu());
                nomorKK.setText(user.getNomorKK());
                nomorTelp.setText(user.getNomorTelepon());
                alamatRumah.setText(user.getAlamatRumah());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setActionBarTitle(String title)
    {
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}
