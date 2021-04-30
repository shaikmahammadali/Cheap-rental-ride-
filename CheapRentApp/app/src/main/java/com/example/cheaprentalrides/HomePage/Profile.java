package com.example.cheaprentalrides.HomePage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cheaprentalrides.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class Profile extends Fragment {
    private DatabaseReference reference;
    TextView pro_name,pro_phone;
    TextInputEditText e_fullname,e_phone,e_Email,E_vehicle_number;
    MaterialButton edit,update;
    Query checkUser;
    String Full_name , phone , Email,vehicle_number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phone= HomePage.phone_userid;
        reference= FirebaseDatabase.getInstance().getReference("users");
        pro_name= view.findViewById(R.id.profile_fullname);
        pro_phone= view.findViewById(R.id.profile_Phone);
        e_fullname= view.findViewById(R.id.full_name);
        e_phone= view.findViewById(R.id.phone);
        e_Email= view.findViewById(R.id.email);
        E_vehicle_number= view.findViewById(R.id.Vehicle_Number);
        checkUser=reference.orderByChild("phone").equalTo(phone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Full_name=snapshot.child(phone).child("name").getValue(String.class);
                    pro_name.setText(Full_name);
                    pro_phone.setText(phone);
                    e_fullname.setText(Full_name);
                    e_phone.setText(phone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("fireDatabase",error.getMessage());

            }
        });
    }
}