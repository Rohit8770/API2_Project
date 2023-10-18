package com.example.apiProject.Cat_and_Sub.CataLog.CataLogResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CatalogListResponse {
    @SerializedName("categoryList")
    @Expose
    private List<Category> categoryList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

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

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public class Category{
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("category_status")
        @Expose
        private String categoryStatus;
        @SerializedName("sub_category_list")
        @Expose
        private List<SubCategory> subCategoryList;

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

        public void setCategoryStatus(String categoryStatus) {
            this.categoryStatus = categoryStatus;
        }

        public List<SubCategory> getSubCategoryList() {
            return subCategoryList;
        }

        public void setSubCategoryList(List<SubCategory> subCategoryList) {
            this.subCategoryList = subCategoryList;
        }
        public class SubCategory{
            @SerializedName("sub_category_id")
            @Expose
            private String subCategoryId;
            @SerializedName("category_id")
            @Expose
            private String categoryId;
            @SerializedName("subcategory_name")
            @Expose
            private String subcategoryName;
            @SerializedName("subcategory_status")
            @Expose
            private String subcategoryStatus;
            @SerializedName("product_list")
            @Expose
            private List<Product> productList;

            private Boolean isSelect;

            public Boolean getSelect() {
                return isSelect;
            }

            public void setSelect(Boolean select) {
                isSelect = select;
            }

            public String getSubCategoryId() {
                return subCategoryId;
            }

            public void setSubCategoryId(String subCategoryId) {
                this.subCategoryId = subCategoryId;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getSubcategoryName() {
                return subcategoryName;
            }

            public void setSubcategoryName(String subcategoryName) {
                this.subcategoryName = subcategoryName;
            }

            public String getSubcategoryStatus() {
                return subcategoryStatus;
            }

            public void setSubcategoryStatus(String subcategoryStatus) {
                this.subcategoryStatus = subcategoryStatus;
            }

            public List<Product> getProductList() {
                return productList;
            }

            public void setProductList(List<Product> productList) {
                this.productList = productList;
            }

            public class Product{
                @SerializedName("product_id")
                @Expose
                private String productId;
                @SerializedName("product_name")
                @Expose
                private String productName;
                @SerializedName("product_image")
                @Expose
                private String productImage;
                @SerializedName("old_product_image")
                @Expose
                private String oldProductImage;
                @SerializedName("product_price")
                @Expose
                private String productPrice;
                @SerializedName("product_desc")
                @Expose
                private String productDesc;
                @SerializedName("is_veg")
                @Expose
                private String isVeg;
                @SerializedName("product_total_rating")
                @Expose
                private String productTotalRating;
                @SerializedName("product_average_rating")
                @Expose
                private String productAverageRating;

                public String getProductId() {
                    return productId;
                }

                public void setProductId(String productId) {
                    this.productId = productId;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }
                public String getProductImage() {
                    return productImage;
                }

                public void setProductImage(String productImage) {
                    this.productImage = productImage;
                }

                public String getOldProductImage() {
                    return oldProductImage;
                }

                public void setOldProductImage(String oldProductImage) {
                    this.oldProductImage = oldProductImage;
                }

                public String getProductPrice() {
                    return productPrice;
                }

                public void setProductPrice(String productPrice) {
                    this.productPrice = productPrice;
                }

                public String getProductDesc() {
                    return productDesc;
                }

                public void setProductDesc(String productDesc) {
                    this.productDesc = productDesc;
                }

                public String getIsVeg() {
                    return isVeg;
                }

                public void setIsVeg(String isVeg) {
                    this.isVeg = isVeg;
                }

                public String getProductTotalRating() {
                    return productTotalRating;
                }

                public void setProductTotalRating(String productTotalRating) {
                    this.productTotalRating = productTotalRating;
                }

                public String getProductAverageRating() {
                    return productAverageRating;
                }

                public void setProductAverageRating(String productAverageRating) {
                    this.productAverageRating = productAverageRating;
                }
            }
        }
    }
}