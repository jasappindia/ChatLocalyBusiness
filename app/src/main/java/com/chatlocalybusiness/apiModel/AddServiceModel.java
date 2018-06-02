package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windows on 1/17/2018.
 */

public class AddServiceModel {

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

    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("b_user_id")
    @Expose
    private String bUserId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getBUserId() {
        return bUserId;
    }

    public void setBUserId(String bUserId) {
        this.bUserId = bUserId;
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
