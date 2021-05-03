package com.example.cheaprentalrides.HomePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cheaprentalrides.R;


public class search_results extends Fragment {



    public search_results() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search_results, container, false);

        // Inflate the layout for this fragment
        return view;
    }
}