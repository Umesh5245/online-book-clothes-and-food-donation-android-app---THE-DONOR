package com.example.onlinedonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminMainActivity extends AppCompatActivity {

    private TextView txt_name;
    private CircleImageView ngoLogo;
    private ImageView img_request;
    private ImageView img_viewProfile;
    private ImageView img_showDonations;
    private ImageView img_showHistory;
    private ImageView img_showNotification;
    private ImageView img_dashboard;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    String imageIcon;
    String ngoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBar, null));
        mAuth = FirebaseAuth.getInstance();


        img_dashboard = findViewById(R.id.admin_activity_pending);
        img_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this,NgoDashboardActivity.class);
                startActivity(intent);
            }
        });

        txt_name = findViewById(R.id.admin_activity_main_icon_txt);
        ngoLogo = findViewById(R.id.admin_activity_main_icon);
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful())
                {

                    imageIcon = task.getResult().getString("image");
                    ngoName = task.getResult().getString("name");
                    txt_name.setText(ngoName);


                    Glide.with(AdminMainActivity.this).load(imageIcon).into(ngoLogo);




                }


            }
        });




        img_showNotification = findViewById(R.id.admin_activity_notifications);
        img_showNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this,NotificationAdminActivity.class);
                startActivity(intent);
            }
        });


        img_showDonations = findViewById(R.id.admin_activity_show_donations);
        img_showDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this,AdminDonationItemActivity.class);
                startActivity(intent);
            }
        });

        img_showHistory = findViewById(R.id.admin_activity_main_history);
        img_showHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminMainActivity.this,AdminHistoryActivity.class);
                startActivity(intent);




            }
        });



        img_viewProfile = findViewById(R.id.admin_activity_view_profile);
        img_viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this,AdminProfileActivity.class);
                startActivity(intent);

            }
        });



        img_request = findViewById(R.id.admin_activity_img_request);
        img_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminMainActivity.this,AdminRequestActivity.class);
                startActivity(intent);


            }
        });
    }
}
