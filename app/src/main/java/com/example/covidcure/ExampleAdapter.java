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
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    public Context mcontext;
    public ArrayList<ExampleItem> marrayList;

    public ExampleAdapter(Context mcontext, ArrayList<ExampleItem> marrayList) {
        this.mcontext = mcontext;
        this.marrayList = marrayList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.card_item,parent,false);
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(view);
        return exampleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        ExampleItem examplItem = marrayList.get(position);

        String imageurl = examplItem.getMimgurl();
        String title = examplItem.getMtitle();
        String description = examplItem.getMdescription();
        String date = examplItem.getMdate();

        /////////////
        final String newsurl = examplItem.getMurl();
        ////////////


        holder.textview_title.setText(title);
        holder.textview_description.setText(description);
        holder.textview_date.setText(date);
        Glide.with(mcontext).load(imageurl).into(holder.imageview_news);

        ///////////////////////////
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //////////////////////////////////NEED HELP!!!!!!!!!!!!!!!!!!/////////

                Uri uri = Uri.parse(newsurl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mcontext.startActivity(intent);  /////////////////// mcontext is very very necessary! //////////////////////

            }
        });
        //////////////////////////

    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageview_news;
        public TextView textview_title;
        public TextView textview_description;
        public TextView textview_date;

        ///////////////////////////
        public Button button;
        ///////////////////////////

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview_news = itemView.findViewById(R.id.imageview_news);
            textview_title = itemView.findViewById(R.id.textview_title);
            textview_description = itemView.findViewById(R.id.textview_description);
            textview_date = itemView.findViewById(R.id.textview_date);

            /////////////////////////
            button = itemView.findViewById(R.id.button);
            ////////////////////////
        }
    }

}
