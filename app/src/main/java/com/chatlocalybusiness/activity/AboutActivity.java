package com.chatlocalybusiness.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Utill;

/**
 * Created by windows on 1/15/2018.
 */

public class AboutActivity  extends AppCompatActivity implements View.OnClickListener {

    ViewGroup ll_about;
    ImageView iv_arrowBack;
    RelativeLayout rl_policy;
    RelativeLayout rl_terms;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ll_about=(ViewGroup)findViewById(R.id.ll_about);
        iv_arrowBack=(ImageView)findViewById(R.id.iv_arrowBack);
        rl_terms=(RelativeLayout)findViewById(R.id.rl_terms);
        rl_policy=(RelativeLayout)findViewById(R.id.rl_policy);
        rl_policy.setOnClickListener(this);
        rl_terms.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
    new BasicUtill().CheckStatus(AboutActivity.this,0,ll_about);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.rl_policy:
                //    Utill.openUrl(getBaseContext(),"https://www.chatlocaly.com/legal");

                if(new Utill().isConnected(this)) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://www.chatlocaly.com/legal"));
                    startActivity(i);
                }else Toast.makeText(this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_terms:
                if(new Utill().isConnected(this)) {
                Intent i2 = new Intent(Intent.ACTION_VIEW);
                i2.setData(Uri.parse("https://www.chatlocaly.com/legal"));
                startActivity(i2);
                }else Toast.makeText(this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
                //  Utill.openUrl(getBaseContext(),"https://www.chatlocaly.com/legal");

                break;
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
        }


    }
}
