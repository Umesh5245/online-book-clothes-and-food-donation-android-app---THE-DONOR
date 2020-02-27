package com.example.onlinedonation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Ngo_Requirement_RyclerAdapter extends RecyclerView.Adapter<Home_Ngo_Requirement_RyclerAdapter.MainAdapter>{

    private ArrayList<Home_Ngo_Requirement_RecyclerClass> arrayList;
    private Context context;

    public Home_Ngo_Requirement_RyclerAdapter(ArrayList<Home_Ngo_Requirement_RecyclerClass> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_layout_recyclerview, parent,false);
        return new MainAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter holder, int position) {

        holder.imageView.setImageResource(arrayList.get(position).getImageView());
        holder.description.setText(arrayList.get(position).getNgoRequirement());
        holder.location.setText(arrayList.get(position).getNgoLocation());
        holder.name.setText(arrayList.get(position).getNgoName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MainAdapter extends RecyclerView.ViewHolder{

        CircleImageView imageView;
        TextView name;
        TextView location;
        TextView description;

        public MainAdapter(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.home_recyclerImage);
            name = itemView.findViewById(R.id.home_layout_NgoName);
            location = itemView.findViewById(R.id.home_layout_NgoLocation);
            description = itemView.findViewById(R.id.home_layout_txt_description);


        }
    }




}
