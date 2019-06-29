package com.example.gaye.gezgin.fragments;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gaye.gezgin.R;
import com.example.gaye.gezgin.activities.AddPhotoActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private ImageView ivProfile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        ivProfile = v.findViewById(R.id.iv_profile);
        v.findViewById(R.id.iv_instagram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInstagram();
            }
        });

        v.findViewById(R.id.iv_addphoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddPhotoActivity.class);
                startActivity(i);
            }
        });

        return v;
    }

    private void showPhoto() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("userprofilephoto").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //  dismissProgressDialog();
                Picasso.with(getActivity()).load(uri).fit().centerCrop().into(ivProfile, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError() {
                        progressDialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.i("--", "-- Error: " + e.getLocalizedMessage());
                Toast.makeText(getActivity(), "Beklenmeyen bir hata oluştu." + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openInstagram() {
        Uri instagramUri = Uri.parse("https://www.instagram.com/gayedurmazz");
        Intent intent = new Intent(Intent.ACTION_VIEW, instagramUri);
        intent.setPackage("com.instagram.android");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, instagramUri));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        showPhoto();
    }
}
