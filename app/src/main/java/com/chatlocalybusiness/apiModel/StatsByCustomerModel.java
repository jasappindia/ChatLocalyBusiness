package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by windows on 4/16/2018.
 */

public class StatsByCustomerModel implements Serializable{


        @SerializedName("data")
        @Expose
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    public class Customer  implements Serializable{

        @SerializedName("sumof_total")
        @Expose
        private String sumofTotal;
        @SerializedName("sumof_tax")
        @Expose
        private String sumofTax;
        @SerializedName("c_full_name")
        @Expose
        private String cFullName;
        @SerializedName("c_user_id")
        @Expose
        private String cUserId;

        @SerializedName("sumof_earnings")
        @Expose
        private String sumofEarnings;

        public String getSumofEarnings() {
            return sumofEarnings;
        }

        public void setSumofEarnings(String sumofEarnings) {
            this.sumofEarnings = sumofEarnings;
        }

        public String getSumofTotal() {
            return sumofTotal;
        }

        public void setSumofTotal(String sumofTotal) {
            this.sumofTotal = sumofTotal;
        }

        public String getSumofTax() {
            return sumofTax;
        }

        public void setSumofTax(String sumofTax) {
            this.sumofTax = sumofTax;
        }

        public String getCFullName() {
            return cFullName;
        }

        public void setCFullName(String cFullName) {
            this.cFullName = cFullName;
        }

        public String getCUserId() {
            return cUserId;
        }

        public void setCUserId(String cUserId) {
            this.cUserId = cUserId;
        }

    }

    public class Data  implements Serializable{

        @SerializedName("payment_stats")
        @Expose
        private List<PaymentStat> paymentStats = null;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public List<PaymentStat> getPaymentStats() {
            return paymentStats;
        }

        public void setPaymentStats(List<PaymentStat> paymentStats) {
            this.paymentStats = paymentStats;
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

    public class PaymentStat  implements Serializable{

        @SerializedName("sumof_total")
        @Expose
        private String sumofTotal;
        @SerializedName("sumof_tax")
        @Expose
        private String sumofTax;
        @SerializedName("month")
        @Expose
        private String month;
        @SerializedName("customers")
        @Expose
        private List<Customer> customers = null;

        public String getSumofTotal() {
            return sumofTotal;
        }

        public void setSumofTotal(String sumofTotal) {
            this.sumofTotal = sumofTotal;
        }

        public String getSumofTax() {
            return sumofTax;
        }

        public void setSumofTax(String sumofTax) {
            this.sumofTax = sumofTax;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public List<Customer> getCustomers() {
            return customers;
        }

        public void setCustomers(List<Customer> customers) {
            this.customers = customers;
        }

    }
}
