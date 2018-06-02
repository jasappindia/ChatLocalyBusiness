package com.chatlocalybusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.Constants;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by windows on 4/26/2018.
 */

public class SplashActivity extends AppCompatActivity {

    private ChatBusinessSharedPreference preference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preference = new ChatBusinessSharedPreference(this);
        preference.saveToken_Id(FirebaseInstanceId.getInstance().getToken());
        postdelaySplash();
    }

    public void postdelaySplash()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

                if(preference.getLoginStatus()!=null)
                {
                    if(preference.getLoginStatus().equalsIgnoreCase(Constants.LOGIN))
                    {
                        startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                        finish();
                        return;
                    }

                }else { startActivity(new Intent(SplashActivity.this,GetStartedActivity.class));
                    finish();

                }
            }
        }, 2000);
    }
}
