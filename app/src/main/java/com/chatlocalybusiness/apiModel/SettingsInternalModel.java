package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by windows on 4/6/2018.
 */

public class SettingsInternalModel {


    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class BusinessManager {

        @SerializedName("b_user_id")
        @Expose
        private String bUserId;
        @SerializedName("b_ut_id")
        @Expose
        private String bUtId;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("b_full_name")
        @Expose
        private String bFullName;
        @SerializedName("designation")
        @Expose
        private String designation;

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

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
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

    }

    public class BusinessUsers {

        @SerializedName("business_manager")
        @Expose
        private List<BusinessManager> businessManager = null;
        @SerializedName("business_worker")
        @Expose
        private List<BusinessWorker> businessWorker = null;

        @SerializedName("remaining_workers_limit")
        @Expose
        private int remaining_workers_limit;

        @SerializedName("remaining_managers_limit")
        @Expose
        private int remaining_managers_limit;

        public int getRemaining_workers_limit() {
            return remaining_workers_limit;
        }

        public void setRemaining_workers_limit(int remaining_workers_limit) {
            this.remaining_workers_limit = remaining_workers_limit;
        }

        public int getRemaining_managers_limit() {
            return remaining_managers_limit;
        }

        public void setRemaining_managers_limit(int remaining_managers_limit) {
            this.remaining_managers_limit = remaining_managers_limit;
        }

        public List<BusinessManager> getBusinessManager() {
            return businessManager;
        }

        public void setBusinessManager(List<BusinessManager> businessManager) {
            this.businessManager = businessManager;
        }

        public List<BusinessWorker> getBusinessWorker() {
            return businessWorker;
        }

        public void setBusinessWorker(List<BusinessWorker> businessWorker) {
            this.businessWorker = businessWorker;
        }

    }

    public class BusinessWorker {

        @SerializedName("b_user_id")
        @Expose
        private String bUserId;
        @SerializedName("b_ut_id")
        @Expose
        private String bUtId;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("b_full_name")
        @Expose
        private String bFullName;
        @SerializedName("designation")
        @Expose
        private String designation;

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

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
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

    }

    public class Data {

        @SerializedName("business_users")
        @Expose
        private BusinessUsers businessUsers;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public BusinessUsers getBusinessUsers() {
            return businessUsers;
        }

        public void setBusinessUsers(BusinessUsers businessUsers) {
            this.businessUsers = businessUsers;
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
