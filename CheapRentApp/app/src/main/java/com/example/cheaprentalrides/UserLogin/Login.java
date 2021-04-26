package com.example.cheaprentalrides.UserLogin;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cheaprentalrides.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    TextInputEditText name,phone,otp;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    MaterialButton login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=findViewById(R.id.Full_name);
        phone=findViewById(R.id.Phone);
        otp=findViewById(R.id.otp);
        login=findViewById(R.id.login);
        rootnode=FirebaseDatabase.getInstance();
        reference=rootnode.getReference("users");
        //save users data in firebase
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String person_name,String_phone,String_otp;
            person_name=name.getEditableText().toString();
            String_phone=phone.getEditableText().toString();
            String_otp=otp.getEditableText().toString();
            LoginHelper user_data=new LoginHelper(person_name,String_phone,String_otp);
            reference.child(String_phone).setValue(user_data);
            /*Intent intent =new Intent(getApplicationContext(), PlaceholderFragment.class);
            startActivity(intent)*/

            }
        });


    }
}

