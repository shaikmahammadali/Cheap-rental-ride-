package com.example.cheaprentalrides.HomePage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.cheaprentalrides.R;
import com.google.android.material.tabs.TabLayout;

public class HomePage extends AppCompatActivity {
    /*private ViewPager viewPager;
    private TabLayout tabLayout;*/
    private final int ID_SEARCH=0,ID_POST=1,ID_PROFILE=2;
    private String []tabs={"SEARCH","POST","PROFILE"};
    MeowBottomNavigation bottomNavigation;

    public HomePage() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        /*tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);
        SharedPreferences prefs =  getApplicationContext().getSharedPreferences("Loginid",
                Context.MODE_PRIVATE);
        phone_userid = prefs.getString("phone", null);
        *//*Log.i("number",phone_userid);*//*
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);*/
        bottomNavigation=findViewById(R.id.botton_navigator);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_SEARCH,R.drawable.ic_baseline_search_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_POST,R.drawable.ic_baseline_add_circle_24));
        bottomNavigation.add((new MeowBottomNavigation.Model(ID_PROFILE,R.drawable.ic_baseline_person_24)));
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //fragment initilization
                Toast.makeText(HomePage.this, tabs[item.getId()], Toast.LENGTH_SHORT).show();
                Fragment fragment=null;

                //check condition
                switch (item.getId()){
                    case ID_SEARCH : // when id is 0 Initilize search fragment
                        fragment=new Search();
                        break;
                    case ID_POST : // when id is 1 Initilize post fragment
                        fragment=new Post();
                        break;
                    case ID_PROFILE : // when id is 2 Initilize Profile fragment
                        fragment =new Profile();
                        break;
                }
                loadFragment(fragment);


            }
        });
        //set search fragment initially selected
        bottomNavigation.show(ID_SEARCH,true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(HomePage.this, tabs[item.getId()], Toast.LENGTH_SHORT).show();
                Fragment fragment=null;

                //check condition
                switch (item.getId()){
                    case ID_SEARCH : // when id is 0 Initilize search fragment
                        fragment=new Search();
                        break;
                    case ID_POST : // when id is 1 Initilize post fragment
                        fragment=new Post();
                        break;
                    case ID_PROFILE : // when id is 2 Initilize Profile fragment
                        fragment =new Profile();
                        break;
                }
                loadFragment(fragment);

            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(HomePage.this, "You Reselected "+tabs[item.getId()] , Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadFragment(Fragment fragment) {
        //replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                 .replace(R.id.framelayout,fragment).commit();

    }
}

