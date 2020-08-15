package com.example.bismillahyukbisayuk;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.bismillahyukbisayuk.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class MainActivity extends AppCompatActivity {

    TextView textHello;
    CardView menu1, menu2, menu3, menu4;
    Toolbar toolbar;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    String tgl, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textHello = findViewById(R.id.texthello);
        menu1 = findViewById(R.id.nav_menu1);
        menu2 = findViewById(R.id.nav_menu2);
        menu3 = findViewById(R.id.nav_menu3);
        menu4 = findViewById(R.id.nav_menu4);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(Calendar.getInstance().getTime());

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahProfilAnak = new Intent(MainActivity.this, InformasiAnakActivity.class);
                pindahProfilAnak.putExtra("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                startActivity(pindahProfilAnak);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahGrafik = new Intent(MainActivity.this, GrafikActivity.class);
                startActivity(pindahGrafik);
            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahGizi = new Intent(MainActivity.this, StatusGiziActivity.class);
                startActivity(pindahGizi);
            }
        });

        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahResep = new Intent(MainActivity.this, ResepActivity.class);
                startActivity(pindahResep);
            }
        });

        updateUsia();
    }

    private void updateUsia() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                tgl = user.getTanggalLahir();

                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while (!isInterrupted()) {
                                Thread.sleep(1000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String sDate = tgl;
                                        String eDate = date;
                                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                                        try {
                                            Date date1 = simpleDateFormat1.parse(sDate);
                                            Date date2 = simpleDateFormat1.parse(eDate);

                                            long startDate = date1.getTime();
                                            long endDate = date2.getTime();

                                            if (startDate <= endDate) {
                                                org.joda.time.Period period = new org.joda.time.Period(startDate, endDate, PeriodType.yearMonthDay());
                                                int years = period.getYears();
                                                int month = period.getMonths();
                                                int days = period.getDays();

                                                String usiaFix = years + " Tahun " + month + " Bulan " + days + " Hari";
                                                int u = (years*12)+month;
                                                String usiaBulan = String.valueOf(u);

                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("usia", usiaFix);
                                                hashMap.put("usiaBulan", usiaBulan);

                                                reference.updateChildren(hashMap);
                                            } else {
                                                Toast.makeText(MainActivity.this, "g", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } catch (InterruptedException e) {

                        }
                    }
                };
                t.start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void setMode(int selectedMode) {
        switch (selectedMode) {
            case R.id.action_about:
                Intent move = new Intent(MainActivity.this, parentsProfil.class);
                move.putExtra("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                startActivity(move);
                break;
        }
    }

}