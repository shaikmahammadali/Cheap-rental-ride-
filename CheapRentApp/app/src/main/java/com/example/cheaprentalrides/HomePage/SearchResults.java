package com.example.cheaprentalrides.HomePage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheaprentalrides.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.checkSelfPermission;


public class SearchResults extends Fragment {

    DatabaseReference reference;
    RecyclerView searchrecyclerview;
    PostPojo postPojo;
    List<PostPojo> list;
    long i=0;
    String str_vehicle_type,str_source,str_destination;
    float loddage;
    String phone;
    TextView dataStatus;
    private static final int REQUEST_CALL = 1;

    public SearchResults() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search_results, container, false);
        searchrecyclerview=view.findViewById(R.id.search_recyclerview);
        dataStatus=view.findViewById(R.id.datastatus);

        //get data from search fragment
        str_source=getArguments().getString("source");
        str_destination=getArguments().getString("destination");
        str_vehicle_type=getArguments().getString("vehicletype");
        loddage=getArguments().getFloat("load");


        /*Log.i("source",str_source);
        Log.i("load",Float.toString(loddage));*/

        list=new ArrayList<>();

        //fetching data from firebase

        reference= FirebaseDatabase.getInstance().getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds:snapshot.getChildren()){

                    /*Log.i("username",ds.getKey());*/
                    reference.child(ds.getKey()).child("post").
                            child("active").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snap) {
                            for (DataSnapshot dataSnapshot:snap.getChildren()){
                                postPojo=dataSnapshot.getValue(PostPojo.class);

                                if (postPojo.getSource().equals(str_source) && postPojo.getDestination().equals(str_destination)
                                        &&postPojo.getVehicle_type().equals(str_vehicle_type) &&postPojo.getVehicle_load()>=loddage ){
                                    Log.i("source",postPojo.source);
                                    list.add(postPojo);
                                    i++;

                                }


                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }




                    //setting fetched data to recyclerview adapter
                    Search_MyRecyclerviewAdapter searchadapter = new Search_MyRecyclerviewAdapter(getContext(),list);
                    searchrecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    searchrecyclerview.setAdapter(searchadapter);


               /* Log.i("postsize",Long.toString(list.size()));
                Toast.makeText(getActivity(), Long.toString(list.size()), Toast.LENGTH_SHORT).show();*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }








        });




        // Inflate the layout for this fragment
        return view;
    }
}