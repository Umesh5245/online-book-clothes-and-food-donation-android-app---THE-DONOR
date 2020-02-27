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

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {



    private RecyclerView recyclerViewRequest;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerViewRequest = findViewById(R.id.home_recyclerViewRequest);

        Query query = firebaseFirestore.collection("NgoRequest");


        getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBar, null));

        FirestoreRecyclerOptions<UserNgoRequest> options = new FirestoreRecyclerOptions.Builder<UserNgoRequest>()
                .setQuery(query, UserNgoRequest.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<UserNgoRequest, HomeActivity.ProductViewHolder>(options) {
            @NonNull
            @Override
            public HomeActivity.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_admin_donation_layout, parent, false);
                return new HomeActivity.ProductViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull HomeActivity.ProductViewHolder holder, int position, @NonNull UserNgoRequest model) {




                holder.ngoDesc.setText(model.getDesc());
                holder.ngoName.setText(model.getName());
                holder.ngoCity.setText(model.getCity());

                Glide.with(HomeActivity.this).load(model.getImage()).into(holder.ngoImage);


            }
        };

        recyclerViewRequest.setHasFixedSize(true);
        recyclerViewRequest.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRequest.setAdapter(adapter);
        recyclerViewRequest.addItemDecoration(new SimpleDividerItemDecoration(this));


        adapter.startListening();








    }






   public class ProductViewHolder extends RecyclerView.ViewHolder{
        CircleImageView ngoImage;
        TextView ngoDesc;
        TextView ngoCity;
        TextView ngoName;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            ngoImage = itemView.findViewById(R.id.home_recyclerImage);
            ngoCity = itemView.findViewById(R.id.home_layout_NgoLocation);
            ngoName = itemView.findViewById(R.id.home_layout_NgoName);
            ngoDesc = itemView.findViewById(R.id.home_layout_txt_description);
        }
    }







    }







