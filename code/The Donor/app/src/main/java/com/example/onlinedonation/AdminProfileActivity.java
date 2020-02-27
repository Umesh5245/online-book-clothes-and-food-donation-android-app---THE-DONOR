package com.example.onlinedonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdminProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    private CircleImageView img_profile;
    private Uri mainUri;





    private FirebaseFirestore firebaseFirestore;

    FirebaseStorage storage;
    private ProgressBar progressBar;

    StorageReference storageReference;
    FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private TextView txtNogoName;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminProfileActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }

        return true;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        toolbar = findViewById(R.id.toolbar);
        txtNogoName = findViewById(R.id.admin_activity_ngo_name_txt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBar, null));


        progressBar = findViewById(R.id.progressBarAdminProfile);
        progressBar.setVisibility(View.VISIBLE);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();




        img_profile = findViewById(R.id.admin_activity_profile_upload);



        firebaseFirestore.collection("User").document(firebaseUser.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful())
                {

                    progressBar.setVisibility(View.VISIBLE);
                    if(task.getResult().exists()){
                        String image = task.getResult().getString("image");

                        if(AdminProfileActivity.this!=null)
                        {

                            TextView txt_u_name =  findViewById(R.id.admin_activity_user_edt);
                            txt_u_name.setText(task.getResult().getString("name"));
                            TextView txt_u_name1 =  findViewById(R.id.admin_activity_user_edt);
                            txt_u_name1.setText(task.getResult().getString("name"));
                            TextView txt_u_mobile =  findViewById(R.id.admin_activity_nago_phone);
                            txt_u_mobile.setText(task.getResult().getString("phone"));
                            TextView txt_u_email =  findViewById(R.id.admin_activity_ngo__email);
                            txt_u_email.setText(task.getResult().getString("email"));
                            txtNogoName.setText(task.getResult().getString("name"));



                            if(image!=null)
                            {
                                Glide.with(AdminProfileActivity.this).load(image).into(img_profile);
                                progressBar.setVisibility(View.GONE);

                            }
                            else if(image == null)
                            {
                                progressBar.setVisibility(View.GONE);
                            }



                        }


                    }

                }

            }
        });






        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(AdminProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(AdminProfileActivity.this,"Permission Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(AdminProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                    else{
                        //Toast.makeText(SetupActivity.this, "You already have permission" , Toast.LENGTH_SHORT).show();
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .start(AdminProfileActivity.this);



                    }
                }
                else{

                }
            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mainUri = result.getUri();
               progressBar.setVisibility(View.VISIBLE);



                final StorageReference ref
                        = storageReference
                        .child("images").child(firebaseUser.getEmail()+".jpg");
                ref.putFile(mainUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

//                                Map<String,String> usermap = new HashMap<>();
//
//                                usermap.put("image", uri.toString());
                                firebaseFirestore.collection("User").document(firebaseUser.getEmail()).update("image",uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {

                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        img_profile.setImageURI(mainUri);
                                        progressBar.setVisibility(View.GONE);

                                        Toast.makeText(AdminProfileActivity.this, "Image uploaded successfullly", Toast.LENGTH_SHORT).show();;



                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AdminProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });




                    }
                });





            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }





    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
