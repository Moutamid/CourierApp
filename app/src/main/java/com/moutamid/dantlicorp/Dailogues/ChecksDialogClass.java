package com.moutamid.dantlicorp.Dailogues;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kyanogen.signatureview.SignatureView;
import com.moutamid.dantlicorp.Fragments.HomeFragment;
import com.moutamid.dantlicorp.MainActivity;
import com.moutamid.dantlicorp.Model.ChecksModel;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ChecksDialogClass extends AppCompatActivity {

    private EditText editTextName, editTextBox, editTextdropoff;
    private TextView buttonSubmit, title, remove;
    String type;
    CheckBox not_available;
    SignatureView signature_view;
    FirebaseStorage storage;
    StorageReference storageReference;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checks_details);
        remove = findViewById(R.id.remove);
        editTextName = findViewById(R.id.editTxtName);
        editTextBox = findViewById(R.id.editTextBox);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextdropoff = findViewById(R.id.editTextdropoff);
        not_available = findViewById(R.id.not_available);
        signature_view = findViewById(R.id.signature_view);
        type = getIntent().getStringExtra("type");
        title = findViewById(R.id.title);
        title.setText(type + getString(R.string.details));
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
//        editTextName.setText();
        if (type.equals(getString(R.string.check_out))) {
            if (Stash.getString("address_check") != null) {
                editTextName.setText(Stash.getString("address_check"));
            }
        }
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signature_view.clearCanvas();

            }
        });
        not_available.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    signature_view.clearCanvas();
                } else {
                    signature_view.setEnableSignature(true);

                }
            }
        });

        signature_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (not_available.isChecked()) {
                    signature_view.setEnableSignature(false);
                    Toast.makeText(ChecksDialogClass.this, "Please unselect checkbox to add signature", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name_str = editTextName.getText().toString();
                String box_str = editTextBox.getText().toString();
                String editTextdropoff_str = editTextdropoff.getText().toString();
                if (name_str.isEmpty() || box_str.isEmpty() || editTextdropoff_str.isEmpty()) {
                    // Display an error message if the edit text fields are empty.
                    Toast.makeText(ChecksDialogClass.this, getString(R.string.please_enter_details), Toast.LENGTH_SHORT).show();
                } else {
                    Stash.put("address_check", name_str);
                    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    screenShot(signature_view, name_str, box_str, editTextdropoff_str, date);
                }

            }
        });
    }

    public void screenShot(View view, String name_str, String box_str, String editTextdropoff_str, String date) {
        Dialog lodingbar = new Dialog(ChecksDialogClass.this);
        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bao); // bmp is bitmap from user image file
        bitmap.recycle();
        byte[] byteArray = bao.toByteArray();
        String imageB64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        lodingbar.dismiss();
        ChecksModel checksModel = new ChecksModel();
        checksModel.name = name_str;
        checksModel.picked_up = box_str;
        checksModel.drop_off = editTextdropoff_str;
        checksModel.lat = Constants.cur_lat;
        checksModel.lng = (Constants.cur_lng);
        checksModel.date = date;
        if (not_available.isChecked()) {
            checksModel.sign = "Not available";
        } else {
//            Toast.makeText(ChecksDialogClass.this, "yessljsss", Toast.LENGTH_SHORT).show();
            lodingbar.dismiss();
            checksModel.sign = imageB64;
        }
        if (type.equals(getString(R.string.check_in))) {
            type = "check_in";
            Stash.put("check_in", "yes");
        } else {
            HomeFragment.address = "Can't fetch automatically";
            type = "check_out";
            Stash.put("check_in", "no");

        }
        UserModel userModel = (UserModel) Stash.getObject("UserDetails", UserModel.class);
        String key = Stash.getString("route_key");
        Constants.UserReference.child(userModel.id).child("Routes").child(key).child(type).child(Constants.UserReference.push().getKey()).setValue(checksModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(ChecksDialogClass.this, "yesssss", Toast.LENGTH_SHORT).show();
                if (task.isComplete()) {
                    if (type.equals("check_in")) {
                        sendFCMPush("Check in", Stash.getString("admin_token"), checksModel.picked_up, checksModel.drop_off, checksModel.name);
                    }
                    else
                    {
                        sendFCMPush("Check Out", Stash.getString("admin_token"), checksModel.picked_up, checksModel.drop_off, checksModel.name);

                    }
                    Toast.makeText(ChecksDialogClass.this, ChecksDialogClass.this.getString(R.string.successfully_submitted), Toast.LENGTH_SHORT).show();
                    lodingbar.dismiss();
                    startActivity(new Intent(ChecksDialogClass.this, MainActivity.class));
                    finishAffinity();
                } else {
                    Toast.makeText(ChecksDialogClass.this, ChecksDialogClass.this.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    lodingbar.dismiss();
                }

            }

        });
    }

    private void sendFCMPush(String title, String token, String number, String dropped, String address) {
        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
//        Toast.makeText(this, "yes" + token, Toast.LENGTH_SHORT).show();
        try {
          UserModel  userModel = (UserModel) Stash.getObject("UserDetails", UserModel.class);

            notifcationBody.put("title", title);
            notifcationBody.put("message", userModel.name + " has picked " + number + " and dropped "+ dropped +" packages " + " from " + address);
            notification.put("to", token);
            notification.put("data", notifcationBody);
//            Toast.makeText(ChecksDialogClass.this, notification.toString(), Toast.LENGTH_SHORT).show();

            Log.e("DATAAAAAA", notification.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.NOTIFICATIONAPIURL, notification,
                response -> {
                    Log.e("True", response + "");
//                    Toast.makeText(ChecksDialogClass.this, response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("Responce", response.toString());
                },
                error -> {
                    Log.e("False", error + "");
                    Toast.makeText(ChecksDialogClass.this, "error", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "key=" + Constants.ServerKey);
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }

}