package com.moutamid.dantlicorp.Dailogues;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.moutamid.dantlicorp.MainActivity;
import com.moutamid.dantlicorp.Model.ChecksModel;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.Objects;

public class StartRouteDialogClass extends Dialog {

    public Activity c;
    public Dialog d;
    private EditText editTextName, editTextBox;
    private TextView buttonSubmit, title;
    String type;
    LinearLayout add_check_in_lyt, add_check_out_lyt, start_layout;

    public StartRouteDialogClass(Activity a, LinearLayout add_check_in_lyt, LinearLayout add_check_out_lyt, LinearLayout start_layout) {
        super(a);
        this.c = a;
        this.add_check_in_lyt = add_check_in_lyt;
        this.add_check_out_lyt = add_check_out_lyt;
        this.start_layout = start_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.route_details);
        editTextName = findViewById(R.id.editTxtName);
        editTextBox = findViewById(R.id.editTextBox);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        title = findViewById(R.id.title);
        UserModel userModel = (UserModel) Stash.getObject("UserDetails", UserModel.class);
        editTextName.setText(userModel.email);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name_str = editTextName.getText().toString();
                String box_str = editTextBox.getText().toString();
                if (name_str.isEmpty() || box_str.isEmpty()) {
                    // Display an error message if the edit text fields are empty.
                    Toast.makeText(c, c.getString(R.string.please_enter_details), Toast.LENGTH_SHORT).show();
                } else {
                    Dialog lodingbar = new Dialog(c);
                    lodingbar.setContentView(R.layout.loading);
                    Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
                    lodingbar.setCancelable(false);
                    lodingbar.show();
                    DatabaseReference routes = Constants.UserReference.child(userModel.id).child("Routes");
                    String key = routes.push().getKey();

                    ChecksModel checksModel = new ChecksModel();
                    checksModel.email = name_str;
                    checksModel.route_name = box_str;
                    checksModel.status = "start";
                    checksModel.key = key;

                    UserModel userModel = (UserModel) Stash.getObject("UserDetails", UserModel.class);

                    Constants.UserReference.child(userModel.id).child("Routes").child(key).setValue(checksModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                dismiss();
                                Stash.put("route_start", "yes");
                                Stash.put("route_key", key);
                                Toast.makeText(c, c.getString(R.string.successfully_submitted), Toast.LENGTH_SHORT).show();
                                lodingbar.dismiss();
                                add_check_in_lyt.setVisibility(View.VISIBLE);
                                add_check_out_lyt.setVisibility(View.VISIBLE);
                                start_layout.setVisibility(View.GONE);
                                c.startActivity(new Intent(getContext(), MainActivity.class));
                                c.finishAffinity();
                            } else {
                                dismiss();
                                Toast.makeText(c, c.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                                lodingbar.dismiss();
                            }
                        }
                    });
                }
            }
        });

    }


}