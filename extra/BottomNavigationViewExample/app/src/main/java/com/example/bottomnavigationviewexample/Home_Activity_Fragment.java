package com.example.bottomnavigationviewexample;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Activity_Fragment extends Fragment {


    private ArrayList<Home_Ngo_Requirement_RecyclerClass> arrayList;
    private RecyclerView recyclerView;
    private ArrayList<Home_Slider_Class> homeSliderClassArrayList;
    private SliderView homeSlider;
    private SliderView homeSliderNgo;
    private ArrayList<Home_Slider_Ngo_Class> homeSliderNgoClassArrayList;
    private Button btn_ShowNgoList;
    private ScrollView scrollView;

    public Home_Activity_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_ShowNgoList = view.findViewById(R.id.home_drawable_ShowAllNgo);
        btn_ShowNgoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new NgoList(),null).commit();
            }
        });


        arrayList = new ArrayList<>();
        homeSliderClassArrayList = new ArrayList<>();
        Home_Ngo_Requirement_RecyclerClass h1 = new Home_Ngo_Requirement_RecyclerClass();
        h1.setImageView(R.drawable.ngo_1);
        h1.setNgoName("name1");
        h1.setNgoLocation("Gauridad/Rajkot");
        h1.setNgoRequirement("We require some food so help us");

        Home_Ngo_Requirement_RecyclerClass h2 = new Home_Ngo_Requirement_RecyclerClass();
        h2.setImageView(R.drawable.ngo_2);
        h2.setNgoName("name1");
        h2.setNgoLocation("Gauridad/Rajkot");
        h2.setNgoRequirement("We require some food so help us");

        Home_Ngo_Requirement_RecyclerClass h3 = new Home_Ngo_Requirement_RecyclerClass();
        h3.setImageView(R.drawable.ngo_3);
        h3.setNgoName("name1");
        h3.setNgoLocation("Gauridad/Rajkot");
        h3.setNgoRequirement("We require some food so help us");


        arrayList.add(h1);
        arrayList.add(h2);
        arrayList.add(h3);



        recyclerView = getActivity().findViewById(R.id.home_recyclerView);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setNestedScrollingEnabled(true);

        Home_Ngo_Requirement_RyclerAdapter mainAdapter = new Home_Ngo_Requirement_RyclerAdapter(arrayList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mainAdapter);

        Home_Slider_Class c1 = new Home_Slider_Class();
        c1.setP_Location("Rajkot");
        c1.setP_Name("Mayank");
        c1.setP_Ngo("Robin Hood");
        c1.setProductImage(R.drawable.books);

        Home_Slider_Class c2 = new Home_Slider_Class();
        c2.setP_Location("Gauridad");
        c2.setP_Name("Umesh");
        c2.setP_Ngo("Saathi");
        c2.setProductImage(R.drawable.foo);

        Home_Slider_Class c3 = new Home_Slider_Class();
        c3.setP_Location("Bedi");
        c3.setP_Name("Maya");
        c3.setP_Ngo("Sewa");
        c3.setProductImage(R.drawable.clothes);
        homeSliderClassArrayList.add(c1);
        homeSliderClassArrayList.add(c2);
        homeSliderClassArrayList.add(c3);

        homeSlider = getActivity().findViewById(R.id.home_sliderViewContainerDonation);



        Home_Slider_Adapter adapter = new Home_Slider_Adapter(getContext(),homeSliderClassArrayList);
        homeSlider.setSliderAdapter(adapter);
        homeSlider.setIndicatorVisibility(true);
        homeSlider.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        homeSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        homeSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        homeSlider.setIndicatorSelectedColor(Color.BLACK);
        homeSlider.setIndicatorUnselectedColor(Color.GRAY);
        homeSlider.setIndicatorRadius(0);
        homeSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        homeSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        homeSlider.startAutoCycle();

        homeSliderNgo = getActivity().findViewById(R.id.home_sliderViewContainerNgo);
        homeSliderNgoClassArrayList = new ArrayList<>();
        Home_Slider_Ngo_Class s1 = new Home_Slider_Ngo_Class();
        s1.setNgo_Logo(R.drawable.ngo_1);
        s1.setNgo_Name("Robin Hood Army");
        Home_Slider_Ngo_Class s2 = new Home_Slider_Ngo_Class();
        s2.setNgo_Logo(R.drawable.ngo_2);
        s2.setNgo_Name("Seva");
        Home_Slider_Ngo_Class s3 = new Home_Slider_Ngo_Class();
        s3.setNgo_Logo(R.drawable.ngo_3);
        s3.setNgo_Name("Food Donators");
        Home_Slider_Ngo_Class s4 = new Home_Slider_Ngo_Class();
        s4.setNgo_Logo(R.drawable.ngo_4);
        s4.setNgo_Name("Sambhav");
        Home_Slider_Ngo_Class s5 = new Home_Slider_Ngo_Class();
        s5.setNgo_Logo(R.drawable.ngo_5);
        s5.setNgo_Name("Saathi");
        homeSliderNgoClassArrayList.add(s1);
        homeSliderNgoClassArrayList.add(s2);
        homeSliderNgoClassArrayList.add(s3);
        homeSliderNgoClassArrayList.add(s4);
        homeSliderNgoClassArrayList.add(s5);

        Home_Slider_Ngo_Adapter homeSliderNgoAdapter = new Home_Slider_Ngo_Adapter(getContext(), homeSliderNgoClassArrayList);


        homeSliderNgo.setSliderAdapter(homeSliderNgoAdapter);
        homeSliderNgo.setIndicatorVisibility(true);
        homeSliderNgo.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        homeSliderNgo.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        homeSliderNgo.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        homeSliderNgo.setIndicatorSelectedColor(Color.BLACK);
        homeSliderNgo.setIndicatorUnselectedColor(Color.GRAY);
        homeSliderNgo.setIndicatorVisibility(false);
        homeSliderNgo.setIndicatorRadius(0);
        homeSliderNgo.setScrollTimeInSec(4); //set scroll delay in seconds :
        homeSliderNgo.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        homeSliderNgo.startAutoCycle();





    }
}
