package com.example.cheaprentalrides.HomePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.cheaprentalrides.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Post extends Fragment {
    TextInputEditText source,destination,load,passenger,vehicle_name,vehicle_des;
    RadioGroup rb_post_vehivle_type;
    DatabaseReference reference;
    MaterialButton post;
    public Post() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_post, container, false);
        source=view.findViewById(R.id.post_source);
        destination=view.findViewById(R.id.post_destination);
        load=view.findViewById(R.id.post_Load);
        passenger=view.findViewById(R.id.post_Passenger_count);
        vehicle_name=view.findViewById(R.id.post_vehicle_name);
        vehicle_des=view.findViewById(R.id.post_vehicle_description);
        post=view.findViewById(R.id.post);
        rb_post_vehivle_type=view.findViewById(R.id.rg_post_vehcle_type);
        rb_post_vehivle_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Profile profile =new Profile();
                switch (checkedId){
                    case R.id.rb_load_vehicle :
                        profile.enableEditText(load);
                        profile.disabledEditText(passenger);
                        break;
                    case R.id.rb_passenger :
                        profile.enableEditText(passenger);
                        profile.disabledEditText(load);
                        break;

                }
            }
        });

        /*//firebase registeration
        reference= FirebaseDatabase.getInstance().getReference("user");*/

       //Post data to firebase
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}