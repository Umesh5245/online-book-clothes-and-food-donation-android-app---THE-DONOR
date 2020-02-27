package com.example.bottomnavigationviewexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class LoginSliderAdapter extends SliderViewAdapter<LoginSliderAdapter.MySlider>{

    Context context;
    private ArrayList<SliderClass> sliderClass;

    public LoginSliderAdapter(Context context,ArrayList<SliderClass> sliderClass) {
        this.context = context;
        this.sliderClass = sliderClass;
    }

    @Override
    public MySlider onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_slider, parent,false);
        return new MySlider(view);
    }

    @Override
    public void onBindViewHolder(MySlider viewHolder, int position) {



        switch (position) {
            case 0:
                viewHolder.imageViewBackground.setImageResource(sliderClass.get(position).getImageId());
                break;
            case 1:
                viewHolder.imageViewBackground.setImageResource(sliderClass.get(position).getImageId());
                break;

            case 2:
                viewHolder.imageViewBackground.setImageResource(sliderClass.get(position).getImageId());
                break;

        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    public class MySlider extends SliderViewAdapter.ViewHolder{
            ImageView imageViewBackground;
            View itemView;

        public MySlider(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.imgView_slider);
            this.itemView = itemView;

        }
    }


}
