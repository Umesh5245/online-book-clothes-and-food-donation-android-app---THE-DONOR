package com.example.onlinedonation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NgoRegistrationActivity extends AppCompatActivity {

        TextView name;
        String sName;
        TextView mobile;
        String sMobile;
        TextView email;
        String sMail;
        TextView password;
        String sPassword;
        TextView upload;
        TextView uploaded;
        Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_registration);
        name = findViewById(R.id.ngoregister_uname);
        mobile = findViewById(R.id.ngoregister_mobile);
        email = findViewById(R.id.ngoregister_email);
        password = findViewById(R.id.ngoregister_password);
        upload = findViewById(R.id.ngoRegisterUploadFile);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("*/*");
                startActivityForResult(intent1, 7);
            }
        });



        uploaded = findViewById(R.id.ngoRegisterUploadedFile);
        register = findViewById(R.id.ngofragment_btn_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(NgoRegistrationActivity.this, "We have accepted your document we will inform you soon", Toast.LENGTH_SHORT).show();




















            }
        });
    }
}
