package com.example.apiProject.Cat_and_Sub.Category.Output;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apiProject.Cat_and_Sub.Category.networkResponse.CatagoryListResponse;
import com.example.apiProject.Cat_and_Sub.Category.networkResponse.CommonResponse;
import com.example.apiProject.Utils.SharedPreference;
import com.example.apiProject.Cat_and_Sub.network.RestClient;
import com.example.apiProject.Cat_and_Sub.network.Restcall;
import com.example.apiProject.R;
import com.example.apiProject.Utils.VariableBag;

import rx.Subscriber;
import rx.schedulers.Schedulers;
public class ReturnDataActivity extends AppCompatActivity {

    private RecyclerView rcvData;
    private EditText etSearch;
    private Button btnAdd;
    private CategoryAdapter categoryAdapter;
    private Restcall restcall;
    SharedPreference sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_data);

        rcvData = findViewById(R.id.rcvData);
        etSearch = findViewById(R.id.etSearch);
        btnAdd = findViewById(R.id.btnAdd);
        sharedPreference=new SharedPreference(this);
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        rcvData.setLayoutManager(new LinearLayoutManager(ReturnDataActivity.this));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (categoryAdapter!=null){
                    categoryAdapter.Search(charSequence, rcvData);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReturnDataActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
    protected  void onResume(){
        super.onResume();
        GetCategoryCall();

    }


        private  void GetCategoryCall(){
        restcall.getCategory("getCategory",sharedPreference.getStringvalue("user_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
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
                                Toast.makeText(ReturnDataActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(CatagoryListResponse catagoryListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (catagoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE) && catagoryListResponse.getCategoryList()!=null
                                        && catagoryListResponse.getCategoryList().size() > 0){

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(ReturnDataActivity.this);
                                    rcvData.setLayoutManager(layoutManager);
                                    categoryAdapter=new CategoryAdapter(ReturnDataActivity.this,catagoryListResponse.getCategoryList());
                                    rcvData.setAdapter(categoryAdapter);



                                    categoryAdapter.SetUpInterFace(new CategoryAdapter.CategoryClick() {
                                        @Override
                                        public void EditClick(CatagoryListResponse.Category category) {
                                            Intent intent = new Intent(ReturnDataActivity.this, AddActivity.class);
                                            intent.putExtra("category_id", category.getCategoryId());
                                            intent.putExtra("category_name", category.getCategoryName());
                                            startActivity(intent);
                                        }

                                        public void DeleteClick(CatagoryListResponse.Category category) {
                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReturnDataActivity.this);
                                            alertDialog.setTitle("Alert!!");
                                            alertDialog.setMessage("Are you sure, you want to delete " + category.getCategoryName());

                                            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    DeleteCategoryCall(category.getCategoryId());
                                                    dialogInterface.dismiss();

                                                }
                                            });

                                            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            });
                                            alertDialog.show();

                                        }
                                    });
                                } else {
                                    Toast.makeText(ReturnDataActivity.this, catagoryListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
        }

    public void DeleteCategoryCall(String category_id) {
        restcall.DeleteCategory("DeleteCategory",sharedPreference.getStringvalue("user_id"),category_id)
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
                                Toast.makeText(ReturnDataActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CommonResponse commonResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {
                                    Toast.makeText(ReturnDataActivity.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    GetCategoryCall();
                                } else {
                                    Toast.makeText(ReturnDataActivity.this, ""+commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }
}



