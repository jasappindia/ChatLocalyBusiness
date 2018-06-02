package com.chatlocalybusiness.apiModel;

/**
 * Created by windows on 12/8/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UpdateProfileModel {

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

    @SerializedName("b_user_id")
    @Expose
    private String bUserId;
    @SerializedName("b_full_name")
    @Expose
    private String bFullName;
    @SerializedName("b_profile_image")
    @Expose
    private String bProfileImage;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("business_setup")
    @Expose
    private String business_setup;

    public String getBusiness_setup() {
        return business_setup;
    }
    public String getBUserId() {
        return bUserId;
    }

    public void setBUserId(String bUserId) {
        this.bUserId = bUserId;
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


}