package com.example.onlinedonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AdminHistoryActivity extends AppCompatActivity {


    private ArrayList<Admin_History_Class> arrayList;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_history);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBar, null));


        recyclerView = findViewById(R.id.admin_history_recyler);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        arrayList = new ArrayList<>();
        Admin_History_Class c1 = new Admin_History_Class();
        c1.setDate("10-08-19");
        c1.setDonorName("Mayank");
        c1.setItemImage(R.drawable.foo);
        Admin_History_Class c2 = new Admin_History_Class();
        c2.setDate("19-08-19");
        c2.setDonorName("Tony");
        c2.setItemImage(R.drawable.books);
        Admin_History_Class c3 = new Admin_History_Class();
        c3.setDate("10-08-19");
        c3.setDonorName("Williams");
        c3.setItemImage(R.drawable.foo);
        Admin_History_Class c4 = new Admin_History_Class();
        c4.setDate("10-10-19");
        c4.setDonorName("Roberts");
        c4.setItemImage(R.drawable.books);
        Admin_History_Class c5 = new Admin_History_Class();
        c5.setDate("10-08-20");
        c5.setDonorName("Casey");
        c5.setItemImage(R.drawable.clothes);
        Admin_History_Class c6 = new Admin_History_Class();
        c6.setDate("10-08-19");
        c6.setDonorName("Mayank");
        c6.setItemImage(R.drawable.foo);


        arrayList.add(c1);
        arrayList.add(c2);
        arrayList.add(c3);
        arrayList.add(c4);
        arrayList.add(c5);
        arrayList.add(c6);

        AdminHistoryAdapter adapter = new AdminHistoryAdapter(AdminHistoryActivity.this,arrayList);


        LinearLayoutManager layoutManager = new LinearLayoutManager(AdminHistoryActivity.this);

        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(AdminHistoryActivity.this));
        recyclerView.setAdapter(adapter);




    }
}
