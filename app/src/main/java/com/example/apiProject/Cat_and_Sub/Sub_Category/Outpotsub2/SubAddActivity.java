package com.example.apiProject.Cat_and_Sub.Sub_Category.Outpotsub2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apiProject.Cat_and_Sub.Category.networkResponse.CatagoryListResponse;
import com.example.apiProject.Cat_and_Sub.Sub_Category.SubNetworkRespose.SubCatagoryListResponse;
import com.example.apiProject.Cat_and_Sub.Sub_Category.SubNetworkRespose.SubCommonResponse;
import com.example.apiProject.Utils.SharedPreference;
import com.example.apiProject.Cat_and_Sub.network.RestClient;
import com.example.apiProject.Cat_and_Sub.network.Restcall;
import com.example.apiProject.R;
import com.example.apiProject.Utils.Tools;
import com.example.apiProject.Utils.VariableBag;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SubAddActivity extends AppCompatActivity {
    EditText etName1;
    Button btnAddon;
    Restcall restcall;
    AppCompatSpinner spinnerAdd;
    String selectedCategoryId,selectedSubCategoryId,selectedCategoryName;
    int selectedpos = 0;
    boolean isEdit ;
    SharedPreference sharedPreference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_add);
        etName1=findViewById(R.id.etvName1);
        btnAddon=findViewById(R.id.btnAddon);
        spinnerAdd=findViewById(R.id.spinnerAdd);
        sharedPreference=new SharedPreference(this);
       // tools=new Tools(this);
        restcall= RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        GetCategory();


        Bundle bundle =getIntent().getExtras();
        if (bundle !=null && bundle.getString("category_id")!=null){

            isEdit =true;
            selectedCategoryId=bundle.getString("category_id");
            selectedSubCategoryId=bundle.getString("sub_category_id");
            selectedCategoryName=bundle.getString("subcategory_name");
            btnAddon.setText("Edit");
            etName1.setText(selectedCategoryName);
        }else {
            isEdit =false;
            btnAddon.setText("Add");
        }

        btnAddon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedCategoryId == null || selectedCategoryId.equals("-1")){
                    Toast.makeText(SubAddActivity.this, "Please select Category", Toast.LENGTH_SHORT).show();

                } else if (etName1!=null && etName1.getText().toString().trim().equalsIgnoreCase("")){
                    etName1.setError("Enter sub category name");
                    etName1.requestFocus();
                }else{
                    if (isEdit){
                        subCategoryEdit();
                    } else{
                        AddSubCategory(selectedCategoryId,etName1.getText().toString().trim());
                    }
                }
            }
        });
    }
    public void AddSubCategory (String category_id,String subcategory_name) {
        restcall.AddSubCategory("AddSubCategory",category_id,subcategory_name,sharedPreference.getStringvalue("user_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCommonResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("API Error", e.getMessage());
                                Toast.makeText(SubAddActivity.this, "This is Wrong Tag", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(SubCommonResponse subCommonResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (subCommonResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)){
                                    etName1.setText("");
                                    finish();
                                }
                                Toast.makeText(SubAddActivity.this, ""+subCommonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


    }

    public void GetCategory(){
        restcall.getCategory("getCategory",sharedPreference.getStringvalue("user_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CatagoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("##",e.getLocalizedMessage());
                                Toast.makeText(SubAddActivity.this, "" +e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CatagoryListResponse catagoryListResponse) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (catagoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                        && catagoryListResponse.getCategoryList()!=null
                                        && catagoryListResponse.getCategoryList().size()>0){

                                    List<CatagoryListResponse.Category> activeCateGories =catagoryListResponse.getCategoryList();
                                    String[] categoryNameArray=new String[activeCateGories.size() +1];
                                    String[] categoryIdArray=new String[activeCateGories.size() +1];

                                    categoryNameArray[0] = "Select Category";
                                    categoryIdArray[0] ="-1";

                                    for (int i =0; i< activeCateGories.size(); i++){
                                        categoryNameArray[i + 1] =activeCateGories.get(i).getCategoryName();
                                        categoryIdArray[i + 1] =activeCateGories.get(i).getCategoryId();
                                    }
                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SubAddActivity.this,
                                            android.R.layout.simple_spinner_item,categoryNameArray);


                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerAdd.setAdapter(arrayAdapter);

                                    spinnerAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                            selectedpos=position;
                                            if (selectedpos >=0 && selectedpos < categoryIdArray.length){
                                                selectedCategoryId = categoryIdArray[selectedpos];
                                                //     selectedCategoryName = categoryNameArray[selectedpos];

                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }
                                Toast.makeText(SubAddActivity.this, ""+catagoryListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


    }

    public  void subCategoryEdit(){
        restcall.editSubCategory("EditSubCategory", selectedCategoryId,
                        etName1.getText().toString(),selectedSubCategoryId,
                        sharedPreference.getStringvalue("user_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCommonResponse>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SubAddActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(SubCommonResponse subCatagoryListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               Toast.makeText(SubAddActivity.this,"select category edit",  Toast.LENGTH_SHORT).show();

                                if (subCatagoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)){
                                    etName1.setText("");
                                    startActivity(new Intent(SubAddActivity.this, ResultSubActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(SubAddActivity.this, subCatagoryListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });


    }

}

























/*
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.apiProject.Cat_and_Sub.Category.networkResponse.CatagoryListResponse;
import com.example.apiProject.Cat_and_Sub.Sub_Category.SubNetworkRespose.SubCatagoryListResponse;
import com.example.apiProject.Cat_and_Sub.Sub_Category.SubNetworkRespose.SubCommonResponse;
import com.example.apiProject.Cat_and_Sub.network.RestClient;
import com.example.apiProject.Cat_and_Sub.network.Restcall;
import com.example.apiProject.R;
import com.example.apiProject.Utils.SharedPreference;
import com.example.apiProject.Utils.VariableBag;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SubAddActivity extends AppCompatActivity {

    AppCompatSpinner secondspinner;
    EditText subcategoryedt;
    SharedPreference sharedPreference;
    Button addsubitembtn;

    int selectedpos = 0;

    String category_id;

    Boolean isEdit = false;


    String selectedCategory;

    String selectedCategoryId;
    String  newSubCategoryName;

    String selectedCategoryName;

    //CategoryListResponse categoryListResponse;
    String subcategoryId;

    String subcategoryName;


    List<SubCatagoryListResponse.SubCategory> subCategoryList;

    List<CatagoryListResponse.Category> categoryListResponseList;

    SubCatagoryListResponse categorySubListResponse;

    Restcall restCall;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_add);

        subcategoryedt = findViewById(R.id.etvName1);

        addsubitembtn = findViewById(R.id.btnAddon);




        sharedPreference  = new SharedPreference(this);

        secondspinner = findViewById(R.id.spinnerAdd);
        restCall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);


        GetCategoryCall();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isEdit = true;
            subcategoryId = extras.getString("subcategoryId");
            category_id = extras.getString("category_id");
            subcategoryName = extras.getString("subcategoryName");
            subcategoryedt.setText(subcategoryName);
            addsubitembtn.setText("Edit");

        }
        else{
            AddSubcategorCall(selectedCategoryId,subcategoryedt.getText().toString().trim());

        }




        //IMP

        addsubitembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedCategoryId == null || selectedCategoryId.isEmpty() || selectedCategoryId.equals("-1")) {
                    Toast.makeText(SubAddActivity.this, "Select the category", Toast.LENGTH_SHORT).show();
                } else if (subcategoryedt.getText().toString().trim().equalsIgnoreCase("")) {
                    subcategoryedt.setError("please add category name");
                    subcategoryedt.requestFocus();
                } else {
                    if(isEdit){
                        EditSubCategoryCall(category_id,subcategoryName);
                    }else{
                        AddSubcategorCall(selectedCategoryId,subcategoryedt.getText().toString().trim());
                        finish();
                    }

                }
            }
        });
    }


    public void AddSubcategorCall(String category_id,String subcategory_name) {
        restCall.AddSubCategory("AddSubCategory",category_id,subcategory_name,sharedPreference.getStringvalue("user_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("##", e.getLocalizedMessage());
                                //Toast.makeText(MainActivity.this,,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                Toast.makeText(SubAddActivity.this, "NO Connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(SubCommonResponse commonSubResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonSubResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {
                                    subcategoryedt.setText("");
                                    startActivity(new Intent(SubAddActivity.this,ResultSubActivity.class));
                                    finish();

                                }
                                Toast.makeText(SubAddActivity.this, commonSubResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }



    private  void GetCategoryCall() {
        restCall.getCategory("getCategory", sharedPreference.getStringvalue("user_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CatagoryListResponse>() {
                    @Override
                    public void onCompleted() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    }
                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(SubAddActivity.this,""+ e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    @Override
                    public void onNext(CatagoryListResponse catagoryListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (catagoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE) && catagoryListResponse.getCategoryList() != null
                                        && catagoryListResponse.getCategoryList().size() > 0) {


                                    List<CatagoryListResponse.Category> activeCategories = catagoryListResponse.getCategoryList();

                                    String[] CategoryArray = new String[activeCategories.size() + 1];
                                    String[] CategoryIdArray = new String[activeCategories.size() + 1];

                                    CategoryArray[0] = "Select Category";
                                    CategoryIdArray[0] = "-1";

                                    for (int i = 0; i < activeCategories.size(); i++) {
                                        CategoryArray[i + 1] = activeCategories.get(i).getCategoryName();
                                        CategoryIdArray[i + 1] = activeCategories.get(i).getCategoryId();
                                    }

                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SubAddActivity.this, android.R.layout.simple_spinner_dropdown_item, CategoryArray);
                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    secondspinner.setAdapter(arrayAdapter);

                                    secondspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            selectedpos = i;

                                            if (selectedpos >= 0 && selectedpos < CategoryIdArray.length) {
                                                selectedCategoryId = CategoryIdArray[selectedpos];
                                                selectedCategoryName = CategoryArray[selectedpos];

                                                //GetSubCategoryCall();
                                            }
                                        }
                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                        }
                                    });
                                    // Your code to populate CategoryArray and CategoryIdArray goes here
                                }


                            }
                        });

                    }
                });
    }


    public void spinnerF(){

    }

    public void EditSubCategoryCall(String categoryId, String newCategoryName) {
        if (categoryId.isEmpty() || newCategoryName.isEmpty()) {
            Toast.makeText(this, "Category ID and new name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        restCall.editSubCategory("EditSubCategory",category_id,newSubCategoryName,subcategoryId,sharedPreference.getStringvalue("user_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SubCommonResponse>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(SubCommonResponse commonSubResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonSubResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {
                                    Toast.makeText(SubAddActivity.this, "Category edited successfully", Toast.LENGTH_SHORT).show();
                                    addsubitembtn.setText("");
                                    Intent intent = new Intent();
                                    intent.putExtra("editedCategoryId", categoryId);
                                    intent.putExtra("editedCategoryName", newCategoryName);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else {
                                    Toast.makeText(SubAddActivity.this, commonSubResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
                });

    }








}

*/





















