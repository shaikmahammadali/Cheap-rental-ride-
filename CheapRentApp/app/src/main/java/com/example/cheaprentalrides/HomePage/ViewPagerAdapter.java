package com.example.cheaprentalrides.HomePage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

@SuppressWarnings("deprecation")
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :return new Search();
            case 1: return  new Post();
            case 2 :return  new Profile();
        }
        return null;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0 : return "Search";
            case 1: return "Post";
            case 2 : return  "Profile";
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
