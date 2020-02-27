package com.example.onlinedonation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NotificationAdminActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;
    String ngoName;
    String desc;
    String msg;
    DateFormat df;
    String date;
    EditText edt_msg;
    private PopupWindow popupWindow;
    RelativeLayout relativeLayout;
    Button btnSend;

    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_admin);
        relativeLayout = findViewById(R.id.notificationRelative);

        recyclerView = findViewById(R.id.admin_notificationRecycler);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        df = new SimpleDateFormat("h:mm,MM d");

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
                        protected void onBindViewHolder(@NonNull final NotificationAdminActivity.ProductViewHolder holder, int position, @NonNull final AdminNotificationClass model) {




                            holder.itemDesc.setText(model.getDesc());
                            holder.time.setText(model.getTime());
                            holder.donorEmail.setText(model.getName());

                            holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {



                                    LayoutInflater layoutInflater = (LayoutInflater) NotificationAdminActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View customView = layoutInflater.inflate(R.layout.ngo_confirm_user_btn,null);
                                    popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    popupWindow.showAtLocation(relativeLayout, Gravity.TOP, 0, 300);
                                    popupWindow.setFocusable(true);
                                    popupWindow.update();
                                    edt_msg = customView.findViewById(R.id.ngoConfirmUserBtn_edtMesage);
                                    btnSend = customView.findViewById(R.id.ngoConfirmUserBtn_btnSend);
                                    btnSend.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            desc = model.getDesc();

                                            msg = edt_msg.getText().toString();
                                            date = df.format(Calendar.getInstance().getTime());


                                            Map<String,String> map = new HashMap<>();
                                            map.put("NgoName", ngoName);
                                            map.put("desc", desc);
                                            map.put("time", date);
                                            map.put("msg", msg);
                                            map.put("item", model.getItem());
                                            map.put("donor", model.getDonor());

                                            firebaseFirestore.collection(model.getName()+"Pending").document(ngoName+date).set(map);
                                            firebaseFirestore.collection(ngoName+"Pending").document(ngoName+date).set(map);

                                               popupWindow.dismiss();
                                            Toast.makeText(NotificationAdminActivity.this, "Notification Send To User", Toast.LENGTH_SHORT).show();








                                        }
                                    });









                                }
                            });



                            holder.btnShowProfile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(NotificationAdminActivity.this,NgoViewUserActivity.class);
                                    intent.putExtra("email", model.getName());
                                    startActivity(intent);





                                }
                            });







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

        Button btnShowProfile;
        public ProductViewHolder(@NonNull View view) {
            super(view);

            btnShowProfile = view.findViewById(R.id.admin_notification_btnShowProfile);
            donorEmail = view.findViewById(R.id.admin_notification_donorName);
            itemDesc = view.findViewById(R.id.admin_notification_desc);
            time = view.findViewById(R.id.admin_notification_donorDate);
            btnAccept = view.findViewById(R.id.admin_notification_btnAccept);
        }
    }



}
