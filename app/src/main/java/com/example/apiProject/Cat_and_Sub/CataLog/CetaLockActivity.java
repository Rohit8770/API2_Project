package com.example.apiProject.Cat_and_Sub.CataLog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apiProject.Cat_and_Sub.CataLog.CataLogResponse.CatalogListResponse;
import com.example.apiProject.Cat_and_Sub.CataLog.CateLockAdapter.CatalogCataegoryAdapter;
import com.example.apiProject.Utils.DialogProgressBar;
import com.example.apiProject.Utils.SharedPreference;
import com.example.apiProject.Cat_and_Sub.network.RestClient;
import com.example.apiProject.Cat_and_Sub.network.Restcall;
import com.example.apiProject.R;
import com.example.apiProject.Utils.Tools;
import com.example.apiProject.Utils.VariableBag;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CetaLockActivity extends AppCompatActivity {

    RecyclerView rcvCataCategory;
    Restcall restcall;
   // DialogProgressBar dialogProgressBar;
    SharedPreference sharedPreference;
    CatalogCataegoryAdapter catalogCataegoryAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceta_lock);

        rcvCataCategory=findViewById(R.id.rcvCataCategory);

        sharedPreference = new SharedPreference(this);
      //  dialogProgressBar=new DialogProgressBar(this);
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
       // dialogProgressBar.cancelDialog();
        GetCatalog();

    }
    private void GetCatalog() {
        restcall.GetCatalog("GetCatalog", sharedPreference.getStringvalue("user_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CatalogListResponse>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                          //      dialogProgressBar.createDialog();
                                Toast.makeText(CetaLockActivity.this, "Check internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(CatalogListResponse catalogueListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            //    dialogProgressBar.createDialog();
                                if (catalogueListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                && catalogueListResponse.getCategoryList()!=null&& catalogueListResponse.getCategoryList().size()>0) {

                                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(CetaLockActivity.this);
                                    rcvCataCategory.setLayoutManager(layoutManager);
                                    catalogCataegoryAdapter = new CatalogCataegoryAdapter(CetaLockActivity.this,catalogueListResponse.getCategoryList());
                                    rcvCataCategory.setAdapter(catalogCataegoryAdapter);
                                }
                            }
                        });
                    }
                });

    }
}
