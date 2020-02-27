package com.example.onlinedonation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserDashboardFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;

    public UserDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_dashboard, container, false);
    }





    private class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView ngoName;
        TextView itemDesc;
        TextView itemDate;
        Button btnUndo;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            ngoName = itemView.findViewById(R.id.user_dashboard_ngoName);
            itemDesc = itemView.findViewById(R.id.user_dashboard_itemDesc);
            itemDate = itemView.findViewById(R.id.user_dashboard_date);

            btnUndo = itemView.findViewById(R.id.user_dashboard_btnUndo);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.userDashboardRecycler);
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection(mAuth.getCurrentUser().getEmail());

        FirestoreRecyclerOptions<UserDashboardClass> options = new FirestoreRecyclerOptions.Builder<UserDashboardClass>()
                .setQuery(query, UserDashboardClass.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<UserDashboardClass, UserDashboardFragment.ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final UserDashboardClass model) {

                holder.itemDesc.setText(model.getDesc());
                holder.ngoName.setText(model.getName());
                holder.itemDate.setText(model.getTime());
                holder.btnUndo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        firebaseFirestore.collection(mAuth.getCurrentUser().getEmail()).document(model.getName()+model.getTime()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if(task.isSuccessful())
                                {
                                    Map<String,String> map = new HashMap<>();

                                    final String ngoName = task.getResult().getString("name");
                                    final String ngoDate = task.getResult().getString("time");

                                    map.put("city", task.getResult().getString("city"));
                                    map.put("desc", task.getResult().getString("desc"));
                                    map.put("email", task.getResult().getString("email"));
                                    map.put("image", task.getResult().getString("image"));
                                    map.put("mobile", task.getResult().getString("mobile"));
                                    map.put("name", task.getResult().getString("name"));
                                    map.put("time", task.getResult().getString("time"));
                                    map.put("item", task.getResult().getString("item"));
                                    map.put("donor", task.getResult().getString("donor"));

                                    firebaseFirestore.collection("NgoRequest").document(model.getName()+model.getTime()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(getContext(), "Request is undone from your dashboard", Toast.LENGTH_SHORT).show();
                                            firebaseFirestore.collection(mAuth.getCurrentUser().getEmail()).document(model.getName()+model.getTime()).delete();
                                            firebaseFirestore.collection(ngoName).document(ngoDate).delete();


                                        }
                                    });




                                }


                            }
                        });
                    }
                });



            }

            @NonNull
            @Override
            public UserDashboardFragment.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_user_dashboard, parent, false);
                return new UserDashboardFragment.ProductViewHolder(view);
            }


        };



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));


        adapter.startListening();










    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
