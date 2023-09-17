package com.moutamid.dantlicorp.Activities.Home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moutamid.dantlicorp.Model.SocialModel;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    ImageView profile_img;
    TextView name, dob, email, phone_number, cnic_number;
    private static final int PICK_IMAGE_GALLERY = 111;
    Calendar myCalendar = Calendar.getInstance();
    Uri image_profile_str = null;
    UserModel userNew;
    EditText facebook_url_edt, twitter_url_edt, instagram_url_edt, reddit_url_edt, pinterest_url_edt, linkedIn_url_edt;
    String facebook_url_str, twitter_url_str, instagram_url_str, reddit_url_str, pinterest_url_str, linkedIn_url_str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profile_img = findViewById(R.id.profile_pic);
        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email);
        phone_number = findViewById(R.id.phone_number);
        cnic_number = findViewById(R.id.cnic_number);
        userNew = (UserModel) Stash.getObject("UserDetails", UserModel.class);
        SocialModel socialModel = (SocialModel) Stash.getObject("UserLinks", SocialModel.class);
        name.setText(userNew.name);
        dob.setText(userNew.dob);
        email.setText(userNew.email);
        phone_number.setText(userNew.phone_number);
        cnic_number.setText(userNew.cnic);

        facebook_url_edt = findViewById(R.id.facebook);
        twitter_url_edt = findViewById(R.id.twitter);
        instagram_url_edt = findViewById(R.id.instagram);
        reddit_url_edt = findViewById(R.id.raddit);
        pinterest_url_edt = findViewById(R.id.pinterest);
        linkedIn_url_edt = findViewById(R.id.linked_in);
        if (socialModel != null) {
            facebook_url_edt.setText(socialModel.facebook_url);
            twitter_url_edt.setText(socialModel.twitter_url);
            instagram_url_edt.setText(socialModel.instagram_url);
            reddit_url_edt.setText(socialModel.reddit_url);
            pinterest_url_edt.setText(socialModel.pinterest_url);
            linkedIn_url_edt.setText(socialModel.linkedIn_url);
        }
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            dob.setText(sdf.format(myCalendar.getTime()));
        };
        dob.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        Glide.with(EditProfileActivity.this).load(userNew.image_url).into(profile_img);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK) {
            image_profile_str = data.getData();
            profile_img.setImageURI(image_profile_str);
            profile_img.setVisibility(View.VISIBLE);
            Log.d("data_image", image_profile_str + "  " + Uri.parse(userNew.image_url));

        }
    }


    private void registerRequest() {

        Config.showProgressDialog(EditProfileActivity.this);
        if (image_profile_str != null) {
            String filePathName = "users/";
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName + timestamp);
            UploadTask urlTask = storageReference.putFile(image_profile_str);
            Task<Uri> uriTask = urlTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
//                    Log.d("data", image_profile_str + "  " + Uri.parse(userNew.image_url) + "  " + task.getException().getMessage());
                    Config.dismissProgressDialog();
                    Toast.makeText(EditProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                return storageReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadImageUri = task.getResult();
                    if (downloadImageUri != null) {
                        UserModel userModel = new UserModel();
                        userModel.name = name.getText().toString();
                        userModel.dob = dob.getText().toString();
                        userModel.email = email.getText().toString();
                        userModel.phone_number = phone_number.getText().toString();
                        userModel.cnic = cnic_number.getText().toString();
                        userModel.image_url = downloadImageUri.toString();
                        userModel.id = Constants.auth().getUid();

                        Constants.UserReference.child(Objects.requireNonNull(Constants.auth().getUid())).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Stash.put("UserDetails", userModel);
                                getLinks();
                            }
                        });
                    }
                }

            });
        } else {
            UserModel userModel = new UserModel();
            userModel.name = name.getText().toString();
            userModel.dob = dob.getText().toString();
            userModel.email = email.getText().toString();
            userModel.phone_number = phone_number.getText().toString();
            userModel.cnic = cnic_number.getText().toString();
            userModel.image_url = userNew.image_url;
            userModel.id = Constants.auth().getUid();
            Constants.UserReference.child(Objects.requireNonNull(Constants.auth().getUid())).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Stash.put("UserDetails", userModel);
                    getLinks();
                }
            });
        }

    }

    public void sign_up(View view) {
        if (validation()) {
            registerRequest();
        }
    }

    private boolean validation() {
        if (name.getText().toString().isEmpty()) {
            name.setError("Enter Name");
            name.requestFocus();
            Config.openKeyboard(this);

            return false;
        } else if (dob.getText().toString().isEmpty()) {
            dob.setError("Select Date of Birth");
            dob.requestFocus();
            Config.openKeyboard(this);
            return false;

        } else if (phone_number.getText().toString().isEmpty()) {
            phone_number.setError("Enter Phone Number");
            phone_number.requestFocus();
            Config.openKeyboard(this);

            return false;

        } else if (cnic_number.getText().toString().isEmpty()) {
            cnic_number.setError("Enter CNIC");
            cnic_number.requestFocus();
            Config.openKeyboard(this);
            return false;

        } else if (!Config.isNetworkAvailable(this)) {
            Config.showToast(this, "You are not connected to network");
            return false;
        } else {
            return true;
        }
    }

    public void profile_image(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);
    }

    public void getLinks() {
        if (!facebook_url_edt.getText().toString().isEmpty()) {
            facebook_url_str = facebook_url_edt.getText().toString();

        }
        if (!twitter_url_edt.getText().toString().isEmpty()) {
            twitter_url_str = twitter_url_edt.getText().toString();

        }
        if (!instagram_url_edt.getText().toString().isEmpty()) {

            instagram_url_str = instagram_url_edt.getText().toString();

        }
        if (!reddit_url_edt.getText().toString().isEmpty()) {
            reddit_url_str = reddit_url_edt.getText().toString();

        }
        if (!pinterest_url_edt.getText().toString().isEmpty()) {
            pinterest_url_str = pinterest_url_edt.getText().toString();

        }
        if (!linkedIn_url_edt.getText().toString().isEmpty()) {
            linkedIn_url_str = linkedIn_url_edt.getText().toString();
        }

        SocialModel socialModel = new SocialModel();
        socialModel.facebook_url = facebook_url_str;
        socialModel.twitter_url = twitter_url_str;
        socialModel.instagram_url = instagram_url_str;
        socialModel.reddit_url = reddit_url_str;
        socialModel.pinterest_url = pinterest_url_str;
        socialModel.linkedIn_url = linkedIn_url_str;
        Constants.UserReference.child(Constants.auth().getUid()).child("social_links").setValue(socialModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Stash.put("UserLinks", socialModel);
                Config.dismissProgressDialog();
                onBackPressed();
            }
        });
    }

    public void backPress(View view) {
        onBackPressed();
    }
}