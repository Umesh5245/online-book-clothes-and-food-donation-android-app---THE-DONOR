package com.example.onlinedonation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminHistoryAdapter extends RecyclerView.Adapter<AdminHistoryAdapter.HistoryAdapter>{


    private Context context;
    private ArrayList<Admin_History_Class> arrayList;


    public AdminHistoryAdapter(Context context, ArrayList<Admin_History_Class> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public HistoryAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_admin_history_layout, parent,false);

        return new HistoryAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter holder, int position) {

        holder.itemImage.setImageResource(arrayList.get(position).getItemImage());
        holder.donorName.setText(arrayList.get(position).getDonorName());
        holder.date.setText(arrayList.get(position).getDate());


    }

    @Override
    public int getItemCount() {
         return arrayList.size();
    }

    public class HistoryAdapter extends RecyclerView.ViewHolder{

        private ImageView itemImage;
        private TextView donorName;
        private TextView date;

        public HistoryAdapter(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.admin_activity_history_item_image);
            donorName = itemView.findViewById(R.id.admin_activity_history_donor_name);
            date = itemView.findViewById(R.id.admin_activity_history_date);


        }
    }



}
