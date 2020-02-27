//package com.example.onlinedonation;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.smarteist.autoimageslider.SliderViewAdapter;
//
//import java.util.ArrayList;
//
//public class Home_Slider_Adapter extends SliderViewAdapter<Home_Slider_Adapter.MySlider>{
//
//    Context context;
//    ArrayList<Home_Slider_Class> arrayList;
//
//    public Home_Slider_Adapter(Context context, ArrayList<Home_Slider_Class> arrayList) {
//        this.context = context;
//        this.arrayList = arrayList;
//    }
//
//    @Override
//    public MySlider onCreateViewHolder(ViewGroup parent) {
//
//        View view = LayoutInflater.from(context).inflate(R.layout.home_layout_slider, parent,false);
//
//        return new MySlider(view);
//    }
//
//    @Override
//    public void onBindViewHolder(MySlider viewHolder, int position) {
//
//        viewHolder.p_Image.setImageResource(arrayList.get(position).getProductImage());
//        viewHolder.p_Ngo.setText(arrayList.get(position).getP_Ngo());
//        viewHolder.p_Location.setText(arrayList.get(position).getP_Location());
//        viewHolder.p_Name.setText(arrayList.get(position).getP_Name());
//
//    }
//
//    @Override
//    public int getCount() {
//        return arrayList.size();
//    }
//
//    public class MySlider extends SliderViewAdapter.ViewHolder{
//
//
//        ImageView p_Image;
//        TextView p_Name;
//        TextView p_Location;
//        TextView p_Ngo;
//
//        public MySlider(View itemView) {
//            super(itemView);
//            p_Image = itemView.findViewById(R.id.layout_slider_img);
//            p_Name = itemView.findViewById(R.id.home_layout_Name);
//            p_Location = itemView.findViewById(R.id.home_layout_location);
//            p_Ngo = itemView.findViewById(R.id.home_layout_NgoName);
//
//        }
//    }
//}
