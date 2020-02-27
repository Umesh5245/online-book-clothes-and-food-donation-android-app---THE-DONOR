package com.example.onlinedonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DonorPendingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;
    String ngoName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_pending);


        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.DonorDashboardRecycler);
        mAuth = FirebaseAuth.getInstance();



        Query query = firebaseFirestore.collection(mAuth.getCurrentUser().getEmail()+"Pending");

        FirestoreRecyclerOptions<DonorPendingClass> options = new FirestoreRecyclerOptions.Builder<DonorPendingClass>()
                .setQuery(query, DonorPendingClass.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<DonorPendingClass, DonorPendingActivity.ProductViewHolder>(options) {
            @NonNull
            @Override
            public DonorPendingActivity.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_pending_recycler, parent, false);
                return new DonorPendingActivity.ProductViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull DonorPendingActivity.ProductViewHolder holder, int position, @NonNull final DonorPendingClass model) {



                holder.p_date.setText(model.getTime());
                holder.p_desc.setText(model.getDesc());
                holder.p_msg.setText(model.getMsg());













            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DonorPendingActivity.this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(DonorPendingActivity.this));


        adapter.startListening();






    }


    private class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView p_desc;
        TextView p_msg;
        TextView p_date;


        public ProductViewHolder(@NonNull View view) {
            super(view);

            p_desc= view.findViewById(R.id.donorpendingDesc);
            p_msg = view.findViewById(R.id.donorpendingMsg);
            p_date = view.findViewById(R.id.donorpendingDate);


        }
    }



}
