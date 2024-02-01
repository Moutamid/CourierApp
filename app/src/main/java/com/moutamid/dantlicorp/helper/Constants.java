package com.moutamid.dantlicorp.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Constants {
    public static String db_path = "DantliCorp";
    public static String ADMIN_UID = "admin123";
    public static String TIME_SHEET = "time_sheet";
    public static double cur_lat =0.0;
    public static double cur_lng =0.0;
    public static boolean is_journey_start =false;

    public static String NOTIFICATIONAPIURL = "https://fcm.googleapis.com/fcm/send";
public static String ServerKey = "AAAAzzvbhX8:APA91bGavDjgYZn9tdcqZCSxPEZtmvOxUSRbNxSrpakLAvMAZ8uZ5pmaqBxo4AVmpued6aKR-Nwkj8pngfV_yhNvdAytaTh_8wuGcZ-ueTYe90LFF_zgwzVXtEyYLQv42JJae9SWdHC9";

    public static FirebaseAuth auth() {
        return FirebaseAuth.getInstance();
    }

    public static DatabaseReference databaseReference() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("DantliCorp");
        db.keepSynced(true);
        return db;
    }

    public static DatabaseReference UserReference = FirebaseDatabase.getInstance().getReference(db_path).child("users");
    public static DatabaseReference VideosReference = FirebaseDatabase.getInstance().getReference(db_path).child("videos");
    public static DatabaseReference ChatReference = FirebaseDatabase.getInstance().getReference(db_path).child("chats");
    public static DatabaseReference ChatListReference = FirebaseDatabase.getInstance().getReference(db_path).child("chats_list");
    public static DatabaseReference LocationReference = FirebaseDatabase.getInstance().getReference(db_path).child("location");
    public static DatabaseReference RouteReference = FirebaseDatabase.getInstance().getReference(db_path).child("routes");
}
