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
import com.moutamid.dantlicorp.Model.TimeSheetModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

public class UserDetailsDialogClass extends Dialog {

    public Activity c;
    public Dialog d;
    private EditText editTextDate, editTextHours, editTextDays, editTextPay;
    private TextView buttonSubmit;


    public UserDetailsDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.user_details);
        editTextDate = findViewById(R.id.editTextDate);
        editTextHours = findViewById(R.id.editTextHours);
        editTextDays = findViewById(R.id.editTextDays);
        editTextPay = findViewById(R.id.editTextPay);
        buttonSubmit = findViewById(R.id.buttonSubmit);


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date_str = editTextDate.getText().toString();
                String hours_str = editTextHours.getText().toString();
                String days_str = editTextDays.getText().toString();
                String pay_str = editTextPay.getText().toString();

                if (date_str.isEmpty() || hours_str.isEmpty() || days_str.isEmpty() || pay_str.isEmpty()) {
                    // Display an error message if the edit text fields are empty.
                    Toast.makeText(c, "Please enter details", Toast.LENGTH_SHORT).show();
                } else {
                    Config.showProgressDialog(c);
                    TimeSheetModel timeSheetModel = new TimeSheetModel();
                    timeSheetModel.date = date_str;
                    timeSheetModel.days = days_str;
                    timeSheetModel.hours = hours_str;
                    timeSheetModel.pay = pay_str;
                    Constants.UserReference.child(Constants.auth().getUid()).child(Constants.TIME_SHEET).setValue(timeSheetModel).addOnCompleteListener(new OnCompleteListener<Void>() {
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