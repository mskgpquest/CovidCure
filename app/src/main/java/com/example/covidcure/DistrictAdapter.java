package com.example.covidcure;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder> {

    public Context mcontext;
    public ArrayList<DistrictItem> marrayList;

    public DistrictAdapter(Context mcontext, ArrayList<DistrictItem> marrayList) {
        this.mcontext = mcontext;
        this.marrayList = marrayList;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.district_item,parent,false);
        DistrictViewHolder districtViewHolder = new DistrictViewHolder(view);
        return districtViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewHolder holder, int position) {

        DistrictItem districtItem = marrayList.get(position);

        String dist_name = districtItem.getDist_name();
        String dist_confirm = districtItem.getDist_confirm();
        String dist_active = districtItem.getDist_active();
        String dist_death = districtItem.getDist_death();
        String dist_recovered = districtItem.getDist_recovered();


        holder.dist_name.setText(dist_name);
        holder.dist_confirm.setText(dist_confirm);
        holder.dist_active.setText(dist_active);
        holder.dist_death.setText(dist_death);
        holder.dist_recovered.setText(dist_recovered);
    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }

    public static class DistrictViewHolder extends RecyclerView.ViewHolder{

        public TextView dist_name, dist_confirm, dist_active, dist_death, dist_recovered;

        public DistrictViewHolder(@NonNull View itemView) {
            super(itemView);
            dist_name = itemView.findViewById(R.id.dist_name);
            dist_confirm = itemView.findViewById(R.id.dist_confirm);
            dist_active = itemView.findViewById(R.id.dist_active);
            dist_death = itemView.findViewById(R.id.dist_death);
            dist_recovered = itemView.findViewById(R.id.dist_recovered);

        }
    }

}
