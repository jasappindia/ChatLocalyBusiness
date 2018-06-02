package com.chatlocalybusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.utill.BasicUtill;

/**
 * Created by Shiv on 12/21/2017.
 */

public class SubscriptionActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onResume() {
        super.onResume();

        ViewGroup rl_main=(ViewGroup)findViewById(R.id.rl_main);
        new BasicUtill().CheckStatus(SubscriptionActivity.this,0,rl_main);
    }
    private TextView tv_subsInfo,tv_subsPlan;
    private ImageView iv_arrowBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        tv_subsPlan=(TextView)findViewById(R.id.tv_subsPlan);
        tv_subsInfo=(TextView)findViewById(R.id.tv_subsInfo);
        iv_arrowBack=(ImageView) findViewById(R.id.iv_arrowBack);
        tv_subsInfo.setOnClickListener(this);
        tv_subsPlan.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_subsInfo :
                startActivity(new Intent(this,SubscriptionInfoActivity.class));
                break;
            case R.id.tv_subsPlan :
                startActivity(new Intent(this,SubscriptionPlanActivity.class));
                break;
            case R.id.iv_arrowBack :
                onBackPressed();
                break;
        }
    }
}
