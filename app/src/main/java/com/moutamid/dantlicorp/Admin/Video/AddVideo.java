package com.moutamid.dantlicorp.Admin.Video;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddVideo extends AppCompatActivity {
    private static final int PICK_IMAGE_GALLERY = 111;
    ImageView video_thumbnail;
    private Uri uri = null;
    EditText edt_url;
    TextView upload_video;
    String thumbnail = "";
    String url = "";
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        video_thumbnail = findViewById(R.id.video_thumbnail);
        edt_url = findViewById(R.id.edt_url);
        upload_video = findViewById(R.id.upload_video);
//          intent.putExtra("url", url);
//            intent.putExtra("thumbnail", thumbnail);
//
        if (!getIntent().getStringExtra("url").equals("")) {
            edt_url.setText(getIntent().getStringExtra("url"));
            url = getIntent().getStringExtra("url");

        }
        if (!getIntent().getStringExtra("thumbnail").equals("")) {
            thumbnail = getIntent().getStringExtra("thumbnail");
            Glide.with(AddVideo.this).load(thumbnail).into(video_thumbnail);
        }
        if (!getIntent().getStringExtra("key").equals("")) {
            key = getIntent().getStringExtra("key");
        }
        video_thumbnail.setOnClickListener(v -> image_Select());
        upload_video.setOnClickListener(v -> {
            if (edt_url.getText().toString().isEmpty()) {
                edt_url.setError("Please Enter");
            } else {
                if (Config.isNetworkAvailable(AddVideo.this)) {
                    uploadvideo();
                } else {
                    Toast.makeText(AddVideo.this, "No network connection available", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK) {
            uri = data.getData();
            video_thumbnail.setImageURI(uri);
            video_thumbnail.setVisibility(View.VISIBLE);
        }
    }

    public void uploadvideo() {
        if (!key.equals("")) {
            Config.showProgressDialog(AddVideo.this);
            if (uri == null) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("thumbnail", "" + thumbnail);
                hashMap.put("key", key);
                hashMap.put("url", edt_url.getText().toString());
                Constants.VideosReference.child(key).setValue(hashMap)
                        .addOnSuccessListener(aVoid -> {
                            Config.dismissProgressDialog();
                            Toast.makeText(AddVideo.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Config.dismissProgressDialog();
                            Toast.makeText(AddVideo.this, "Please try again", Toast.LENGTH_SHORT).show();
                        });

            } else {
                Config.showProgressDialog(AddVideo.this);
                String filePathName = "videos/";
                final String timestamp = "" + System.currentTimeMillis();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName + timestamp);
                UploadTask urlTask = storageReference.putFile(uri);
                Task<Uri> uriTask = urlTask.continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return storageReference.getDownloadUrl();
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri downloadImageUri = task.getResult();
                        if (downloadImageUri != null) {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("thumbnail", "" + downloadImageUri);
                            hashMap.put("key", key);
                            hashMap.put("url", edt_url.getText().toString());
                           Constants.VideosReference.child(key).setValue(hashMap)
                                    .addOnSuccessListener(aVoid -> {
                                        Config.dismissProgressDialog();
                                        Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Config.dismissProgressDialog();
                                        Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    }

                }).addOnFailureListener(e -> {
                    Config.dismissProgressDialog();
                    Toast.makeText(this, "Failed to upload", Toast.LENGTH_SHORT).show();
                });
            }
        }
        else if (uri != null) {
            Config.showProgressDialog(AddVideo.this);
            String filePathName = "videos/";
            final String timestamp = "" + System.currentTimeMillis();

            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName+timestamp);
            UploadTask urlTask = storageReference.putFile(uri);
            Task<Uri> uriTask = urlTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return storageReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadImageUri = task.getResult();
                    if (downloadImageUri != null) {
                        String key =Constants.VideosReference.push().getKey();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("thumbnail", "" + downloadImageUri);
                        hashMap.put("key", key);
                        hashMap.put("url", edt_url.getText().toString());
                        Constants.VideosReference.child(key).setValue(hashMap)
                                .addOnSuccessListener(aVoid -> {
                                    Config.dismissProgressDialog();
                                    Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Config.dismissProgressDialog();
                                    Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
                                });
                    }
                }

            }).addOnFailureListener(e -> {
                Config.dismissProgressDialog();
                Toast.makeText(this, "Failed to upload", Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "Please select Image", Toast.LENGTH_SHORT).show();
        }

    }

    public void image_Select() {
        Dexter.withActivity(AddVideo.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

}