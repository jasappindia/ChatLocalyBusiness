package com.chatlocalybusiness.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.applozic.mobicomkit.Applozic;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;
import com.google.firebase.iid.FirebaseInstanceId;

import static com.chatlocalybusiness.utill.Constants.applicationKey;

/**
 * Created by Shiv on 12/6/2017.
 */
public class GetStartedActivity extends AppCompatActivity {

    AlertDialog alertdialog;
    ViewGroup ll_main;
    ChatBusinessSharedPreference preference;
    ProgressBar progressBar;
    Utill util;
    BasicUtill basicUtill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);
        Applozic.init(this, applicationKey);
        preference = new ChatBusinessSharedPreference(this);
        preference.saveToken_Id(FirebaseInstanceId.getInstance().getToken());
        if(preference.getLoginStatus()!=null)
        {
            if(preference.getLoginStatus().equalsIgnoreCase(Constants.LOGIN))
            {
                startActivity(new Intent(GetStartedActivity.this,HomeActivity.class));
                finish();
                return;
            }

        }
        util = new Utill();
        basicUtill=new BasicUtill();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ll_main = (ViewGroup) findViewById(R.id.ll_main);
        Button btn_getStarted = (Button) findViewById(R.id.btn_getStarted);
        TextView tv_alreadyMember = (TextView) findViewById(R.id.tv_alreadyMember);
        btn_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GetStartedActivity.this, OtpGenerateActivity.class);
                intent.putExtra(Constants.REGISTER_FLAG, Constants.NEW_REGISTER);
                intent.putExtra(Constants.LOGIN_AS, Constants.ADMIN);
                startActivity(intent);
            }
        });

        tv_alreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GetStartedActivity.this, LoginActivity.class));

            }
        });
       /* try {
            init();
        } catch (Exception ex) {
            preference.logout();
        }*/
    }

    public void init() {
        int businessId = preference.getBusinessId();
        if (businessId != 0) {
            if (util.isConnected(GetStartedActivity.this)) {
                ViewGroup rl_main=(ViewGroup)findViewById(R.id.rl_main);
                basicUtill.CheckStatus(GetStartedActivity.this, 1, rl_main);
            }else {
                if (preference.getBusinessROlE().equals(Constants.ADMIN))
                    onApprovedAdmin();
                else onApprovedWorker();
            }
        }


    }
    @Override
    protected void onResume() {
        super.onResume();
//    basicUtill.CheckStatus(GetStartedActivity.this,1);

    }
    /* public void CheckStatus() {
        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "hvdjv");
        param.put("b_user_id", preference.getUserId());
        param.put("b_id", String.valueOf(preference.getBusinessId()));

        Call<CheckStatusModel> call = apiServices.checkStatus(param);
        call.enqueue(new Callback<CheckStatusModel>() {
            @Override
            public void onResponse(Call<CheckStatusModel> call, Response<CheckStatusModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        CheckStatusModel.Data data = response.body().getData();
                        if (preference.getLoginKey() != null) {
                            if (!data.getLoginKey().equals(preference.getLoginKey())) {
                                loginAlert();
                            } else if (!data.getApp_version().equalsIgnoreCase(Constants.APP_VERSION)) {
                                appVersionAlert();
                            }
                            //  'BLOCKED', 'APPROVED', 'UNAPPROVED'
                            else if (data.getBusinessStatus().equalsIgnoreCase("BLOCKED")) {
                                blockAlert(data.getStatus_comment());
                            } else if (data.getBusinessStatus().equalsIgnoreCase("UNAPPROVED")) {
                                reviewAlert();
                            } else if (data.getBusinessStatus().equalsIgnoreCase("APPROVED")) {

                                if (preference.getBusinessROlE().equals(Constants.ADMIN))
                                    onApprovedAdmin();
                                else
                                    onApprovedWorker();

                            }

                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<CheckStatusModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(GetStartedActivity.this, "check internet connectoin", Toast.LENGTH_SHORT).show();
            }
        });

    }*/

  /*  public void blockAlert(String blockMessage) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//           builder.setTitle("Are you sure ?");
        builder.setTitle("You are blocked out!");
        builder.setMessage(blockMessage);
        builder.setPositiveButton("Appeal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(GetStartedActivity.this, AppealActivity.class));
            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss(); // dismiss the dialog
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

                return true;
            }
        });

        alertdialog = builder.create();
        alertdialog.setCancelable(false);

        *//*alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        *//*
        alertdialog.show();
    }

    public void reviewAlert() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//           builder.setTitle("Are you sure ?");
        builder.setTitle("You have been blocked out!");
        builder.setMessage("We will reveiew your appeal and get back to you shortly");

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss(); // dismiss the dialog
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

                return true;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
        alertdialog = builder.create();
        alertdialog.setCancelable(false);

        *//*alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        *//*
        alertdialog.show();
    }
*/
  /*  public void loginAlert() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//           builder.setTitle("Are you sure ?");
        builder.setTitle("You have been logged out!");
        builder.setMessage("You are already login with some other device");

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss(); // dismiss the dialog
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

                return true;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                preference.logout();
                alertdialog.dismiss();
            }
        });
        alertdialog = builder.create();
        alertdialog.setCancelable(false);

        *//*alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        *//*
        alertdialog.show();
    }
*/


    public void onApprovedWorker() {
        if (preference.getCompletedStep() != null) {
            if (preference.getCompletedStep().equals("1")) {
                Intent intent = new Intent(GetStartedActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(GetStartedActivity.this, EditProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    public void onApprovedAdmin() {
        if (preference.getCompletedStep().equals("1")) {
            startActivity(new Intent(GetStartedActivity.this, JoinNowActivity.class));
            finish();
        }
        if (preference.getCompletedStep().equals("2")) {
            startActivity(new Intent(GetStartedActivity.this, EditProfileActivity.class));
            finish();
        }
        if (preference.getCompletedStep().equals("3")) {
            startActivity(new Intent(GetStartedActivity.this, NewBusinessSetupActivity.class));
            finish();
        }
        if (preference.getCompletedStep().equals("4")) {
            Intent intent2 = new Intent(GetStartedActivity.this, NewBusinessSetupActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent2.putExtra("step", "4");
            startActivity(intent2);
        }
        if (preference.getCompletedStep().equals("5")) {
            Intent intent2 = new Intent(GetStartedActivity.this, NewBusinessSetupActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent2.putExtra("step", "5");
            startActivity(intent2);
        }
        if (preference.getCompletedStep().equals("6")) {
            Intent intent2 = new Intent(GetStartedActivity.this, HomeActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        }

    }

    public void appVersionAlert() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//           builder.setTitle("Are you sure ?");
        builder.setTitle("You are using an older version!");
        builder.setMessage("Please get the latest version from playstore.");

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss(); // dismiss the dialog
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

                return true;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent sendtoApp = new Intent(android.content.Intent.ACTION_VIEW);
                sendtoApp.setData(Uri.parse("market://details?id=" + "co.inlook"));
                startActivity(sendtoApp);
            }
        });
        alertdialog = builder.create();
        alertdialog.setCancelable(false);

        /*alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        */
        alertdialog.show();
    }

}
