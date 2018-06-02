package com.chatlocalybusiness.firebasenotification;

import android.util.Log;

import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.api.account.register.RegisterUserClientService;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by anjani on 5/9/17.
 */public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
           Log.d(TAG, "Refreshed token: " + refreshedToken);

        if(refreshedToken!=null) {
            ChatBusinessSharedPreference preferences = new ChatBusinessSharedPreference(getApplication());
            preferences.saveToken_Id(refreshedToken);
        }
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//          sendRegistrationToServer(refreshedToken);

            // Get updated InstanceID token.

            Applozic.getInstance(this).setDeviceRegistrationId(refreshedToken);
            if (MobiComUserPreference.getInstance(this).isRegistered()) {
                try {
                    RegistrationResponse registrationResponse = new RegisterUserClientService(this).updatePushNotificationId(refreshedToken);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}