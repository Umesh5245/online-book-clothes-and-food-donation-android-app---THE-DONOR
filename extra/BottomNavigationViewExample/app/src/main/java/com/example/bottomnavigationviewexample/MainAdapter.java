package com.example.bottomnavigationviewexample;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyHolder>  {

    private ArrayList<NgoDetails> arrayList;

    public MainAdapter(ArrayList<NgoDetails> arrayList, Context context) {
        this.arrayList = arrayList;


    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler, parent,false);






        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {



        holder.circleImageView.setImageResource(arrayList.get(position).NgoImage);
        holder.ngoLocation.setText(arrayList.get(position).NgoLocation);
        holder.ngoName.setText(arrayList.get(position).NgoName);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void filter(ArrayList<NgoDetails> details){
        arrayList = details;
        notifyDataSetChanged();

    }



    public class MyHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView ngoName;
        TextView ngoLocation;
        Button btn_NgoDetails;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.image_ngo);
            ngoName = itemView.findViewById(R.id.txt_ngoName);

            ngoLocation = itemView.findViewById(R.id.txt_ngoLocation);




        }
    }











}
