package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windows on 2/6/2018.
 */

public class AdminVerifyModel {

        @SerializedName("data")
        @Expose
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }


    public class Data {

        @SerializedName("business_setup")
        @Expose
        private String businessSetup;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;
        @SerializedName("w_b_user_id")
        @Expose
        private String wBUserId;
        @SerializedName("b_full_name")
        @Expose
        private String bFullName;
        @SerializedName("b_profile_image")
        @Expose
        private String bProfileImage;
        @SerializedName("b_id")
        @Expose
        private String bId;
        @SerializedName("login_key")
        @Expose
        private String loginKey;
        @SerializedName("complated_steps")
        @Expose
        private String complatedSteps;
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


        @SerializedName("business_logo")
        @Expose
        private String businessLogo;
        @SerializedName("business_name")
        @Expose
        private String businessName;

        public String getBusinessLogo() {
            return businessLogo;
        }

        public void setBusinessLogo(String businessLogo) {
            this.businessLogo = businessLogo;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getBusinessSetup() {
            return businessSetup;
        }

        public void setBusinessSetup(String businessSetup) {
            this.businessSetup = businessSetup;
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

        public String getWBUserId() {
            return wBUserId;
        }

        public void setWBUserId(String wBUserId) {
            this.wBUserId = wBUserId;
        }

        public String getBFullName() {
            return bFullName;
        }

        public void setBFullName(String bFullName) {
            this.bFullName = bFullName;
        }

        public String getBProfileImage() {
            return bProfileImage;
        }

        public void setBProfileImage(String bProfileImage) {
            this.bProfileImage = bProfileImage;
        }

        public String getBId() {
            return bId;
        }

        public void setBId(String bId) {
            this.bId = bId;
        }

        public String getLoginKey() {
            return loginKey;
        }

        public void setLoginKey(String loginKey) {
            this.loginKey = loginKey;
        }

        public String getComplatedSteps() {
            return complatedSteps;
        }

        public void setComplatedSteps(String complatedSteps) {
            this.complatedSteps = complatedSteps;
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

    }
}
