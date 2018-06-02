package com.applozic.mobicomkit.uiwidgets.conversation.chatLocaly;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ChatLocallyAppLozicPrefrence {

    private static final String BUSINESS_ID = "BusineesId";
    private static final String BUSINESS_NAME = "BusineesName";
    private static final String LOGIN_STATUS = "LoginStatus";
    public static String User_NAME = "Name";
    public static String USER_IMAGE = "user_image";
    public static String USER_ID = "id";
    public static String USER_MOBILENUMBER = "mobile_number";
    public static String ROlE="Role";
    public static String LOGIN_KEY="Logn_key";
    public static String GCM_Token_Id="token_id";

    public static String MESSAGE_TIME = "MESSAGE_TIME";
    public static String MESSAGE = "Message";
    public static String MESSAGE_CONTENT_TYPE = "MESSAGE_CONTENT_TYPE";

    public static String SEND_MESSAGE = "sendMessage";
    public static String SEND_VIDEO = "sendVideo";
    public static String  SEND_AUDIO = "sendAudio";
    public static String SEND_PHOTO = "sendPhoto";
    public static String SEND_BILL= "sendBill";
    public static String EDIT_BILL = "editBIll";
    public static String BLOCK_THREAD = "blockthread";
    public static String EDIT_BUSINESS = "editBusiness";
    public static String EDIT_BUSINESS_OVERVIEW = "EDIT_BUSINESS_OVERVIEW";
    public static String ADD_PRODUCTS = "ADD_PRODUCTS";
    public static String ADD_SEVICE = "ADD_SEVICE";
    public static String COMPLETED_STEP="COMPLETED_STEP";
    public static String DEVICE_KEY="device_key";
    public static String CHAT_GROUP_ID="CHAT_GROUP_ID";
    public static String CHAT_CLIENT_ID="CHAT_CLIENT_ID";
    public static String RESPONSE_BY="RESPONSE_BY";
    public static String RINGTONE="RINGTONE";
    public static String RINGTONE_TITLE="RINGTONE_TITLE";


    private Context context;
    private static final String APP_SHARED_PREFS = ChatLocallyAppLozicPrefrence.class.getSimpleName(); // Name of the file -.xml
    private static SharedPreferences _sharedPrefs;
    private Editor _prefsEditor;

    public ChatLocallyAppLozicPrefrence(Context ctx) {
        this.context = ctx;
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    public void logout() {
        this._prefsEditor.clear().commit();
        _prefsEditor.putString(LOGIN_STATUS,null);
        _prefsEditor.putString(USER_ID,null);
        _prefsEditor.putInt(BUSINESS_ID,0);
        _prefsEditor.putString(BUSINESS_NAME,null);
        _prefsEditor.putString(User_NAME,null);
        _prefsEditor.putString(USER_IMAGE,null);
        _prefsEditor.putString(USER_MOBILENUMBER,null);
        _prefsEditor.putString(ROlE,null);
        _prefsEditor.putString(LOGIN_KEY,null);
        _prefsEditor.putString(COMPLETED_STEP,null);
        _prefsEditor.putString(CHAT_GROUP_ID,null);
        _prefsEditor.putString(CHAT_CLIENT_ID,null);
        _prefsEditor.putBoolean(RESPONSE_BY,false);
        _prefsEditor.putString(RINGTONE,null);
        _prefsEditor.putString(RINGTONE_TITLE,null);

        _prefsEditor.putString(SEND_MESSAGE,null);
        _prefsEditor.putString(SEND_VIDEO,null);
        _prefsEditor.putString(SEND_AUDIO,null);
        _prefsEditor.putString(SEND_PHOTO,null);
        _prefsEditor.putString(SEND_BILL,null);
        _prefsEditor.putString(EDIT_BILL,null);
        _prefsEditor.putString(BLOCK_THREAD,null);
        _prefsEditor.putString(EDIT_BUSINESS,null);
        _prefsEditor.putString(EDIT_BUSINESS_OVERVIEW,null);
        _prefsEditor.putString(ADD_PRODUCTS,null);
        _prefsEditor.putString(ADD_SEVICE,null);

        _prefsEditor.commit();

    }
    public void setResponseBy(boolean b)
    {
        _prefsEditor.putBoolean(RESPONSE_BY,b);
        _prefsEditor.commit();
    }
    public boolean getResponseBy()
    {
        return _sharedPrefs.getBoolean(RESPONSE_BY,false);
    }

    public void setChatGroupID(String groupID,String clientId)
    {
        _prefsEditor.putString(CHAT_GROUP_ID,groupID);
        _prefsEditor.putString(CHAT_CLIENT_ID,clientId);

        _prefsEditor.commit();

    }

    public String getChatGroupId()
    {
        return _sharedPrefs.getString(CHAT_GROUP_ID,null);
    }

    public String getChatClientId()
    {
        return _sharedPrefs.getString(CHAT_CLIENT_ID,null);
    }
    public void setLoginStatus(String loginStatus)
    {
        _prefsEditor.putString(LOGIN_STATUS,loginStatus);
        _prefsEditor.commit();
    }
    public String getLoginStatus()
    {
        return _sharedPrefs.getString(LOGIN_STATUS,null);
    }

    public void saveCompletedStep(String step)
    {
        _prefsEditor.putString(COMPLETED_STEP,step);
        _prefsEditor.commit();
    }
    public String getCompletedStep()
    {
        return _sharedPrefs.getString(COMPLETED_STEP,null);

    }
    public void setSendMessage( String sendMessage)
    {
        _prefsEditor.putString(SEND_MESSAGE,sendMessage);
        _prefsEditor.commit();
    }
    public String getSendMessage( )
    {
        return _sharedPrefs.getString(SEND_MESSAGE,null);
    }

  public void setMessage( String sendMessage)
    {
        _prefsEditor.putString(MESSAGE,sendMessage);
        _prefsEditor.commit();
    }
    public String getMessage( )
    {
        return _sharedPrefs.getString(MESSAGE,null);
    }
 public void setMessageTime( long sendMessage)
    {
        _prefsEditor.putLong(MESSAGE_TIME,sendMessage);
        _prefsEditor.commit();
    }
    public Long getMessageTime( )
    {
        return _sharedPrefs.getLong(MESSAGE_TIME,0);
    }



    public void setSendVideo( String sendVideo)
    {
        _prefsEditor.putString(SEND_VIDEO,sendVideo);
        _prefsEditor.commit();
    }
    public String getSendVideo( )
    {
        return _sharedPrefs.getString(SEND_VIDEO,null);
    }
    public void setSendAudio( String sendAudio)
    {
        _prefsEditor.putString(SEND_AUDIO,sendAudio);
        _prefsEditor.commit();
    }
    public String getSendAudio( )
    {
        return _sharedPrefs.getString(SEND_AUDIO,null);
    }
    public void setSendPhoto( String sendPhoto)
    {
        _prefsEditor.putString(SEND_PHOTO,sendPhoto);
        _prefsEditor.commit();
    }
    public String getSendPhoto( )
    {
        return _sharedPrefs.getString(SEND_PHOTO,null);
    }
    public void setSendBill( String sendBill)
    {
        _prefsEditor.putString(SEND_BILL,sendBill);
        _prefsEditor.commit();
    }
    public String getSendBill( )
    {
        return _sharedPrefs.getString(SEND_MESSAGE,null);
    }
    public void setEditBill( String editBill)
    {
        _prefsEditor.putString(EDIT_BILL,editBill);
        _prefsEditor.commit();
    }
    public String getEditBill()
    {
        return _sharedPrefs.getString(EDIT_BILL,null);
    }
    public void setBlockThread(String blockThread)
    {
        _prefsEditor.putString(BLOCK_THREAD,blockThread);
        _prefsEditor.commit();
    }
    public String getBlockThread( )
    {
        return _sharedPrefs.getString(BLOCK_THREAD,null);
    }
    public void setEditBusiness( String editBusiness)
    {
        _prefsEditor.putString(EDIT_BUSINESS,editBusiness);
        _prefsEditor.commit();
    }
    public String getEditBusiness( )
    {
        return _sharedPrefs.getString(EDIT_BUSINESS,null);
    }

    public void setEditBusinessOverview( String editBusinessOverview)
    {
        _prefsEditor.putString(EDIT_BUSINESS_OVERVIEW,editBusinessOverview);
        _prefsEditor.commit();
    }
    public String getEditBusinessOverview( )
    {
        return _sharedPrefs.getString(EDIT_BUSINESS_OVERVIEW,null);
    }

    public void setAddProducts( String addProducts)
    {
        _prefsEditor.putString(ADD_PRODUCTS,addProducts);
        _prefsEditor.commit();
    }
    public String getAddProducts( )
    {
        return _sharedPrefs.getString(ADD_PRODUCTS,null);
    }

    public void setAddSevice( String addSevice)
    {
        _prefsEditor.putString(ADD_SEVICE,addSevice);
        _prefsEditor.commit();
    }
    public String getAddSevice( )
    {
        return _sharedPrefs.getString(ADD_SEVICE,null);
    }


    public void saveImage(final String image_url)
    {
        _prefsEditor.putString(USER_IMAGE,image_url);
        _prefsEditor.commit();

    }

    public void saveFirstName(final String name)
    {
        _prefsEditor.putString(User_NAME,name);
        _prefsEditor.commit();

    }
     public void saveLoginKey( String lognKey)
    {
        _prefsEditor.putString(LOGIN_KEY,lognKey);
        _prefsEditor.commit();

    }   public void saveBusinessRole( String role)
    {
        _prefsEditor.putString(ROlE,role);
        _prefsEditor.commit();

    }


    public void saveUserMobile(String mobile_number)
    {

        _prefsEditor.putString(USER_MOBILENUMBER,mobile_number);
        _prefsEditor.commit();

    }


    public void saveBusinessId(Integer bId) {
            _prefsEditor.putInt(BUSINESS_ID,bId);
            _prefsEditor.commit();
    }

    public int getBusinessId()
    {
        return _sharedPrefs.getInt(BUSINESS_ID,0);
    }
    public static String getGCM_Token_Id() {
        return  _sharedPrefs.getString(GCM_Token_Id, null);
    }

    public  void  saveToken_Id(final String Token_Id)
    {
        _prefsEditor.putString(GCM_Token_Id, Token_Id);
        _prefsEditor.commit();
    }

    public static String getUserId() {
        return _sharedPrefs.getString(USER_ID,
                "0");
    }

    public static String getBusinessROlE() {
        return _sharedPrefs.getString(ROlE, null);
    }
    public static String getLoginKey() {
        return _sharedPrefs.getString(LOGIN_KEY, null);
    }


    public static String getUser_NAME() {
        return _sharedPrefs.getString(User_NAME, null);
    }

    public static String getUserImage() {
        return _sharedPrefs.getString(USER_IMAGE, null);
    }


    public static String getUserMobilenumber() {
        return _sharedPrefs.getString(USER_MOBILENUMBER, null);
    }

    public void saveUserId(String bUserId) {
        _prefsEditor.putString(USER_ID, bUserId);
        _prefsEditor.commit();

    }

    public void setBusinessName(String businessName) {
        _prefsEditor.putString(BUSINESS_NAME, businessName);
        _prefsEditor.commit();
    }
    public String getBusinessName()
    {
        return _sharedPrefs.getString(BUSINESS_NAME,null);
    }

    public void setDeviceKey(String deviceKey)
    {
        _prefsEditor.putString(DEVICE_KEY,deviceKey);
        _prefsEditor.commit();
    }
    public String getDeviceKey() {
    return _sharedPrefs.getString(DEVICE_KEY,null);
    }



    public void setRingToneUri(String ringToneuri) {
        _prefsEditor.putString(RINGTONE,ringToneuri);
        _prefsEditor.commit();

    }

    public String getringtoneUri()
    {
        return _sharedPrefs.getString(RINGTONE,null);
    }

    public String getRingtoneTitle()
    {
        return _sharedPrefs.getString(RINGTONE_TITLE,null);
    }

    public void setRingtoneTilte(String ringtoneTitle) {

        _prefsEditor.putString(RINGTONE_TITLE,ringtoneTitle);
        _prefsEditor.commit();

    }

    public void setMessageContentType(int messageContentType) {
        _prefsEditor.putInt(MESSAGE_CONTENT_TYPE,messageContentType);
        _prefsEditor.commit();
    }
    public int getMessageContentType()
    {
        return _sharedPrefs.getInt(MESSAGE_CONTENT_TYPE,-1);
    }
}