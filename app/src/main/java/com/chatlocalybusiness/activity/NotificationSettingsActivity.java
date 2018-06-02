package com.chatlocalybusiness.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.RingtoneListAdapter;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.ResponseModel;
import com.chatlocalybusiness.apiModel.RingToneListModel;
import com.chatlocalybusiness.others.RecyclerClick_Listener;
import com.chatlocalybusiness.others.RecyclerTouchListener;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationSettingsActivity extends AppCompatActivity implements View.OnClickListener {
//        implements View.OnClickListener {

    protected ImageView ivArrowBack;
    protected TextView tvTitleBar;
    protected CheckBox cbOnOffNotification;
    protected TextView tvOnOffNotification;
    protected TextView tvAudio;
    protected TextView tvVibration, tv_notificationSound, tv_audio;
    protected CheckBox cbVibrationOnOff;
    private RelativeLayout rl_notificationSound;
    private ProgressBar progressBar;
    Context context;
    private ChatBusinessSharedPreference prefrence;
    private ArrayList<RingToneListModel> ringToneListModelList;
    private Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_notification);

        prefrence=new ChatBusinessSharedPreference(this);
        initView();
        if(prefrence.getRingtoneTitle()!=null)
        tv_audio.setText(prefrence.getRingtoneTitle());


    }
    @Override
    public void onClick(View view) {


        if (view.getId() == R.id.iv_arrowBack)
        {
            onBackPressed();

        }
        else if (view.getId() == R.id.cb_vibration_on_off)
        {
            if (((CheckBox) view).isChecked()) {
                prefrence.setVibration_status(true);
            } else
                prefrence.setVibration_status(false);
        }
        else if (view.getId() == R.id.rl_notificationSound) {

            ringToneListModelList = (ArrayList<RingToneListModel>) listRingtones();
            customDialog(context, ringToneListModelList);

        }
        if (view.getId() == R.id.cb_on_off_notification)
        {

            if (((CheckBox) view).isChecked()) {
//                ll_vibration.setVisibility(View.VISIBLE);
                prefrence.setNotication_status(true);
                updateUserNotificationStatus(prefrence.getUserId(),"1", Constants.Encryption_Key);

            } else
            {
//                ll_vibration.setVisibility(View.INVISIBLE);
                prefrence.setNotication_status(false);
                updateUserNotificationStatus(prefrence.getUserId(),"0",Constants.Encryption_Key);


            }

        }

    }

    private void initView() {
        context = NotificationSettingsActivity.this;
        ringToneListModelList = new ArrayList<>();
//        ll_vibration=(LinearLayout) findViewById(R.id.ll_vibration);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        rl_notificationSound = (RelativeLayout) findViewById(R.id.rl_notificationSound);
        ivArrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        ivArrowBack.setOnClickListener(this);
        tvTitleBar = (TextView) findViewById(R.id.tv_titleBar);
        tvTitleBar.setOnClickListener(this);
        cbOnOffNotification = (CheckBox) findViewById(R.id.cb_on_off_notification);
        tvOnOffNotification = (TextView) findViewById(R.id.tv_on_off_notification);
        tvAudio = (TextView) findViewById(R.id.tv_audio);
        tvVibration = (TextView) findViewById(R.id.tv_vibration);
        tv_notificationSound = (TextView) findViewById(R.id.tv_notificationSound);
        tv_audio = (TextView) findViewById(R.id.tv_audio);
        cbVibrationOnOff = (CheckBox) findViewById(R.id.cb_vibration_on_off);

        cbVibrationOnOff.setOnClickListener(this);
        tv_notificationSound.setOnClickListener(this);
        cbOnOffNotification.setOnClickListener(this);
        rl_notificationSound.setOnClickListener(this);

        // value set

        if (prefrence.getVibration_status())
            cbVibrationOnOff.setChecked(true);
        else
            cbVibrationOnOff.setChecked(false);
       //notification
        if (prefrence.getNotication_status())
        {
            cbOnOffNotification.setChecked(true);
//            ll_vibration.setVisibility(View.VISIBLE);
        }
        else {
            cbOnOffNotification.setChecked(false);
//            ll_vibration.setVisibility(View.INVISIBLE);
        }

    }


    public List<RingToneListModel> listRingtones() {
        List<RingToneListModel> ringToneListModels = new ArrayList<>();
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();
        while (cursor.moveToNext()) {


            String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            Uri ringtoneURI = manager.getRingtoneUri(cursor.getPosition());
            RingToneListModel ringToneListModel = new RingToneListModel(title, ringtoneURI);
            ringToneListModels.add(ringToneListModel);
            // Do something with the title and the URI of ringtone
        }
        RingToneListModel ringToneListModel = new RingToneListModel("Default Sound", Uri.parse("Default Sound"));
        ringToneListModels.add(0, ringToneListModel);

        return ringToneListModels;
    }


    public void customDialog(final Context context, final List<RingToneListModel> list) {

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.popupwindowcitylistdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popupwindowcitylistdialog, null);
        final LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.ll_ringtone);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.rView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RingtoneListAdapter adapter = new RingtoneListAdapter(context, list);
        recyclerView.setAdapter(adapter);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerClick_Listener() {
            @Override
            public void onClick(View view, int position) {

                if (dialog != null && dialog.isShowing()) {

                    prefrence.setRingToneUri(list.get(position).getRingtoneListUri());
                    prefrence.setRingtoneTilte(list.get(position).getRingtoneTitle());
                    tv_audio.setText(list.get(position).getRingtoneTitle());
//                    dialog.dismiss();

                    try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), notification);


//                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), Uri.parse(prefrence.getringtoneUri()));
//                        mp.start();
                      if(r!=null)
                      {
                          if (r.isPlaying())
                              r.stop();
                      }
                       r = RingtoneManager.getRingtone(getBaseContext(), Uri.parse(prefrence.getringtoneUri()));

                        //playing sound alarm
                        r.play();
                        Thread th = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(5000);  //30000 is for 30 seconds, 1 sec =1000
                                    if (r.isPlaying())
                                        r.stop();   // for stopping the ringtone
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        th.start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


//                    sendNotification("title", "check message body");
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(r!=null)
                {
                    if (r.isPlaying())
                        r.stop();
                }
            }
        });

    }




    private void sendNotification(String title,
                                  String messageBody) {


//        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);

        Intent intent = new Intent(this, NotificationSettingsActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0   , intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);


        if (prefrence.getVibration_status()) {
            //Vibration
            notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        } else
            notificationBuilder.setVibrate(new long[]{0L});



        if (prefrence.getRingtoneTitle().equalsIgnoreCase("Default Sound"))

            notificationBuilder.setSound(defaultSoundUri);
        else//Ton
            notificationBuilder.setSound(Uri.parse(prefrence.getringtoneUri()));

        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(messageBody);
        notificationBuilder.setSmallIcon(R.mipmap.logo);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0  , notificationBuilder.build());

        //


    }





 public void updateUserNotificationStatus(String c_user_id, final String notificationStatus, String encrypt) {

     progressBar.setVisibility(View.VISIBLE);
        final ApiServices apiService = ApiClient.getClient().create(ApiServices.class);

        HashMap<String, String> params = new HashMap<>();
        params.put("b_user_id", prefrence.getUserId());
        params.put("notification_status",notificationStatus);
        params.put("encrypt_key", "shdvk");
        Call<ResponseModel> call = apiService.setNotificationStatus(params);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                progressBar.setVisibility(View.GONE);
               if(response.isSuccessful())
               {
                   if(response.body().getData().getResultCode().equalsIgnoreCase("1"))
                   {  if(notificationStatus.equalsIgnoreCase("1"))
                       Toast.makeText(NotificationSettingsActivity.this, "Notification on", Toast.LENGTH_SHORT).show();
                       else
                       Toast.makeText(NotificationSettingsActivity.this, "Notification off", Toast.LENGTH_SHORT).show();
                   }
               }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(NotificationSettingsActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

            }
        });


    }


}
