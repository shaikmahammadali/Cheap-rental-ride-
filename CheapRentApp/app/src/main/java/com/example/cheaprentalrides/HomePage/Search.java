package com.example.cheaprentalrides.HomePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.cheaprentalrides.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


public class Search extends Fragment {

    private TextInputEditText source,destination,load,passenger_count;
    private RadioGroup rg_search_vehicle_type;
    private MaterialButton search;
    public Search() {
        //constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);

        //hooks
        source=view.findViewById(R.id.search_source);
        destination=view.findViewById(R.id.search_destination);
        load=view.findViewById(R.id.search_Load);
        passenger_count=view.findViewById(R.id.search_Passenger_count);
        rg_search_vehicle_type =view.findViewById(R.id.rg_search_vehcle_type);
        search=view.findViewById(R.id.search);
        rg_search_vehicle_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Profile profile=new Profile();
                switch (checkedId){
                    case R.id.rb_load_vehicle:
                        profile.disabledEditText(passenger_count);
                        profile.enableEditText(load);
                        break;
                    case R.id.rb_passenger :
                        profile.enableEditText(passenger_count);
                        profile.disabledEditText(load);
                        break;

                }
            }
        });

        // onclick listener
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get data from fields


            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}