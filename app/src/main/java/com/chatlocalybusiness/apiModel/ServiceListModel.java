package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by windows on 2/14/2018.
 */

public class ServiceListModel {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class BlockedService {

        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("service_name")
        @Expose
        private String serviceName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("service_price")
        @Expose
        private String servicePrice;
        @SerializedName("service_label")
        @Expose
        private String serviceLabel;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("service_status")
        @Expose
        private String serviceStatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("service_image")
        @Expose
        private String serviceImage;
        @SerializedName("service_images")
        @Expose
        private List<ServiceImage___> serviceImages = null;
        @SerializedName("service_brands")
        @Expose
        private List<ServiceBrands___> serviceBrands;

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getServicePrice() {
            return servicePrice;
        }

        public void setServicePrice(String servicePrice) {
            this.servicePrice = servicePrice;
        }

        public String getServiceLabel() {
            return serviceLabel;
        }

        public void setServiceLabel(String serviceLabel) {
            this.serviceLabel = serviceLabel;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(String serviceStatus) {
            this.serviceStatus = serviceStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getServiceImage() {
            return serviceImage;
        }

        public void setServiceImage(String serviceImage) {
            this.serviceImage = serviceImage;
        }

        public List<ServiceImage___> getServiceImages() {
            return serviceImages;
        }

        public void setServiceImages(List<ServiceImage___> serviceImages) {
            this.serviceImages = serviceImages;
        }

        public List<ServiceBrands___> getServiceBrands() {
            return serviceBrands;
        }

        public void setServiceBrands(List<ServiceBrands___> serviceBrands) {
            this.serviceBrands = serviceBrands;
        }

    }

    public class Data {

        @SerializedName("remaining_count")
        @Expose
        private Integer remainingCount;
        @SerializedName("service_list")
        @Expose
        private ServiceList serviceList;
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

        public ServiceList getServiceList() {
            return serviceList;
        }

        public void setServiceList(ServiceList serviceList) {
            this.serviceList = serviceList;
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

    public class PublishService {

        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("service_name")
        @Expose
        private String serviceName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("service_price")
        @Expose
        private String servicePrice;
        @SerializedName("service_label")
        @Expose
        private String serviceLabel;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("service_status")
        @Expose
        private String serviceStatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("service_image")
        @Expose
        private String serviceImage;
        @SerializedName("service_images")
        @Expose
        private List<ServiceImage> serviceImages = null;
        @SerializedName("service_brands")
        @Expose
        private List<ServiceBrands> serviceBrands;

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getServicePrice() {
            return servicePrice;
        }

        public void setServicePrice(String servicePrice) {
            this.servicePrice = servicePrice;
        }

        public String getServiceLabel() {
            return serviceLabel;
        }

        public void setServiceLabel(String serviceLabel) {
            this.serviceLabel = serviceLabel;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(String serviceStatus) {
            this.serviceStatus = serviceStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getServiceImage() {
            return serviceImage;
        }

        public void setServiceImage(String serviceImage) {
            this.serviceImage = serviceImage;
        }

        public List<ServiceImage> getServiceImages() {
            return serviceImages;
        }

        public void setServiceImages(List<ServiceImage> serviceImages) {
            this.serviceImages = serviceImages;
        }

        public List<ServiceBrands> getServiceBrands() {
            return serviceBrands;
        }

        public void setServiceBrands(List<ServiceBrands> serviceBrands) {
            this.serviceBrands = serviceBrands;
        }

    }

    public class ServiceBrands {

        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("brand_name")
        @Expose
        private String brandName;

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

    }

    public class ServiceBrands_ {

        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("brand_name")
        @Expose
        private String brandName;

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

    }

    public class ServiceBrands__ {

        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("brand_name")
        @Expose
        private String brandName;

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

    }

    public class ServiceBrands___ {

        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("brand_name")
        @Expose
        private String brandName;

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

    }

    public class ServiceImage {

        @SerializedName("sim_id")
        @Expose
        private String simId;
        @SerializedName("image_name")
        @Expose
        private String imageName;

        public String getSimId() {
            return simId;
        }

        public void setSimId(String simId) {
            this.simId = simId;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

    }

    public class ServiceImage_ {

        @SerializedName("sim_id")
        @Expose
        private String simId;
        @SerializedName("image_name")
        @Expose
        private String imageName;

        public String getSimId() {
            return simId;
        }

        public void setSimId(String simId) {
            this.simId = simId;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

    }

    public class ServiceImage__ {

        @SerializedName("sim_id")
        @Expose
        private String simId;
        @SerializedName("image_name")
        @Expose
        private String imageName;

        public String getSimId() {
            return simId;
        }

        public void setSimId(String simId) {
            this.simId = simId;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

    }

    public class ServiceImage___ {

        @SerializedName("sim_id")
        @Expose
        private String simId;
        @SerializedName("image_name")
        @Expose
        private String imageName;

        public String getSimId() {
            return simId;
        }

        public void setSimId(String simId) {
            this.simId = simId;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

    }

    public class ServiceList {

        @SerializedName("publish_service")
        @Expose
        private List<PublishService> publishService = null;
        @SerializedName("unpublish_service")
        @Expose
        private List<UnpublishService> unpublishService = null;
        @SerializedName("unapproved_service")
        @Expose
        private List<UnapprovedService> unapprovedService = null;
        @SerializedName("blocked_service")
        @Expose
        private List<BlockedService> blockedService = null;

        public List<PublishService> getPublishService() {
            return publishService;
        }

        public void setPublishService(List<PublishService> publishService) {
            this.publishService = publishService;
        }

        public List<UnpublishService> getUnpublishService() {
            return unpublishService;
        }

        public void setUnpublishService(List<UnpublishService> unpublishService) {
            this.unpublishService = unpublishService;
        }

        public List<UnapprovedService> getUnapprovedService() {
            return unapprovedService;
        }

        public void setUnapprovedService(List<UnapprovedService> unapprovedService) {
            this.unapprovedService = unapprovedService;
        }

        public List<BlockedService> getBlockedService() {
            return blockedService;
        }

        public void setBlockedService(List<BlockedService> blockedService) {
            this.blockedService = blockedService;
        }

    }

    public class UnapprovedService {

        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("service_name")
        @Expose
        private String serviceName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("service_price")
        @Expose
        private String servicePrice;
        @SerializedName("service_label")
        @Expose
        private String serviceLabel;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("service_status")
        @Expose
        private String serviceStatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("service_image")
        @Expose
        private String serviceImage;
        @SerializedName("service_images")
        @Expose
        private List<ServiceImage__> serviceImages = null;
        @SerializedName("service_brands")
        @Expose
        private List<ServiceBrands__> serviceBrands;

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getServicePrice() {
            return servicePrice;
        }

        public void setServicePrice(String servicePrice) {
            this.servicePrice = servicePrice;
        }

        public String getServiceLabel() {
            return serviceLabel;
        }

        public void setServiceLabel(String serviceLabel) {
            this.serviceLabel = serviceLabel;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(String serviceStatus) {
            this.serviceStatus = serviceStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getServiceImage() {
            return serviceImage;
        }

        public void setServiceImage(String serviceImage) {
            this.serviceImage = serviceImage;
        }

        public List<ServiceImage__> getServiceImages() {
            return serviceImages;
        }

        public void setServiceImages(List<ServiceImage__> serviceImages) {
            this.serviceImages = serviceImages;
        }

        public List<ServiceBrands__> getServiceBrands() {
            return serviceBrands;
        }

        public void setServiceBrands(List<ServiceBrands__> serviceBrands) {
            this.serviceBrands = serviceBrands;
        }

    }

    public class UnpublishService {

        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("service_name")
        @Expose
        private String serviceName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("service_price")
        @Expose
        private String servicePrice;
        @SerializedName("service_label")
        @Expose
        private String serviceLabel;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("service_status")
        @Expose
        private String serviceStatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("service_image")
        @Expose
        private String serviceImage;
        @SerializedName("service_images")
        @Expose
        private List<ServiceImage_> serviceImages = null;
        @SerializedName("service_brands")
        @Expose
        private List<ServiceBrands_ >serviceBrands=null;

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getServicePrice() {
            return servicePrice;
        }

        public void setServicePrice(String servicePrice) {
            this.servicePrice = servicePrice;
        }

        public String getServiceLabel() {
            return serviceLabel;
        }

        public void setServiceLabel(String serviceLabel) {
            this.serviceLabel = serviceLabel;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(String serviceStatus) {
            this.serviceStatus = serviceStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getServiceImage() {
            return serviceImage;
        }

        public void setServiceImage(String serviceImage) {
            this.serviceImage = serviceImage;
        }

        public List<ServiceImage_> getServiceImages() {
            return serviceImages;
        }

        public void setServiceImages(List<ServiceImage_> serviceImages) {
            this.serviceImages = serviceImages;
        }

        public List<ServiceBrands_> getServiceBrands() {
            return serviceBrands;
        }

        public void setServiceBrands(List<ServiceBrands_> serviceBrands) {
            this.serviceBrands = serviceBrands;
        }

    }
}
