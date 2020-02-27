package com.example.onlinedonation;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NgoList extends Fragment {


    private RecyclerView recyclerView;
    private ArrayList<NgoDetails> arrayList;
    private EditText edt_search_Ngo;
    private ArrayList<NgoDetails> temp_list;
    private EditText searchNgo;
    private ScrollView scrollView;
    MainAdapter mainAdapter;

    private boolean mScrollable = true;
    public NgoList() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ngo_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





        edt_search_Ngo = getActivity().findViewById(R.id.search_content);




        arrayList = new ArrayList<>();
        NgoDetails n1 = new NgoDetails();
        n1.setNgoImage(R.drawable.ngo1);
        n1.setNgoName("Darpan");
        n1.setNgoLocation("Gauridad/Rajkot");
        NgoDetails n2 = new NgoDetails();
        n2.setNgoImage(R.drawable.ngo2);
        n2.setNgoName("Robin Hood Army");
        n2.setNgoLocation("Indira Circle/Rajkot");
        NgoDetails n3 = new NgoDetails();
        n3.setNgoImage(R.drawable.ngo3);
        n3.setNgoLocation("Indira Circle/Rajkot");
        n3.setNgoName("Bhoomi");

        NgoDetails n4 = new NgoDetails();
        n4.setNgoImage(R.drawable.ngo4);
        n4.setNgoName("Smile Foundation");
        n4.setNgoLocation("Madhapar Chowk/Rajkot");

        NgoDetails n5 = new NgoDetails();
        n5.setNgoImage(R.drawable.ngo_1);
        n5.setNgoName("Ngo5 Foundation");
        n5.setNgoLocation("Madhapar Chowk/Rajkot");

        NgoDetails n6 = new NgoDetails();
        n6.setNgoImage(R.drawable.ngo_2);
        n6.setNgoName("Ngo6 Foundation");
        n6.setNgoLocation("Madhapar Chowk/Rajkot");

        NgoDetails n7 = new NgoDetails();
        n7.setNgoImage(R.drawable.ngo_3);
        n7.setNgoName("Ngo7 Foundation");
        n7.setNgoLocation("Madhapar Chowk/Rajkot");

        NgoDetails n8 = new NgoDetails();
        n8.setNgoImage(R.drawable.ngo_3);
        n8.setNgoName("Smile Foundation");
        n8.setNgoLocation("Madhapar Chowk/Rajkot");

        NgoDetails n9 = new NgoDetails();
        n9.setNgoImage(R.drawable.ngo_4);
        n9.setNgoName("Ngo4 Foundation");
        n9.setNgoLocation("Madhapar Chowk/Rajkot");

        NgoDetails n10 = new NgoDetails();
        n10.setNgoImage(R.drawable.ngo_5);
        n10.setNgoName("Ngo5 Foundation");
        n10.setNgoLocation("Madhapar Chowk/Rajkot");

        arrayList.add(n1);
        arrayList.add(n2);
        arrayList.add(n3);
        arrayList.add(n4);
        arrayList.add(n5);
        arrayList.add(n6);
        arrayList.add(n7);
        arrayList.add(n8);
        arrayList.add(n9);
        arrayList.add(n10);

        temp_list = new ArrayList<>(arrayList);





        recyclerView = getActivity().findViewById(R.id.recycler_View);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setNestedScrollingEnabled(true);

        mainAdapter = new MainAdapter(arrayList, getActivity());



        searchNgo = view.findViewById(R.id.search_content);
        searchNgo.addTextChangedListener(new TextWatcher() {
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


                ArrayList<NgoDetails> filterdNames = new ArrayList<>();



                if(text.length()== 0 || text == null)
                {
                    filterdNames.addAll(temp_list);

                }

                else
                {


                    for (NgoDetails details : temp_list) {
                        //if the existing elements contains the search input



                        if (details.getNgoName().toLowerCase().contains(text.toLowerCase())) {
                            //adding the element to filtered list
                            filterdNames.add(details);
                            arrayList.clear();

                        }
                    }


                    arrayList.addAll(filterdNames);

                }


                mainAdapter.filter(filterdNames);


            }




        });






        mainAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainAdapter);




    }
}
