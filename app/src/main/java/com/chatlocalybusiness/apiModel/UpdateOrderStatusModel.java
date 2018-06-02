package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windows on 4/6/2018.
 */

public class UpdateOrderStatusModel {

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

        @SerializedName("order_detail")
        @Expose
        private OrderDetail orderDetail;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public OrderDetail getOrderDetail() {
            return orderDetail;
        }

        public void setOrderDetail(OrderDetail orderDetail) {
            this.orderDetail = orderDetail;
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
    public class OrderDetail {

        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("paid_date")
        @Expose
        private String paidDate;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPaidDate() {
            return paidDate;
        }

        public void setPaidDate(String paidDate) {
            this.paidDate = paidDate;
        }

    }
}
