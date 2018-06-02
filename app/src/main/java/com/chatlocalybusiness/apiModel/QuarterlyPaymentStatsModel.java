package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by windows on 4/16/2018.
 */

public class QuarterlyPaymentStatsModel implements Serializable {

        @SerializedName("data")
        @Expose
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }



    public class Data implements Serializable{

        @SerializedName("payment_stats")
        @Expose
        private PaymentStats paymentStats;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public PaymentStats getPaymentStats() {
            return paymentStats;
        }

        public void setPaymentStats(PaymentStats paymentStats) {
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

    public class PaymentStats implements Serializable {

        @SerializedName("quarter_1")
        @Expose
        private Quarter1 quarter1;
        @SerializedName("quarter_2")
        @Expose
        private Quarter2 quarter2;
        @SerializedName("quarter_3")
        @Expose
        private Quarter3 quarter3;
        @SerializedName("quarter_4")
        @Expose
        private Quarter4 quarter4;

        public Quarter1 getQuarter1() {
            return quarter1;
        }

        public void setQuarter1(Quarter1 quarter1) {
            this.quarter1 = quarter1;
        }

        public Quarter2 getQuarter2() {
            return quarter2;
        }

        public void setQuarter2(Quarter2 quarter2) {
            this.quarter2 = quarter2;
        }

        public Quarter3 getQuarter3() {
            return quarter3;
        }

        public void setQuarter3(Quarter3 quarter3) {
            this.quarter3 = quarter3;
        }

        public Quarter4 getQuarter4() {
            return quarter4;
        }

        public void setQuarter4(Quarter4 quarter4) {
            this.quarter4 = quarter4;
        }

    }

    public class Quarter1 implements Serializable{

        @SerializedName("sumof_total")
        @Expose
        private String sumofTotal;
        @SerializedName("sumof_tax")
        @Expose
        private String sumofTax;
        @SerializedName("sumof_earnings")
        @Expose
        private String sumofEarnings;

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

        public String getSumofEarnings() {
            return sumofEarnings;
        }

        public void setSumofEarnings(String sumofEarnings) {
            this.sumofEarnings = sumofEarnings;
        }

    }
    public class Quarter2 implements Serializable{

        @SerializedName("sumof_total")
        @Expose
        private String sumofTotal;
        @SerializedName("sumof_tax")
        @Expose
        private String sumofTax;
        @SerializedName("sumof_earnings")
        @Expose
        private String sumofEarnings;

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

        public String getSumofEarnings() {
            return sumofEarnings;
        }

        public void setSumofEarnings(String sumofEarnings) {
            this.sumofEarnings = sumofEarnings;
        }

    }

    public class Quarter3 implements Serializable{

        @SerializedName("sumof_total")
        @Expose
        private String sumofTotal;
        @SerializedName("sumof_tax")
        @Expose
        private String sumofTax;
        @SerializedName("sumof_earnings")
        @Expose
        private String sumofEarnings;

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

        public String getSumofEarnings() {
            return sumofEarnings;
        }

        public void setSumofEarnings(String sumofEarnings) {
            this.sumofEarnings = sumofEarnings;
        }

    }

    public class Quarter4 implements Serializable{

        @SerializedName("sumof_total")
        @Expose
        private String sumofTotal;
        @SerializedName("sumof_tax")
        @Expose
        private String sumofTax;
        @SerializedName("sumof_earnings")
        @Expose
        private String sumofEarnings;

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

        public String getSumofEarnings() {
            return sumofEarnings;
        }

        public void setSumofEarnings(String sumofEarnings) {
            this.sumofEarnings = sumofEarnings;
        }

    }
}
