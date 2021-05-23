
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
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomePage extends AppCompatActivity {

    private final int ID_SEARCH=0,ID_POST=1,ID_PROFILE=2;
    private String []tabs={"SEARCH","POST","PROFILE"};
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment;
    public HomePage() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        chipNavigationBar=findViewById(R.id.chipnavigation);

        //default fragment
        chipNavigationBar.setItemSelected(R.id.srch,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Search()).commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.srch : // when id is 0 Initilize search fragment
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Search()).commit();
                        break;
                    case R.id.pst : // when id is 1 Initilize post fragment
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Post()).commit();
                        break;
                    case R.id.profle : // when id is 2 Initilize Profile fragment
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Profile()).commit();
                        break;

                }

            }
        });
    }


}

