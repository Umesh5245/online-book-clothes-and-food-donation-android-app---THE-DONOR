package com.example.onlinedonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {


    FirebaseFirestore firebaseFirestore;
    int requestFood;
    int requestClothes;
    int requestBooks;

    int donatedFood;
    int donatedClothes;
    int donatedBooks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        final PieChart pieChart = findViewById(R.id.piechart);


        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("NgoRequestBooks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    requestBooks = task.getResult().size();

                    firebaseFirestore.collection("NgoRequestFood").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful())
                            {
                                requestFood = task.getResult().size();



                                firebaseFirestore.collection("NgoRequestClothes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        if(task.isSuccessful())
                                        {
                                            requestClothes = task.getResult().size();


                                            List<PieEntry> entries = new ArrayList<>();

                                            entries.add(new PieEntry(Float.parseFloat(String.valueOf(requestBooks)), "Books"));
                                            entries.add(new PieEntry(Float.parseFloat(String.valueOf(requestClothes)), "Clothes"));
                                            entries.add(new PieEntry(Float.parseFloat(String.valueOf(requestFood)), "Food"));


                                            PieDataSet set = new PieDataSet(entries, "Total Ngo Request");

                                            pieChart.setCenterText("Ngo Requests");
                                            PieData data = new PieData(set);
                                            pieChart.setData(data);
                                            set.setColors(ColorTemplate.COLORFUL_COLORS);
                                            pieChart.animateXY(5000, 5000);
                                            pieChart.getDescription().setEnabled(false);
                                            pieChart.getLegend().setEnabled(false);
                                            pieChart.invalidate();

                                        }

                                    }
                                });


                            }

                        }
                    });

                }

            }
        });



        final PieChart pieChart1 = findViewById(R.id.piechart1);

        firebaseFirestore.collection("NgoAcceptBooks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    donatedBooks = task.getResult().size();

                    firebaseFirestore.collection("NgoAcceptFood").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful())
                            {
                                donatedFood = task.getResult().size();



                                firebaseFirestore.collection("NgoAcceptClothes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        if(task.isSuccessful())
                                        {
                                            donatedClothes = task.getResult().size();


                                            List<PieEntry> entries = new ArrayList<>();

                                            entries.add(new PieEntry(Float.parseFloat(String.valueOf(donatedBooks)), "Books"));
                                            entries.add(new PieEntry(Float.parseFloat(String.valueOf(donatedClothes)), "Clothes"));
                                            entries.add(new PieEntry(Float.parseFloat(String.valueOf(donatedFood)), "Food"));


                                            PieDataSet set = new PieDataSet(entries, "Total Ngo Donatios");

                                            pieChart1.setCenterText("Ngo Donations");
                                            PieData data = new PieData(set);
                                            pieChart1.setData(data);
                                            set.setColors(ColorTemplate.COLORFUL_COLORS);
                                            pieChart1.animateXY(5000, 5000);
                                            pieChart1.getDescription().setEnabled(false);
                                            pieChart1.getLegend().setEnabled(false);
                                            pieChart1.invalidate();

                                        }

                                    }
                                });


                            }

                        }
                    });

                }

            }
        });













    }
}
