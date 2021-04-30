package com.example.cheaprentalrides.HomePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.cheaprentalrides.UserLogin.Login;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
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
    MaterialButton edit,update,signout;
    Query checkUser;

    String Full_name , phone , Email,vehicle_number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View profileview =inflater.inflate(R.layout.fragment_profile, container, false);

        // getting user id from shared preference
        SharedPreferences prefs =  getActivity().getSharedPreferences("Loginid",
                Context.MODE_PRIVATE);
        phone = prefs.getString("phone", null);
        //getting Firebase refrence
        reference= FirebaseDatabase.getInstance().getReference("users");

        //hooks
        pro_name= profileview.findViewById(R.id.profile_fullname);
        pro_phone= profileview.findViewById(R.id.profile_Phone);
        e_fullname= profileview.findViewById(R.id.full_name);
        e_phone= profileview.findViewById(R.id.phone);
        e_Email= profileview.findViewById(R.id.email);
        signout=profileview.findViewById(R.id.signout);
        E_vehicle_number= profileview.findViewById(R.id.Vehicle_Number);

        // query checking
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
        //signout operation
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(),Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return profileview;
    }


}