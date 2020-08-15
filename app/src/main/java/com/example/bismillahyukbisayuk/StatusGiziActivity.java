package com.example.bismillahyukbisayuk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.bismillahyukbisayuk.Adapter.BeritaAdapter;
import com.example.bismillahyukbisayuk.Adapter.ResepAdapter;
import com.example.bismillahyukbisayuk.Model.Berita;
import com.example.bismillahyukbisayuk.Model.Resep;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StatusGiziActivity extends AppCompatActivity {

    Toolbar toolbar;

    private RecyclerView recyclerView;
    private BeritaAdapter beritaAdapter;
    private List<Berita> beritaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_gizi);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        beritaList = new ArrayList<>();
        beritaAdapter =new BeritaAdapter(beritaList);
        recyclerView.setAdapter(beritaAdapter);

        readBerita();

    }

    private void readBerita() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Berita");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                beritaList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Berita berita = snapshot.getValue(Berita.class);
                    beritaList.add(berita);
                }
                beritaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}