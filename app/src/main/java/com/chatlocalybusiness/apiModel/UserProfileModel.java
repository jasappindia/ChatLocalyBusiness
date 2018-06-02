package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windows on 3/30/2018.
 */

public class UserProfileModel  {

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

        @SerializedName("user_data")
        @Expose
        private UserData userData;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public UserData getUserData() {
            return userData;
        }

        public void setUserData(UserData userData) {
            this.userData = userData;
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
    public class UserData {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("full_name")
        @Expose
        private String fullName;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

    }
}
