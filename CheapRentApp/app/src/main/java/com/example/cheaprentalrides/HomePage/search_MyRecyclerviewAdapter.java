package com.example.cheaprentalrides.HomePage;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class search_MyRecyclerviewAdapter extends RecyclerView.Adapter<search_MyRecyclerviewAdapter.MyViewHolder> {

    @NonNull
    @Override
    public search_MyRecyclerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull search_MyRecyclerviewAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
