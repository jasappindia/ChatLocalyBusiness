package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by windows on 2/7/2018.
 */

public class BusinessInfoModelNew implements Serializable {


        @SerializedName("data")
        @Expose
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;


    }
    public class BusinessDetail implements Serializable{

        @SerializedName("b_id")
        @Expose
        private String bId;
        @SerializedName("business_name")
        @Expose
        private String businessName;
        @SerializedName("home_services")
        @Expose
        private String homeServices;
        @SerializedName("services_km")
        @Expose
        private String servicesKm;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("pin_code")
        @Expose
        private String pinCode;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("landmark")
        @Expose
        private String landmark;
        @SerializedName("locality_name")
        @Expose
        private String localityName;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("b_cm_code")
        @Expose
        private String bCmCode;
        @SerializedName("b_mobile_number")
        @Expose
        private String bMobileNumber;
        @SerializedName("business_banner")
        @Expose
        private String businessBanner;
        @SerializedName("business_logo")
        @Expose
        private String businessLogo;
        @SerializedName("business_images")
        @Expose
        private List<BusinessImage> businessImages = null;
        @SerializedName("rating_count")
        @Expose
        private Integer ratingCount;
        @SerializedName("avg_rating")
        @Expose
        private String avgRating;
        @SerializedName("ratings")
        @Expose
        private Ratings ratings;
        @SerializedName("products")
        @Expose
        private List<Product> products = null;
        @SerializedName("services")
        @Expose
        private List<Service> services = null;
        @SerializedName("category_one")
        @Expose
        private String categoryOne;
        @SerializedName("category_two")
        @Expose
        private String categoryTwo;
        @SerializedName("current_subscription_plan")
        @Expose
        private CurrentSubscriptionPlan currentSubscriptionPlan;

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

        public String getHomeServices() {
            return homeServices;
        }

        public void setHomeServices(String homeServices) {
            this.homeServices = homeServices;
        }

        public String getServicesKm() {
            return servicesKm;
        }

        public void setServicesKm(String servicesKm) {
            this.servicesKm = servicesKm;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPinCode() {
            return pinCode;
        }

        public void setPinCode(String pinCode) {
            this.pinCode = pinCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getLocalityName() {
            return localityName;
        }

        public void setLocalityName(String localityName) {
            this.localityName = localityName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getBCmCode() {
            return bCmCode;
        }

        public void setBCmCode(String bCmCode) {
            this.bCmCode = bCmCode;
        }

        public String getBMobileNumber() {
            return bMobileNumber;
        }

        public void setBMobileNumber(String bMobileNumber) {
            this.bMobileNumber = bMobileNumber;
        }

        public String getBusinessBanner() {
            return businessBanner;
        }

        public void setBusinessBanner(String businessBanner) {
            this.businessBanner = businessBanner;
        }

        public String getBusinessLogo() {
            return businessLogo;
        }

        public void setBusinessLogo(String businessLogo) {
            this.businessLogo = businessLogo;
        }

        public List<BusinessImage> getBusinessImages() {
            return businessImages;
        }

        public void setBusinessImages(List<BusinessImage> businessImages) {
            this.businessImages = businessImages;
        }

        public Integer getRatingCount() {
            return ratingCount;
        }

        public void setRatingCount(Integer ratingCount) {
            this.ratingCount = ratingCount;
        }

        public String getAvgRating() {
            return avgRating;
        }

        public void setAvgRating(String avgRating) {
            this.avgRating = avgRating;
        }

        public Ratings getRatings() {
            return ratings;
        }

        public void setRatings(Ratings ratings) {
            this.ratings = ratings;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public List<Service> getServices() {
            return services;
        }

        public void setServices(List<Service> services) {
            this.services = services;
        }

        public String getCategoryOne() {
            return categoryOne;
        }

        public void setCategoryOne(String categoryOne) {
            this.categoryOne = categoryOne;
        }

        public String getCategoryTwo() {
            return categoryTwo;
        }

        public void setCategoryTwo(String categoryTwo) {
            this.categoryTwo = categoryTwo;
        }

        public CurrentSubscriptionPlan getCurrentSubscriptionPlan() {
            return currentSubscriptionPlan;
        }

        public void setCurrentSubscriptionPlan(CurrentSubscriptionPlan currentSubscriptionPlan) {
            this.currentSubscriptionPlan = currentSubscriptionPlan;
        }

    }
    public class BusinessImage implements Serializable{

        @SerializedName("bim_id")
        @Expose
        private String bimId;
        @SerializedName("image_name")
        @Expose
        private String imageName;

        public String getBimId() {
            return bimId;
        }

        public void setBimId(String bimId) {
            this.bimId = bimId;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

    }

    public class CurrentSubscriptionPlan implements Serializable{

        @SerializedName("sp_id")
        @Expose
        private String spId;
        @SerializedName("sp_name")
        @Expose
        private String spName;
        @SerializedName("business_manager_limit")
        @Expose
        private String businessManagerLimit;
        @SerializedName("business_worker_limit")
        @Expose
        private String businessWorkerLimit;
        @SerializedName("categories_limit")
        @Expose
        private String categoriesLimit;
        @SerializedName("block_stats")
        @Expose
        private String blockStats;
        @SerializedName("payment_stats")
        @Expose
        private String paymentStats;
        @SerializedName("strat_date")
        @Expose
        private String stratDate;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("category_one")
        @Expose
        private String categoryOne;
        @SerializedName("category_two")
        @Expose
        private String categoryTwo;

        public String getSpId() {
            return spId;
        }

        public void setSpId(String spId) {
            this.spId = spId;
        }

        public String getSpName() {
            return spName;
        }

        public void setSpName(String spName) {
            this.spName = spName;
        }

        public String getBusinessManagerLimit() {
            return businessManagerLimit;
        }

        public void setBusinessManagerLimit(String businessManagerLimit) {
            this.businessManagerLimit = businessManagerLimit;
        }

        public String getBusinessWorkerLimit() {
            return businessWorkerLimit;
        }

        public void setBusinessWorkerLimit(String businessWorkerLimit) {
            this.businessWorkerLimit = businessWorkerLimit;
        }

        public String getCategoriesLimit() {
            return categoriesLimit;
        }

        public void setCategoriesLimit(String categoriesLimit) {
            this.categoriesLimit = categoriesLimit;
        }

        public String getBlockStats() {
            return blockStats;
        }

        public void setBlockStats(String blockStats) {
            this.blockStats = blockStats;
        }

        public String getPaymentStats() {
            return paymentStats;
        }

        public void setPaymentStats(String paymentStats) {
            this.paymentStats = paymentStats;
        }

        public String getStratDate() {
            return stratDate;
        }

        public void setStratDate(String stratDate) {
            this.stratDate = stratDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getCategoryOne() {
            return categoryOne;
        }

        public void setCategoryOne(String categoryOne) {
            this.categoryOne = categoryOne;
        }

        public String getCategoryTwo() {
            return categoryTwo;
        }

        public void setCategoryTwo(String categoryTwo) {
            this.categoryTwo = categoryTwo;
        }

    }
    public class Data implements Serializable{

        @SerializedName("business_detail")
        @Expose
        private BusinessDetail businessDetail;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public BusinessDetail getBusinessDetail() {
            return businessDetail;
        }

        public void setBusinessDetail(BusinessDetail businessDetail) {
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
    public class Product implements Serializable{

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

    public class ProductImage implements Serializable {

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

    public class Ratings implements Serializable{

        @SerializedName("one_star")
        @Expose
        private Integer oneStar;
        @SerializedName("two_star")
        @Expose
        private Integer twoStar;
        @SerializedName("three_star")
        @Expose
        private Integer threeStar;
        @SerializedName("four_star")
        @Expose
        private Integer fourStar;
        @SerializedName("five_star")
        @Expose
        private Integer fiveStar;

        public Integer getOneStar() {
            return oneStar;
        }

        public void setOneStar(Integer oneStar) {
            this.oneStar = oneStar;
        }

        public Integer getTwoStar() {
            return twoStar;
        }

        public void setTwoStar(Integer twoStar) {
            this.twoStar = twoStar;
        }

        public Integer getThreeStar() {
            return threeStar;
        }

        public void setThreeStar(Integer threeStar) {
            this.threeStar = threeStar;
        }

        public Integer getFourStar() {
            return fourStar;
        }

        public void setFourStar(Integer fourStar) {
            this.fourStar = fourStar;
        }

        public Integer getFiveStar() {
            return fiveStar;
        }

        public void setFiveStar(Integer fiveStar) {
            this.fiveStar = fiveStar;
        }

    }

    public class Service implements Serializable{

        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("b_id")
        @Expose
        private String bId;
        @SerializedName("business_name")
        @Expose
        private String businessName;
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
        private List<ServiceBrand> serviceBrands = null;

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
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

        public List<ServiceBrand> getServiceBrands() {
            return serviceBrands;
        }

        public void setServiceBrands(List<ServiceBrand> serviceBrands) {
            this.serviceBrands = serviceBrands;
        }

    }
    public class ServiceBrand implements Serializable{

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
    public class ServiceImage implements Serializable{

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
}
