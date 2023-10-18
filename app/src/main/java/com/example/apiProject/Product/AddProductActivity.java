package com.example.apiProject.Product;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apiProject.Cat_and_Sub.Category.networkResponse.CommonResponse;
import com.example.apiProject.Cat_and_Sub.Sub_Category.SubNetworkRespose.SubCommonResponse;
import com.example.apiProject.Cat_and_Sub.network.RestClient;
import com.example.apiProject.Cat_and_Sub.network.Restcall;
import com.example.apiProject.R;
import com.example.apiProject.Utils.DialogProgressBar;
import com.example.apiProject.Utils.SharedPreference;
import com.example.apiProject.Utils.VariableBag;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class AddProductActivity extends AppCompatActivity {

    ImageView ivProductImage;
    TextInputEditText etvProductName,etvProductPrice,etvProductDescription;
    MaterialButton btnSubmit,btnCancel;
    DialogProgressBar dialogProgressBar;
    SwitchCompat switchOnOff;
    AppCompatImageButton btnEdit;
    Restcall restCall;
    SharedPreference sharedPreference;
    String currentPhotoPath = "",isVeg = "",categoryId,subCategoryId,productId,productName,productPrice,productDes,productImage,productIsVeg,productOldImage;
    int REQUEST_CAMERA_PERMISSION = 101;
    ActivityResultLauncher<Intent> cameraLauncher;
    File currentPhotoFile;
    TextView tvNonVeg,tvVeg;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product2);
        ivProductImage = findViewById(R.id.ivProductImage);
        etvProductName = findViewById(R.id.etvProductName);
        etvProductPrice = findViewById(R.id.etvProductPrice);
        etvProductDescription = findViewById(R.id.etvProductDescription);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        switchOnOff = findViewById(R.id.switchOnOff);
        btnEdit = findViewById(R.id.btnEdit);
        tvNonVeg = findViewById(R.id.tvNonVeg);
        tvVeg = findViewById(R.id.tvVeg);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dialogProgressBar = new DialogProgressBar(AddProductActivity.this);

         sharedPreference= new SharedPreference(this);
        restCall = RestClient.createService(Restcall.class, VariableBag.BASE_URL,VariableBag.API_KEY);

        Intent intent = getIntent();
        categoryId = intent.getExtras().getString("categoryId");
        subCategoryId = intent.getExtras().getString("subCategoryId");



        if (intent.getExtras().getString("productId") != null && intent.getExtras().getString("productId") != ""){

            productId = intent.getExtras().getString("productId");
            productName = intent.getExtras().getString("productName");
            productDes = intent.getExtras().getString("product_desc");
            productPrice = intent.getExtras().getString("product_price");
            productIsVeg = intent.getExtras().getString("isVeg");
            productImage = intent.getExtras().getString("productImage");
            productOldImage = intent.getExtras().getString("oldProductImage");
            etvProductName.setText(productName);
            etvProductPrice.setText(productPrice);
            etvProductDescription.setText(productDes);

            btnSubmit.setText("Edit");
            if (productIsVeg != null){
                if (productIsVeg.equals("1")){
                    switchOnOff.setChecked(true);
                }
                Glide
                        .with(AddProductActivity.this)
                        .load(productImage)
                        .placeholder(R.drawable.ic_imageholder)
                        .into(ivProductImage);
                flag = true;
            }

        }
        else {
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        currentPhotoPath="";
                        if (checkCameraPermission()) {
                            openCamera();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });
            cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Camera capture was successful, handle the result.
                    displayImage(AddProductActivity.this, ivProductImage, currentPhotoPath);
                } else {
                    Toast.makeText(this, "Not", Toast.LENGTH_SHORT).show();
                }
            });
            flag = false;
        }



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchOnOff.isChecked()){
                    isVeg = "1";
                }
                else {
                    isVeg = "0";
                }
                if (etvProductName.getText().toString().isEmpty()){
                    etvProductName.setError("Enter ProductName");
                }
                else if (etvProductPrice.getText().toString().isEmpty()){
                    etvProductPrice.setError("Enter ProductPrice");
                }
                else if (etvProductDescription.getText().toString().isEmpty()){
                    etvProductDescription.setError("Enter ProductDescription");
                }
                else {
                    dialogProgressBar.createDialog();
                    if (flag == true){
                        editProduct();
                    }
                    else {
                        addProduct();
                    }
                }
            }
        });
    }

    private void displayImage(AddProductActivity addProductActivity, ImageView ivProductImage, String currentPhotoPath) {
        Glide
                .with(addProductActivity)
                .load(currentPhotoPath)
                .placeholder(R.drawable.ic_imageholder)
                .into(ivProductImage);
    }

    void addProduct(){
        RequestBody tag = RequestBody.create(MediaType.parse("text/plain"),"AddProduct");
        RequestBody category_id = RequestBody.create(MediaType.parse("text/plain"), categoryId);
        RequestBody sub_category_id = RequestBody.create(MediaType.parse("text/plain"), subCategoryId);
        RequestBody product_name = RequestBody.create(MediaType.parse("text/plain"), etvProductName.getText().toString());
        RequestBody product_price = RequestBody.create(MediaType.parse("text/plain"), etvProductPrice.getText().toString());
        RequestBody product_desc = RequestBody.create(MediaType.parse("text/plain"), etvProductDescription.getText().toString());
        RequestBody is_veg = RequestBody.create(MediaType.parse("text/plain"), isVeg);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), sharedPreference.getStringvalue("user_id"));
        MultipartBody.Part fileToUploadfile = null;


        if (fileToUploadfile == null && currentPhotoPath != "") {
            try {
                StrictMode.VmPolicy.Builder builder2 = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder2.build());
                File file = new File(currentPhotoPath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                fileToUploadfile = MultipartBody.Part.createFormData("product_image", file.getName(), requestBody);
            } catch (Exception e) {
                Toast.makeText(this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        restCall.AddProduct(tag,category_id,sub_category_id,product_name,product_price,product_desc,is_veg,user_id,fileToUploadfile)
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
                                dialogProgressBar.cancelDialog();
                                Toast.makeText(AddProductActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CommonResponse commonResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialogProgressBar.cancelDialog();
                                Toast.makeText(AddProductActivity.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if (commonResponse.getStatus().equals(VariableBag.SUCCESS_CODE)){
                                    finish();
                                }
                            }
                        });
                    }
                });

    }
    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return false;
        }
        return true;
    }
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.myshop",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraLauncher.launch(takePictureIntent);
            }
        }

    }
    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoFile = image;
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    void editProduct(){

        restCall.EditProduct("EditProduct",categoryId,subCategoryId,productId,
                        etvProductName.getText().toString(),
                        etvProductPrice.getText().toString(),
                        productOldImage,etvProductDescription.getText().toString(),
                        isVeg,productImage,sharedPreference.getStringvalue("user_id"))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialogProgressBar.cancelDialog();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddProductActivity.this, "check internet connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CommonResponse commonResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialogProgressBar.cancelDialog();
                                Toast.makeText(AddProductActivity.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if (commonResponse.getStatus().equals(VariableBag.SUCCESS_CODE)){
                                    finish();
                                }
                            }
                        });
                    }
                });
    }

}