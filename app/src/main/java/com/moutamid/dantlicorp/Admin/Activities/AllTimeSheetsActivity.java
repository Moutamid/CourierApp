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
import com.google.firebase.database.ValueEventListener;
import com.moutamid.dantlicorp.Admin.Adapter.InvoiceListAdapter;
import com.moutamid.dantlicorp.Model.TimeSheetModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class AllTimeSheetsActivity extends AppCompatActivity {
    ArrayList<TimeSheetModel> list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_time_sheets);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllTimeSheetsActivity.this));
        recyclerView.setHasFixedSize(false);

        list = new ArrayList<>();

        getData();
    }

    private void getData() {
        Dialog lodingbar = new Dialog(AllTimeSheetsActivity.this);
        String userID = Stash.getString("userID");

        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();
        Constants.UserReference.child(userID).child(Constants.TIME_SHEET)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            list.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    TimeSheetModel model = dataSnapshot.getValue(TimeSheetModel.class);
                                if (model.status.equals("accepted")||model.status.equals("rejected")) {

                                    list.add(model);
                                    Log.d("listSize", "ee : " + model.number);
                                }
                            }


                        }
                        lodingbar.dismiss();
                        InvoiceListAdapter adapter = new InvoiceListAdapter(AllTimeSheetsActivity.this, list);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        lodingbar.dismiss();
                        Toast.makeText(AllTimeSheetsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}