package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by windows on 4/2/2018.
 */

public class TagPeopleModel  {

        @SerializedName("data")
        @Expose
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    public class ChatGroupUser {

        @SerializedName("cgu_user_id")
        @Expose
        private String cguUserId;
        @SerializedName("full_name")
        @Expose
        private String fullName;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;

        public String getCguUserId() {
            return cguUserId;
        }

        public void setCguUserId(String cguUserId) {
            this.cguUserId = cguUserId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

    }

    public class Data {

        @SerializedName("total_unreadCount")
        @Expose
        private Integer totalUnreadCount;
        @SerializedName("message_list")
        @Expose
        private List<MessageList> messageList = null;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public Integer getTotalUnreadCount() {
            return totalUnreadCount;
        }

        public void setTotalUnreadCount(Integer totalUnreadCount) {
            this.totalUnreadCount = totalUnreadCount;
        }

        public List<MessageList> getMessageList() {
            return messageList;
        }

        public void setMessageList(List<MessageList> messageList) {
            this.messageList = messageList;
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
    public class File {

        @SerializedName("key")
        @Expose
        private String key;
        @SerializedName("blobKey")
        @Expose
        private String blobKey;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("size")
        @Expose
        private Integer size;
        @SerializedName("contentType")
        @Expose
        private String contentType;
        @SerializedName("thumbnailUrl")
        @Expose
        private String thumbnailUrl;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getBlobKey() {
            return blobKey;
        }

        public void setBlobKey(String blobKey) {
            this.blobKey = blobKey;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

    }
    public class MessageList {

        @SerializedName("contentType")
        @Expose
        private Integer contentType;
        @SerializedName("file")
        @Expose
        private File file;
        @SerializedName("createdAtTime")
        @Expose
        private Integer createdAtTime;
        @SerializedName("to")
        @Expose
        private String to;
        @SerializedName("to_user_id")
        @Expose
        private String toUserId;
        @SerializedName("to_full_name")
        @Expose
        private String toFullName;
        @SerializedName("to_profile_image")
        @Expose
        private String toProfileImage;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("chat_group_id")
        @Expose
        private String chatGroupId;
        @SerializedName("chat_group_name")
        @Expose
        private String chatGroupName;
        @SerializedName("groupId")
        @Expose
        private Integer groupId;
        @SerializedName("unreadCount")
        @Expose
        private Integer unreadCount;
        @SerializedName("b_id")
        @Expose
        private String bId;
        @SerializedName("business_name")
        @Expose
        private String businessName;
        @SerializedName("business_logo")
        @Expose
        private String businessLogo;
        @SerializedName("home_services")
        @Expose
        private String homeServices;
        @SerializedName("services_km")
        @Expose
        private String servicesKm;
        @SerializedName("rating_count")
        @Expose
        private Integer ratingCount;
        @SerializedName("business_rating")
        @Expose
        private String businessRating;
        @SerializedName("customer_tag")
        @Expose
        private String customerTag;
        @SerializedName("customer_notification")
        @Expose
        private String customerNotification;
        @SerializedName("block_chat_group")
        @Expose
        private String blockChatGroup;
        @SerializedName("chat_group_users")
        @Expose
        private List<ChatGroupUser> chatGroupUsers = null;
        @SerializedName("message")
        @Expose
        private String message;

        public Integer getContentType() {
            return contentType;
        }

        public void setContentType(Integer contentType) {
            this.contentType = contentType;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public Integer getCreatedAtTime() {
            return createdAtTime;
        }

        public void setCreatedAtTime(Integer createdAtTime) {
            this.createdAtTime = createdAtTime;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getToFullName() {
            return toFullName;
        }

        public void setToFullName(String toFullName) {
            this.toFullName = toFullName;
        }

        public String getToProfileImage() {
            return toProfileImage;
        }

        public void setToProfileImage(String toProfileImage) {
            this.toProfileImage = toProfileImage;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getChatGroupId() {
            return chatGroupId;
        }

        public void setChatGroupId(String chatGroupId) {
            this.chatGroupId = chatGroupId;
        }

        public String getChatGroupName() {
            return chatGroupName;
        }

        public void setChatGroupName(String chatGroupName) {
            this.chatGroupName = chatGroupName;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getUnreadCount() {
            return unreadCount;
        }

        public void setUnreadCount(Integer unreadCount) {
            this.unreadCount = unreadCount;
        }

        public String getBId() {
            return bId;
        }

        public void setBId(String bId) {
            this.bId = bId;
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

        public String getHomeServices() {
            return homeServices;
        }

        public void setHomeServices(String homeServices) {
            this.homeServices = homeServices;
        }

        public String getServicesKm() {
            return servicesKm;
        }

        public void setServicesKm(String servicesKm) {
            this.servicesKm = servicesKm;
        }

        public Integer getRatingCount() {
            return ratingCount;
        }

        public void setRatingCount(Integer ratingCount) {
            this.ratingCount = ratingCount;
        }

        public String getBusinessRating() {
            return businessRating;
        }

        public void setBusinessRating(String businessRating) {
            this.businessRating = businessRating;
        }

        public String getCustomerTag() {
            return customerTag;
        }

        public void setCustomerTag(String customerTag) {
            this.customerTag = customerTag;
        }

        public String getCustomerNotification() {
            return customerNotification;
        }

        public void setCustomerNotification(String customerNotification) {
            this.customerNotification = customerNotification;
        }

        public String getBlockChatGroup() {
            return blockChatGroup;
        }

        public void setBlockChatGroup(String blockChatGroup) {
            this.blockChatGroup = blockChatGroup;
        }

        public List<ChatGroupUser> getChatGroupUsers() {
            return chatGroupUsers;
        }

        public void setChatGroupUsers(List<ChatGroupUser> chatGroupUsers) {
            this.chatGroupUsers = chatGroupUsers;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
