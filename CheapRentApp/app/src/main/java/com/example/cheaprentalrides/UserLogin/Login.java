package com.example.cheaprentalrides.UserLogin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cheaprentalrides.R;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    TextInputEditText name,phone,otp;
    FirebaseDatabase rootnode;
    TextView textView_phone,textView_otp;
    DatabaseReference reference;
    MaterialButton login;
    String Verficationcodebysystem;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=findViewById(R.id.Full_name);
        phone=findViewById(R.id.Phone);
        otp=findViewById(R.id.otp);
        login=findViewById(R.id.login);
        textView_phone=findViewById(R.id.tv_phone);
        textView_otp=findViewById(R.id.tv_otp);
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
            SendVerificationToUser(String_phone);


            LoginHelper user_data=new LoginHelper(person_name,String_phone,String_otp);
            reference.child(String_phone).setValue(user_data);
            /*Intent intent =new Intent(getApplicationContext(), PlaceholderFragment.class);
            startActivity(intent)*/


            }
        });


    }

    private void SendVerificationToUser(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phone,
               5,TimeUnit.MINUTES, (Activity) TaskExecutors.MAIN_THREAD,mcallback );


    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Verficationcodebysystem=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if(code!=null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            textView_otp.setText(e.getMessage());

        }
    }

    private void verifyCode(String verificationcode) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(Verficationcodebysystem,verificationcode);
        signInTheUserByCredential(credential);
    }

    private void signInTheUserByCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener()
    }


}

