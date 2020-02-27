package com.example.onlinedonation;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private Button btn_login;
    private TextView txt_register;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private EditText etxt_mail;
    private EditText etxt_password;
    private TextInputLayout txtInputLayout1;
    private TextInputLayout txtInputLayout2;
    private ProgressBar progressBar;

    public LoginFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loadLocale();

        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressBarLogin);
                firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        etxt_mail = view.findViewById(R.id.editTextEmail);
        etxt_password = view.findViewById(R.id.editTextPassword);
        final String[] mail = new String[1];
        final String[] password = new String[1];
        txtInputLayout1 = getActivity().findViewById(R.id.textInputEmail);
        txtInputLayout2 = getActivity().findViewById(R.id.textInputPassword);
        btn_login = view.findViewById(R.id.fragment_btn_login);
        txt_register = view.findViewById(R.id.fragment_txt_register);
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment, new RegisterFragment()).commit();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                txtInputLayout1.setError(null);
                txtInputLayout2.setError(null);
                mail[0] = etxt_mail.getText().toString();
                password[0] = etxt_password.getText().toString();
                                if(mail[0].isEmpty() || password[0].isEmpty())
                {
                    if(mail[0].isEmpty())
                    {

                        txtInputLayout1.setError("please enter email");

                    }
                    if(password[0].isEmpty())
                    {
                        txtInputLayout2.setError("please enter password");
                    }




                }
                else
                {

                    mAuth.signInWithEmailAndPassword(mail[0], password[0]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                progressBar.setVisibility(View.GONE);

                                firebaseFirestore.collection("User").document(mail[0]).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        if(task.isSuccessful())
                                        {
                                            if(task.getResult().getString("access")!=null)
                                            {

                                                if(task.getResult().getString("access").equals("admin"))
                                                {

                                                    Intent intent = new Intent(getActivity(), AdminMainActivity.class);
                                                    startActivity(intent);
                                                    getActivity().finish();

                                                }

                                            }
                                            else{

                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                startActivity(intent);
                                                getActivity().finish();

                                            }


                                        }


                                    }
                                });




                            }
                            else
                            {
                                progressBar.setVisibility(View.GONE);

                                Log.d("error",task.getException().toString());
                                Toast.makeText(getContext(), "message "+task.getException().toString(), Toast.LENGTH_SHORT).show();

                            }




                        }
                    });

                }


            }
        });



        loadLocale();
        Button changeLang=getActivity().findViewById(R.id.button123);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });

    }
    private void showChangeLanguageDialog() {
        final String[] listitmes={"hindi","english","gujarati"};
        AlertDialog.Builder mbuilder=new AlertDialog.Builder(getActivity());
        mbuilder.setTitle("Choose Language");
        mbuilder.setSingleChoiceItems(listitmes, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0)
                {
                    setLocale("hi");
                    getActivity().recreate();
                }
                else if(which==1)
                {
                    setLocale("en");
                    getActivity().recreate();
                }
                else if(which==2)
                {
                    setLocale("gu");
                    getActivity(). recreate();
                }

                dialog.dismiss();
            }
        });
        AlertDialog mDialog=mbuilder.create();
        mDialog.show();
    }
    private void setLocale(String lang) {
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config,getActivity().getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getActivity().getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();

    }
    public  void loadLocale(){
        SharedPreferences prefs=getActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language =prefs.getString("My_Lang","");
        setLocale(language);
    }
}
