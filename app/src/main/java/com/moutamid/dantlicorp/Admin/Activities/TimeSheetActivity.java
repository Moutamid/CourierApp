package com.moutamid.dantlicorp.Admin.Activities;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private TextView editTextnumber, editTextName, editTextEmail, editTextDate, editTextstartTime, editTextEndTime, editTextTotal, editTextComments;
    Button deny, accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sheet2);
        TimeSheetModel timeSheetModel = (TimeSheetModel) Stash.getObject(Constants.TIME_SHEET, TimeSheetModel.class);

        accept = findViewById(R.id.accept);
        deny = findViewById(R.id.deny);
        editTextnumber = findViewById(R.id.editTextnumber);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDate = findViewById(R.id.editTextDate);
        editTextstartTime = findViewById(R.id.editTextstartTime);
        editTextEndTime = findViewById(R.id.editTextEndTime);
        editTextTotal = findViewById(R.id.editTextTotal);
        editTextComments = findViewById(R.id.editTextComments);
        work_type = findViewById(R.id.work_type);
        editTextnumber.setText(timeSheetModel.number);
        editTextName.setText(timeSheetModel.name);
        editTextEmail.setText(timeSheetModel.email);
        editTextDate.setText(timeSheetModel.date);
        editTextstartTime.setText(timeSheetModel.startTime);
        editTextEndTime.setText(timeSheetModel.endTime);
        editTextTotal.setText(timeSheetModel.total);
        editTextComments.setText(timeSheetModel.comments);
        work_type.setText(timeSheetModel.work_type_str);
        String userID = Stash.getString("userID");
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
                        finish();
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
                        finish();
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