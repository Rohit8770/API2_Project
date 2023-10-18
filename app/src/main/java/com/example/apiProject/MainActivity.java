package com.example.apiProject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.apiProject.Cat_and_Sub.MargeActivity;
import com.example.apiProject.Cat_and_Sub.User.UserFlashActivity;
import com.example.apiProject.Utils.SharedPreference;

public class MainActivity extends AppCompatActivity {

    Button btn1,btn2,btn3;
    TextView Text;
    SharedPreference sharedPreference;
    String useridtxt;
    Intent i;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        Text=findViewById(R.id.txt1111);
        sharedPreference=new SharedPreference(this);
     //   btn3=findViewById(R.id.btn3);


        useridtxt=sharedPreference.getStringvalue("user_id");
        Text.setText(useridtxt);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, MargeActivity.class);
                startActivity(i);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, UserFlashActivity.class);
                startActivity(i);
            }
        });
       /* btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, ProductFileActivity.class);
                startActivity(i);
            }
        });*/
    }
}