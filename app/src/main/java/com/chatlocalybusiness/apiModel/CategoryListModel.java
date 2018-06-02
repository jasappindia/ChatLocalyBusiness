package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by windows on 12/14/2017.
 */

public class CategoryListModel {

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

    @SerializedName("sub_category_list")
    @Expose
    private List<SubCategoryList> subCategoryList = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;

    public List<SubCategoryList> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategoryList> subCategoryList) {
        this.subCategoryList = subCategoryList;
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

    public class SubCategoryList {

    @SerializedName("sub_cat_id")
    @Expose
    private String subCatId;
    @SerializedName("sub_cat_name")
    @Expose
    private String subCatName;
    @SerializedName("icon")
    @Expose
    private String icon;

    public String getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

  }


}
