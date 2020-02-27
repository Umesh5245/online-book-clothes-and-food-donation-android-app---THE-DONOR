package com.example.bottomnavigationviewexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MainAdapter>{


    private ArrayList<Admin_Donation_Class> arrayList;
    private Context context;

    public AdminAdapter(ArrayList<Admin_Donation_Class> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_admin_donation_layout, parent,false);

        return new MainAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter holder, int position) {

        holder.itemImage.setImageResource(arrayList.get(position).getImageId());
        holder.description.setText(arrayList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public void filter(ArrayList<Admin_Donation_Class> details){
        arrayList = details;
        notifyDataSetChanged();

    }

    public class MainAdapter extends RecyclerView.ViewHolder{

        private ImageView itemImage;
        private TextView description;
        public MainAdapter(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.admin_donation_donor_item_image);
            description = itemView.findViewById(R.id.admin_donation_item_description);
        }
    }

}
