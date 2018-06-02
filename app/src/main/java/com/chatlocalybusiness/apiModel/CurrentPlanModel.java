package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windows on 12/22/2017.
 */

public class CurrentPlanModel {


    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class CurrentSubscriptionPlan {

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
        @SerializedName("payment_type")
        @Expose
        private String paymentType;
        @SerializedName("sp_name_label")
        @Expose
        private String spNameLabel;
        @SerializedName("day_left")
        @Expose
        private String dayLeft;
        @SerializedName("end_date_label")
        @Expose
        private String endDateLabel;

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

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getSpNameLabel() {
            return spNameLabel;
        }

        public void setSpNameLabel(String spNameLabel) {
            this.spNameLabel = spNameLabel;
        }

        public String getDayLeft() {
            return dayLeft;
        }

        public void setDayLeft(String dayLeft) {
            this.dayLeft = dayLeft;
        }

        public String getEndDateLabel() {
            return endDateLabel;
        }

        public void setEndDateLabel(String endDateLabel) {
            this.endDateLabel = endDateLabel;
        }

    }

    public class Data {
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;
        @SerializedName("current_subscription_plan")
        @Expose
        private CurrentSubscriptionPlan currentSubscriptionPlan;

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

        public CurrentSubscriptionPlan getCurrentSubscriptionPlan() {
            return currentSubscriptionPlan;
        }

        public void setCurrentSubscriptionPlan(CurrentSubscriptionPlan currentSubscriptionPlan) {
            this.currentSubscriptionPlan = currentSubscriptionPlan;
        }

    }
}