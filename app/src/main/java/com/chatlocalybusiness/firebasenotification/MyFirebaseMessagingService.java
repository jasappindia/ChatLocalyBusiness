package com.chatlocalybusiness.firebasenotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.applozic.mobicomkit.api.MobiComKitConstants;
import com.applozic.mobicomkit.api.conversation.Message;
import com.applozic.mobicomkit.api.notification.MobiComPushReceiver;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.applozic.mobicomkit.uiwidgets.notification.NotificationIntentService;
import com.applozic.mobicommons.json.GsonUtils;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.HomeActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;


/**
 * Created by anjani on 5/9/17.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From:" + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            String message = "";
            Map<String, String> payload = remoteMessage.getData();
            try {
                JSONObject object = new JSONObject(payload);
                JSONObject APPLOZIC_01 = new JSONObject(object.getString("APPLOZIC_01"));
                JSONObject message1 = new JSONObject(APPLOZIC_01.getString("message"));
                message = message1.toString();
                String message2 = message1.getString("message");

                int groupId=message1.getInt("groupId");
                if(ConversationActivity.channelKey!=groupId)
                sendNotification("ChatLocaly Business", message2);

            } catch (JSONException e) {
                e.printStackTrace();
            }

          /*  Intent intent=new Intent(MobiComKitConstants.MESSAGE_JSON_INTENT);
            intent.putExtra(MobiComKitConstants.MESSAGE_JSON_INTENT,message);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
          */

            if (MobiComPushReceiver.isMobiComPushNotification(remoteMessage.getData())) {
                Log.i(TAG, "Applozic notification processing...");
                MobiComPushReceiver.processMessageAsync(this, remoteMessage.getData());
                return;
            }

/*

            if (*/
/* Check if data needs to be processed by long running job *//*
 true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }
*/

        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
 /*   private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }
*/

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */


    int id;

    private void sendNotification(String title, String messageBody) {


        Intent intent = new Intent(this, HomeActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.logo);
            notificationBuilder.setColor(getResources().getColor(R.color.colorAccent));
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }

        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(messageBody);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) Calendar.getInstance().getTimeInMillis() /* ID of notification */, notificationBuilder.build());
    }
}