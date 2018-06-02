package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by windows on 5/21/2018.
 */

public class ProductDetailModel {

        @SerializedName("data")
        @Expose
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }



    public class BusinessDetail {

        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("b_id")
        @Expose
        private String bId;
        @SerializedName("business_name")
        @Expose
        private String businessName;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("product_status")
        @Expose
        private String productStatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("brand_name")
        @Expose
        private String brandName;
        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("product_images")
        @Expose
        private List<ProductImage> productImages = null;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getBId() {
            return bId;
        }

        public void setBId(String bId) {
            this.bId = bId;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getProductStatus() {
            return productStatus;
        }

        public void setProductStatus(String productStatus) {
            this.productStatus = productStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public List<ProductImage> getProductImages() {
            return productImages;
        }

        public void setProductImages(List<ProductImage> productImages) {
            this.productImages = productImages;
        }

    }
    public class Data {

        @SerializedName("business_detail")
        @Expose
        private List<BusinessDetail> businessDetail = null;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public List<BusinessDetail> getBusinessDetail() {
            return businessDetail;
        }

        public void setBusinessDetail(List<BusinessDetail> businessDetail) {
            this.businessDetail = businessDetail;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

    }
    public class ProductImage {

        @SerializedName("pim_id")
        @Expose
        private String pimId;
        @SerializedName("product_image")
        @Expose
        private String productImage;

        public String getPimId() {
            return pimId;
        }

        public void setPimId(String pimId) {
            this.pimId = pimId;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

    }
}
