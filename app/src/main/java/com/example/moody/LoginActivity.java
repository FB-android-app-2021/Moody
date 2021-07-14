package com.example.moody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moody.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //if someone is signed in take them straight to main activity
        if (ParseUser.getCurrentUser() != null) {
            goDailyActivity();
        }

        etUsername = binding.etUsername;
        etPassword = binding.etPassword;
        btnLogin = binding.btnLogin;
        btnSignup = binding.btnSignup;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onCLick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onCLick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                ParseUser user = new ParseUser();
                // Set core properties
                user.setUsername(username);
                user.setPassword(password);
                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                  public void done(ParseException e) {
                    if (e == null) {
                      goDailyActivity();
                    } else {
                        Log.e(TAG, "Issue with Sign up", e);
                        Toast.makeText(LoginActivity.this, "This account could not be created", Toast.LENGTH_SHORT).show();
                        return;
                    }
                  }
                });
            }
        });
    }
    //if correct credentials navigate to main activity
    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to log in user" + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                //if parse exception is null then it succeeded
                if(e != null) {
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "These username and password do not match an existing user.", Toast.LENGTH_SHORT).show();
                    return;
                }
                goDailyActivity();
                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void goDailyActivity() {
        Intent i = new Intent(LoginActivity.this, DailyActivity.class);
        startActivity(i);
        //keeps user from going back to login activity
        finish();
    }
}