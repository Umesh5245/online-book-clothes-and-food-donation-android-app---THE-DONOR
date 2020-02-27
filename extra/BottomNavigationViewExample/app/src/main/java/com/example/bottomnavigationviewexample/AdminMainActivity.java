package com.example.bottomnavigationviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminMainActivity extends AppCompatActivity {

    private ImageView img_request;
    private ImageView img_viewProfile;
    private ImageView img_showDonations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        img_showDonations = findViewById(R.id.admin_activity_show_donations);
        img_showDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this,AdminDonationItemActivity.class);
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
