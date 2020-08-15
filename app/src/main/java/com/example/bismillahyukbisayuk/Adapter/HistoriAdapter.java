package com.example.bismillahyukbisayuk.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bismillahyukbisayuk.Model.Histori;
import com.example.bismillahyukbisayuk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class HistoriAdapter extends RecyclerView.Adapter<HistoriAdapter.ViewHolder> {

    private Context context;
    private List<Histori> mHistori;
    public static final String[] Months = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus",
            "September", "Oktober", "November", "Desember"};

    public HistoriAdapter(Context context, List<Histori> mHistori) {
        this.context = context;
        this.mHistori = mHistori;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_histori, parent, false);
        return new HistoriAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Histori histori = mHistori.get(position);

        String tgl = histori.getTanggalrekam();

        String[] arr = tgl.split("/");
        String t = arr[0];
        String b = arr[1];
        int j = Integer.parseInt(b);
        String bN = Months[j-1];
        String y = arr[2];

        String tglFix = t+" "+bN+" "+y;

        holder.tanggal.setText(tglFix);
        holder.statusgizi.setText(histori.getStatusgizi());
        holder.berat.setText("Berat: "+histori.getBerat()+" kg");
        holder.tinggi.setText("Tinggi: "+histori.getTinggi()+" cm");
        holder.vitamin.setText(histori.getVitamin());
        holder.imunisasi.setText(histori.getImunisasi());

    }

    @Override
    public int getItemCount() {
        return mHistori.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tanggal;
        public TextView statusgizi;
        public TextView berat;
        public TextView tinggi;
        public TextView vitamin;
        public TextView imunisasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tanggal = itemView.findViewById(R.id.tanggal);
            statusgizi = itemView.findViewById(R.id.tv_status_gizi);
            berat = itemView.findViewById(R.id.tv_berat_badan);
            tinggi = itemView.findViewById(R.id.tv_tinggi_badan);
            vitamin = itemView.findViewById(R.id.tv_vitamin);
            imunisasi = itemView.findViewById(R.id.imunisasi);
        }
    }
}