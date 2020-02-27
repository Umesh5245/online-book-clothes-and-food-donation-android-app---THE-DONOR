package com.example.onlinedonation;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonateItemFragment extends Fragment {


    private Spinner spinnerSelectItem;
    private Spinner spinnerSelectCity;
    private ArrayList<String> item;
    private Uri mainUri;
    DateFormat df;
    String date;
    private FirebaseAuth mAuth;
    private ArrayList<String> city;
    private Button takePicBtn;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private Bitmap compressedImageFile;
    private Button btn_Submit;

    private DatabaseReference reference;
    private FirebaseFirestore firebaseFirestore;
    private ProgressBar progressBar;

    String collectionString;

    private String txt_item_select;
    private EditText edt_user_name;
    private EditText edt_user_mobile;
    private String txt_user_city;
    private EditText edt_item_desc;
    private EditText edt_user_email;
    String uName;
    String uAddress;
    FirebaseStorage storage;
    String uMobile;
    String uEmail;
    String iDesc;
    StorageReference storageReference;
    FirebaseUser firebaseUser;
    private String itemSelected;



    public DonateItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donate_item, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        date = df.format(Calendar.getInstance().getTime());

        collectionString = UUID.randomUUID().toString();



        uName = edt_user_name.getText().toString();
        uEmail = edt_user_email.getText().toString();
        uMobile = edt_user_mobile.getText().toString();

        txt_item_select = spinnerSelectItem.getSelectedItem().toString();
        txt_user_city = spinnerSelectCity.getSelectedItem().toString();





        progressBar.setVisibility(View.VISIBLE);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mainUri = result.getUri();
                //uptill here we got the real image Now we will convert it to thumbnail for less memory consumption




                final StorageReference ref
                        = storageReference
                        .child("DonationImages").child(collectionString+".jpg");
                ref.putFile(mainUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {


                                Map<String,String> map = new HashMap<>();
                                map.put("id", collectionString);


                                firebaseFirestore.collection("UserDonation"+txt_item_select).document().set(map);
                                firebaseFirestore.collection("UserDonations").document(mAuth.getCurrentUser().getEmail()+date).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful())

                                        {

                                            firebaseFirestore.collection("UserDonations").document(mAuth.getCurrentUser().getEmail()+date).update("image",uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {

                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    imageView.setImageURI(mainUri);
                                                    progressBar.setVisibility(View.GONE);

                                                    Toast.makeText(getContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();;



                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });



                                        }



                                    }
                                });





                            }
                        });




                    }
                });











                imageView.setImageURI(mainUri);








            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





         df = new SimpleDateFormat("h:mm,MM d");




        edt_user_name = view.findViewById(R.id.fragment_donate_edt_name);
        edt_user_mobile = view.findViewById(R.id.fragment_donate_edt_mobile);
        edt_item_desc = view.findViewById(R.id.fragment_donate_item_description);
        edt_user_email = view.findViewById(R.id.fragment_donate_edt_email);




        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mAuth = FirebaseAuth.getInstance();

        btn_Submit = view.findViewById(R.id.fragment_donate_submit);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();







        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);






                iDesc = edt_item_desc.getText().toString();








                   firebaseFirestore.collection("UserDonations").document(mAuth.getCurrentUser().getEmail()+date).update("name",uName);
                   firebaseFirestore.collection("UserDonations").document(mAuth.getCurrentUser().getEmail()+date).update("email",uEmail);
                   firebaseFirestore.collection("UserDonations").document(mAuth.getCurrentUser().getEmail()+date).update("mobile",uMobile);
                   firebaseFirestore.collection("UserDonations").document(mAuth.getCurrentUser().getEmail()+date).update("item",txt_item_select);
                   firebaseFirestore.collection("UserDonations").document(mAuth.getCurrentUser().getEmail()+date).update("city",txt_user_city);
                   firebaseFirestore.collection("UserDonations").document(mAuth.getCurrentUser().getEmail()+date).update("desc",iDesc).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful())
                           {
                               Toast.makeText(getContext(), "Item added successfully", Toast.LENGTH_SHORT).show();
                               progressBar.setVisibility(View.GONE);
                           }
                       }
                   });
















            }
        });




        progressBar = view.findViewById(R.id.progressBarDonateItem);


        takePicBtn = view.findViewById(R.id.fragment_donate_take_img);

        firebaseFirestore = FirebaseFirestore.getInstance();



        //work done uptill here only


        takePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getContext(),"Permission Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                    else{
                        //Toast.makeText(SetupActivity.this, "You already have permission" , Toast.LENGTH_SHORT).show();
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .start(getActivity());



                    }
                }
                else{

                }



            }
        });





        imageView = view.findViewById(R.id.fragment_donate_img);

        spinnerSelectCity = view.findViewById(R.id.fragment_donate_edt_city_spinner);
        spinnerSelectItem = view.findViewById(R.id.fragment_donate_spinner_items);






        city = new ArrayList<>();
        city.add("Select City");
        city.add("Rajkot");
        city.add("Gandhinagar");
        city.add("Jamnagar");
        city.add("Saurashtra");
        city.add("Morbi");


        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(),R.layout.layout_city_spinner,city){
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



        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(),R.layout.layout_item_spinner,item){
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










        spinnerSelectItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 1:

                        imageView.setImageResource(R.drawable.foo);


                        Calendar rightNow = Calendar.getInstance();
                        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
                        if(currentHourIn24Format>=11)
                        {

                            Toast.makeText(getContext(), "You can not donate food item this late", Toast.LENGTH_SHORT).show();
                            btn_Submit.setVisibility(View.GONE);

                        }




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




}
