package com.example.onlinedonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class UserHistoryActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;
    RelativeLayout relativeLayout;
    private PopupWindow popupWindow;
    Button btnRate;
    RatingBar barRate;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);

        toolbar = findViewById(R.id.userToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History");

        relativeLayout = findViewById(R.id.userHistoryRelative);

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.userHistoryRecycler);
        mAuth = FirebaseAuth.getInstance();



        Query query = firebaseFirestore.collection(mAuth.getCurrentUser().getEmail()+"History");
        FirestoreRecyclerOptions<UserHistoryClass> options = new FirestoreRecyclerOptions.Builder<UserHistoryClass>()
                .setQuery(query, UserHistoryClass.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<UserHistoryClass, UserHistoryActivity.ProductViewHolder>(options) {
            @NonNull
            @Override
            public UserHistoryActivity.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history_recycler, parent, false);
                return new UserHistoryActivity.ProductViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserHistoryActivity.ProductViewHolder holder, int position, @NonNull final UserHistoryClass model) {



                holder.p_date.setText(model.getTime());
                holder.p_desc.setText(model.getDesc());
                holder.ngoName.setText(model.getNgo());
                holder.btn_rate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {




                        LayoutInflater layoutInflater = (LayoutInflater) UserHistoryActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View customView = layoutInflater.inflate(R.layout.rating_popup_window,null);
                        popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        popupWindow.showAtLocation(relativeLayout, Gravity.TOP, 0, 300);
                        btnRate = customView.findViewById(R.id.submitRating);
                        barRate = customView.findViewById(R.id.giveRating);
                        btnRate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                float fR = barRate.getRating();
                                int rR = (int)fR;
                                Log.d("stars", rR+"stars");

                                Map<String,String> sMap =  new HashMap<>();
                                sMap.put("Star","Start");
                                firebaseFirestore.collection(model.getNgo()+rR).document().set(sMap);


                                Toast.makeText(UserHistoryActivity.this, "You have given "+fR+"stars", Toast.LENGTH_SHORT).show();


                                popupWindow.dismiss();


                            }
                        });












                    }
                });















            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserHistoryActivity.this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(UserHistoryActivity.this));


        adapter.startListening();








    }


    private class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView p_desc;
        TextView ngoName;
        TextView p_date;
        Button btn_rate;

        public ProductViewHolder(@NonNull View view) {
            super(view);

            p_desc= view.findViewById(R.id.userHistoryDesc);
            p_date = view.findViewById(R.id.userHistoryDate);
            ngoName = view.findViewById(R.id.ngoName);
            btn_rate = view.findViewById(R.id.userHistoryRate);

        }
    }








}
