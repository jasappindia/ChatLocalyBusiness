package com.chatlocalybusiness.services;

/**
 * Created by prateek on 2/8/16.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    String title = "";
    public static boolean message_received = false;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        message_received = false;
        send_Notification(remoteMessage);


    }


    private void send_Notification(RemoteMessage remoteMessage) {

        String notifications_text = "";
        JSONObject response = null;
        Map<String, String> params = remoteMessage.getData();
        try {
            response = new JSONObject(params.get("msg").toString());
        } catch (Exception e) {
        }

      /*  String title = remoteMessage.getNotification().getTitle();
        Intent resultIntent = new Intent(this, SplashActivity.class);
        TaskStackBuilder TSB = TaskStackBuilder.create(this);
        TSB.addParentStack(SplashActivity.class);
        TSB.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = TSB.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
        nb.setSmallIcon(R.drawable.logo_);
        nb.setContentIntent(resultPendingIntent);
        nb.setAutoCancel(false);
        nb.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.drawable.logo_));
        nb.setContentTitle(getString(R.string.app_name));
         NotificationCompat.BigTextStyle s1 = new NotificationCompat.BigTextStyle().bigText(notifications_text);
        nb.setStyle(s1);
        nb.setContentText(remoteMessage.getNotification().getBody());

        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(random(), nb.build());*/
    }

    int random() {
        Random rand = new Random();
        int randomNum = 1 + rand.nextInt((100000 - 1) + 1);
        return randomNum;
    }




}