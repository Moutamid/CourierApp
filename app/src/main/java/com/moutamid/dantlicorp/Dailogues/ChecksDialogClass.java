package com.moutamid.dantlicorp.Dailogues;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.moutamid.dantlicorp.Model.ChecksModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChecksDialogClass extends Dialog {

    public Activity c;
    public Dialog d;
    private EditText editTextName, editTextBox;
    private TextView buttonSubmit, title;
    String type;

    public ChecksDialogClass(Activity a, String type) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.checks_details);
        editTextName = findViewById(R.id.editTxtName);
        editTextBox = findViewById(R.id.editTextBox);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        title = findViewById(R.id.title);
        title.setText(type+" Details");


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name_str = editTextName.getText().toString();
                String box_str = editTextBox.getText().toString();
                if (name_str.isEmpty() || box_str.isEmpty()) {
                    // Display an error message if the edit text fields are empty.
                    Toast.makeText(c, "Please enter details.", Toast.LENGTH_SHORT).show();
                } else {
                    Config.showProgressDialog(c);
                    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                    ChecksModel checksModel = new ChecksModel();
                    checksModel.name = name_str;
                    checksModel.box = box_str;
                    checksModel.lat = Constants.cur_lat;
                    checksModel.lng = (Constants.cur_lng);
                    checksModel.date = date;
                    if (type.equals("Check In")) {
                        type = "check_in";
                    } else {
                        type = "check_out";
                    }
                    Constants.UserReference.child(Constants.auth().getUid()).child(type).child(Constants.UserReference.push().getKey()).setValue(checksModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {

                                dismiss();
                                Toast.makeText(c, "Successfully Submitted", Toast.LENGTH_SHORT).show();
                                Config.dismissProgressDialog();
                            } else {
                                dismiss();
                                Toast.makeText(c, "Something went wrong", Toast.LENGTH_SHORT).show();
                                Config.dismissProgressDialog();

                            }
                        }
                    });
                }
            }
        });

    }


}