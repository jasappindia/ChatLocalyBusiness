package com.chatlocalybusiness.getterSetterModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by windows on 3/13/2018.
 */

public class ProductDetailsModel implements Serializable {

    private String businessName;
    private String productName;
    private String productId;
    private String price;
    private String discription ;
    private String discount;
    private String proThumbnail=null;
    private String sku=null;
    private String brandName=null;
    private String brandId=null;
    private String productStatus=null;
    private List<ProductImages> productImages;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProThumbnail() {
        return proThumbnail;
    }

    public void setProThumbnail(String proThumbnail) {
        this.proThumbnail = proThumbnail;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public List<ProductImages> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImages> productImages) {
        this.productImages = productImages;
    }

     public class ProductImages implements Serializable {
         String imageName;
         String imageId;

         public String getImageId() {
             return imageId;
         }

         public void setImageId(String imageId) {
             this.imageId = imageId;
         }

         public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }
    }

}
