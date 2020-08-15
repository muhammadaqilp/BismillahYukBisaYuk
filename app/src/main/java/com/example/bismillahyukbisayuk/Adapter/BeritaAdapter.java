package com.example.bismillahyukbisayuk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bismillahyukbisayuk.Model.Berita;
import com.example.bismillahyukbisayuk.Model.Resep;
import com.example.bismillahyukbisayuk.R;
import com.example.bismillahyukbisayuk.ResepDetailActivity;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {

    private Context mContext;
    private List<Berita> mBerita;

    public BeritaAdapter(List<Berita> mBerita) {
        this.mBerita = mBerita;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.berita_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        mContext = holder.itemView.getContext();

        final Berita berita = mBerita.get(position);

        holder.namaNotif.setText(berita.getJudulberita());
        holder.isiNotif.setText(berita.getIsiberita());

        if (berita.getJenisberita().equals("resep")){
            Glide.with(mContext)
                    .load(R.drawable.ic_recipe)
                    .into(holder.iconNotif);
        }

    }

    @Override
    public int getItemCount() {
        return mBerita.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView namaNotif;
        public TextView isiNotif;
        public ImageView iconNotif;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namaNotif = itemView.findViewById(R.id.nama_notif);
            isiNotif = itemView.findViewById(R.id.pesan);
            iconNotif = itemView.findViewById(R.id.imageberita);
        }
    }
}