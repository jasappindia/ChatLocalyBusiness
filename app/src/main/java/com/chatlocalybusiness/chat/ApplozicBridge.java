

package com.chatlocalybusiness.chat;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;
import com.applozic.mobicomkit.api.account.user.PushNotificationTask;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.applozic.mobicomkit.api.conversation.Message;
import com.applozic.mobicomkit.api.conversation.MobiComConversationService;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.applozic.mobicomkit.uiwidgets.conversation.adapter.DetailedConversationAdapter;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.utill.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Created by applozic on 12/5/15.
 */
public class ApplozicBridge {

    /**
     * Starts the chat activity if user is not loggedIn then it will login to applozic server and launch the chat-list
     * else if user is loggedIn then directly opens chat-list
     *
     * @param context
     * @param user    :User object
     */

    public static void startChatActivity(Context context, User user) {
        if (!MobiComUserPreference.getInstance(context).isLoggedIn()) {

            UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

                @Override
                public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                    String pushNotificationId = "";//Todo: get pushnotification id.
                    gcmRegister(context, pushNotificationId);
                    launchChat(context);


                }

                @Override
                public void onFailure(RegistrationResponse registrationResponse, Exception exception) {

                }
            };

            user = user != null ? user : getLoggedInUserInformation();

            new UserLoginTask(user, listener, context).execute((Void) null);

        } else {
            launchChat(context);
        }
    }

    /**
     * Method  to launch chat activity if user is already registered with applozic server
     *
     * @param context
     */

    public static void launchChat(Context context) {
        Intent intent = new Intent(context, ConversationActivity.class);
        context.startActivity(intent);
    }

    /**
     * Method to Registers the User and launch the chat list
     *
     * @param context
     * @param user    :user object
     */

    public static void registerUserAndLaunchChat(Context context, User user) {
        startChatActivity(context, user);
    }


    /**
     * Method to launch Individual chat pass userId and display name to whom u want to launch Individual Chat directly
     *
     * @param context
     * @param userId
     * @param displayName
     */

    public static void launchIndividualChat(Context context, String userId, String displayName) {
        Intent intent = new Intent(context, ConversationActivity.class);
        if (!TextUtils.isEmpty(userId)) {
            intent.putExtra(ConversationUIService.USER_ID, userId);
        }
        if (!TextUtils.isEmpty(displayName)) {
            intent.putExtra(ConversationUIService.DISPLAY_NAME, displayName);
        }
        intent.putExtra(ConversationUIService.TAKE_ORDER,true); //Skip chat list for showing on back press
        context.startActivity(intent);
    }


    public static void launchIndividualGroupChat(Context context, String groupid, String buserId,String toolbar,String customerID,String chatLocalygroupId) {


//        sendCustomMessage(context,"check message",Integer.parseInt(groupid),"test", DetailedConversationAdapter.BILL_KEY_ID,"2");


        Intent intent = new Intent(context, ConversationActivity.class);
        if (!TextUtils.isEmpty(groupid)) {
            intent.putExtra(ConversationUIService.GROUP_ID, Integer.parseInt(groupid));
        }
        if (!TextUtils.isEmpty(buserId)) {
            intent.putExtra(ConversationUIService.DISPLAY_NAME, buserId);
        }


        String pushNotificationId = "";//Todo: get pushnotification id.
        gcmRegister(context, pushNotificationId);

        intent.putExtra(ConversationUIService.CHATLOCALY_GROUPID,chatLocalygroupId);
        intent.putExtra(ConversationUIService.TOOLBAR_TITLE,toolbar);
        intent.putExtra(ConversationUIService.CUSTOMER_ID,customerID);
//        intent.putExtra(ConversationUIService.TAKE_ORDER,true); //Skip chat list for showing on back press
        context.startActivity(intent);
        }

    public static void gcmRegister(Context context, String pushnotificationId) {

        if (!MobiComUserPreference.getInstance(context).isRegistered()) {
            Log.i("ApplozicBridge", "user is not Registered");
            MobiComUserPreference pref = MobiComUserPreference.getInstance(context);

            if (!TextUtils.isEmpty(pushnotificationId)) {
                pref.setDeviceRegistrationId(pushnotificationId);
            }
            return;
        }

        PushNotificationTask pushNotificationTask = null;
        PushNotificationTask.TaskListener listener = new PushNotificationTask.TaskListener() {

            @Override
            public void onSuccess(RegistrationResponse registrationResponse) {
            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
            }
        };

        pushNotificationTask = new PushNotificationTask(pushnotificationId, listener, context);
        pushNotificationTask.execute((Void) null);

    }

    /**
     * This method can be used to get app logged-in user's information.
     * if user information is stored in DB or preference, Code to get user's information should go here
     *
     * @return
     */

    public static User getLoggedInUserInformation() {
        User user = new User();
        user.setUserId("Applozic-test");//useIid of user
   //     user.setDisplayName("ApplozicChat");//displayName of user
        //user.setEmail(); optional
        return user;
    }


    public static User createUserbyId(String userId)
    {
        User user = new User();
        user.setUserId(userId); //userId it can be any unique user identifier NOTE : +,*,? are not allowed chars in userId.
        return user;

    }

    /**
     * Method to get the  Email Id  from google primary account
     *
     * @param context
     * @return
     */

    public static String getUserEmailId(Context context) {
        String userEmailId = "";
        try {
            AccountManager manager = AccountManager.get(context);
            Account[] accounts = manager.getAccountsByType("com.google");
            List<String> possibleEmails = new LinkedList<String>();

            for (Account account : accounts) {
                // TODO: Check possibleEmail against an email regex or treat
                // account.name as an email address only for certain
                // account.type values.
                possibleEmails.add(account.name);
            }

            if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
                userEmailId = possibleEmails.get(0);
            }
        } catch (Exception e) {

        }
        return userEmailId;
    }

    /**
     * Method to get the User Name  from google primary account
     *
     * @param context
     * @return
     */

    public static String getUsername(Context context) {
        try {
            AccountManager manager = AccountManager.get(context);
            Account[] accounts = manager.getAccountsByType("com.google");
            List<String> possibleEmails = new LinkedList<String>();

            for (Account account : accounts) {
                // TODO: Check possibleEmail against an email regex or treat
                possibleEmails.add(account.name);
            }

            if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
                String email = possibleEmails.get(0);
                String[] parts = email.split("@");

                if (parts.length > 1)
                    return parts[0];
            }

        } catch (Exception e) {

        }
        return null;
    }
    public static String getURLForResource (int resourceId) {

        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +"tata_salt.png").toString();
    }
    public static  void sendCustomMessage(Context context,String messageTosend,int channelKey,String filePath,String billConfirmationCode,String billOrderId)
    {

        MobiComUserPreference userPreferences = MobiComUserPreference.getInstance(context);
        Message message = new Message();
        // file with message
           List<String> filePaths = new ArrayList<>();
            // image link
           String file=filePath;
           Log.e("filepath",file);
           filePaths.add(file); // You can add any number of filePaths to this
           message.setContentType(Message.ContentType.ATTACHMENT.getValue());

            message.setFilePaths(filePaths);

        if (messageTosend != null && !TextUtils.isEmpty(messageTosend)) {
            message.setMessage(messageTosend);


        }
        // you can also add text message with the attachment
/*

        AssetManager assetManager=context.getAssets();
        try {
            =assetManager.open("tata_salt.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        //Note:This is only for sending a message to Group then pass the channelKey
        message.setGroupId(channelKey);
        //Note:This is only for sending a message to User then pass the receiver UserId
        //  message.setTo(receiverUserId);
       //  message.setContactIds(receiverUserId);
        message.setRead(Boolean.TRUE);
        message.setStoreOnDevice(Boolean.TRUE);
        message.setCreatedAtTime(System.currentTimeMillis() + userPreferences.getDeviceTimeOffset());
        message.setSendToDevice(Boolean.FALSE);
        message.setType(Message.MessageType.MT_OUTBOX.getValue());
        message.setDeviceKeyString(userPreferences.getDeviceKeyString());
        message.setSource(Message.Source.MT_MOBILE_APP.getValue());
        //Messsage metadata map
        Map<String,String> messageMetaDataMap = new HashMap<>();
        messageMetaDataMap.put(Constants.BILL_ORDER_ID,billOrderId);
        messageMetaDataMap.put(Constants.BILL_CONFIRMATION_CODE,billConfirmationCode);
        message.setMetadata(messageMetaDataMap);
        //Method for sending a message
        new MobiComConversationService(context).sendMessage(message);
    }
}
