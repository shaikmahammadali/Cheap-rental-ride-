package com.example.cheaprentalrides.UserLogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cheaprentalrides.HomePage.HomePage;
import com.example.cheaprentalrides.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    TextInputEditText name, phone, otp;
    public FirebaseDatabase rootnode;
    TextView textView_phone, textView_otp, sendotp_btn;
    public DatabaseReference reference;
    MaterialButton login;
    Button home;
    String person_name, String_phone, String_otp;
    String Verficationcodebysystem, verficationcode;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent =new Intent(this,HomePage.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.Full_name);
        phone = findViewById(R.id.Phone);
        otp = findViewById(R.id.otp);
        sendotp_btn = findViewById(R.id.sentotp);
        login = findViewById(R.id.login);
        textView_phone = findViewById(R.id.tv_phone);
        textView_otp = findViewById(R.id.tv_otp);
        home=findViewById(R.id.homepage);
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("users");

        //homepage navigation
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names,phones;
                names=name.getEditableText().toString();
                phones=phone.getEditableText().toString();
                LoginHelper loginHelper =new LoginHelper(names,phones);
                reference.child(phones).setValue(loginHelper);
                reference.child(phones).child("email").setValue("alishaik648@gmail.com");
                startActivity(new Intent(Login.this, HomePage.class));
            }
        });

        //save users data in firebase
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otp.getText().toString();
                if (code.isEmpty() || code.length() < 6) {
                    otp.setError("Wrong OTP....");
                    otp.requestFocus();
                    return;
                }
                verifyCode(code);
                Intent intent = new Intent(Login.this, HomePage.class);
                startActivity(intent);
            }
        });
        sendotp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                person_name = name.getEditableText().toString();
                String_phone = phone.getEditableText().toString();
                //empty space removal
                String_phone = String_phone.replaceAll("\\s", "");
                if (String_phone.length() == 10 && (String_phone.charAt(0) == '9' | String_phone.charAt(0) == '8' |
                        String_phone.charAt(0) == '7' | String_phone.charAt(0) == '6')) {
                    String_otp = otp.getEditableText().toString();
                    SendVerificationToUser(String_phone);
                    textView_phone.setTextColor(Color.GREEN);
                    textView_phone.setText("Valid Phone Number");
                } else {
                    textView_phone.setText("Invalid Phone Number");
                    textView_phone.setTextColor(Color.RED);
                }

            /*Intent intent =new Intent(getApplicationContext(), PlaceholderFragment.class);
            startActivity(intent)*/


            }
        });


    }

    private void SendVerificationToUser(String phone) {
        String phonenumber = "+91" + phone;
       /* PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phone,
               90,TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD ,mcallback );*/

      /*  PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber,60,
                TimeUnit.SECONDS, this,mcallback);*/
        mAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phonenumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mcallback)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        mAuth.setLanguageCode("fr");
        Log.i("mobilenumber", "+91" + phone);


    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            textView_otp.setText(e.getMessage());
            textView_otp.setTextColor(Color.RED);

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Verficationcodebysystem = s;
            textView_otp.setText("OTP sent Valid upto 5 Min");
            textView_otp.setTextColor(Color.GREEN);
        }
    };

    private void verifyCode(String verificationcode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(Verficationcodebysystem, verificationcode);
        signInTheUserByCredential(credential);

    }

    private void signInTheUserByCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            textView_otp.setText("OTP Verified Successfully");
                            LoginHelper userdetails=new LoginHelper(person_name,String_phone);
                            reference.child(String_phone).setValue(userdetails);
                            save_login_session(String_phone);
                            Intent intent = new Intent(Login.this, HomePage.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            textView_phone.setTextColor(Color.RED);
                        }
                    }
                });
    }

    private void save_login_session(String phone){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("Loginid",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phone", phone);
        editor.apply();
    }


}

