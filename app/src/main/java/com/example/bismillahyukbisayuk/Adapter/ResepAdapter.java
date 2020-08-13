package com.example.bismillahyukbisayuk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bismillahyukbisayuk.Model.Resep;
import com.example.bismillahyukbisayuk.Model.User;
import com.example.bismillahyukbisayuk.MonitoringActivity2;
import com.example.bismillahyukbisayuk.R;
import com.example.bismillahyukbisayuk.ResepDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ResepAdapter extends RecyclerView.Adapter<ResepAdapter.ViewHolder> {

    private Context mContext;
    private List<Resep> mResep;

    private FirebaseUser firebaseUser;

    public ResepAdapter(List<Resep> mResep) {
        this.mResep = mResep;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resep_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        mContext = holder.itemView.getContext();

        final Resep resep = mResep.get(position);

        holder.namaResep.setText(resep.getNamaresep());
        holder.yukResep.setText("Yuk Pelajari Bagaimana Cara Memasak "+resep.getNamaresep());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(mContext, ResepDetailActivity.class);
                move.putExtra("resepid", resep.getResepid());
                mContext.startActivity(move);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResep.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView namaResep;
        public TextView yukResep;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namaResep = itemView.findViewById(R.id.namaresep);
            yukResep = itemView.findViewById(R.id.yukresep);
        }
    }
}
