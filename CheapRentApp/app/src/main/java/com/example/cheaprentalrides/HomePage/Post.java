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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheaprentalrides.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Post extends Fragment {
    public TextInputEditText source, destination, load, passenger, vehicle_name, vehicle_des;
    RadioGroup rg_post_vehivle_type;
    RadioButton rb_load, rb_passengers;
    DatabaseReference reference;
    MaterialButton post,active_posts,deleted_posts;
    String str_source, str_destination, str_vehicle_type, str_vehicle_name, str_vehicle_details,phone;
    float float_vehicle_load;
    RecyclerView recyclerView;
    PostsRecyclerviewAdapter postsRecyclerviewAdapter;
    List<PostPojo> postslist;

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
        post = view.findViewById(R.id.post);
        rg_post_vehivle_type = view.findViewById(R.id.rg_post_vehcle_type);
        active_posts=view.findViewById(R.id.Active_posts);
        deleted_posts=view.findViewById(R.id.deleted_posts);



        // getting user id from shared preference
        SharedPreferences prefs =  getActivity().getSharedPreferences("Loginid",
                Context.MODE_PRIVATE);
        phone = prefs.getString("phone", null);

        // firebase Registertaion
        reference = FirebaseDatabase.getInstance().getReference("users").child(phone).child("post").child("active");

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
                                    if ( ! reference.push().setValue(postPojo).isSuccessful()) {
                                        Toast.makeText(getActivity(), "Post gets Uploaded", Toast.LENGTH_SHORT).show();
                                        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Post()).commit();
                                    }
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


        //deactive posts
        deleted_posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference=FirebaseDatabase.getInstance()
                        .getReference("users").child(phone).child("post");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("deletedposts")){
                            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new DeletedPost()).commit();
                            Toast.makeText(getActivity(), "DeActivated posts", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "No Deleted Posts", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });

        // avtivate posts
        active_posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Active posts", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ActivePosts()).commit();

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}