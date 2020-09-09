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

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MonitoringActivity2 extends AppCompatActivity {

    Toolbar toolbar;
    EditText namaAnak, tempatTglLahir, tanggalRekam, beratBadan, tinggiBadan, namaVitamin, namaImunisasi;
    Button simpan;
    String profileid;
    String date;
    String usia, usiaBulan;
    int u;
    ProgressDialog pd;
    List<double[]> values = new ArrayList<>();

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
                        Period period = new Period(startDate, endDate, PeriodType.yearMonthDay());
                        int tahun = period.getYears();
                        int bulan = period.getMonths();
                        int hari = period.getDays();

                        usia = tahun + " Tahun " + bulan + " Bulan " + hari + " Hari";
                        u = (tahun * 12) + bulan;
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

                String status;
                status = initial(bb,u);

//                addHistori(bb, tb, vit, imun);
                updateInfo(bb, tb, vit, imun, usia, usiaBulan, status);

            }
        });
    }

    private String initial(String bb, int u) {
        String gizi = null;
        int beratb = Integer.parseInt(bb);

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

    private void updateInfo(final String bb, final String tb, final String vit, final String imun, final String usia, final String usiaBulan, final String statusGizi) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("usia", usia);
        hashMap.put("usiaBulan", usiaBulan);
        hashMap.put("beratBadan", bb);
        hashMap.put("tinggiBadan", tb);
        hashMap.put("statusGizi", statusGizi);
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
        hashMap1.put("statusgizi", statusGizi);
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