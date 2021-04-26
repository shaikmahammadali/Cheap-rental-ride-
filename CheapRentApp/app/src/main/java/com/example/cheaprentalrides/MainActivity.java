package com.example.cheaprentalrides;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private  static int splash_screen=4000;
    //variables
    Animation topAnim,bottomAnim;
    ImageView van,traveller,tempo,car;
    TextView appname,appCaption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spalsh_screen);

        //Animation
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_aninmation);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        van=findViewById(R.id.van);
        traveller=findViewById(R.id.traveller);
        tempo=findViewById(R.id.tempo);
        car=findViewById(R.id.car);
        appname=findViewById(R.id.appname);
        appCaption=findViewById(R.id.namecaption);


        van.setAnimation(topAnim);
        traveller.setAnimation(topAnim);
        tempo.setAnimation(topAnim);
        car.setAnimation(topAnim);
        appname.setAnimation(bottomAnim);
        appCaption.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,Login.class));
                finish();
            }
        },splash_screen);
    }
}