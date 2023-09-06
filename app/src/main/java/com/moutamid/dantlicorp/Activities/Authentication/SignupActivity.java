package com.moutamid.dantlicorp.Activities.Authentication;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {
    boolean isCard;
    private Uri ImageUri;
    ImageView profile_pic;
    Calendar myCalendar = Calendar.getInstance();
    EditText name, dob, email, password, confirm_password, phone_number, cnic;
    String image_str, image_profile_str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        profile_pic = findViewById(R.id.profile_pic);
        initComponent();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            dob.setText(sdf.format(myCalendar.getTime()));
        };

        dob.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
    }

    public void initComponent() {
        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        phone_number = findViewById(R.id.phone_number);
        cnic = findViewById(R.id.cnic_number);
    }

    public void login(View view) {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
    }

    public void sign_up(View view) {
        if (validation()) {
            registerRequest();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK && data != null) {
                ImageUri = result.getUri();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(ImageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                image_profile_str = Config.compressImage(ImageUri + "", this);
                profile_pic.setImageURI(ImageUri);
            }
        }
    }



    private void registerRequest() {
        Config.showProgressDialog(SignupActivity.this);
//        JSONObject parameters = new JSONObject();
//        try {
//            parameters.put("name", name.getText().toString());
//            parameters.put("birth_date", dob.getText().toString());
//            parameters.put("email", email.getText().toString());
//            parameters.put("phone", phone_number.getText().toString());
//            parameters.put("password", password.getText().toString());
//             parameters.put("cnic", cnic.getText().toString());
//            parameters.put("profile_pic", image_profile_str);
//            Log.d("TAG", "Params: " + parameters);
//
//            ApiRequest.Call_Api(Request.Method.POST, SignupActivity.this, Config.REGISTER, parameters, new com.moutamid.DANTLI CORP.Networking.Callback() {
//                @Override
//                public void Responce(String resp) {
//                    Config.dismissProgressDialog();
//                    Log.d("TAG", "Signup: " + resp);
////                    Config.showToast(SignupActivity.this, resp);
//                    if (!resp.isEmpty()) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(resp);
//                            String success = jsonObject.getString("success");
//                            if (success.equals("true")) {
//                                Config.alertDialogue(SignupActivity.this, "Successfully Registered, PLease Verify to Login", true);
//                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
//                                finish();
//                            } else {
//                                Config.alertDialogue(SignupActivity.this, jsonObject.getString("message"), false);
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        Config.showToast(SignupActivity.this, "Something went wrong");
//                    }
//                }
//            });
//
//        } catch (JSONException e) {
//            Config.dismissProgressDialog();

//            e.printStackTrace();
//        }
    }

    private boolean validation() {
        if (image_profile_str == null || image_profile_str.isEmpty()) {
            Config.showToast(this, "Please select your profile picture");
            return false;

        } else if (name.getText().toString().isEmpty()) {
            name.setError("Enter Name");
            name.requestFocus();
            Config.openKeyboard(this);

            return false;
        } else if (dob.getText().toString().isEmpty()) {
            dob.setError("Select Date of Birth");
            dob.requestFocus();
            Config.openKeyboard(this);

            return false;

        } else if (password.getText().toString().isEmpty()) {
            password.setError("Enter Password");
            password.requestFocus();
            Config.openKeyboard(this);

            return false;

        } else if (phone_number.getText().toString().isEmpty()) {
            phone_number.setError("Enter Phone Number");
            phone_number.requestFocus();
            Config.openKeyboard(this);

            return false;

        } else if (cnic.getText().toString().isEmpty()) {
            cnic.setError("Enter CNIC");
            cnic.requestFocus();
            Config.openKeyboard(this);
            return false;

        } else if (image_str == null || image_str.isEmpty()) {
            Config.showToast(this, "Please select your CNIC picture");
            return false;

        } else if (!Config.isNetworkAvailable(this)) {
            Config.showToast(this, "You are not connected to network");
            return false;
        } else {
            return true;
        }
    }

    public void profile_image(View view) {

        Dexter.withActivity(SignupActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        isCard = false;
                        CropImage.activity().setAspectRatio(4, 4)
                                .setRequestedSize(500, 500)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(SignupActivity.this);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }

                }).check();

    }
}