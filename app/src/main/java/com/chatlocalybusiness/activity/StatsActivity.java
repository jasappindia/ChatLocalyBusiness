package com.chatlocalybusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Utill;

/**
 * Created by Shiv on 12/20/2017.
 */
public class StatsActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onResume() {
        super.onResume();
        ViewGroup rl_main=(ViewGroup)findViewById(R.id.rl_main);

        new BasicUtill().CheckStatus(StatsActivity.this,0,rl_main);
    }
    private TextView tv_blocked,tv_payment;
    private ImageView iv_arrowBack ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        tv_blocked=(TextView)findViewById(R.id.tv_blocked);
        tv_payment=(TextView)findViewById(R.id.tv_payment);
        iv_arrowBack=(ImageView)findViewById(R.id.iv_arrowBack);

        iv_arrowBack.setOnClickListener(this);
        tv_blocked.setOnClickListener(this);
        tv_payment.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.tv_blocked:
                if(new Utill().isConnected(this))
                startActivity(new Intent(this,BlockedChatsActivity.class));
                else Toast.makeText(this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_payment:
                if(new Utill().isConnected(this)){
                Intent intent=new Intent(StatsActivity.this,PaymentStatsActivity.class);
                startActivity(intent);
                }else Toast.makeText(this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
