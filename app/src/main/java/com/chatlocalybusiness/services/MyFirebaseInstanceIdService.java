package com.chatlocalybusiness.services;

import android.util.Log;

import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    ChatBusinessSharedPreference sharedpreference;
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sharedpreference =new ChatBusinessSharedPreference(getApplicationContext());
        sharedpreference.saveToken_Id(refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    // [END refresh_token]

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

}