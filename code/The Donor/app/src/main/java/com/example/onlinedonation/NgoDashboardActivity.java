package com.example.onlinedonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NgoDashboardActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;
    String ngoName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_dashboard);
        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.userDashboardRecycler);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful())
                {
                    ngoName = task.getResult().getString("name");


                   Query query = firebaseFirestore.collection(ngoName+"Pending");

                    FirestoreRecyclerOptions<NgoDashboardClass> options = new FirestoreRecyclerOptions.Builder<NgoDashboardClass>()
                            .setQuery(query, NgoDashboardClass.class)
                            .build();

                    adapter = new FirestoreRecyclerAdapter<NgoDashboardClass, NgoDashboardActivity.ProductViewHolder>(options) {
                        @NonNull
                        @Override
                        public NgoDashboardActivity.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_ngo_dashboard, parent, false);
                            return new NgoDashboardActivity.ProductViewHolder(view);
                        }

                        @Override
                        protected void onBindViewHolder(@NonNull NgoDashboardActivity.ProductViewHolder holder, int position, @NonNull final NgoDashboardClass model) {



                            holder.p_date.setText(model.getTime());
                            holder.p_desc.setText(model.getDesc());
                            holder.p_msg.setText(model.getMsg());
                            holder.p_check.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    final Map<String,String> map = new HashMap<>();
                                    map.put("donor", model.getDonor());
                                    map.put("time", model.getTime());
                                    map.put("desc", model.getDesc());
                                    map.put("ngo", model.getNgoName());


                                    firebaseFirestore.collection(model.getDonor()+"History").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            firebaseFirestore.collection(model.getDonor()+"Pending").document(model.getNgoName()+model.getTime()).delete();
                                            firebaseFirestore.collection(model.getNgoName()+"Pending").document(model.getNgoName()+model.getTime()).delete();
                                            firebaseFirestore.collection(model.getNgoName()+"Collected").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    Toast.makeText(NgoDashboardActivity.this, "All task done", Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                            Map<String,String> nMap = new HashMap<>();
                                            nMap.put("one","one");

                                            firebaseFirestore.collection("NgoAccept"+model.getItem()).document().set(nMap);




                                        }
                                    });







                                }
                            });














                        }
                    };

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(NgoDashboardActivity.this));
                    recyclerView.setAdapter(adapter);
                    recyclerView.addItemDecoration(new SimpleDividerItemDecoration(NgoDashboardActivity.this));


                    adapter.startListening();






















                }


            }
        });



    }


    private class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView p_desc;
        TextView p_msg;
        TextView p_date;
        CheckBox p_check;

        public ProductViewHolder(@NonNull View view) {
            super(view);

           p_desc= view.findViewById(R.id.pendingDesc);
            p_msg = view.findViewById(R.id.pendingMsg);
            p_date = view.findViewById(R.id.pendingDate);
            p_check = view.findViewById(R.id.pendingBox);

        }
    }



}
