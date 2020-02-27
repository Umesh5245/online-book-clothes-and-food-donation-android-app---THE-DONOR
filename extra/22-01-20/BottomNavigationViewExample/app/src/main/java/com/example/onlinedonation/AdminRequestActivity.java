package com.example.onlinedonation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;


public class AdminRequestActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner spinnerSelectItem;
    private Spinner spinnerSelectCity;
    private ArrayList<String> item;

    private ArrayList<String> city;
    String imageUrl;

    private ImageView imageView;
    StorageReference storageReference;
    FirebaseUser firebaseUser;
    FirebaseStorage storage;

    EditText edtName;
    EditText edtMobile;
    EditText edtEmail;
    EditText edtDesc;
    String ngoName;
    String ngoMobile;
    String ngoEmail;
    String ngoCity;
    String ngoDesc;
    FirebaseAuth mAuth;
    private String collectionString;
    private DatabaseReference reference;
    private FirebaseFirestore firebaseFirestore;
    private Button btnSubmit;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_request);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBar, null));
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBarAdminRequest);
        progressBar.setVisibility(View.VISIBLE);


        edtDesc = findViewById(R.id.fragment_donate_item_description);
        edtEmail = findViewById(R.id.fragment_donate_edt_email);
        edtMobile = findViewById(R.id.fragment_donate_edt_mobile);
        edtName = findViewById(R.id.fragment_donate_edt_name);

        btnSubmit = findViewById(R.id.fragment_donate_submit);

        mAuth = FirebaseAuth.getInstance();
        collectionString = UUID.randomUUID().toString();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful())
            {

                edtName.setText(task.getResult().get("name").toString());
                edtEmail.setText(task.getResult().get("email").toString());
                edtMobile.setText(task.getResult().get("phone").toString());


                 imageUrl = task.getResult().get("image").toString();

                 imageUrl = task.getResult().get("image").toString();
                progressBar.setVisibility(View.GONE);



            }
            }
        });













        imageView = findViewById(R.id.fragment_donate_img);

        spinnerSelectCity = findViewById(R.id.fragment_donate_edt_city_spinner);
        spinnerSelectItem = findViewById(R.id.fragment_donate_spinner_items);






        city = new ArrayList<>();
        city.add("Select City");
        city.add("Rajkot");
        city.add("Gandhinagar");
        city.add("Jamnagar");
        city.add("Saurashtra");
        city.add("Morbi");


        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(AdminRequestActivity.this,R.layout.layout_city_spinner,city){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }


        };spinnerSelectCity.setAdapter(arrayAdapter2);













        item = new ArrayList<>();
        item.add("Select the item");
        item.add("Food");
        item.add("Clothes");
        item.add("Books");



        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(AdminRequestActivity.this,R.layout.layout_item_spinner,item){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        spinnerSelectItem.setAdapter(arrayAdapter1);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ngoCity = spinnerSelectCity.getSelectedItem().toString();
                ngoName = edtName.getText().toString();
                ngoDesc = edtDesc.getText().toString();
                ngoEmail = edtEmail.getText().toString();
                ngoMobile = edtMobile.getText().toString();
            progressBar.setVisibility(View.VISIBLE);


                DateFormat df = new SimpleDateFormat("h:mm,MM d");
                String date = df.format(Calendar.getInstance().getTime());

                Map<String,String> map = new HashMap<>();
                map.put("name", ngoName);
                map.put("city", ngoCity);
                map.put("desc", ngoDesc);
                map.put("email", ngoEmail);
                map.put("mobile",ngoMobile);
                map.put("image", imageUrl);
                map.put("time",date);





                firebaseFirestore.collection("NgoRequest").document(ngoName+date).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Toast.makeText(AdminRequestActivity.this, "Request Uploaded Successfully", Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            Toast.makeText(AdminRequestActivity.this, "Error", Toast.LENGTH_LONG).show();

                        }


                    }
                });




            }
        });



        spinnerSelectItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 1:

                        imageView.setImageResource(R.drawable.foo);

                        break;
                    case 2:

                        imageView.setImageResource(R.drawable.clothes);
                        break;
                    case 3:

                        imageView.setImageResource(R.drawable.books);
                        break;
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });











    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
