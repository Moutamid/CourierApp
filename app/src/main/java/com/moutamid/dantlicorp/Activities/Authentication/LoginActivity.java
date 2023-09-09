package com.moutamid.dantlicorp.Activities.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.moutamid.dantlicorp.Admin.AdminPanel;
import com.moutamid.dantlicorp.MainActivity;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;


public class LoginActivity extends AppCompatActivity {
    EditText email, password;

    String email_str, password_str;
    String name, emailstr, image_gmail;
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference mDatabase;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initComponent();
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initValues();
                loginRequest();
            }
        });
    }

    public void initComponent() {
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void initValues() {
        email_str = email.getText().toString();
        password_str = password.getText().toString();
    }

    public void sign_up(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }


    private void loginRequest() {
        if (email_str.length() == 0) {
            email.setError("Please Error");
        } else if (password_str.length() == 0) {
            password.setError("Please Error");
        } else {

            if (email.getText().toString().equals("admin@gmail.com") && password.getText().toString().equals("admin123")) {

                startActivity(new Intent(this, AdminPanel.class));
            } else {
                Config.showProgressDialog(LoginActivity.this);
                Constants.auth().signInWithEmailAndPassword(
                        email.getText().toString(),
                        password.getText().toString()
                ).addOnSuccessListener(authResult -> {
                    Config.dismissProgressDialog();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }).addOnFailureListener(e -> {
                    Config.dismissProgressDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }


        }
    }
}