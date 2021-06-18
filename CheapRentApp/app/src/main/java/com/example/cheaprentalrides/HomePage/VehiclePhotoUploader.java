package com.example.cheaprentalrides.HomePage;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.cheaprentalrides.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class VehiclePhotoUploader extends Fragment {
    ImageView vehiclephotoadder;
    List<Uri> photosUri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ImageSlider imageSlider;
    TextView uploadingStatus;
    List<SlideModel> slideModels;
    ProgressBar progressBar;
    private String phone;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uploadingStatus.setVisibility(View.VISIBLE);

        if (requestCode == 4 && resultCode == getActivity().RESULT_OK) ;
        {
            slideModels.clear();
            photosUri = new ArrayList<>();
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    try {
                        InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
                        BitmapFactory.decodeStream(inputStream);
                        uploadToFirebase(imageUri);
                        slideModels.add(new SlideModel(imageUri.toString(), "Vehicle Photoes"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                imageSlider.setImageList(slideModels, true);


            } else {
                Uri imageUri = data.getData();
                slideModels.clear();
                if (imageUri == null) {
                    Toast.makeText(getContext(), "Select any images", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
                        BitmapFactory.decodeStream(inputStream);
                        uploadToFirebase(imageUri);
                        slideModels.add(new SlideModel(imageUri.toString(), "Vehicle Photoes"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageSlider.setImageList(slideModels, true);

                }

            }


        }
        uploadingStatus.setText("Vola... Images Uploaded Sucessfully..");
        uploadingStatus.setTextColor(Color.GREEN);

    }

    //upload image to firebasedatabase and firebase storage
    private void uploadToFirebase(Uri selectedImageUri) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(selectedImageUri));
        // delete old vehicle photoes
        deleteVehiclePhotoes();

        fileRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        progressBar.setVisibility(View.INVISIBLE);
                        databaseReference.push().setValue(uri.toString());
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Image Uploading Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private String getFileExtension(Uri selectImageURI) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(selectImageURI));
    }

    private void deleteVehiclePhotoes() {
        FirebaseDatabase.getInstance().getReference().child("users").child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("vehiclephotoes")) {

                    for (DataSnapshot ds : snapshot.child("vehiclephotoes").getChildren()) {

                        StorageReference photoreference = FirebaseStorage.getInstance().
                                getReferenceFromUrl(ds.getValue(String.class));
                        photoreference.delete();
                        ds.getRef().removeValue();
                    }
                    Toast.makeText(getContext(), "Old Image deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_photo_uploader, container, false);
        imageSlider = view.findViewById(R.id.imageslider);
        uploadingStatus = view.findViewById(R.id.txt_uploadstatus);
        slideModels = new ArrayList<>();
        vehiclephotoadder = view.findViewById(R.id.vehicle_photoes);
        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);


        //get userid as phone number from shared preferences
        SharedPreferences prefs = getActivity().getSharedPreferences("Loginid",
                Context.MODE_PRIVATE);
        phone = prefs.getString("phone", null);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(phone).child("vehiclephotoes");
        storageReference = FirebaseStorage.getInstance().getReference().child("users").child(phone).child("vehiclephotoes");

        vehiclephotoadder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                } else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setType("image/*");
                    startActivityForResult(intent, 4);

                }
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
                    imageSlider.setImageList(slideModels, true);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {

            }
        });



        /*for (int i=0;i<photosUri.size();i++){
            slideModels.add(new SlideModel(photosUri.get(i).toString(),"Vehicle Photoes"));
        }*/


        // Inflate the layout for this fragment
        return view;
    }
}