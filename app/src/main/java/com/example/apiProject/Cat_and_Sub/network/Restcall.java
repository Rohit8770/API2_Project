package com.example.apiProject.Cat_and_Sub.network;

import com.example.apiProject.Cat_and_Sub.Category.networkResponse.CatagoryListResponse;
import com.example.apiProject.Cat_and_Sub.Category.networkResponse.CommonResponse;
import com.example.apiProject.Cat_and_Sub.CataLog.CataLogResponse.CatalogListResponse;
import com.example.apiProject.Cat_and_Sub.Product.ResponseProduct.ProductCategoryListResponse;
import com.example.apiProject.Cat_and_Sub.Sub_Category.SubNetworkRespose.SubCatagoryListResponse;
import com.example.apiProject.Cat_and_Sub.Sub_Category.SubNetworkRespose.SubCommonResponse;
import com.example.apiProject.Cat_and_Sub.User.UserNetwork.LoginResponse;
import com.example.apiProject.Cat_and_Sub.User.UserNetwork.UserCommonResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Single;

public interface Restcall {

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CommonResponse> AddCategory(
            @Field("tag") String tag,
            @Field("user_id") String user_id,
            @Field("category_name") String category_name);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CommonResponse> EditCategory(
            @Field("tag") String tag,
            @Field("category_name") String category_name,
            @Field("category_id") String category_id,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CommonResponse> ActiveDectiveCategory(
            @Field("tag") String tag,
            @Field("category_status") String category_status,
            @Field("category_id") String category_id);
    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CatagoryListResponse> getCategory(
            @Field("tag") String tag,
            @Field("user_id") String user_id);
    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CommonResponse> DeleteCategory(
            @Field("tag") String tag,
            @Field("user_id") String user_id,
            @Field("category_id") String category_id);







    @FormUrlEncoded
    @POST("SubCategoryController.php")
    Single<SubCommonResponse> AddSubCategory(
            @Field("tag") String tag,
            @Field("category_id") String category_id,
            @Field("subcategory_name") String subcategory_name,
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("SubCategoryController.php")
    Single<SubCatagoryListResponse> getSubCategory(
            @Field("tag") String tag,
            @Field("category_id") String category_id,
            @Field("user_id") String user_id);
    @FormUrlEncoded
    @POST("SubCategoryController.php")
    Single<SubCommonResponse> editSubCategory(
            @Field("tag") String tag,
            @Field("category_id")String category_id,
            @Field("subcategory_name") String subcategory_name,
            @Field("sub_category_id") String sub_category_id,
            @Field("user_id") String user_id);
    @FormUrlEncoded
    @POST("SubCategoryController.php")
    Single<SubCommonResponse> DeleteSubCategory(
            @Field("tag") String tag,
            @Field("user_id") String user_id,
           @Field("sub_category_id") String sub_category_id);



    @FormUrlEncoded
    @POST("UserController.php")
    Single<UserCommonResponse> AddUser(
            @Field("tag") String tag,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("UserController.php")
    Single<LoginResponse> LoginUser(
            @Field("tag") String tag,
            @Field("email") String email,
            @Field("password") String password);



@Multipart
@POST("ProductController.php")
Single<CommonResponse> AddProduct(
        @Part("tag") RequestBody tag,
        @Part("category_id") RequestBody category_id,
        @Part("sub_category_id") RequestBody sub_category_id,
        @Part("product_name") RequestBody product_name,
        @Part("product_price") RequestBody product_price,
        @Part("product_desc") RequestBody product_desc,
        @Part("is_veg") RequestBody is_veg,
        @Part("user_id") RequestBody user_id,
        @Part MultipartBody.Part product_image);


    @FormUrlEncoded
    @POST("ProductController.php")
    Single<ProductCategoryListResponse> getProduct(
            @Field("tag") String tag,
            @Field("category_id") String category_id,
            @Field("sub_category_id") String sub_category_id,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("ProductController.php")
    Single<CommonResponse> DeleteProduct(
            @Field("tag") String tag,
            @Field("product_id") String product_id,
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("ProductController.php")
    Single<CommonResponse> EditProduct(
            @Field("tag") String tag,
            @Field("category_id") String category_id,
            @Field("sub_category_id") String sub_category_id,
            @Field("product_id") String product_id,
            @Field("product_name") String product_name,
            @Field("product_price") String product_price,
            @Field("old_product_image") String old_product_image,
            @Field("product_desc") String product_desc,
            @Field("is_veg") String is_veg,
            @Field("user_id") String user_id,
            @Field("product_image") String product_image);



    @FormUrlEncoded
    @POST("ProductController.php")
    Single<CatalogListResponse> GetCatalog(
            @Field("tag") String tag,
            @Field("user_id") String user_id);


}


