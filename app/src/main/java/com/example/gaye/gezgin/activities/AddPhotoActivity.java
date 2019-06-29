package com.example.gaye.gezgin.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gaye.gezgin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddPhotoActivity extends AppCompatActivity {

    private static int IMAGE_REQUEST = 229;
    ImageView ivProfile;
    Uri filePath;

    private FirebaseStorage firebaseStorage;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        firebaseStorage = FirebaseStorage.getInstance();
        ivProfile = (ImageView) findViewById(R.id.iv_profile);

        showPhoto();

        findViewById(R.id.btn_selection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhoto();
            }
        });

    }

    private void showPhoto() {

        showProgressDialog();
        StorageReference storageRef = firebaseStorage.getReference();
        storageRef.child("userprofilephoto").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //  dismissProgressDialog();
                Picasso.with(AddPhotoActivity.this).load(uri).fit().centerCrop().into(ivProfile, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        dismissProgressDialog();
                    }

                    @Override
                    public void onError() {
                        dismissProgressDialog();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dismissProgressDialog();
                Log.i("--", "-- Error: " + e.getLocalizedMessage());
                Toast.makeText(AddPhotoActivity.this, "Beklenmeyen bir hata oluştu." + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void selectPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Resim Seçiniz"), IMAGE_REQUEST);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(AddPhotoActivity.this);
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    private void savePhoto() {

        if (filePath != null) {
            showProgressDialog();
            StorageReference storageRef = firebaseStorage.getReference();
            storageRef.child("userprofilephoto").putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dismissProgressDialog();
                    Toast.makeText(AddPhotoActivity.this, "Fotoğraf başarılı bir şekilde kaydedildi.", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dismissProgressDialog();
                    Toast.makeText(AddPhotoActivity.this, "Fotoğraf kaydedilemedi.", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //İstediğim aktiviteden, olumlu cevap geldiyse ve veri varsa
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Picasso.with(AddPhotoActivity.this).load(filePath).fit().centerCrop().into(ivProfile);
            } catch (Exception e) {
                Toast.makeText(AddPhotoActivity.this, "Beklenmeyen bir hata oluştu " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}
