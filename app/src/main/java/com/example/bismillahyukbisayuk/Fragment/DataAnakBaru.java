package com.example.bismillahyukbisayuk.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.bismillahyukbisayuk.Model.User;
import com.example.bismillahyukbisayuk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataAnakBaru extends Fragment {

    TextView tglSekarang, beratAnak, tinggiAnak, usiaAnak, namaImunisasi, namaVitamin;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    String profileid;
    String date;
    public static final String[] Months = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus",
            "September", "Oktober", "November", "Desember"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_anak, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        profileid = firebaseUser.getUid();

        tglSekarang = view.findViewById(R.id.tglSekarang);
        beratAnak = view.findViewById(R.id.berat);
        tinggiAnak = view.findViewById(R.id.tinggi);
        usiaAnak = view.findViewById(R.id.usia);
        namaImunisasi = view.findViewById(R.id.namaimunisasi);
        namaVitamin = view.findViewById(R.id.namavitamin);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(Calendar.getInstance().getTime());
        
        userInfo();
        
        return view;
    }

    private void userInfo() {
        String[] arr = date.split("/");
        String t = arr[0];
        String b = arr[1];
        int j = Integer.parseInt(b);
        String bN = Months[j-1];
        String y = arr[2];

        final String tglFix = t+" "+bN+" "+y;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getContext() == null){
                    return;
                }

                User user = dataSnapshot.getValue(User.class);
                tglSekarang.setText(tglFix);
                beratAnak.setText(user.getBeratBadan()+" Kg");
                tinggiAnak.setText(user.getTinggiBadan()+" Cm");
                usiaAnak.setText(user.getUsia());
                namaImunisasi.setText(" - ");
                namaVitamin.setText(" - ");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
