package com.example.cheaprentalrides.HomePage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cheaprentalrides.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Post extends Fragment {
    public TextInputEditText source, destination, load, passenger, vehicle_name, vehicle_des;
    RadioGroup rg_post_vehivle_type;
    RadioButton rb_load, rb_passengers;
    DatabaseReference reference;
    MaterialButton post,active_posts,deactive_posts;
    String str_source, str_destination, str_vehicle_type, str_vehicle_name, str_vehicle_details,phone;
    float float_vehicle_load;

    public Post() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        //hooks
        source = view.findViewById(R.id.post_source);
        destination = view.findViewById(R.id.post_destination);
        load = view.findViewById(R.id.post_Load);
        passenger = view.findViewById(R.id.post_Passenger_count);
        vehicle_name = view.findViewById(R.id.post_vehicle_name);
        vehicle_des = view.findViewById(R.id.post_vehicle_description);
        rb_load = view.findViewById(R.id.rb_load_vehicle);
        rb_passengers = view.findViewById(R.id.rb_passenger);
        active_posts=view.findViewById(R.id.active_posts);
        deactive_posts=view.findViewById(R.id.deActive_posts);
        post = view.findViewById(R.id.post);
        rg_post_vehivle_type = view.findViewById(R.id.rg_post_vehcle_type);

        //deactive posts
        deactive_posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // avtivate posts
        active_posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // getting user id from shared preference
        SharedPreferences prefs =  getActivity().getSharedPreferences("Loginid",
                Context.MODE_PRIVATE);
        phone = prefs.getString("phone", null);

        // firebase Registertaion
        reference = FirebaseDatabase.getInstance().getReference("users").child(phone).child("post").child("active").push();

        // radio group listener
        rg_post_vehivle_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Profile profile = new Profile();
                switch (checkedId) {
                    case R.id.rb_load_vehicle:
                        profile.enableEditText(load);
                        profile.disabledEditText(passenger);
                        str_vehicle_type = "LOAD";
                        break;
                    case R.id.rb_passenger:
                        profile.enableEditText(passenger);
                        profile.disabledEditText(load);
                        str_vehicle_type = "PASSENGERS";
                        break;

                }
            }
        });


        //Post data to firebase
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get data from views
                str_source = source.getText().toString().toUpperCase().replaceAll("\\s", "");
                str_destination = destination.getText().toString().toUpperCase().replaceAll("\\s", "");
                str_vehicle_name = vehicle_name.getText().toString().toUpperCase();
                str_vehicle_details = vehicle_des.getText().toString();
                if (rb_passengers.isChecked()) {
                    float_vehicle_load = Float.parseFloat(passenger.getText().toString());
                }
                else if (rb_load.isChecked())
                    float_vehicle_load = Float.parseFloat(load.getText().toString());
                if (!str_source.isEmpty()) {
                    if (!str_destination.isEmpty()) {
                        if (float_vehicle_load > 0) {
                            if (!str_vehicle_name.isEmpty()) {

                                if (!str_vehicle_details.isEmpty()) {

                                    // push data to firebase
                                    PostPojo postPojo = new PostPojo(str_source, str_destination, str_vehicle_type,
                                            float_vehicle_load, str_vehicle_name, str_vehicle_details);
                                    if ( ! reference.setValue(postPojo).isSuccessful())
                                        Toast.makeText(getActivity(), "Post gets Uploaded", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(getActivity(), " Post NOT Uploaded\n Technical Error", Toast.LENGTH_SHORT).show();


                                } else
                                    vehicle_des.setError("Fill Vehicle Details");
                            } else
                                vehicle_name.setError("Enter Vehicle Name");


                        } else
                            load.setError("Enter Load");

                    } else {
                        destination.setError("Enter Destination");
                    }
                } else
                    source.setError("Enter Source");

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}