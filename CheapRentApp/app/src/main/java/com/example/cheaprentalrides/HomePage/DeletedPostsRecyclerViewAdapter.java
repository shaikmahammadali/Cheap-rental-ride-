package com.example.cheaprentalrides.HomePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheaprentalrides.R;

import java.util.List;

public class DeletedPostsRecyclerViewAdapter extends RecyclerView.Adapter<DeletedPostsRecyclerViewAdapter.DeletedPostHolder> {
    Context ctx;
    List<PostPojo> deletedpostlist;

    public DeletedPostsRecyclerViewAdapter(Context ctx, List<PostPojo> deletedpostlist) {
        this.ctx = ctx;
        this.deletedpostlist = deletedpostlist;
    }

    
    
    
    @NonNull
    @Override
    public DeletedPostsRecyclerViewAdapter.DeletedPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeletedPostHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_row_design,parent,false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull DeletedPostsRecyclerViewAdapter.DeletedPostHolder holder, int position) {

        holder.source.setText(deletedpostlist.get(position).getSource()+"  TO  ");
        holder.destination.setText(deletedpostlist.get(position).getDestination());
        holder.vehicle_name.setText("Vehicle : "+deletedpostlist.get(position).getVehicle_name());
        holder.vehicle_details.setText("Vehicle Detials :\n\t\t\t\t"+deletedpostlist.get(position).getVehicle_details());

        if(deletedpostlist.get(position).vehicle_type.equals("LOAD")){
            holder.load.setText("Load : "+String.valueOf(deletedpostlist.get(position).getVehicle_load())+" Tonnes");
        }
        else {
            holder.load.setText("Passengers : "+String.valueOf(deletedpostlist.get(position).getVehicle_load()));
        }
        holder.delete.setVisibility(View.GONE);
        
        
    }

    @Override
    public int getItemCount() {
        return deletedpostlist.size();
    }

    public class DeletedPostHolder extends RecyclerView.ViewHolder {
        TextView source,destination,vehicle_name,load,date,vehicle_details;
        ImageButton delete;
        public DeletedPostHolder(@NonNull View itemView) {
            super(itemView);
            source=itemView.findViewById(R.id.source);
            destination=itemView.findViewById(R.id.destination);
            vehicle_name=itemView.findViewById(R.id.vehicle_name);
            load=itemView.findViewById(R.id.load);
            /*date=itemView.findViewById(R.id.timeofupload);*/
            vehicle_details=itemView.findViewById(R.id.vehicle_description);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
