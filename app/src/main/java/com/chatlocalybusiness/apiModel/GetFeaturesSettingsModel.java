package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windows on 12/25/2017.
 */

public class GetFeaturesSettingsModel {

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

    @SerializedName("user_setting")
    @Expose
    private UserSetting userSetting;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;

    public UserSetting getUserSetting() {
        return userSetting;
    }

    public void setUserSetting(UserSetting userSetting) {
        this.userSetting = userSetting;
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

public class UserSetting {

    @SerializedName("b_user_id")
    @Expose
    private String bUserId;
    @SerializedName("b_ut_id")
    @Expose
    private String bUtId;
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
 @SerializedName("untag_thread")
    @Expose
    private String unTagThread;
 @SerializedName("tag_thread")
    @Expose
    private String tagThread;
 @SerializedName("unblock_thread")
    @Expose
    private String unBlockThread;

    public String getUnTagThread() {
        return unTagThread;
    }

    public void setUnTagThread(String unTagThread) {
        this.unTagThread = unTagThread;
    }

    public String getTagThread() {
        return tagThread;
    }

    public void setTagThread(String tagThread) {
        this.tagThread = tagThread;
    }

    public String getUnBlockThread() {
        return unBlockThread;
    }

    public void setUnBlockThread(String unBlockThread) {
        this.unBlockThread = unBlockThread;
    }

    public String getBUserId() {
        return bUserId;
    }

    public void setBUserId(String bUserId) {
        this.bUserId = bUserId;
    }

    public String getBUtId() {
        return bUtId;
    }

    public void setBUtId(String bUtId) {
        this.bUtId = bUtId;
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

}
}
