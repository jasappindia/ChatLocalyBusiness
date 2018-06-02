package com.chatlocalybusiness.getterSetterModel;

import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;

import java.io.Serializable;
import java.util.List;

/**
 * Created by windows on 3/13/2018.
 */

public class ServiceDetailsModel implements Serializable {

    private String businessName;
    private String serviceName;
    private String serviceId;
    private String servicePrice;
    private String discription ;
    private String discount;
    private String proThumbnail=null;
    private String sku=null;
    private String caption;
    private List<ServiceBrands> serviceBrands=null;
    private String brandId=null;
    private String serviceStatus=null;
    private List<ServiceImages> productImages;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
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

    public String getProThumbnail() {
        return proThumbnail;
    }

    public void setProThumbnail(String proThumbnail) {
        this.proThumbnail = proThumbnail;
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

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }


    public List<ServiceBrands> getServiceBrands() {
        return serviceBrands;
    }

    public void setServiceBrands(List<ServiceBrands> serviceBrands) {
        this.serviceBrands = serviceBrands;
    }

    public List<ServiceImages> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ServiceImages> productImages) {
        this.productImages = productImages;
    }


    public class ServiceImages implements Serializable {
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
    public class ServiceBrands implements Serializable
    {
        String brandName;
        String brandId;

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
    }

}
