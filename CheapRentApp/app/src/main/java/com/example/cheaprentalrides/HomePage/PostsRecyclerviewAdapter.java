package com.example.cheaprentalrides.HomePage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheaprentalrides.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class PostsRecyclerviewAdapter extends RecyclerView.Adapter<PostsRecyclerviewAdapter.PostHolder>  {

    Context ctx;
    List<PostPojo> postslist;
    DatabaseReference reference;
    String phone;

    public PostsRecyclerviewAdapter(Context ctx, List<PostPojo> postslist) {
        this.ctx = ctx;
        this.postslist = postslist;



    }




    @NonNull
    @Override
    public PostsRecyclerviewAdapter.PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_row_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostsRecyclerviewAdapter.PostHolder holder, int position) {
        holder.source.setText(postslist.get(position).getSource()+"  TO  ");
        holder.destination.setText(postslist.get(position).getDestination());
        holder.vehicle_name.setText("Vehicle : "+postslist.get(position).getVehicle_name());
        holder.vehicle_details.setText("Vehicle Detials :\n\t\t\t\t"+postslist.get(position).getVehicle_details());

        if(postslist.get(position).vehicle_type.equals("LOAD")){
            holder.load.setText("Load : "+String.valueOf(postslist.get(position).getVehicle_load())+" Tonnes");
        }
        else {
            holder.load.setText("Passengers : "+String.valueOf(postslist.get(position).getVehicle_load()));
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs =  v.getContext().getSharedPreferences("Loginid",
                        Context.MODE_PRIVATE);
                phone = prefs.getString("phone", null);

                Toast.makeText(v.getContext(), "delete", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Post Deletion");
                builder.setMessage("Are you sure to delete the Post");
                builder.setIcon(R.drawable.ic_baseline_warning_24);
                // positive button
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference=FirebaseDatabase.getInstance().getReference("users").child(phone).child("post");
                        reference.child("active").child(postslist.get(position).getPostid()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {


                                reference.child("deletedposts").child(postslist.get(position).getPostid()).
                                        setValue(postslist.get(position));

                                postslist.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(),postslist.size());
                                Toast.makeText(ctx, "Post Deleted ", Toast.LENGTH_SHORT).show();

                            }
                        });






                    }
                });
                //negative button
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();





            }
        });



    }

    @Override
    public int getItemCount() {
        return postslist.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        TextView source,destination,vehicle_name,load,date,vehicle_details;
        ImageButton delete;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            source=itemView.findViewById(R.id.source);
            destination=itemView.findViewById(R.id.destination);
            vehicle_name=itemView.findViewById(R.id.vehicle_name);
            delete=itemView.findViewById(R.id.delete);
            load=itemView.findViewById(R.id.load);
            /*date=itemView.findViewById(R.id.timeofupload);*/
            vehicle_details=itemView.findViewById(R.id.vehicle_description);



        }
    }
}
