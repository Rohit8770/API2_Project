package com.example.apiProject.Cat_and_Sub.Category.networkResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CatagoryListResponse {

    @SerializedName("categoryList")
    @Expose
    private List<Category> categoryList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CatagoryListResponse(List<Category> categoryList, String message, String status) {
        this.categoryList = categoryList;
        this.message = message;
        this.status = status;
    }

    public class Category {

        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("category_status")
        @Expose
        private String categoryStatus;


        public Category(String categoryId, String categoryName, String categoryStatus) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.categoryStatus = categoryStatus;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryStatus() {
            return categoryStatus;
        }



    }
}


