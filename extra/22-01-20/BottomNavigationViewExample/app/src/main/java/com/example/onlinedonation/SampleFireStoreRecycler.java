package com.example.onlinedonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SampleFireStoreRecycler extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_fire_store_recycler);

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.sampleRecycler);



        Query query = firebaseFirestore.collection("UserDonations");
        FirestoreRecyclerOptions<SampleClass> options = new FirestoreRecyclerOptions.Builder<SampleClass>()
                .setQuery(query, SampleClass.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<SampleClass, ProductViewHolder>(options) {
            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.samplerecyclerfirestore, parent, false);


                return new ProductViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull SampleClass model) {
               holder.uName.setText(model.getName());
                Glide.with(SampleFireStoreRecycler.this).load(model.getImage()).into(holder.uImage);

            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);








    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView uName;
        private ImageView uImage;



        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            uName = itemView.findViewById(R.id.sampleTextView);
            uImage = itemView.findViewById(R.id.sampleImage);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        adapter.startListening();



    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
