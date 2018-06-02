package com.chatlocalybusiness.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.CurrentPlanModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shiv on 12/21/2017.
 */
public class SubscriptionInfoActivity  extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_planType,tv_daysLeft,tv_lastDay,tv_lastPayment,tv_changePlan;
     private Button tv_makePayment;
     private ImageView iv_arrowBack;
     private LinearLayout ll_subsinfo;
     private Utill utill;
     private ChatBusinessSharedPreference preference;
     private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_info);
        init();
    }

    public void init()
    {
        preference=new ChatBusinessSharedPreference(this);
        utill=new Utill();
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        ll_subsinfo=(LinearLayout)findViewById(R.id.ll_subsinfo);
        tv_planType=(TextView)findViewById(R.id.tv_planType);
        tv_daysLeft=(TextView)findViewById(R.id.tv_daysLeft);
        tv_lastDay=(TextView)findViewById(R.id.tv_lastDay);
        tv_lastPayment=(TextView)findViewById(R.id.tv_lastPayment);
        tv_changePlan=(TextView)findViewById(R.id.tv_changePlan);
        tv_makePayment=(Button)findViewById(R.id.tv_makePayment);
        iv_arrowBack=(ImageView)findViewById(R.id.iv_arrowBack);
        ll_subsinfo=(LinearLayout)findViewById(R.id.ll_subsinfo);

        iv_arrowBack.setOnClickListener(this);
        tv_changePlan.setOnClickListener(this);
        tv_makePayment.setOnClickListener(this);
        getCurrentPlanInfo();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_changePlan :

                break;
            case R.id.tv_makePayment :

                break;
            case R.id.iv_arrowBack :
                onBackPressed();
                break;
        }
    }

    public void getCurrentPlanInfo()
    {
        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices= ApiClient.getClient().create(ApiServices.class);

        HashMap<String,String> params=new HashMap<>();
        params.put("encrypt_key", Constants.Encryption_Key);
        params.put("b_user_id",preference.getUserId());
        params.put("b_id",String.valueOf(preference.getBusinessId()));


        Call<CurrentPlanModel> call=apiServices.currentPlanDetails(params);
        call.enqueue(new Callback<CurrentPlanModel>() {
            @Override
            public void onResponse(Call<CurrentPlanModel> call, Response<CurrentPlanModel> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful())
                {
                    if(response.body().getData().getResultCode().equalsIgnoreCase("1"))
                    {
                        CurrentPlanModel.CurrentSubscriptionPlan planDetails=response.body().getData().getCurrentSubscriptionPlan();
                        ll_subsinfo.setVisibility(View.VISIBLE);
                        tv_planType.setText(planDetails.getSpNameLabel());
                        tv_daysLeft.setText(planDetails.getDayLeft());
                        tv_lastDay.setText(planDetails.getEndDateLabel());
                        tv_lastPayment.setText(planDetails.getPaymentType());
                    }
                    else Toast.makeText(getApplicationContext(),response.body().getData().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<CurrentPlanModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext()," check internet connection",Toast.LENGTH_SHORT).show();
            }
        });


    }

}
