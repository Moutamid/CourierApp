package com.moutamid.dantlicorp.Admin.Activities;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.moutamid.dantlicorp.Model.TimeSheetModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.Objects;

public class TimeSheetActivity extends AppCompatActivity {
    TextView work_type;
    private TextView comment_txt, editTextTotalDays, editTextEndDate, editTextStartDate, editTextnumber, editTextName, editTextEmail, editTextDate, editTextstartTime, editTextEndTime, editTextTotal, editTextComments;
    Button deny, accept;
    LinearLayout days_layout, hours_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sheet2);
        TimeSheetModel timeSheetModel = (TimeSheetModel) Stash.getObject(Constants.TIME_SHEET, TimeSheetModel.class);

        comment_txt = findViewById(R.id.comment_txt);
        days_layout = findViewById(R.id.days);
        hours_layout = findViewById(R.id.hours);
        accept = findViewById(R.id.accept);
        deny = findViewById(R.id.deny);
        editTextTotalDays = findViewById(R.id.editTextTotalDays);
        editTextEndDate = findViewById(R.id.editTextEndDate);
        editTextStartDate = findViewById(R.id.editTextStartDate);
        editTextnumber = findViewById(R.id.editTextnumber);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDate = findViewById(R.id.editTextDate);
        editTextstartTime = findViewById(R.id.editTextstartTime);
        editTextEndTime = findViewById(R.id.editTextEndTime);
        editTextTotal = findViewById(R.id.editTextTotal);
        editTextComments = findViewById(R.id.editTextComments);
        work_type = findViewById(R.id.work_type);
        if (timeSheetModel.work_type_str.equals("Hours")) {
            hours_layout.setVisibility(View.GONE);
            days_layout.setVisibility(View.VISIBLE);
        } else if (timeSheetModel.work_type_str.equals("Days")) {
            hours_layout.setVisibility(View.VISIBLE);
            days_layout.setVisibility(View.GONE);
        }
        Log.d("nothing", timeSheetModel.comments);
        if(timeSheetModel.comments.equals("nothing"))
        {
            editTextComments.setVisibility(View.GONE);
            comment_txt.setVisibility(View.GONE);

        }
        editTextnumber.setText(timeSheetModel.number);
        editTextName.setText(timeSheetModel.name);
        editTextEmail.setText(timeSheetModel.email);
        editTextDate.setText(timeSheetModel.date);
        editTextstartTime.setText(timeSheetModel.startTime);
        editTextEndTime.setText(timeSheetModel.endTime);
        editTextTotal.setText(timeSheetModel.total);
        editTextStartDate.setText(timeSheetModel.startTime);
        editTextEndDate.setText(timeSheetModel.endTime);
        editTextTotalDays.setText(timeSheetModel.total);
        editTextComments.setText(timeSheetModel.comments);
        work_type.setText(timeSheetModel.work_type_str);
        String userID = Stash.getString("userID");
//        Toast.makeText(this, "user"+ userID, Toast.LENGTH_SHORT).show();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            if (userID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            {
                accept.setVisibility(View.GONE);
                deny.setVisibility(View.GONE);
            }
        }
        DatabaseReference ref = Constants.UserReference.child(userID).child(Constants.TIME_SHEET).child(timeSheetModel.key);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog lodingbar = new Dialog(TimeSheetActivity.this);
                lodingbar.setContentView(R.layout.loading);
                Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
                lodingbar.setCancelable(false);
                lodingbar.show();
                ref.child("status").setValue("accepted").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        onBackPressed();
                        lodingbar.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        lodingbar.dismiss();
                        Toast.makeText(TimeSheetActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });            }
        });
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog lodingbar = new Dialog(TimeSheetActivity.this);
                lodingbar.setContentView(R.layout.loading);
                Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
                lodingbar.setCancelable(false);
                lodingbar.show();
                ref.child("status").setValue("rejected").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        onBackPressed();
                        lodingbar.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        lodingbar.dismiss();
                        Toast.makeText(TimeSheetActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }


}