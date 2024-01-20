package com.moutamid.dantlicorp.Admin.Activities;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.dantlicorp.Admin.Adapter.InvoiceListAdapter;
import com.moutamid.dantlicorp.Model.TimeSheetModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class PendingAllTimeSheetsActivity extends AppCompatActivity {
    ArrayList<TimeSheetModel> list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_time_sheets);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(PendingAllTimeSheetsActivity.this));
        recyclerView.setHasFixedSize(false);
        list = new ArrayList<>();
        getData();
    }

    private void getData() {
        Dialog lodingbar = new Dialog(PendingAllTimeSheetsActivity.this);
        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();
        Constants.UserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                if (userSnapshot.exists()) {
                    list.clear();

                    for (DataSnapshot user : userSnapshot.getChildren()) {
                        String userID = user.getKey(); // Get the user ID

                        if (userID != null) {
                            // Now, iterate over the time sheets of the current user
                            DatabaseReference timeSheetRef = Constants.UserReference.child(userID).child(Constants.TIME_SHEET);

                            timeSheetRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot timeSheetSnapshot) {
                                    if (timeSheetSnapshot.exists()) {
                                        for (DataSnapshot timeSheetData : timeSheetSnapshot.getChildren()) {
                                            TimeSheetModel model = timeSheetData.getValue(TimeSheetModel.class);
                                            if (model != null && model.status.equals("pending")&& model.userID!=null) {
                                                list.add(model);
                                                Log.d("listSize", "ee : " + model.number);
                                            }
                                        }
                                    } else {
                                        // User has no time sheets
                                        // You can handle this case (e.g., show a message or take appropriate action)
                                        Log.d("listSize", "User has no time sheets");
                                    }

                                    lodingbar.dismiss();
                                    InvoiceListAdapter adapter = new InvoiceListAdapter(PendingAllTimeSheetsActivity.this, list);
                                    recyclerView.setAdapter(adapter);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    lodingbar.dismiss();
                                    Toast.makeText(PendingAllTimeSheetsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                lodingbar.dismiss();
                Toast.makeText(PendingAllTimeSheetsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}