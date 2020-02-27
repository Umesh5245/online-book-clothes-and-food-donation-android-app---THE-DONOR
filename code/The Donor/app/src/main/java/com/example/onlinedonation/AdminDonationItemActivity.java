package com.example.onlinedonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.jintin.mixadapter.MixAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdminDonationItemActivity extends AppCompatActivity {

    private ArrayList<Admin_Donation_Class> arrayList,temp_list;
    private RecyclerView recyclerView;
    private EditText search_Donation;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter,adapter2,adapter3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_donation_item);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBar, null));

        firebaseFirestore = FirebaseFirestore.getInstance();
        search_Donation = findViewById(R.id.admin_search_donation);



        recyclerView = findViewById(R.id.admin_activity_donation_recycler);




        Query query = firebaseFirestore.collection("UserDonations");


        FirestoreRecyclerOptions<Admin_Donation_Class> options = new FirestoreRecyclerOptions.Builder<Admin_Donation_Class>()
                .setQuery(query, Admin_Donation_Class.class)
                .build();















        adapter = new FirestoreRecyclerAdapter<Admin_Donation_Class, ProductViewHolder>(options) {
            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_admin_donation_layout, parent, false);
                return new ProductViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Admin_Donation_Class model) {




                holder.itemDesc.setText(model.getDesc());
                Glide.with(AdminDonationItemActivity.this).load(model.getImage()).into(holder.itemImage);


            }
        };







        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.startListening();



        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));












    }














    private class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemDesc;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.admin_donation_donor_item_image);
            itemDesc = itemView.findViewById(R.id.admin_donation_item_description);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}

