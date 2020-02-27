package com.example.onlinedonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView img_Home;
    private ImageView img_UserProfile;
    private FloatingActionButton btn_addItem;
    private PopupWindow popupWindow;
    private RelativeLayout relativeLayout;
    private Button btnCloseAddItem;
    private Button donateItem;
    private Toolbar toolbar;
    private ImageView dashboard;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logout:
               FirebaseAuth.getInstance().signOut();
               Intent intent = new Intent(MainActivity.this,LoginActivity.class);
               startActivity(intent);
               break;
            case  R.id.showStatistics:
                Intent intent1 = new Intent(MainActivity.this,StatsActivity.class);
                startActivity(intent1);

        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        dashboard = findViewById(R.id.notification);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new UserDashboardFragment()).commit();

            }
        });



        getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBar, null));





        relativeLayout = findViewById(R.id.mainRelativeLayout);

        btn_addItem = findViewById(R.id.item_add);
        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(popupWindow!=null)
                {
                    popupWindow.dismiss();
                }

                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.fragment_add_button_first_view,null);
                popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 300);
                btnCloseAddItem = customView.findViewById(R.id.frgmentCloseAddItem);

                donateItem = customView.findViewById(R.id.fragmentAddBtnFood);




                donateItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new DonateItemFragment()).commit();
                    }
                });


                btnCloseAddItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        img_Home = findViewById(R.id.home);
        img_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popupWindow!=null)
                popupWindow.dismiss();

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new Home_Activity_Fragment()).commit();

            }
        });

        img_UserProfile = findViewById(R.id.profile);
        img_UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popupWindow!=null)
                popupWindow.dismiss();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new UserProfileFragment()).commit();
            }
        });








        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Donation App");

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new Home_Activity_Fragment()).commit();





    }




}
