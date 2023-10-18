package com.example.apiProject.Cat_and_Sub.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.apiProject.Cat_and_Sub.User.Activity1.UserActivity;
import com.example.apiProject.R;

public class UserFlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_flash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(UserFlashActivity.this, UserActivity.class);
                startActivity(i);
                finish();
            }
        }, 1000);
    }
}