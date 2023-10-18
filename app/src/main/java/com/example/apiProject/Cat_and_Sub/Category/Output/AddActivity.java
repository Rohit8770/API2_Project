package com.example.apiProject.Cat_and_Sub.Category.Output;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apiProject.Cat_and_Sub.Category.networkResponse.CommonResponse;
import com.example.apiProject.Utils.SharedPreference;
import com.example.apiProject.Cat_and_Sub.network.RestClient;
import com.example.apiProject.Cat_and_Sub.network.Restcall;
import com.example.apiProject.R;
import com.example.apiProject.Utils.Tools;
import com.example.apiProject.Utils.VariableBag;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class AddActivity extends AppCompatActivity {

    EditText etName;
    Button btnAdd;
    Tools tools;
    Restcall restcall;
    boolean isEdit=false;
    String category_id,category_name;
    SharedPreference sharedPreference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etName=findViewById(R.id.etName);
        btnAdd=findViewById(R.id.btnAdd);
        sharedPreference=new SharedPreference(this);
        tools=new Tools(this);
        restcall= RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);


        if (getIntent() != null && getIntent().getStringExtra("category_id") != null) {
            isEdit = true;
            category_id = getIntent().getStringExtra("category_id");
            category_name = getIntent().getStringExtra("category_name");
            etName.setText(category_name);
            btnAdd.setText("Edit");

           } else {
            isEdit = false;
            btnAdd.setText("Add");
        }

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (etName.getText().toString().trim().equalsIgnoreCase("")) {
                        etName.setError("Enter Category Name");
                        etName.requestFocus();
                    } else {
                        if (isEdit) {
                            EditCategory();
                        } else {
                            Addcategory(etName.getText().toString().trim());
                        }
                    }
                }
            });

        }
    public void EditCategory() {
        tools.showLoading();
        restcall.EditCategory("EditCategory",etName.getText().toString(), category_id ,sharedPreference.getStringvalue("user_id"))
                .subscribeOn(Schedulers.io())
                .observeOn((Schedulers.newThread()))
                .subscribe(new Subscriber<CommonResponse>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(CommonResponse commonResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.showLoading();
                                if (commonResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {
                                    etName.setText("");
                                    Toast.makeText(AddActivity.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(AddActivity.this, ReturnDataActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(AddActivity.this, "Edit Failed", Toast.LENGTH_SHORT).show();
                                    }
                                    Toast.makeText(AddActivity.this, "success"+commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        });
                    }
                });


    }
    public void Addcategory (String category_name) {
        restcall.AddCategory("AddCategory",sharedPreference.getStringvalue("user_id"),category_name)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CommonResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(CommonResponse commonResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {
                                    etName.setText("");
                                    finish();
                                }
                                Toast.makeText(AddActivity.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

        }
}