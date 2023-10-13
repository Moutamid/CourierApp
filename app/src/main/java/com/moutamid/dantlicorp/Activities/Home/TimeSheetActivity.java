package com.moutamid.dantlicorp.Activities.Home;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.moutamid.dantlicorp.Model.TimeSheetModel;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class TimeSheetActivity extends AppCompatActivity {
    RadioGroup work_type;
    private EditText editTextnumber, editTextName, editTextEmail, editTextDate, editTextstartTime, editTextEndTime, editTextTotal, editTextComments;
    CheckBox agree_terms;
    private TextView buttonSubmit;
    String work_type_str = "select";
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sheet);
        editTextnumber = findViewById(R.id.editTextnumber);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDate = findViewById(R.id.editTextDate);
        editTextstartTime = findViewById(R.id.editTextstartTime);
        editTextEndTime = findViewById(R.id.editTextEndTime);
        editTextTotal = findViewById(R.id.editTextTotal);
        editTextComments = findViewById(R.id.editTextComments);
        agree_terms = findViewById(R.id.agree_terms);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        work_type = findViewById(R.id.work_type);
        UserModel userModel = (UserModel) Stash.getObject("UserDetails", UserModel.class);
        editTextName.setText(userModel.name);
        editTextEmail.setText(userModel.email);
        work_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                work_type_str = radioButton.getText().toString();
            }
        });
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            editTextDate.setText(sdf.format(myCalendar.getTime()));
        };

        editTextDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(TimeSheetActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        editTextstartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TimeSheetActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                        if (hourOfDay > 12) {
                            editTextstartTime.setText(String.valueOf(hourOfDay - 12) + ":" + (String.valueOf(selectedMinute) + " PM"));
                        } else if (hourOfDay == 12) {
                            editTextstartTime.setText("12" + ":" + (String.valueOf(selectedMinute) + " PM"));
                        } else if (hourOfDay < 12) {
                            if (hourOfDay != 0) {
                                editTextstartTime.setText(String.valueOf(hourOfDay) + ":" + (String.valueOf(selectedMinute) + " AM"));
                            } else {
                                editTextstartTime.setText("12" + ":" + (String.valueOf(selectedMinute) + " AM"));
                            }
                        }
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Start Time");
                mTimePicker.show();
            }
        });
        editTextEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TimeSheetActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                        if (hourOfDay > 12) {
                            editTextEndTime.setText(String.valueOf(hourOfDay - 12) + ":" + (String.valueOf(selectedMinute) + " PM"));
                        } else if (hourOfDay == 12) {
                            editTextEndTime.setText("12" + ":" + (String.valueOf(selectedMinute) + " PM"));
                        } else if (hourOfDay < 12) {
                            if (hourOfDay != 0) {
                                editTextEndTime.setText(String.valueOf(hourOfDay) + ":" + (String.valueOf(selectedMinute) + " AM"));
                            } else {
                                editTextEndTime.setText("12" + ":" + (String.valueOf(selectedMinute) + " AM"));
                            }
                        }
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
                        try {
                            Date date1 = simpleDateFormat.parse(editTextstartTime.getText().toString());
                            Date date2 = simpleDateFormat.parse(editTextEndTime.getText().toString());
//                           Date date1 = simpleDateFormat.parse("08:00 AM");
//                           Date date2 = simpleDateFormat.parse("04:00 PM");

                            long difference = date2.getTime() - date1.getTime();
                            int days = (int) (difference / (1000 * 60 * 60 * 24));
                            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
                            hours = (hours < 0 ? -hours : hours);
                            Toast.makeText(TimeSheetActivity.this, "jkhjhj"+hours, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.i("=====>", "error " + e.getMessage());

                        }
                    }

                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select End Time");
                mTimePicker.show();
            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editTextnumber.getText().toString();
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String date = editTextDate.getText().toString();
                String startTime = editTextstartTime.getText().toString();
                String endTime = editTextEndTime.getText().toString();
                String total = editTextTotal.getText().toString();
                String comments = editTextComments.getText().toString();

                if (number.isEmpty() || name.isEmpty() || email.isEmpty() || date.isEmpty() || startTime.isEmpty()
                        || endTime.isEmpty() || total.isEmpty() || comments.isEmpty()) {
                    // Display an error message if the edit text fields are empty.
                    Toast.makeText(TimeSheetActivity.this, "Please enter complete details", Toast.LENGTH_SHORT).show();
                } else if (work_type_str.equals("select")) {
                    Toast.makeText(TimeSheetActivity.this, "Please select Invoice Based on (Hours or Days)", Toast.LENGTH_SHORT).show();
                } else if (!agree_terms.isChecked()) {
                    Toast.makeText(TimeSheetActivity.this, "Please check Confirmation of Accuracy", Toast.LENGTH_SHORT).show();
                } else {
                    Dialog lodingbar = new Dialog(TimeSheetActivity.this);
                    lodingbar.setContentView(R.layout.loading);
                    Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
                    lodingbar.setCancelable(false);
                    lodingbar.show();
                    TimeSheetModel timeSheetModel = new TimeSheetModel();
                    timeSheetModel.date = date;
                    timeSheetModel.number = number;
                    timeSheetModel.name = name;
                    timeSheetModel.email = email;
                    timeSheetModel.startTime = startTime;
                    timeSheetModel.endTime = endTime;
                    timeSheetModel.total = total;
                    timeSheetModel.comments = comments;
                    timeSheetModel.lat = Constants.cur_lat;
                    timeSheetModel.lng = (Constants.cur_lng);

                    String userID = Stash.getString("userID");

                    Constants.UserReference.child(userID).child(Constants.TIME_SHEET).push().setValue(timeSheetModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                Toast.makeText(TimeSheetActivity.this, "Successfully Submitted", Toast.LENGTH_SHORT).show();
                                lodingbar.dismiss();
                                onBackPressed();
                            } else {
                                Toast.makeText(TimeSheetActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                lodingbar.dismiss();
                            }
                        }
                    });
                }
            }
        });

    }


}