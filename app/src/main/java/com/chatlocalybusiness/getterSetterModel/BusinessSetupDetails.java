package com.chatlocalybusiness.getterSetterModel;

import java.io.Serializable;

/**
 * Created by windows on 5/18/2018.
 */

public class BusinessSetupDetails implements Serializable {


    BusinessInfo businessInfo;
    BusinessCategory businessCategory;
    BusinessLocation businessLocation;

    public BusinessInfo getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(BusinessInfo businessInfo) {
        this.businessInfo = businessInfo;
    }

    public BusinessCategory getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(BusinessCategory businessCategory) {
        this.businessCategory = businessCategory;
    }

    public BusinessLocation getBusinessLocation() {
        return businessLocation;
    }

    public void setBusinessLocation(BusinessLocation businessLocation) {
        this.businessLocation = businessLocation;
    }

    public class BusinessInfo implements Serializable {

    String businessLogo;
    String businessName;
    String homeService;
    String distance;

        public String getBusinessLogo() {
            return businessLogo;
        }

        public void setBusinessLogo(String businessLogo) {
            this.businessLogo = businessLogo;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getHomeService() {
            return homeService;
        }

        public void setHomeService(String homeService) {
            this.homeService = homeService;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }

    public class BusinessCategory implements Serializable{

        String subscriptionPlan;
        String Cat1;
        String Cat2;

        public String getSubscriptionPlan() {
            return subscriptionPlan;
        }

        public void setSubscriptionPlan(String subscriptionPlan) {
            this.subscriptionPlan = subscriptionPlan;
        }

        public String getCat1() {
            return Cat1;
        }

        public void setCat1(String cat1) {
            Cat1 = cat1;
        }

        public String getCat2() {
            return Cat2;
        }

        public void setCat2(String cat2) {
            Cat2 = cat2;
        }
    }

    public class BusinessLocation implements Serializable {
        String City;
        String Locality;
        String pincode;
        String address;
        String landmark;

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getLocality() {
            return Locality;
        }

        public void setLocality(String locality) {
            Locality = locality;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }
    }
}
