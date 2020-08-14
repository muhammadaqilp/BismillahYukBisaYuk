package com.example.bismillahyukbisayuk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bismillahyukbisayuk.Model.Resep;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResepDetailActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextView namaMasakan, yukMasakan;
    ImageView fotoResep;
    String fotonya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resep_detail);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        namaMasakan = findViewById(R.id.judul_edukasi3);
        yukMasakan = findViewById(R.id.judul_edukasi4);
        fotoResep = findViewById(R.id.resepimage);

        final String resepid = getIntent().getStringExtra("resepid");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Resep").child(resepid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Resep resep = dataSnapshot.getValue(Resep.class);
                namaMasakan.setText(resep.getNamaresep());
                yukMasakan.setText("Yuk Ketahui Resep dari "+resep.getNamaresep());
                Glide.with(ResepDetailActivity.this)
                        .load(resep.getResepimage())
                        .into(fotoResep);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}