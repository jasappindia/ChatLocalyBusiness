package com.chatlocalybusiness.apiModel;

/**
 * Created by windows on 12/8/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OtpVerifyModel implements Serializable {


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

        @SerializedName("home_services")
        @Expose
        private String homeServices;
        @SerializedName("services_km")
        @Expose
        private String servicesKm;

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

    }
    public class CurrentSubscriptionPlan implements Serializable{

        @SerializedName("sp_id")
        @Expose
        private String spId;

        @SerializedName("category_one")
        @Expose
        private String categoryOne;

        @SerializedName("category_two")
        @Expose
        private String categoryTwo;

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

    }

    public class Data implements Serializable{

        @SerializedName("complated_step")
        @Expose
        private String complatedStep;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;
        @SerializedName("user_detail")
        @Expose
        private UserDetail userDetail;

        public String getComplatedStep() {
            return complatedStep;
        }

        public void setComplatedStep(String complatedStep) {
            this.complatedStep = complatedStep;
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

        public UserDetail getUserDetail() {
            return userDetail;
        }

        public void setUserDetail(UserDetail userDetail) {
            this.userDetail = userDetail;
        }

    }

    public class LocationDetail implements Serializable{

        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("pin_code")
        @Expose
        private String pinCode;
        @SerializedName("locality_name")
        @Expose
        private String localityName;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("landmark")
        @Expose
        private String landmark;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getPinCode() {
            return pinCode;
        }

        public void setPinCode(String pinCode) {
            this.pinCode = pinCode;
        }

        public String getLocalityName() {
            return localityName;
        }

        public void setLocalityName(String localityName) {
            this.localityName = localityName;
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

    }

    public class UserDetail implements Serializable{

        @SerializedName("login_key")
        @Expose
        private String loginKey;
        @SerializedName("b_user_id")
        @Expose
        private String bUserId;
        @SerializedName("b_mobile_number")
        @Expose
        private String bMobileNumber;
        @SerializedName("b_full_name")
        @Expose
        private String bFullName;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("send_message")
        @Expose
        private String sendMessage;
        @SerializedName("send_photo")
        @Expose
        private String sendPhoto;
        @SerializedName("send_video")
        @Expose
        private String sendVideo;
        @SerializedName("send_audio")
        @Expose
        private String sendAudio;
        @SerializedName("send_bill")
        @Expose
        private String sendBill;
        @SerializedName("edit_bill")
        @Expose
        private String editBill;
        @SerializedName("block_thead")
        @Expose
        private String blockThead;
        @SerializedName("edit_business_profile")
        @Expose
        private String editBusinessProfile;
        @SerializedName("edit_business_info")
        @Expose
        private String editBusinessInfo;
        @SerializedName("edit_overview")
        @Expose
        private String editOverview;
        @SerializedName("add_products")
        @Expose
        private String addProducts;
        @SerializedName("add_services")
        @Expose
        private String addServices;
        @SerializedName("b_profile_image")
        @Expose
        private String bProfileImage;
        @SerializedName("business_detail")
        @Expose
        private BusinessDetail businessDetail;
        @SerializedName("location_detail")
        @Expose
        private LocationDetail locationDetail;
        @SerializedName("current_subscription_plan")
        @Expose
        private CurrentSubscriptionPlan currentSubscriptionPlan;
        @SerializedName("b_id")
        @Expose
        private String bId;
        @SerializedName("business_name")
        @Expose
        private String businessName;
        @SerializedName("business_logo")
        @Expose
        private String businessLogo;

        public String getLoginKey() {
            return loginKey;
        }

        public void setLoginKey(String loginKey) {
            this.loginKey = loginKey;
        }

        public String getBUserId() {
            return bUserId;
        }

        public void setBUserId(String bUserId) {
            this.bUserId = bUserId;
        }

        public String getBMobileNumber() {
            return bMobileNumber;
        }

        public void setBMobileNumber(String bMobileNumber) {
            this.bMobileNumber = bMobileNumber;
        }

        public String getBFullName() {
            return bFullName;
        }

        public void setBFullName(String bFullName) {
            this.bFullName = bFullName;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getSendMessage() {
            return sendMessage;
        }

        public void setSendMessage(String sendMessage) {
            this.sendMessage = sendMessage;
        }

        public String getSendPhoto() {
            return sendPhoto;
        }

        public void setSendPhoto(String sendPhoto) {
            this.sendPhoto = sendPhoto;
        }

        public String getSendVideo() {
            return sendVideo;
        }

        public void setSendVideo(String sendVideo) {
            this.sendVideo = sendVideo;
        }

        public String getSendAudio() {
            return sendAudio;
        }

        public void setSendAudio(String sendAudio) {
            this.sendAudio = sendAudio;
        }

        public String getSendBill() {
            return sendBill;
        }

        public void setSendBill(String sendBill) {
            this.sendBill = sendBill;
        }

        public String getEditBill() {
            return editBill;
        }

        public void setEditBill(String editBill) {
            this.editBill = editBill;
        }

        public String getBlockThead() {
            return blockThead;
        }

        public void setBlockThead(String blockThead) {
            this.blockThead = blockThead;
        }

        public String getEditBusinessProfile() {
            return editBusinessProfile;
        }

        public void setEditBusinessProfile(String editBusinessProfile) {
            this.editBusinessProfile = editBusinessProfile;
        }

        public String getEditBusinessInfo() {
            return editBusinessInfo;
        }

        public void setEditBusinessInfo(String editBusinessInfo) {
            this.editBusinessInfo = editBusinessInfo;
        }

        public String getEditOverview() {
            return editOverview;
        }

        public void setEditOverview(String editOverview) {
            this.editOverview = editOverview;
        }

        public String getAddProducts() {
            return addProducts;
        }

        public void setAddProducts(String addProducts) {
            this.addProducts = addProducts;
        }

        public String getAddServices() {
            return addServices;
        }

        public void setAddServices(String addServices) {
            this.addServices = addServices;
        }

        public String getBProfileImage() {
            return bProfileImage;
        }

        public void setBProfileImage(String bProfileImage) {
            this.bProfileImage = bProfileImage;
        }

        public BusinessDetail getBusinessDetail() {
            return businessDetail;
        }

        public void setBusinessDetail(BusinessDetail businessDetail) {
            this.businessDetail = businessDetail;
        }

        public LocationDetail getLocationDetail() {
            return locationDetail;
        }

        public void setLocationDetail(LocationDetail locationDetail) {
            this.locationDetail = locationDetail;
        }

        public CurrentSubscriptionPlan getCurrentSubscriptionPlan() {
            return currentSubscriptionPlan;
        }

        public void setCurrentSubscriptionPlan(CurrentSubscriptionPlan currentSubscriptionPlan) {
            this.currentSubscriptionPlan = currentSubscriptionPlan;
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

        public String getBusinessLogo() {
            return businessLogo;
        }

        public void setBusinessLogo(String businessLogo) {
            this.businessLogo = businessLogo;
        }

    }}