package com.example.cheaprentalrides.HomePage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cheaprentalrides.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ActivePosts extends Fragment {

   View view;
    RecyclerView recyclerView;
    private String phone;
    private DatabaseReference reference;
    private List<PostPojo> postslist;
    private PostsRecyclerviewAdapter postsRecyclerviewAdapter;

    public ActivePosts() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.fragment_active_posts, container, false);
        SharedPreferences prefs =  getActivity().getSharedPreferences("Loginid",
                Context.MODE_PRIVATE);
        phone = prefs.getString("phone", null);
        reference = FirebaseDatabase.getInstance().getReference("users").child(phone).child("post").child("active");
        recyclerView=view.findViewById(R.id.recyclerview);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postslist=new ArrayList<>();
                for (DataSnapshot ds:snapshot.getChildren()){
                    PostPojo activepst=ds.getValue(PostPojo.class);
                    postslist.add(activepst);
                }
                postsRecyclerviewAdapter=new PostsRecyclerviewAdapter(getActivity(),postslist);

                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(postsRecyclerviewAdapter);
                Toast.makeText(getActivity(), "Active posts"+postslist.size(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // Inflate the layout for this fragment
        return view;
    }
}