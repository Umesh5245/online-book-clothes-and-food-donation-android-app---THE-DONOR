package com.example.onlinedonation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Slider_Ngo_Adapter extends SliderViewAdapter<Home_Slider_Ngo_Adapter.MyAdapter> {

    private Context context;
    private ArrayList<Home_Slider_Ngo_Class> arrayList;

    public Home_Slider_Ngo_Adapter(Context context, ArrayList<Home_Slider_Ngo_Class> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_layout_slider_ngo,parent,false);

        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter viewHolder, int position) {

        viewHolder.imageView.setImageResource(arrayList.get(position).getNgo_Logo());
        viewHolder.ngoName.setText(arrayList.get(position).getNgo_Name());


    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public class MyAdapter extends SliderViewAdapter.ViewHolder{
    CircleImageView imageView;
    TextView ngoName;
        public MyAdapter(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.home_layout_slider_ngo_logo);
            ngoName = itemView.findViewById(R.id.home_layout_slider_ngo_name);

        }
    }
}
