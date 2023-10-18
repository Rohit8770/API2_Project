package com.example.apiProject.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apiProject.Cat_and_Sub.Category.networkResponse.CatagoryListResponse;
import com.example.apiProject.Cat_and_Sub.Category.networkResponse.CommonResponse;
import com.example.apiProject.Cat_and_Sub.Product.ProductAdapter;
import com.example.apiProject.Cat_and_Sub.Product.ResponseProduct.ProductCategoryListResponse;
import com.example.apiProject.Cat_and_Sub.Sub_Category.SubNetworkRespose.SubCatagoryListResponse;
import com.example.apiProject.Cat_and_Sub.Sub_Category.SubNetworkRespose.SubCommonResponse;
import com.example.apiProject.Cat_and_Sub.network.RestClient;
import com.example.apiProject.Cat_and_Sub.network.Restcall;
import com.example.apiProject.R;
import com.example.apiProject.Utils.SharedPreference;
import com.example.apiProject.Utils.VariableBag;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;




public class ProductMainActivity extends AppCompatActivity {


    RecyclerView recyclerViewProduct;
    AppCompatSpinner spinnerCategory,spinnerSubCategory;
    FloatingActionButton btnProductAdd;
    Restcall restCall;
    EditText etvSearch;
    ProductProAdapter productProAdapter;
    ImageView ivClose;
    TextView tvNoData;
    SharedPreference sharedPreference;
    List<String> categoryNameList,subCategoryNameList;
    String categoryId,subCategoryId;

    @Override
    protected void onResume() {
        super.onResume();
        if (categoryId == null){
            getCategory();
        }
        else {
            getProduct();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main);
        recyclerViewProduct = findViewById(R.id.recyclerViewProduct);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerSubCategory = findViewById(R.id.spinnerSubCategory);
        btnProductAdd = findViewById(R.id.btnProductAdd);
        etvSearch = findViewById(R.id.etvSearch);
        ivClose = findViewById(R.id.ivClose);
        tvNoData = findViewById(R.id.tvNoData);

        ivClose.setVisibility(View.GONE);
        etvSearch.setVisibility(View.GONE);
        restCall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        categoryNameList = new ArrayList<>();
        subCategoryNameList = new ArrayList<>();
        //recyclerViewProduct.setVisibility(View.GONE);
        sharedPreference = new SharedPreference(this);

        etvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (productProAdapter != null) {
                    if (!etvSearch.getText().toString().isEmpty()) {
                        ivClose.setVisibility(View.VISIBLE);
                    } else {
                        ivClose.setVisibility(View.GONE);
                    }
                    productProAdapter.Search(charSequence, tvNoData, recyclerViewProduct);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etvSearch.setText("");
                ivClose.setVisibility(View.GONE);
            }
        });
        btnProductAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryId == null){
                    Toast.makeText(ProductMainActivity.this, "Select Category", Toast.LENGTH_SHORT).show();
                }
                else if (subCategoryId == null ){
                    Toast.makeText(ProductMainActivity.this,"Select Sub Category", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(ProductMainActivity.this,AddProductActivity.class);
                    intent.putExtra("categoryId",categoryId);
                    intent.putExtra("subCategoryId",subCategoryId);
                    startActivity(intent);
                }
            }
        });

    }
    void getCategory(){
        restCall.getCategory("getCategory",sharedPreference.getStringvalue("user_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CatagoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProductMainActivity.this, "check internet connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CatagoryListResponse catagoryListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                categoryNameList.clear();
                                categoryNameList.add("Select");
                                for (int i=0;i<catagoryListResponse.getCategoryList().size();i++){
                                    categoryNameList.add(catagoryListResponse.getCategoryList().get(i).getCategoryName());
                                }
                                ArrayAdapter arrayAdapter = new ArrayAdapter(ProductMainActivity.this, android.R.layout.simple_spinner_dropdown_item,categoryNameList);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCategory.setAdapter(arrayAdapter);
                                spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        for (int j=0;j<catagoryListResponse.getCategoryList().size();j++){
                                            if (adapterView.getSelectedItem().equals("Select")){
                                                spinnerSubCategory.setSelection(0);
                                                subCategoryNameList.clear();
                                                subCategoryNameList.add("Select");
                                                ArrayAdapter arrayAdapter = new ArrayAdapter(ProductMainActivity.this, android.R.layout.simple_spinner_dropdown_item,subCategoryNameList);
                                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                spinnerSubCategory.setAdapter(arrayAdapter);
                                            }
                                            if (catagoryListResponse.getCategoryList().get(j).getCategoryName().equals(adapterView.getSelectedItem())) {
                                                categoryId = catagoryListResponse.getCategoryList().get(j).getCategoryId();
                                                getSubCategory();
                                                break;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                        });
                    }
                });
    }
    void getSubCategory(){
        restCall.getSubCategory("getSubCategory",categoryId,sharedPreference.getStringvalue("user_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCatagoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProductMainActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(SubCatagoryListResponse subCatagoryListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (subCatagoryListResponse.getSubCategoryList() != null && subCatagoryListResponse.getSubCategoryList().size()>0 && subCatagoryListResponse.getStatus().equals(VariableBag.SUCCESS_CODE)){
                                    subCategoryNameList.clear();

                                    subCategoryNameList.add("Select");
                                    for (int k=0;k<subCatagoryListResponse.getSubCategoryList().size();k++){
                                        subCategoryNameList.add(subCatagoryListResponse.getSubCategoryList().get(k).getSubcategoryName());
                                    }
                                    ArrayAdapter arrayAdapter = new ArrayAdapter(ProductMainActivity.this, android.R.layout.simple_spinner_dropdown_item,subCategoryNameList);
                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerSubCategory.setAdapter(arrayAdapter);
                                    spinnerSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                            for (int j=0;j<subCatagoryListResponse.getSubCategoryList().size();j++){
                                                if (subCatagoryListResponse.getSubCategoryList().get(j).getSubcategoryName().equals(adapterView.getSelectedItem())) {
                                                    subCategoryId = subCatagoryListResponse.getSubCategoryList().get(j).getSubCategoryId();
                                                    getProduct();
                                                    break;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }
                            }
                        });
                    }
                });
    }
    void getProduct(){
        restCall.getProduct("getProduct",sharedPreference.getStringvalue("user_id"),categoryId,subCategoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ProductCategoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProductMainActivity.this, "check internet connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(ProductCategoryListResponse productCategoryListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (productCategoryListResponse.getProductList() != null && productCategoryListResponse.getProductList().size()>0 && productCategoryListResponse.getStatus().equals(VariableBag.SUCCESS_CODE)){
                                    etvSearch.setVisibility(View.VISIBLE);
                                    recyclerViewProduct.setVisibility(View.VISIBLE);

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductMainActivity.this);
                                    productProAdapter = new ProductProAdapter(productCategoryListResponse.getProductList(),ProductMainActivity.this);
                                    recyclerViewProduct.setLayoutManager(layoutManager);
                                    recyclerViewProduct.setAdapter(productProAdapter);

                                    productProAdapter.setUpInterface(new ProductProAdapter.ItemClick() {
                                        @Override
                                        public void deleteClick(ProductCategoryListResponse.Product productListResponse) {
                                          //  deleteProduct(productCategoryListResponse.getProductList());
                                        }

                                        @Override
                                        public void editClick(ProductCategoryListResponse.Product productListResponse) {
                                            Intent intent = new Intent(ProductMainActivity.this,AddProductActivity.class);
                                            intent.putExtra("productId",productListResponse.getProductId());
                                            intent.putExtra("categoryId",categoryId);
                                            intent.putExtra("subCategoryId",subCategoryId);
                                            intent.putExtra("productName",productListResponse.getProductName());
                                            intent.putExtra("product_desc",productListResponse.getProductDesc());
                                            intent.putExtra("product_price",productListResponse.getProductPrice());
                                            intent.putExtra("isVeg",productListResponse.getIsVeg());
                                            intent.putExtra("productImage",productListResponse.getProductImage());
                                            intent.putExtra("oldProductImage",productListResponse.getOldProductImage());
                                            startActivity(intent);
                                        }
                                    });
                                }
                                else {
                                    recyclerViewProduct.setVisibility(View.GONE);
                                }

                            }
                        });
                    }
                });
    }
    void deleteProduct(String productId){
        restCall.DeleteProduct("DeleteProduct",sharedPreference.getStringvalue("user_id"),productId)
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
                                Toast.makeText(ProductMainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CommonResponse subCommonResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProductMainActivity.this, subCommonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if (subCommonResponse.getStatus().equals(VariableBag.SUCCESS_CODE)){
                                    getProduct();
                                }
                            }
                        });

                    }
                });
    }
}