package com.example.cheaprentalrides.HomePage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.cheaprentalrides.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class Search_MyRecyclerviewAdapter extends RecyclerView.Adapter<Search_MyRecyclerviewAdapter.MyViewHolder>  {
    
    Context ctx;
    List<PostPojo> postlist;
    List<SlideModel> slideModels;
    String phone;
    private int REQUEST_CODE = 1;

    public Search_MyRecyclerviewAdapter(Context ctx, List<PostPojo> postlist) {
        this.ctx = ctx;
        this.postlist = postlist;
        slideModels=new ArrayList<>();
    }

    @NonNull
    @Override
    public Search_MyRecyclerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.search_row_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Search_MyRecyclerviewAdapter.MyViewHolder holder, int position) {
        phone=postlist.get(position).phone;
        holder.source.setText(postlist.get(position).getSource()+"  TO  ");
        holder.destination.setText(postlist.get(position).getDestination());
        holder.vehicle_name.setText("Vehicle : "+postlist.get(position).getVehicle_name());
        holder.vehicle_details.setText("Vehicle Detials :\n\t\t\t\t"+postlist.get(position).getVehicle_details());

        if(postlist.get(position).vehicle_type.equals("LOAD")){
            holder.load.setText("Load : "+String.valueOf(postlist.get(position).getVehicle_load())+" Tonnes");
        }
        else {
            holder.load.setText("Passengers : "+String.valueOf((int)postlist.get(position).getVehicle_load()));
        }
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ctx.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone)));

            }
        });

        FirebaseDatabase.getInstance().getReference().child("users").child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild("vehiclephotoes")) {
                    slideModels.clear();

                    for (DataSnapshot ds : snapshot.child("vehiclephotoes").getChildren()) {

                        slideModels.add(new SlideModel(ds.getValue(String.class), "Vehicle Photoes"));
                    }
                    holder.imageSlider.setImageList(slideModels, true);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



    @Override
    public int getItemCount() {
        return postlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView source,destination,vehicle_name,load,date,vehicle_details;
        ImageButton call;
        ImageSlider imageSlider;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            source=itemView.findViewById(R.id.source);
            destination=itemView.findViewById(R.id.destination);
            vehicle_name=itemView.findViewById(R.id.vehicle_name);
            load=itemView.findViewById(R.id.load);
            /*date=itemView.findViewById(R.id.timeofupload);*/
            vehicle_details=itemView.findViewById(R.id.vehicle_description);
            imageSlider=itemView.findViewById(R.id.imageslider);
            call=itemView.findViewById(R.id.call);




            
        }

    }
}
