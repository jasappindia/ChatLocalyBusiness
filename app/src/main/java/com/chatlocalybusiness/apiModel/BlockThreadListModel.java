package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by windows on 4/16/2018.
 */

public class BlockThreadListModel {

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

        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("cgu_user_id")
        @Expose
        private String cguUserId;
        @SerializedName("full_name")
        @Expose
        private String fullName;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

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
        @SerializedName("tag_total_unreadCount")
        @Expose
        private Integer tagTotalUnreadCount;
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

        public Integer getTagTotalUnreadCount() {
            return tagTotalUnreadCount;
        }

        public void setTagTotalUnreadCount(Integer tagTotalUnreadCount) {
            this.tagTotalUnreadCount = tagTotalUnreadCount;
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
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("datetime")
        @Expose
        private String datetime;
        @SerializedName("createdAtTime")
        @Expose
        private long createdAtTime;
        @SerializedName("to")
        @Expose
        private String to;
        @SerializedName("to_user_id")
        @Expose
        private String toUserId;
        @SerializedName("to_full_name")
        @Expose
        private String toFullName;
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
        @SerializedName("business_tags")
        @Expose
        private String businessTags;
        @SerializedName("business_notification")
        @Expose
        private String businessNotification;
        @SerializedName("response_by_users")
        @Expose
        private List<ResponseByUser> responseByUsers = null;
        @SerializedName("response_by")
        @Expose
        private String responseBy;
        @SerializedName("chat_group_users")
        @Expose
        private List<ChatGroupUser> chatGroupUsers = null;
        @SerializedName("c_user_id")
        @Expose
        private String cUserId;
        @SerializedName("c_mobile_number")
        @Expose
        private String cMobileNumber;
        @SerializedName("full_name")
        @Expose
        private String fullName;
        @SerializedName("c_profile_image")
        @Expose
        private String cProfileImage;

        @SerializedName("blocked_by")
        @Expose
        private String blockedBy;

        public String getBlockedBy() {
            return blockedBy;
        }

        public void setBlockedBy(String blockedBy) {
            this.blockedBy = blockedBy;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

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

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public long getCreatedAtTime() {
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

        public String getBusinessTags() {
            return businessTags;
        }

        public void setBusinessTags(String businessTags) {
            this.businessTags = businessTags;
        }

        public String getBusinessNotification() {
            return businessNotification;
        }

        public void setBusinessNotification(String businessNotification) {
            this.businessNotification = businessNotification;
        }

        public List<ResponseByUser> getResponseByUsers() {
            return responseByUsers;
        }

        public void setResponseByUsers(List<ResponseByUser> responseByUsers) {
            this.responseByUsers = responseByUsers;
        }

        public String getResponseBy() {
            return responseBy;
        }

        public void setResponseBy(String responseBy) {
            this.responseBy = responseBy;
        }

        public List<ChatGroupUser> getChatGroupUsers() {
            return chatGroupUsers;
        }

        public void setChatGroupUsers(List<ChatGroupUser> chatGroupUsers) {
            this.chatGroupUsers = chatGroupUsers;
        }

        public String getCUserId() {
            return cUserId;
        }

        public void setCUserId(String cUserId) {
            this.cUserId = cUserId;
        }

        public String getCMobileNumber() {
            return cMobileNumber;
        }

        public void setCMobileNumber(String cMobileNumber) {
            this.cMobileNumber = cMobileNumber;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getCProfileImage() {
            return cProfileImage;
        }

        public void setCProfileImage(String cProfileImage) {
            this.cProfileImage = cProfileImage;
        }

    }
    public class ResponseByUser {

        @SerializedName("b_user_id")
        @Expose
        private String bUserId;
        @SerializedName("b_full_name")
        @Expose
        private String bFullName;

        public String getBUserId() {
            return bUserId;
        }

        public void setBUserId(String bUserId) {
            this.bUserId = bUserId;
        }

        public String getBFullName() {
            return bFullName;
        }

        public void setBFullName(String bFullName) {
            this.bFullName = bFullName;
        }

    }
}
