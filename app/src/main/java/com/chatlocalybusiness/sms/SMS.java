package com.chatlocalybusiness.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by windows on 5/2/2018.
 */


    public class SMS extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent)
        {

            final Bundle bundle = intent.getExtras();
            try {
                if (bundle != null) {
                    final Object[] pdusObj = (Object[]) bundle.get("pdus");
                    for (int i = 0; i < pdusObj .length; i++) {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                        String senderNum = phoneNumber ;
                        String message = currentMessage .getDisplayMessageBody();
                        try {
                            if (phoneNumber.equals("RM-VEntry"))
                            {
                            /*ConfrmationCodeFragment SMS = new ConfrmationCodeFragment();
                            SMS.recivedSms(message );

                            ((SettingActivity)context).recivedSms(message);*/

                                Intent intent1 = new Intent("OTP");
                                //put whatever data you want to send, if any
                                intent1.putExtra("message", message);
                                //send broadcast
                                context.sendBroadcast(intent1);
                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

