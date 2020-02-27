package com.example.onlinedonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class NotificationAdminActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;
    String ngoName;

    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_admin);

        recyclerView = findViewById(R.id.admin_notificationRecycler);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful())
                {

                   ngoName = task.getResult().getString("name");



                    query = firebaseFirestore.collection(ngoName);


                    FirestoreRecyclerOptions<AdminNotificationClass> options = new FirestoreRecyclerOptions.Builder<AdminNotificationClass>()
                            .setQuery(query, AdminNotificationClass.class)
                            .build();


                    adapter = new FirestoreRecyclerAdapter<AdminNotificationClass, NotificationAdminActivity.ProductViewHolder>(options) {
                        @NonNull
                        @Override
                        public NotificationAdminActivity.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_admin_notification, parent, false);
                            return new NotificationAdminActivity.ProductViewHolder(view);
                        }

                        @Override
                        protected void onBindViewHolder(@NonNull NotificationAdminActivity.ProductViewHolder holder, int position, @NonNull AdminNotificationClass model) {




                            holder.itemDesc.setText(model.getDesc());
                            holder.time.setText(model.getTime());
                            holder.donorEmail.setText(model.getName());




                        }
                    };

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(NotificationAdminActivity.this));
                    recyclerView.setAdapter(adapter);
                    recyclerView.addItemDecoration(new SimpleDividerItemDecoration(NotificationAdminActivity.this));


                    adapter.startListening();




                }

            }
        });





    }



    private class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView donorEmail;
        TextView itemDesc;
        TextView time;
        Button btnAccept;
        public ProductViewHolder(@NonNull View view) {
            super(view);

            donorEmail = view.findViewById(R.id.admin_notification_donorName);
            itemDesc = view.findViewById(R.id.admin_notification_desc);
            time = view.findViewById(R.id.admin_notification_donorDate);
            btnAccept = view.findViewById(R.id.admin_notification_btnAccept);
        }
    }



}
