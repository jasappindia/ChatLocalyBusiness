package com.chatlocalybusiness.getterSetterModel;

import java.io.Serializable;

/**
 * Created by windows on 2/16/2018.
 */

public class BuyerInfoModel implements Serializable {
    private String buyerId;
    private String buyerMobile;
    private String buyerName;
    private String shippingAddress;
    private String billCategoryDiscription;
    private String billDate;
    private String dueDate;
    private String buyerAddressLine1;
    private String buyerAddressLine2;
    private String buyerCity;
    private String buyerPincode;
    private String buyerState;

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getBuyerAddressLine1() {
        return buyerAddressLine1;
    }

    public void setBuyerAddressLine1(String buyerAddressLine1) {
        this.buyerAddressLine1 = buyerAddressLine1;
    }

    public String getBuyerAddressLine2() {
        return buyerAddressLine2;
    }

    public void setBuyerAddressLine2(String buyerAddressLine2) {
        this.buyerAddressLine2 = buyerAddressLine2;
    }

    public String getBuyerCity() {
        return buyerCity;
    }

    public void setBuyerCity(String buyerCity) {
        this.buyerCity = buyerCity;
    }

    public String getBuyerPincode() {
        return buyerPincode;
    }

    public void setBuyerPincode(String buyerPincode) {
        this.buyerPincode = buyerPincode;
    }

    public String getBuyerState() {
        return buyerState;
    }

    public void setBuyerState(String buyerState) {
        this.buyerState = buyerState;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getBillCategoryDiscription() {
        return billCategoryDiscription;
    }

    public void setBillCategoryDiscription(String billCategoryDiscription) {
        this.billCategoryDiscription = billCategoryDiscription;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

}
