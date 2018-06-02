package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windows on 12/18/2017.
 */

public class BusinessBannerModel {

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

    @SerializedName("b_id")
    @Expose
    private Integer bId;
    @SerializedName("b_user_id")
    @Expose
    private String bUserId;
    @SerializedName("step")
    @Expose
    private String step;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;

    public Integer getBId() {
        return bId;
    }

    public void setBId(Integer bId) {
        this.bId = bId;
    }

    public String getBUserId() {
        return bUserId;
    }

    public void setBUserId(String bUserId) {
        this.bUserId = bUserId;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
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