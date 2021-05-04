package com.example.cheaprentalrides.HomePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.regex.Pattern;


public class Profile extends Fragment {
    private DatabaseReference reference;
    TextView pro_name,pro_phone;
    TextInputEditText e_fullname,e_phone,e_Email,E_vehicle_number;
    MaterialButton edit,update,signout;
    Query checkUser;
    String Full_name ,phone, Email,vehicle_number;

    public Profile() {
    }

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
        edit=profileview.findViewById(R.id.profile_edit);
        update=profileview.findViewById(R.id.profile_update);
        Email=e_Email.getText().toString();
        vehicle_number=E_vehicle_number.getText().toString();

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
                    if(snapshot.child(phone).child("email").exists())
                        e_Email.setText(snapshot.child(phone).child("email").getValue(String.class));
                    else
                        e_Email.setText("");
                    if (snapshot.child(phone).child("vehicle_number").exists())
                        E_vehicle_number.setText(snapshot.child(phone).child("vehicle_number").getValue(String.class));
                    else
                        E_vehicle_number.setText("");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("fireDatabase",error.getMessage());

            }
        });

        //disable edittext
        deActive_EditText(e_fullname);
        deActive_EditText(e_phone);
        deActive_EditText(e_phone);
        deActive_EditText(e_Email);
        deActive_EditText(E_vehicle_number);

        //edit profile
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                active_EditText(e_Email);
                active_EditText(E_vehicle_number);
                active_EditText(e_fullname);
                Toast.makeText(getActivity(), "Edit Profile Now", Toast.LENGTH_SHORT).show();

            }
        });

        //update edited profile data to firebase
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=e_Email.getText().toString();
                vehicle_number=E_vehicle_number.getText().toString();
                Full_name=e_fullname.getText().toString();
                if(isEmailValid(Email)){
                    if (isVehicleNumberValid(vehicle_number)){
                        boolean email_status =reference.child(phone).child("email").setValue(Email).isSuccessful();
                        boolean v_status =reference.child(phone).child("vehicle_number").setValue(vehicle_number).isSuccessful();
                        boolean name_status=reference.child(phone).child("name").setValue(Full_name).isSuccessful();
                        if (!( email_status && v_status && name_status)){
                            Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getActivity(), "Profile NOT Updated ", Toast.LENGTH_SHORT).show();
                        deActive_EditText(e_Email);
                        deActive_EditText(E_vehicle_number);
                        deActive_EditText(e_fullname);
                    }
                    else{
                        E_vehicle_number.setError("INVALID VEHICLE");
                        active_EditText(E_vehicle_number);
                        active_EditText(e_Email);
                    }

                }
                else{
                    e_Email.setError("INVALID EMAIL FORMAT");
                    active_EditText(E_vehicle_number);
                    active_EditText(e_Email);
                }

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

        /*//disable all editText fields
        e_fullname.setFocusable(false);
        e_phone.setFocusable(false);*/

        // Inflate the layout for this fragment
        return profileview;

    }
    public void disabledEditText(TextInputEditText editText){
        /*editText.setFocusable(false);*/

        editText.setVisibility(View.INVISIBLE);


    }
    public void enableEditText(TextInputEditText editText){
        /*editText.setFocusable(true);*/

        editText.setVisibility(View.VISIBLE);
        /*editText.setBackgroundColor(Color.WHITE);*/

    }

    private void active_EditText(TextInputEditText editText){
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setFocusable(true);
    }

    private void deActive_EditText(TextInputEditText editText){
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }
    // email validation
    private boolean isEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            e_Email.setError("EMAIL IS EMPTY");
            return false;
        }
        return pat.matcher(email).matches();
    }

    //IS vehicle Number
    private boolean isVehicleNumberValid(String vehicle_Number){
        int len=vehicle_Number.length();
        if (len==10 || len==12)
            return true;
        else
            return false;

    }

    public String spaceRemove(String str){
        return str.replaceAll("\\s", "");
    }
}