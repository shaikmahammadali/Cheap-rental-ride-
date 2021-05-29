
package com.example.cheaprentalrides.HomePage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
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
        callPermissionRequest();
        storagePermissionRequest();
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

    public void callPermissionRequest(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }

    public void storagePermissionRequest(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Call Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Call permission Denied ", Toast.LENGTH_SHORT).show();
                    callPermissionRequest();

                }
                break;

            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Strorage Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Storage permission Denied ", Toast.LENGTH_SHORT).show();
                    storagePermissionRequest();

                }

        }



    }


}

