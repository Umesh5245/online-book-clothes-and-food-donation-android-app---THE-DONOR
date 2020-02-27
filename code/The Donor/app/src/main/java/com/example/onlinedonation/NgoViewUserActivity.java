package com.example.onlinedonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class NgoViewUserActivity extends AppCompatActivity {

    private CircleImageView profilePic;
    private FirebaseFirestore firebaseFirestore;
    private ProgressBar progressBar;
    private String userName;
    TextView username1;
    TextView username2;
    TextView phone;
    TextView email;
    TextView address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_view_user);


        profilePic = findViewById(R.id.ngofragment_user_icon);
        username1 = findViewById(R.id.ngofragment_user_name);
        username2 = findViewById(R.id.ngofragment_user_name1);
        phone = findViewById(R.id.ngofragment_user_mobile);
        email = findViewById(R.id.ngofragment_user_email);
        address = findViewById(R.id.ngofragment_user_address);

        profilePic = findViewById(R.id.ngofragment_user_icon);
        progressBar = findViewById(R.id.ngoprogressBar);

        firebaseFirestore = FirebaseFirestore.getInstance();

        progressBar.setVisibility(View.VISIBLE);

        userName = getIntent().getStringExtra("email");

        firebaseFirestore.collection("User").document(userName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful())
                {

                    username1.setText(task.getResult().getString("name"));
                    username2.setText(task.getResult().getString("name"));
                    email.setText(task.getResult().getString("email"));
                    phone.setText(task.getResult().getString("mobile"));


                    Glide.with(NgoViewUserActivity.this).load(task.getResult().getString("image")).into(profilePic);




                    progressBar.setVisibility(View.GONE);





                }




            }
        });


    }
}
