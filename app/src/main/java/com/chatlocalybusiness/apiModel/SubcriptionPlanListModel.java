package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by windows on 12/14/2017.
 */

public class SubcriptionPlanListModel {

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

        @SerializedName("sp_name")
        @Expose
        private String spName;
        @SerializedName("business_name")
        @Expose
        private String businessName;

        public String getSpName() {
            return spName;
        }

        public void setSpName(String spName) {
            this.spName = spName;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

    }

    public class Data {

        @SerializedName("sp_plan_list")
        @Expose
        private List<SpPlanList> spPlanList = null;
        @SerializedName("current_subscription_plan")
        @Expose
        private CurrentSubscriptionPlan currentSubscriptionPlan;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public List<SpPlanList> getSpPlanList() {
            return spPlanList;
        }

        public void setSpPlanList(List<SpPlanList> spPlanList) {
            this.spPlanList = spPlanList;
        }

        public CurrentSubscriptionPlan getCurrentSubscriptionPlan() {
            return currentSubscriptionPlan;
        }

        public void setCurrentSubscriptionPlan(CurrentSubscriptionPlan currentSubscriptionPlan) {
            this.currentSubscriptionPlan = currentSubscriptionPlan;
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


    public class PriceList {

        @SerializedName("spr_id")
        @Expose
        private String sprId;
        @SerializedName("display_price")
        @Expose
        private String displayPrice;
        @SerializedName("pay_price")
        @Expose
        private String payPrice;
        @SerializedName("pay_duration")
        @Expose
        private String payDuration;
        @SerializedName("pay_duration_type")
        @Expose
        private String payDurationType;

        public String getSprId() {
            return sprId;
        }

        public void setSprId(String sprId) {
            this.sprId = sprId;
        }

        public String getDisplayPrice() {
            return displayPrice;
        }

        public void setDisplayPrice(String displayPrice) {
            this.displayPrice = displayPrice;
        }

        public String getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(String payPrice) {
            this.payPrice = payPrice;
        }

        public String getPayDuration() {
            return payDuration;
        }

        public void setPayDuration(String payDuration) {
            this.payDuration = payDuration;
        }

        public String getPayDurationType() {
            return payDurationType;
        }

        public void setPayDurationType(String payDurationType) {
            this.payDurationType = payDurationType;
        }

    }

    public class SpPlanList {

        @SerializedName("sp_id")
        @Expose
        private String spId;
        @SerializedName("sp_name")
        @Expose
        private String spName;
        @SerializedName("business_manager")
        @Expose
        private String businessManager;
        @SerializedName("business_worker")
        @Expose
        private String businessWorker;
        @SerializedName("categories")
        @Expose
        private String categories;
        @SerializedName("block_stats")
        @Expose
        private String blockStats;
        @SerializedName("payment_stats")
        @Expose
        private String paymentStats;
        @SerializedName("price_list")
        @Expose
        private List<PriceList> priceList = null;
        @SerializedName("feature_list")
        @Expose
        private List<String> featureList = null;

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

        public String getBusinessManager() {
            return businessManager;
        }

        public void setBusinessManager(String businessManager) {
            this.businessManager = businessManager;
        }

        public String getBusinessWorker() {
            return businessWorker;
        }

        public void setBusinessWorker(String businessWorker) {
            this.businessWorker = businessWorker;
        }

        public String getCategories() {
            return categories;
        }

        public void setCategories(String categories) {
            this.categories = categories;
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

        public List<PriceList> getPriceList() {
            return priceList;
        }

        public void setPriceList(List<PriceList> priceList) {
            this.priceList = priceList;
        }

        public List<String> getFeatureList() {
            return featureList;
        }

        public void setFeatureList(List<String> featureList) {
            this.featureList = featureList;
        }

    }
}