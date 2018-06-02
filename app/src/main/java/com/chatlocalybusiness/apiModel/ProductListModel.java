package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by windows on 3/13/2018.
 */

public class ProductListModel implements Serializable {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class BlockedProduct implements Serializable{

        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("b_id")
        @Expose
        private String bId;
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
        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("product_status")
        @Expose
        private String productStatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("brand_name")
        @Expose
        private String brandName;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("product_images")
        @Expose
        private List<ProductImage___> productImages = null;

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

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
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

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public List<ProductImage___> getProductImages() {
            return productImages;
        }

        public void setProductImages(List<ProductImage___> productImages) {
            this.productImages = productImages;
        }

    }

    public class Data implements Serializable{

        @SerializedName("remaining_count")
        @Expose
        private Integer remainingCount;
        @SerializedName("product_list")
        @Expose
        private ProductList productList;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public Integer getRemainingCount() {
            return remainingCount;
        }

        public void setRemainingCount(Integer remainingCount) {
            this.remainingCount = remainingCount;
        }

        public ProductList getProductList() {
            return productList;
        }

        public void setProductList(ProductList productList) {
            this.productList = productList;
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
    public class ProductImage implements Serializable{

        @SerializedName("pim_id")
        @Expose
        private String pimId;
        @SerializedName("product_image")
        @Expose
        private String productImage;
       /* @SerializedName("image_name")
        @Expose
        private String imageName;*/

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

       /* public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }
*/
    }

    public class ProductImage_ implements Serializable {

        @SerializedName("pim_id")
        @Expose
        private String pimId;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("image_name")
        @Expose
        private String imageName;

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

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

    }

    public class ProductImage__ implements Serializable{

        @SerializedName("pim_id")
        @Expose
        private String pimId;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("image_name")
        @Expose
        private String imageName;

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

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

    }
    public class ProductImage___ implements Serializable {

        @SerializedName("pim_id")
        @Expose
        private String pimId;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("image_name")
        @Expose
        private String imageName;

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

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

    }

    public class ProductList implements Serializable{

        @SerializedName("publish_product")
        @Expose
        private List<PublishProduct> publishProduct = null;
        @SerializedName("unpublish_product")
        @Expose
        private List<UnpublishProduct> unpublishProduct = null;
        @SerializedName("unapproved_product")
        @Expose
        private List<UnapprovedProduct> unapprovedProduct = null;
        @SerializedName("blocked_product")
        @Expose
        private List<BlockedProduct> blockedProduct = null;

        public List<PublishProduct> getPublishProduct() {
            return publishProduct;
        }

        public void setPublishProduct(List<PublishProduct> publishProduct) {
            this.publishProduct = publishProduct;
        }

        public List<UnpublishProduct> getUnpublishProduct() {
            return unpublishProduct;
        }

        public void setUnpublishProduct(List<UnpublishProduct> unpublishProduct) {
            this.unpublishProduct = unpublishProduct;
        }

        public List<UnapprovedProduct> getUnapprovedProduct() {
            return unapprovedProduct;
        }

        public void setUnapprovedProduct(List<UnapprovedProduct> unapprovedProduct) {
            this.unapprovedProduct = unapprovedProduct;
        }

        public List<BlockedProduct> getBlockedProduct() {
            return blockedProduct;
        }

        public void setBlockedProduct(List<BlockedProduct> blockedProduct) {
            this.blockedProduct = blockedProduct;
        }

    }
    public class PublishProduct implements Serializable{

        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("b_id")
        @Expose
        private String bId;
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
        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("product_status")
        @Expose
        private String productStatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("brand_name")
        @Expose
        private String brandName;
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

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
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

    public class UnapprovedProduct implements Serializable{

        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("b_id")
        @Expose
        private String bId;
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
        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("product_status")
        @Expose
        private String productStatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("brand_name")
        @Expose
        private String brandName;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("product_images")
        @Expose
        private List<ProductImage__> productImages = null;

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

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
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

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public List<ProductImage__> getProductImages() {
            return productImages;
        }

        public void setProductImages(List<ProductImage__> productImages) {
            this.productImages = productImages;
        }

    }

    public class UnpublishProduct implements Serializable{

        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("b_id")
        @Expose
        private String bId;
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
        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("product_status")
        @Expose
        private String productStatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("brand_name")
        @Expose
        private String brandName;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("product_images")
        @Expose
        private List<ProductImage_> productImages = null;

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

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
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

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public List<ProductImage_> getProductImages() {
            return productImages;
        }

        public void setProductImages(List<ProductImage_> productImages) {
            this.productImages = productImages;
        }

    }
}
