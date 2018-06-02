package com.chatlocalybusiness.utill;

/**
 * Created by windows on 12/8/2017.
 */

public class Constants {
    public static final String APP_VERSION="1.0";
    public static final String LOGIN_AS="loginAs";
    public static final String ADMIN="Admin";
    public static final String SUB_ADMIN="Manager";
    public static final String WORKER="Worker";
    public static final String USER_MOBILE="Mobile";
    public static final String Encryption_Key="sdf";
    public static final String Employee="Admin_Worker";
    public static final String ENABLE="enable";
    public static final String DISABLE="disable";
    public static final String REGISTER_FLAG="register_flag";
    public static final String NEW_REGISTER="register";
    public static final String ALREADY_REGISTER="login";
    public static final String PRODUCT_SERIALIZABLE="Pro_Serializable";
    public static final String PRODUCT_DETAIL="Pro_Detail";
    public static final String ADD_EDIT_ITEM="ADD_EDIT_ITEM";
    public static final String BUYER_INFO="Buyer_Info";

    public static final String PRODUCT_IMAGES="Pro_Images";
    public static final String IMAGE_PATH_LIST = "ImagePathList";
    public static final String IMAGE_PATH = "ImagePath";
    public static final String POSITION ="position" ;
    public static final String TOTAL_AMOUNT ="totalAmount" ;
    public static final String SHOW_BUSINESS_ICON ="showBusinessIcon" ;
    public static final String SHOW_BUSINESS_LOCATION ="showBusinessLocation" ;
    public static final String SHOW_BILL_DATE ="showBilldate" ;
    public static final String SHOW_DUE_DATE ="showDuedate" ;
    public static final String ROUND_OFF = "roundOff";
    public static final String ROUND_OFF_SCREEN_DATA = "roundOffScreenData";
    public static final String USER_NAME = "USER_NAME";
    public static final String ORDER_DETAILS = "ORDER_DETAILS";
    public static final String PROFILE_DETAILS ="PROFILE_DETAILS" ;
    public static final String DESIGNATION ="DESIGNATION" ;
    public static final String EDIT_BUSINESS ="EDIT_BUSINESS" ;
    public static final String BUSINESS_INFO = "BUSINESS_INFO";
    public static final String BUSINESS_SETUP_DETAILS = "BUSINESS_SETUP_DETAILS";

    public static int limit;
    public static String IMAGE_SELECT_CAPTURE;
    public static String DELETED_IMAGES ="DeletedImages";
    public static String LOGIN ="login";
    public static String LOGOUT ="logout";
    public static String BILL_CONFIRMATION_CODE ="BILL_CONFIRMATION_CODE";
    public static String BILL_ORDER_ID ="BILL_ORDER_ID";

    public static int FINISH =1;

    public static String BUSINESS_STATUS_APPEAL ="APPEAL";
    public static String BUSINESS_STATUS_APPEAL_AGAIN ="APPEAL_AGAIN";
    public static String BUSINESS_STATUS_LOGOUT ="LOGOUT";

    public static String BUSINESS_STATUS_BUS_INFO ="BUSINESSINFO";
    public static String BUSINESS_STATUS_BOTH ="BOTH";
    public static String BUSINESS_STATUS_OVERVIEW ="OVERVIEW";
    public static String BUSINESS_STATUS_NOCHANGES ="NONE";




////////////////////////////////////////////////////////////////////////////////////  gallery code

    public static final int PERMISSION_REQUEST_CODE = 1000;
    public static final int PERMISSION_GRANTED = 1001;
    public static final int PERMISSION_DENIED = 1002;

    public static final int REQUEST_CODE = 2000;

    public static final int FETCH_STARTED = 2001;
    public static final int FETCH_COMPLETED = 2002;
    public static final int ERROR = 2005;

    /**
     * Request code for permission has to be < (1 << 8)
     * Otherwise throws java.lang.IllegalArgumentException: Can only use lower 8 bits for requestCode
     */
    public static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 23;

    public static final String INTENT_EXTRA_ALBUM = "album";
    public static final String INTENT_EXTRA_IMAGES = "images";
    public static final String INTENT_EXTRA_LIMIT = "limit";
    public static final int DEFAULT_LIMIT = 10;
    //public static String applicationKey="f7795dc359c99991ff22f1623a2875e2";
    public static String applicationKey="chatlocalyprivatelimited3e4abf";




    public static String PRODUCT_PUBLISHED_STATUS="ProductStatus";
    public static String SERVICE_PUBLISHED_STATUS="serviceStatus";
    public static String PUBLISHED="Published";
    public static String UNPUBLISHED="UnPublished";
    public static String UNAPPROVED="UnApproved";
    public static String BLOCKED="Blocked";
    public static String EmployeeID="EmployeeID";
    public static String QUARTER_STATS="QUARTER_STATS";
    public static String MONTHLY_STATS="MONTHLY_STATS";
    public static String CUSTOMER_STATS="CUSTOMER_STATS";


    //Maximum number of images that can be selected at a time


}
