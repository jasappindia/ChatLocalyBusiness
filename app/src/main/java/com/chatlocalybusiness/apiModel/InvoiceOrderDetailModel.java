package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by windows on 3/30/2018.
 */

public class InvoiceOrderDetailModel {


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
        @SerializedName("order_token")
        @Expose
        private String orderToken;
        @SerializedName("b_user_id")
        @Expose
        private String bUserId;
        @SerializedName("b_id")
        @Expose
        private String bId;
        @SerializedName("c_user_id")
        @Expose
        private String cUserId;
        @SerializedName("buyer_mobile")
        @Expose
        private String buyerMobile;
        @SerializedName("buyer_name")
        @Expose
        private String buyerName;
        @SerializedName("address_1")
        @Expose
        private String address1;
        @SerializedName("address_2")
        @Expose
        private String address2;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("bill_date")
        @Expose
        private String billDate;

        @SerializedName("paid_date")
        @Expose
        private String paidDate;
        @SerializedName("bill_due_date")
        @Expose
        private String billDueDate;
        @SerializedName("sub_total")
        @Expose
        private String subTotal;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("order_status")
        @Expose
        private String orderStatus;
        @SerializedName("show_rounding_off")
        @Expose
        private String showRoundingOff;
        @SerializedName("use_icon")
        @Expose
        private String useIcon;
        @SerializedName("use_location")
        @Expose
        private String useLocation;
        @SerializedName("show_bill_date")
        @Expose
        private String showBillDate;
        @SerializedName("show_bill_due_date")
        @Expose
        private String showBillDueDate;
        @SerializedName("create_date")
        @Expose
        private String createDate;
        @SerializedName("b_full_name")
        @Expose
        private String bFullName;
        @SerializedName("b_cm_code")
        @Expose
        private String bCmCode;
        @SerializedName("b_mobile_number")
        @Expose
        private String bMobileNumber;
        @SerializedName("c_full_name")
        @Expose
        private String cFullName;
        @SerializedName("c_cm_code")
        @Expose
        private String cCmCode;
        @SerializedName("c_mobile_number")
        @Expose
        private String cMobileNumber;

        @SerializedName("business_name")
        @Expose
        private String businessName;

        @SerializedName("business_logo")
        @Expose
        private String businessLogo;
        @SerializedName("products")
        @Expose
        private List<Product> products = null;
        @SerializedName("send_orders")
        @Expose
        private List<SendOrder> sendOrders = null;
        @SerializedName("applozic_group_id")
        @Expose
        private String applozicGroupId;

        @SerializedName("confirmation_code_status")
        @Expose
        private String confirmationCodeStatus;

        public String getConfirmationCodeStatus() {
            return confirmationCodeStatus;
        }

        public void setConfirmationCodeStatus(String confirmationCodeStatus) {
            this.confirmationCodeStatus = confirmationCodeStatus;
        }

        public String getApplozicGroupId() {
            return applozicGroupId;
        }

        public void setApplozicGroupId(String applozicGroupId) {
            this.applozicGroupId = applozicGroupId;
        }

        public String getPaidDate() {
            return paidDate;
        }

        public void setPaidDate(String paidDate) {
            this.paidDate = paidDate;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getBusinessLogo() {
            return businessLogo;
        }

        public void setBusinessLogo(String businessLogo) {
            this.businessLogo = businessLogo;
        }


        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderToken() {
            return orderToken;
        }

        public void setOrderToken(String orderToken) {
            this.orderToken = orderToken;
        }

        public String getBUserId() {
            return bUserId;
        }

        public void setBUserId(String bUserId) {
            this.bUserId = bUserId;
        }

        public String getBId() {
            return bId;
        }

        public void setBId(String bId) {
            this.bId = bId;
        }

        public String getCUserId() {
            return cUserId;
        }

        public void setCUserId(String cUserId) {
            this.cUserId = cUserId;
        }

        public String getBuyerMobile() {
            return buyerMobile;
        }

        public void setBuyerMobile(String buyerMobile) {
            this.buyerMobile = buyerMobile;
        }

        public String getBuyerName() {
            return buyerName;
        }

        public void setBuyerName(String buyerName) {
            this.buyerName = buyerName;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getBillDate() {
            return billDate;
        }

        public void setBillDate(String billDate) {
            this.billDate = billDate;
        }

        public String getBillDueDate() {
            return billDueDate;
        }

        public void setBillDueDate(String billDueDate) {
            this.billDueDate = billDueDate;
        }

        public String getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(String subTotal) {
            this.subTotal = subTotal;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getShowRoundingOff() {
            return showRoundingOff;
        }

        public void setShowRoundingOff(String showRoundingOff) {
            this.showRoundingOff = showRoundingOff;
        }

        public String getUseIcon() {
            return useIcon;
        }

        public void setUseIcon(String useIcon) {
            this.useIcon = useIcon;
        }

        public String getUseLocation() {
            return useLocation;
        }

        public void setUseLocation(String useLocation) {
            this.useLocation = useLocation;
        }

        public String getShowBillDate() {
            return showBillDate;
        }

        public void setShowBillDate(String showBillDate) {
            this.showBillDate = showBillDate;
        }

        public String getShowBillDueDate() {
            return showBillDueDate;
        }

        public void setShowBillDueDate(String showBillDueDate) {
            this.showBillDueDate = showBillDueDate;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getBFullName() {
            return bFullName;
        }

        public void setBFullName(String bFullName) {
            this.bFullName = bFullName;
        }

        public String getBCmCode() {
            return bCmCode;
        }

        public void setBCmCode(String bCmCode) {
            this.bCmCode = bCmCode;
        }

        public String getBMobileNumber() {
            return bMobileNumber;
        }

        public void setBMobileNumber(String bMobileNumber) {
            this.bMobileNumber = bMobileNumber;
        }

        public String getCFullName() {
            return cFullName;
        }

        public void setCFullName(String cFullName) {
            this.cFullName = cFullName;
        }

        public String getCCmCode() {
            return cCmCode;
        }

        public void setCCmCode(String cCmCode) {
            this.cCmCode = cCmCode;
        }

        public String getCMobileNumber() {
            return cMobileNumber;
        }

        public void setCMobileNumber(String cMobileNumber) {
            this.cMobileNumber = cMobileNumber;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public List<SendOrder> getSendOrders() {
            return sendOrders;
        }

        public void setSendOrders(List<SendOrder> sendOrders) {
            this.sendOrders = sendOrders;
        }

    }
    public class Product {

        @SerializedName("productorder_id")
        @Expose
        private String productorderId;
        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_description")
        @Expose
        private String productDescription;
        @SerializedName("product_qty")
        @Expose
        private String productQty;
        @SerializedName("product_unit")
        @Expose
        private String productUnit;
        @SerializedName("product_price")
        @Expose
        private String productPrice;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("discount_price")
        @Expose
        private String discountPrice;
        @SerializedName("cgst")
        @Expose
        private String cgst;
        @SerializedName("cgst_price")
        @Expose
        private String cgstPrice;
        @SerializedName("sgst")
        @Expose
        private String sgst;
        @SerializedName("sgst_price")
        @Expose
        private String sgstPrice;
        @SerializedName("total_price")
        @Expose
        private String totalPrice;

        public String getProductorderId() {
            return productorderId;
        }

        public void setProductorderId(String productorderId) {
            this.productorderId = productorderId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

        public String getProductQty() {
            return productQty;
        }

        public void setProductQty(String productQty) {
            this.productQty = productQty;
        }

        public String getProductUnit() {
            return productUnit;
        }

        public void setProductUnit(String productUnit) {
            this.productUnit = productUnit;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getCgst() {
            return cgst;
        }

        public void setCgst(String cgst) {
            this.cgst = cgst;
        }

        public String getCgstPrice() {
            return cgstPrice;
        }

        public void setCgstPrice(String cgstPrice) {
            this.cgstPrice = cgstPrice;
        }

        public String getSgst() {
            return sgst;
        }

        public void setSgst(String sgst) {
            this.sgst = sgst;
        }

        public String getSgstPrice() {
            return sgstPrice;
        }

        public void setSgstPrice(String sgstPrice) {
            this.sgstPrice = sgstPrice;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

    }
    public class SendOrder {

        @SerializedName("send_order_id")
        @Expose
        private String sendOrderId;
        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("b_user_id")
        @Expose
        private String bUserId;
        @SerializedName("b_id")
        @Expose
        private String bId;
        @SerializedName("c_user_id")
        @Expose
        private String cUserId;
        @SerializedName("send_date")
        @Expose
        private String sendDate;
        @SerializedName("b_full_name")
        @Expose
        private String bFullName;
        @SerializedName("send_by")
        @Expose
        private String sendBy;

        public String getSendOrderId() {
            return sendOrderId;
        }

        public void setSendOrderId(String sendOrderId) {
            this.sendOrderId = sendOrderId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getBUserId() {
            return bUserId;
        }

        public void setBUserId(String bUserId) {
            this.bUserId = bUserId;
        }

        public String getBId() {
            return bId;
        }

        public void setBId(String bId) {
            this.bId = bId;
        }

        public String getCUserId() {
            return cUserId;
        }

        public void setCUserId(String cUserId) {
            this.cUserId = cUserId;
        }

        public String getSendDate() {
            return sendDate;
        }

        public void setSendDate(String sendDate) {
            this.sendDate = sendDate;
        }

        public String getBFullName() {
            return bFullName;
        }

        public void setBFullName(String bFullName) {
            this.bFullName = bFullName;
        }

        public String getSendBy() {
            return sendBy;
        }

        public void setSendBy(String sendBy) {
            this.sendBy = sendBy;
        }

    }
}
