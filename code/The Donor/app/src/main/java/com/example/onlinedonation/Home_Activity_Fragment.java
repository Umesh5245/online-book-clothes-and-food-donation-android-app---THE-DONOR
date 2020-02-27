package com.example.onlinedonation;


import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.jintin.mixadapter.MixAdapter;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

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
    private ProgressBar progressBar;

    private RatingBar ratingBar;
    private PopupWindow popupWindow;
    private FirebaseAuth mAuth;
    String sampleString="";
    LinearLayoutManager  layoutManager;
    private  RecyclerView recyclerViewDonation;
    private RecyclerView recyclerViewRequest;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter,adapter3,adapter4;
    FirestoreRecyclerAdapter adapter1;
    RelativeLayout relativeLayout;
    Button btnCancel;
    Button btnOk;

    final int duration = 10;
    final int pixelsToMove = 30;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Runnable SCROLLING_RUNNABLE = new Runnable() {

        @Override
        public void run() {
            recyclerViewDonation.smoothScrollBy(pixelsToMove, 0);
            mHandler.postDelayed(this, duration);
        }
    };





    public Home_Activity_Fragment() {
        // Required empty public constructor
    }




    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();


    }

    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();


    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        adapter.startListening();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container,false);


        progressBar = view.findViewById(R.id.progressBarMainActivity);


        return view;
    }







    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();





        firebaseFirestore.collection("NgoRequest").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                   int size =  task.getResult().size();
                   Log.d("size", size+" is the size");



                }


            }
        });






        mAuth = FirebaseAuth.getInstance();
        recyclerViewDonation = view.findViewById(R.id.home_sliderViewContainerDonation);


                btn_ShowNgoList = view.findViewById(R.id.home_drawable_ShowAllNgo);
        btn_ShowNgoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new NgoList(),null).commit();
            }
        });


        recyclerViewRequest = getActivity().findViewById(R.id.home_recyclerViewRequest);
        Query query = firebaseFirestore.collection("NgoRequest");
        FirestoreRecyclerOptions<UserNgoRequest> options = new FirestoreRecyclerOptions.Builder<UserNgoRequest>()
                .setQuery(query, UserNgoRequest.class)
                .build();





        adapter = new FirestoreRecyclerAdapter<UserNgoRequest, Home_Activity_Fragment.ProductViewHolder>(options) {
            @NonNull
            @Override
            public Home_Activity_Fragment.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_layout_recyclerview, parent, false);

                return new Home_Activity_Fragment.ProductViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull final Home_Activity_Fragment.ProductViewHolder holder, int position, @NonNull final UserNgoRequest model) {




                holder.ngoDesc.setText(model.getDesc());
                holder.ngoName.setText(model.getName());
                holder.ngoCity.setText(model.getCity());
                holder.dateTime.setText(model.getTime());
                holder.ratingBar1.setIsIndicator(true);
                if(model.getName().equals("My Ngo"))
                {

                    holder.ratingBar1.setRating(3.5f);
                }
                else
                {
                    holder.ratingBar1.setRating(4.4f);



                }




              holder.confirm.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                      if(holder.confirm.isChecked())
                      {



                          DateFormat df = new SimpleDateFormat("d:MM,h:mm");
                          String date = df.format(Calendar.getInstance().getTime());


                            sampleString = model.getName();
                          Map<String,String>  sMap = new HashMap<>();
                          sMap.put("desc",model.getDesc());
                          sMap.put("time",date);
                          sMap.put("item", model.getItem());
                          sMap.put("name", mAuth.getCurrentUser().getEmail());
                          sMap.put("donor", mAuth.getCurrentUser().getEmail());




//                              progressBar.setVisibility(View.VISIBLE);



                          firebaseFirestore.collection(sampleString).document(model.getTime()).set(sMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task) {


//                                      progressBar.setVisibility(View.GONE);

                                  Toast.makeText(getContext(), "Request added to your dashboard", Toast.LENGTH_SHORT).show();











//

                  firebaseFirestore.collection("NgoRequest").document(model.getName()+model.getTime()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                      @Override
                      public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                          if(task.isSuccessful())
                          {
                              String c_email = task.getResult().getString("email");
                              String c_time = task.getResult().getString("time");


                              if(c_email.equals(model.getEmail()) && c_time.equals(model.getTime()))
                              {

                                  Map<String,String> map = new HashMap<>();
                                  map.put("city", task.getResult().getString("city"));
                                  map.put("desc", task.getResult().getString("desc"));
                                  map.put("email",task.getResult().getString("email"));
                                  map.put("image", task.getResult().getString("image"));
                                  map.put("mobile", task.getResult().getString("mobile"));
                                  map.put("name", task.getResult().getString("name"));
                                  map.put("time", task.getResult().getString("time"));
                                  map.put("item", task.getResult().getString("item"));
                                  map.put("donor", mAuth.getCurrentUser().getEmail());



                                  firebaseFirestore.collection(mAuth.getCurrentUser().getEmail()).document(model.getName()+model.getTime()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                      @Override
                                      public void onComplete(@NonNull Task<Void> task) {

                                          if(task.isSuccessful())
                                          {

                                              Toast.makeText(getContext(), "item details uploaded successfully", Toast.LENGTH_SHORT).show();
                                              firebaseFirestore.collection("NgoRequest").document(model.getName()+model.getTime()).delete();


                                              Fragment currentFragment = getFragmentManager().findFragmentById(R.id.frame_layout);
                                              FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                              fragmentTransaction.detach(currentFragment);
                                              fragmentTransaction.attach(currentFragment);
                                              fragmentTransaction.commit();



                                          }


                                      }
                                  });










                              }






                          }



                      }
                  });














                              }
                          });














                      }


                      else
                      {

                          firebaseFirestore.collection(sampleString).document(model.getTime()).delete();


                      }


                  }

              });





                if(getActivity()!=null)
                {
                    Glide.with(getActivity()).load(model.getImage()).into(holder.ngoImage);
                }


            }
        };















        adapter.startListening();








        recyclerViewRequest.setHasFixedSize(true);
        recyclerViewRequest.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewRequest.setAdapter(adapter);
        recyclerViewRequest.addItemDecoration(new SimpleDividerItemDecoration(getContext()));


















//        homeSlider = getActivity().findViewById(R.id.home_sliderViewContainerDonation);

        Query query1 = firebaseFirestore.collection("TopDonation");
        FirestoreRecyclerOptions<Home_Slider_Class> options1 = new FirestoreRecyclerOptions.Builder<Home_Slider_Class>()
                .setQuery(query1, Home_Slider_Class.class)
                .build();




        adapter1 = new FirestoreRecyclerAdapter<Home_Slider_Class, Home_Activity_Fragment.ProductViewHolderDonations>(options1){

            @NonNull
            @Override
            public ProductViewHolderDonations onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_layout_slider, parent, false);

                return new Home_Activity_Fragment.ProductViewHolderDonations(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolderDonations holder, int position, @NonNull Home_Slider_Class model) {


                holder.uName.setText(model.getName());
                holder.uLocation.setText(model.getLocation());
                holder.uNgo.setText(model.getNgo());






            }
        };









        recyclerViewDonation.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDonation.setLayoutManager(layoutManager);
        recyclerViewDonation.setAdapter(adapter1);




        recyclerViewDonation.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastItem = layoutManager.findLastCompletelyVisibleItemPosition();
                if(lastItem == layoutManager.getItemCount()-1){
                    mHandler.removeCallbacks(SCROLLING_RUNNABLE);
                    Handler postHandler = new Handler();
                    postHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerViewDonation.setAdapter(null);
                            recyclerViewDonation.setAdapter(adapter1);
                            mHandler.postDelayed(SCROLLING_RUNNABLE, 2000);
                        }
                    }, 2000);
                }
            }
        });
        mHandler.postDelayed(SCROLLING_RUNNABLE, 2000);






        adapter1.startListening();
























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



    public class ProductViewHolderDonations extends RecyclerView.ViewHolder{

        ImageView itemImage;
        TextView uName;
        TextView uLocation;
        TextView uNgo;


        public ProductViewHolderDonations(View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.layout_slider_img);
            uName = itemView.findViewById(R.id.home_layout_Name);
            uLocation = itemView.findViewById(R.id.home_layout_location);
            uNgo = itemView.findViewById(R.id.home_layout_NgoName);


        }
    }





    public class ProductViewHolder extends RecyclerView.ViewHolder{
        CircleImageView ngoImage;
        TextView ngoDesc;
        TextView ngoCity;
        TextView ngoName;
        CheckBox confirm;
        TextView dateTime;
        RatingBar ratingBar1;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            ngoImage = itemView.findViewById(R.id.home_recyclerImage);
            ngoCity = itemView.findViewById(R.id.home_layout_NgoLocation);
            ngoName = itemView.findViewById(R.id.home_layout_NgoName);
            ngoDesc = itemView.findViewById(R.id.home_layout_txt_description);
            confirm = itemView.findViewById(R.id.home_layout_checkbox_confirm);
            dateTime = itemView.findViewById(R.id.ngo_requirement_datetime);
            ratingBar1 = itemView.findViewById(R.id.userGivenRating);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();
    }
}





