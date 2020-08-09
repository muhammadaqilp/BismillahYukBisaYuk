package com.example.bismillahyukbisayuk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.bismillahyukbisayuk.Adapter.PagerTabAdapter;
import com.example.bismillahyukbisayuk.Model.User;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InformasiAnakActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem histori, dataBaru;
    private PagerTabAdapter pagerTabAdapter;
    Toolbar toolbar;
    TextView namaLengkap, tempattglLahir, jenisKelamin;
    String profileid;
    public static final String[] Months = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus",
    "September", "Oktober", "November", "Desember"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_anak);

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.pager);
        histori = findViewById(R.id.histori);
        dataBaru = findViewById(R.id.data_baru);
        namaLengkap = findViewById(R.id.nama_lengkap);
        tempattglLahir = findViewById(R.id.tempattgl);
        jenisKelamin = findViewById(R.id.jeniskelamin);

        profileid = getIntent().getStringExtra("profileid");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        infoAnak();

        pagerTabAdapter= new PagerTabAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerTabAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    pagerTabAdapter.notifyDataSetChanged();
                }
                if(tab.getPosition()==1){
                    pagerTabAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void infoAnak() {
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

                namaLengkap.setText(user.getNamaAnak());
                tempattglLahir.setText(tempat+", "+tglFix);
                jenisKelamin.setText(user.getJenisKelamin());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}