package com.example.onlinedonation;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private TextView txt_login;
    private Button btn_register;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private EditText edt_name;
    private EditText edt_mobile;
    private EditText edt_password;
    private EditText edt_email;
    private DatabaseReference reference;
    private FirebaseFirestore firebaseFirestore;
    private ProgressBar progressBar;
    private Button btnNgoRegister;



    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        txt_login = view.findViewById(R.id.fragment_register_txtView);
        btn_register = view.findViewById(R.id.fragment_btn_register);

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment, new LoginFragment()).commit();
            }
        });

            return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();



        btnNgoRegister = view.findViewById(R.id.fragment_btn_registerNgo);

        btnNgoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),NgoRegistrationActivity.class);
                startActivity(intent);



            }
        });



        progressBar = view.findViewById(R.id.progressBarRegister);
        firebaseFirestore = FirebaseFirestore.getInstance();



        edt_email = view.findViewById(R.id.register_email);
        edt_password = view.findViewById(R.id.register_password);
        edt_mobile = view.findViewById(R.id.register_mobile);
        edt_name = view.findViewById(R.id.register_uname);


        final String[] name = new String[1];
        final String[] mobile = new String[1];

        final String[] mail = new String[1];
        final String[] password = new String[1];

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

               name[0] = edt_name.getText().toString();
               mobile[0] = edt_mobile.getText().toString();
               mail[0] = edt_email.getText().toString();
               password[0] = edt_password.getText().toString();

               final Map<String,String> usermap = new HashMap<>();
               usermap.put("name",name[0]);
               usermap.put("mobile", mobile[0]);
               usermap.put("email", mail[0]);

               mAuth.createUserWithEmailAndPassword(mail[0], password[0]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       if(task.isSuccessful()){

                           firebaseFirestore.collection("User").document(mail[0]).set(usermap).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(getContext(),MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                               }
                           });

                       }
                       else{




                       }



                   }
               });



            }
        });


    }
}
