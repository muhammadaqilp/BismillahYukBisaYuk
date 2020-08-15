package com.example.bismillahyukbisayuk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bismillahyukbisayuk.LihatDataDetailActivity;
import com.example.bismillahyukbisayuk.Model.User;
import com.example.bismillahyukbisayuk.MonitoringActivity2;
import com.example.bismillahyukbisayuk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class LihatDataAdapter extends RecyclerView.Adapter<LihatDataAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUser;

    private FirebaseUser firebaseUser;

    public LihatDataAdapter(List<User> mUser) {
        this.mUser = mUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        mContext = holder.itemView.getContext();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final User user = mUser.get(position);

        holder.nik.setText(user.getUsername());
        holder.fullname.setText(user.getNamaAnak());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(mContext, LihatDataDetailActivity.class);
                move.putExtra("profileid", user.getId());
                mContext.startActivity(move);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nik;
        public TextView fullname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nik = itemView.findViewById(R.id.nik);
            fullname = itemView.findViewById(R.id.fullname);
        }
    }
}
