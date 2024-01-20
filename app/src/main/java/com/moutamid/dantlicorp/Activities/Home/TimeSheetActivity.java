package com.moutamid.dantlicorp.Activities.Home;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.moutamid.dantlicorp.Dailogues.ChecksDialogClass;
import com.moutamid.dantlicorp.Model.TimeSheetModel;
import com.moutamid.dantlicorp.Model.UserModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TimeSheetActivity extends AppCompatActivity {
    RadioGroup work_type;
    EditText editTextnumber, editTextTotalDays, editTextEndDate, editTextStartDate, editTextName, editTextEmail, editTextDate, editTextstartTime, editTextEndTime, editTextTotal, editTextComments;
    CheckBox agree_terms;
    TextView buttonSubmit;
    String work_type_str = "select";
    String comment_str = "nothing";

    Calendar myCalendar = Calendar.getInstance();
    Calendar start_Calendar = Calendar.getInstance();
    String start_time, end_time;
    String start_date_str;
    LinearLayout days_layout, hours_layout;
    RadioButton hours, days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sheet);
        editTextnumber = findViewById(R.id.editTextnumber);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextEndDate = findViewById(R.id.editTextEndDate);
        editTextStartDate = findViewById(R.id.editTextStartDate);
        editTextDate = findViewById(R.id.editTextDate);
        editTextstartTime = findViewById(R.id.editTextstartTime);
        editTextEndTime = findViewById(R.id.editTextEndTime);
        editTextTotal = findViewById(R.id.editTextTotal);
        editTextComments = findViewById(R.id.editTextComments);
        agree_terms = findViewById(R.id.agree_terms);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextTotalDays = findViewById(R.id.editTextTotalDays);
        work_type = findViewById(R.id.work_type);
        hours_layout = findViewById(R.id.hours_layout);
        days_layout = findViewById(R.id.days_layout);
        hours = findViewById(R.id.hours);
        days = findViewById(R.id.days);
        UserModel userModel = (UserModel) Stash.getObject("UserDetails", UserModel.class);
        editTextName.setText(userModel.name);
        editTextEmail.setText(userModel.email);
        work_type_str = getString(R.string.hours);
        work_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                work_type_str = radioButton.getText().toString();

                if (work_type_str.equals(getString(R.string.days))) {
                    hours_layout.setVisibility(View.GONE);
                    days_layout.setVisibility(View.VISIBLE);
                    hours.setChecked(false);
                    days.setChecked(true);
                } else {
                    hours_layout.setVisibility(View.VISIBLE);
                    days_layout.setVisibility(View.GONE);
                    hours.setChecked(true);
                    days.setChecked(false);
                }
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
        DatePickerDialog.OnDateSetListener start_date = (view, year, monthOfYear, dayOfMonth) -> {
            start_Calendar.set(Calendar.YEAR, year);
            start_Calendar.set(Calendar.MONTH, monthOfYear);
            start_Calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            editTextStartDate.setText(sdf.format(start_Calendar.getTime()));
            start_date_str = sdf.format(start_Calendar.getTime());
        };
        DatePickerDialog.OnDateSetListener end_date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            try {
                Date date1 = sdf.parse(start_date_str);
                Date date2 = sdf.parse(sdf.format(myCalendar.getTime()));
                long diff = date2.getTime() - date1.getTime();
                if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) == 0) {
                    editTextTotalDays.setText(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1 + " Day");
                } else {
                    editTextTotalDays.setText(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1 + " Days");

                }
            } catch (Exception e) {
                System.out.println("Exceptions: " + e.toString());

            }
            editTextEndDate.setText(sdf.format(myCalendar.getTime()));

        };

        editTextDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(TimeSheetActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        editTextStartDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(TimeSheetActivity.this, start_date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        editTextEndDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(TimeSheetActivity.this, end_date, myCalendar
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
                mTimePicker = new TimePickerDialog(TimeSheetActivity.this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                        start_time = String.format("%02d:%02d %s", hourOfDay == 0 ? 12 : hourOfDay > 12 ? hourOfDay - 12 : hourOfDay, selectedMinute, hourOfDay >= 12 ? "PM" : "AM");
                        editTextstartTime.setText(start_time);
//                        if (hourOfDay > 12) {
//
//                            int i = hourOfDay - 12;
//                            editTextstartTime.setText(String.format("%02d:%02d %s", i, selectedMinute, " PM"));
//                            start_time = hourOfDay + ":" + selectedMinute + " PM";
//
//                        } else if (hourOfDay == 12) {
//                            int i = 12;
//                            editTextstartTime.setText(String.format("%02d:%02d %s", i, selectedMinute, " PM"));
//                            start_time = hourOfDay + ":" + selectedMinute + " PM";
//
//                        } else if (hourOfDay < 12) {
//                            if (hourOfDay != 0) {
//                                editTextstartTime.setText(String.format("%02d:%02d %s", hourOfDay, selectedMinute, " AM"));
//                                start_time = hourOfDay + ":" + selectedMinute + " AM";
//
//                            } else {
//                                int i = 12;
//                                editTextstartTime.setText(String.format("%02d:%02d %s", i, selectedMinute, " AM"));
//                                start_time = hourOfDay + ":" + selectedMinute + " AM";
//
//                            }
//                        }
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Start Time");

                mTimePicker.show();

                mTimePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                mTimePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });
        editTextEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TimeSheetActivity.this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                        end_time = String.format("%02d:%02d %s", hourOfDay == 0 ? 12 : hourOfDay > 12 ? hourOfDay - 12 : hourOfDay, selectedMinute, hourOfDay >= 12 ? "PM" : "AM");
                        editTextEndTime.setText(end_time);
//                        if (hourOfDay > 12) {
//                            int i = hourOfDay - 12;
//                            editTextEndTime.setText(String.format("%02d:%02d %s", i, selectedMinute, " PM"));
//                            end_time = hourOfDay + ":" + selectedMinute + " PM";
//
//                        } else if (hourOfDay == 12) {
//                            int i = 12;
//                            editTextEndTime.setText(String.format("%02d:%02d %s", i, selectedMinute, " PM"));
//                            end_time = hourOfDay + ":" + selectedMinute + " PM";
//
//                        } else if (hourOfDay < 12) {
//                            if (hourOfDay != 0) {
//                                editTextEndTime.setText(String.format("%02d:%02d %s", hourOfDay, selectedMinute, " AM"));
//                                end_time = hourOfDay + ":" + selectedMinute + " AM";
//
//                            } else {
//                                int i = 12;
//                                editTextEndTime.setText(String.format("%02d:%02d %s", i, selectedMinute, " AM"));
//                                end_time = hourOfDay + ":" + selectedMinute + " AM";
//
//
//                            }
//
//                        }
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm aa");
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
                            Date earlierTime = sdf.parse(start_time);
                            Date laterTime = sdf.parse(end_time);
                            long timeDifferenceInMillis = laterTime.getTime() - earlierTime.getTime();
                            int hours = (int) (timeDifferenceInMillis / (1000 * 60 * 60));
                            int minutes = (int) (timeDifferenceInMillis / (1000 * 60)) % 60;
                            if (hours < 0) {
                                hours += 24;
                            }
                            double totalHours = hours + minutes / 60.0;


                            editTextTotal.setText(String.format("%.1f", totalHours));
                        } catch (Exception e) {
                            Log.i("=====>", "error " + e.getMessage());

                        }
                    }

                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select End Time");
                mTimePicker.show();
                mTimePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                mTimePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);

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
                String start_date = editTextStartDate.getText().toString();
                String end_date = editTextEndDate.getText().toString();
                String total_days = editTextTotalDays.getText().toString();
                comment_str = editTextComments.getText().toString();

                if (comment_str.isEmpty()) {
                    comment_str = "nothing";
                }

                if (number.isEmpty() || name.isEmpty() || email.isEmpty()) {
                    // Display an error message if the edit text fields are empty.
                    Toast.makeText(TimeSheetActivity.this, "Please enter complete details", Toast.LENGTH_SHORT).show();
                } else if (work_type_str.equals("select")) {
                    Toast.makeText(TimeSheetActivity.this, "Please select Invoice Based on (Hours or Days)", Toast.LENGTH_SHORT).show();
                } else if (!agree_terms.isChecked()) {
                    Toast.makeText(TimeSheetActivity.this, "Please check Confirmation of Accuracy", Toast.LENGTH_SHORT).show();
                } else if (work_type_str.equals("Hours")) {
                    if (date.isEmpty() || startTime.isEmpty()
                            || endTime.isEmpty() || total.isEmpty()) {
                        Toast.makeText(TimeSheetActivity.this, "Please enter complete details", Toast.LENGTH_SHORT).show();
                    } else {
                        Dialog lodingbar = new Dialog(TimeSheetActivity.this);
                        lodingbar.setContentView(R.layout.loading);
                        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
                        lodingbar.setCancelable(false);
                        lodingbar.show();
                        TimeSheetModel timeSheetModel = new TimeSheetModel();
                        String userID = Stash.getString("userID");
                        String key = Constants.UserReference.child(userID).child(Constants.TIME_SHEET).push().getKey();
                        timeSheetModel.work_type_str = work_type_str;
                        timeSheetModel.number = number;
                        timeSheetModel.name = name;
                        timeSheetModel.email = email;
                        timeSheetModel.date = date;
                        timeSheetModel.startTime = startTime;
                        timeSheetModel.endTime = endTime;
                        timeSheetModel.total = total;
                        timeSheetModel.comments = comment_str;
                        timeSheetModel.lat = Constants.cur_lat;
                        timeSheetModel.lng = (Constants.cur_lng);
                        timeSheetModel.status = "pending";
                        timeSheetModel.key = key;
                        timeSheetModel.userID = userID;

                        Constants.UserReference.child(userID).child(Constants.TIME_SHEET).child(key).setValue(timeSheetModel).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                } else if (work_type_str.equals("Days")) {

                    if (start_date.isEmpty() || end_date.isEmpty() ) {
                        Toast.makeText(TimeSheetActivity.this, "Please enter complete details", Toast.LENGTH_SHORT).show();
                    } else {
                        Dialog lodingbar = new Dialog(TimeSheetActivity.this);
                        lodingbar.setContentView(R.layout.loading);
                        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
                        lodingbar.setCancelable(false);
                        lodingbar.show();
                        TimeSheetModel timeSheetModel = new TimeSheetModel();
                        String userID = Stash.getString("userID");
                        String key = Constants.UserReference.child(userID).child(Constants.TIME_SHEET).push().getKey();
                        timeSheetModel.work_type_str = work_type_str;
                        timeSheetModel.number = number;
                        timeSheetModel.name = name;
                        timeSheetModel.email = email;

                        timeSheetModel.date = "01/01/2000";
                        timeSheetModel.startTime = start_date;
                        timeSheetModel.endTime = end_date;
                        timeSheetModel.total = total_days;

                        timeSheetModel.comments = comment_str;
                        timeSheetModel.lat = Constants.cur_lat;
                        timeSheetModel.lng = (Constants.cur_lng);
                        timeSheetModel.status = "pending";
                        timeSheetModel.key = key;
                        timeSheetModel.userID = userID;

                        Constants.UserReference.child(userID).child(Constants.TIME_SHEET).child(key).setValue(timeSheetModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()) {
                                    sendFCMPush(Stash.getString("admin_token"));
                                    Toast.makeText(TimeSheetActivity.this, "Successfully Submitted", Toast.LENGTH_SHORT).show();
                                    lodingbar.dismiss();
                                    onBackPressed();
                                }
                                else {
                                    Toast.makeText(TimeSheetActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    lodingbar.dismiss();
                                }
                            }
                        });
                    }

                }
            }
        });

    }
    private void sendFCMPush(String token)
    {
        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
//        Toast.makeText(this, "yes" + token, Toast.LENGTH_SHORT).show();
        try {
            UserModel  userModel = (UserModel) Stash.getObject("UserDetails", UserModel.class);

            notifcationBody.put("title", "Timesheet invoice");
            notifcationBody.put("message", userModel.name + " submitted a invoice");
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
                    Toast.makeText(TimeSheetActivity.this, "error", Toast.LENGTH_SHORT).show();
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