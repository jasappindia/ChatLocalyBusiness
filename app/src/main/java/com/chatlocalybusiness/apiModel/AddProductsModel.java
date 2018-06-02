package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windows on 1/12/2018.
 */

public class AddProductsModel {

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

        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("b_user_id")
        @Expose
        private String bUserId;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getBUserId() {
            return bUserId;
        }

        public void setBUserId(String bUserId) {
            this.bUserId = bUserId;
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
/*
*
public class Data {

@SerializedName("product_id")
@Expose
private Integer productId;
@SerializedName("b_user_id")
@Expose
private String bUserId;
@SerializedName("message")
@Expose
private String message;
@SerializedName("resultCode")
@Expose
private String resultCode;

public Integer getProductId() {
return productId;
}

public void setProductId(Integer productId) {
this.productId = productId;
}

public String getBUserId() {
return bUserId;
}

public void setBUserId(String bUserId) {
this.bUserId = bUserId;
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

}*/