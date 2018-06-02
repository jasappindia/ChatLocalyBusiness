package com.chatlocalybusiness.getterSetterModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by windows on 2/16/2018.
 */

public class AddItemBillModel implements Serializable {

    List<ItemDetails> itemDetails = null;

    public List<ItemDetails> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetails> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public class ItemDetails implements Serializable {

        private String itemId;
        private String itemName;
        private String qty;
        private String itemUnit;
        private String taxTotal;
        private String discount;
        private String discountPercentage;
        private String discription;
        private String unit;
        private String Mrp;
        private String cgst="0";
        private String sgst="0";
        private String cgst_Price;
        private String sgst_Price;
        private String productPrice;
        private String rate;
        private String totalPrice;
        private int taxinclusive=0;

        public int getTaxinclusive() {
            return taxinclusive;
        }

        public void setTaxinclusive(int taxinclusive) {
            this.taxinclusive = taxinclusive;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getSgst_Price() {
            return sgst_Price;
        }

        public void setSgst_Price(String sgst_Price) {
            this.sgst_Price = sgst_Price;
        }

        public String getCgst_Price() {
            return cgst_Price;
        }

        public void setCgst_Price(String cgst_Price) {
            this.cgst_Price = cgst_Price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getDiscountPercentage() {
            return discountPercentage;
        }

        public void setDiscountPercentage(String discountPercentage) {
            this.discountPercentage = discountPercentage;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }


        public String getTaxTotal() {
            return taxTotal;
        }

        public void setTaxTotal(String taxTotal) {
            this.taxTotal = taxTotal;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public String getMrp() {
            return Mrp;
        }

        public void setMrp(String mrp) {
            Mrp = mrp;
        }

        public String getCgst() {
            return cgst;
        }

        public void setCgst(String cgst) {
            this.cgst = cgst;
        }

        public String getSgst() {
            return sgst;
        }

        public void setSgst(String sgst) {
            this.sgst = sgst;
        }


        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getItemUnit() {
            return itemUnit;
        }

        public void setItemUnit(String itemUnit) {
            this.itemUnit = itemUnit;
        }


    }
}

