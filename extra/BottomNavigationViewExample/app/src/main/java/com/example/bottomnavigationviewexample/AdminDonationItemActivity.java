package com.example.bottomnavigationviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

public class AdminDonationItemActivity extends AppCompatActivity {

    private ArrayList<Admin_Donation_Class> arrayList,temp_list;
    private RecyclerView recyclerView;
    private EditText search_Donation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_donation_item);

        recyclerView = findViewById(R.id.admin_activity_donation_recycler);

        Admin_Donation_Class c1 = new Admin_Donation_Class();
        c1.setImageId(R.drawable.wall_2);
        c1.setDescription("this is a food having palak paneer");
        Admin_Donation_Class c2 = new Admin_Donation_Class();
        c2.setImageId(R.drawable.books);
        c2.setDescription("20 books for eight standard kids");
        Admin_Donation_Class c3 = new Admin_Donation_Class();
        c3.setImageId(R.drawable.clothes);
        c3.setDescription("i want to donate black regular fit jeans ");
        Admin_Donation_Class c4 = new Admin_Donation_Class();
        c4.setImageId(R.drawable.foo);
        c4.setDescription("My mother made extra dal which can be easily served for two people ");

        arrayList = new ArrayList<>();

        arrayList.add(c1);
        arrayList.add(c2);
        arrayList.add(c3);
        arrayList.add(c4);

        final AdminAdapter adminAdapter = new AdminAdapter(arrayList, AdminDonationItemActivity.this);
        temp_list = new ArrayList<>(arrayList);




        search_Donation = findViewById(R.id.admin_search_donation);
        search_Donation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());


            }

            private void filter(String text) {


                ArrayList<Admin_Donation_Class> filterdNames = new ArrayList<>();




                if(text.length()== 0 || text == null)
                {
                    filterdNames.addAll(temp_list);

                }

                else
                {


                    for (Admin_Donation_Class details : temp_list) {
                        //if the existing elements contains the search input



                        if (details.getDescription().toLowerCase().contains(text.toLowerCase())) {
                            //adding the element to filtered list
                            filterdNames.add(details);
                            arrayList.clear();

                        }
                    }


                    arrayList.addAll(filterdNames);

                }


                adminAdapter.filter(filterdNames);


            }




        });

        adminAdapter.notifyDataSetChanged();




        LinearLayoutManager layoutManager = new LinearLayoutManager(AdminDonationItemActivity.this);

        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(AdminDonationItemActivity.this));
        recyclerView.setAdapter(adminAdapter);












    }
}
