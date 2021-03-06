package com.example.cheaprentalrides.HomePage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cheaprentalrides.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Search extends Fragment {

    private TextInputEditText source,destination,load,passenger_count;
    private RadioGroup rg_search_vehicle_type;
    private RadioButton rb_load,rb_passengers;
    private MaterialButton search;
    String str_vehicle_type,str_source,str_destination;
    float loddage;
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
        rb_load=view.findViewById(R.id.rb_load_vehicle);
        rb_passengers=view.findViewById(R.id.rb_passenger);
        load=view.findViewById(R.id.search_Load);
        passenger_count=view.findViewById(R.id.search_Passenger_count);
        rg_search_vehicle_type =view.findViewById(R.id.rg_search_vehcle_type);
        search=view.findViewById(R.id.search);

        /*InputMethodManager imgr=((InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(
                    InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_NOT_ALWAYS);*/



        rg_search_vehicle_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Profile profile=new Profile();
                switch (checkedId){
                    case R.id.rb_load_vehicle:
                        str_vehicle_type="LOAD";
                        profile.disabledEditText(passenger_count);
                        profile.enableEditText(load);
                        break;
                    case R.id.rb_passenger :
                        str_vehicle_type="PASSENGERS";
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

                str_source=source.getText().toString().toUpperCase().replaceAll("\\s", "");;
                str_destination=destination.getText().toString().toUpperCase().replaceAll("\\s", "");

                if (!(str_source.isEmpty())){
                    if (!(str_destination.isEmpty())){
                       if ((rb_load.isChecked()||rb_passengers.isChecked())){


                           if ((!load.getText().toString().isEmpty()||!passenger_count.getText().toString().isEmpty())){

                               if(str_vehicle_type.equals("LOAD"))
                                   loddage=Float.parseFloat(load.getText().toString());
                               else
                                   loddage=Float.parseFloat(passenger_count.getText().toString());

                               // search post here
                               SearchResults searchResults=new SearchResults();
                               Bundle bundle =new Bundle();
                               bundle.putString("source",str_source);
                               bundle.putString("destination",str_destination);
                               bundle.putString("vehicletype",str_vehicle_type);
                               bundle.putFloat("load",loddage);
                               searchResults.setArguments(bundle);

                               // inflate the fragment

                               getFragmentManager().beginTransaction().add(R.id.fragmentContainer,searchResults).commit();


                               getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,searchResults)
                                       .addToBackStack(null).commit();

                               Toast.makeText(getActivity(), "Searching...", Toast.LENGTH_SHORT).show();


                           }
                           else
                                   load.setError("Field is not be Empty");
                                   passenger_count.setError("Field is not be Empty");

                       }
                        else
                            rb_load.setError("Button is not Checked");
                    }
                    else
                        destination.setError("Field is not be Empty");
                }
                else
                    source.setError("Field is not be Empty");




            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}