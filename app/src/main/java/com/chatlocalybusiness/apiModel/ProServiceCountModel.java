package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windows on 4/23/2018.
 */

public class ProServiceCountModel {


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

        @SerializedName("products_count")
        @Expose
        private Integer productsCount;
        @SerializedName("services_count")
        @Expose
        private Integer servicesCount;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public Integer getProductsCount() {
            return productsCount;
        }

        public void setProductsCount(Integer productsCount) {
            this.productsCount = productsCount;
        }

        public Integer getServicesCount() {
            return servicesCount;
        }

        public void setServicesCount(Integer servicesCount) {
            this.servicesCount = servicesCount;
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
