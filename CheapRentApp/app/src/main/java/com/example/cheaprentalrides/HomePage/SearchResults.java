package com.example.cheaprentalrides.HomePage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cheaprentalrides.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SearchResults extends Fragment {

    DatabaseReference reference;
    RecyclerView searchrecyclerview;
    PostPojo postPojo;
    List<PostPojo> postlist;
    String str_vehicle_type,str_source,str_destination;
    float loddage;
    public SearchResults() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search_results, container, false);
        searchrecyclerview=view.findViewById(R.id.search_recyclerview);
        postlist=new ArrayList<>();

        //get data from search fragment
        str_source=getArguments().getString("source");
        str_destination=getArguments().getString("destination");
        str_vehicle_type=getArguments().getString("vehicletype");
        loddage=getArguments().getFloat("load");

        //fetching data from firebase
        postlist=new ArrayList<PostPojo>();
        reference= FirebaseDatabase.getInstance().getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds:snapshot.getChildren()){


                    reference.child(ds.getKey()).child("post").
                            child("active").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                postPojo=dataSnapshot.getValue(PostPojo.class);
                                if (postPojo.getSource().equals(str_source) && postPojo.getDestination().equals(str_destination)
                                        &&postPojo.getVehicle_type().equals(str_vehicle_type) &&postPojo.getVehicle_load()>=loddage ){

                                    postlist.add(postPojo);


                                }


                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //setting fetched data to recyclerview adapter
        Search_MyRecyclerviewAdapter searchadapter=new Search_MyRecyclerviewAdapter(getActivity(),postlist);
        searchrecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));
        searchrecyclerview.setAdapter(searchadapter);




        // Inflate the layout for this fragment
        return view;
    }
}