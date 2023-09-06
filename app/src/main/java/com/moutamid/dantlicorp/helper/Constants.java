package com.moutamid.dantlicorp.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Constants {
    public static String db_path = "DantliCorp";

    public static FirebaseAuth auth() {
        return FirebaseAuth.getInstance();
    }

    public static DatabaseReference databaseReference() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("DantliCorp");
        db.keepSynced(true);
        return db;
    }

    public static DatabaseReference VideosReference = FirebaseDatabase.getInstance().getReference(db_path).child("Videos");
}
