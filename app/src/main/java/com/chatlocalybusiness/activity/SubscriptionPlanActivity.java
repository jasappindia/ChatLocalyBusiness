package com.chatlocalybusiness.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.AdapterForPlanDetails;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.SubcriptionPlanListModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shiv on 12/21/2017.
 */

public class SubscriptionPlanActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_firmName,tv_currentPlan,tv_basicTab,tv_standardTab,tv_premiumTab,tv_monthlyTarrif,tv_yearTarrif;
    private Button btn_changePlan;
    private ImageView iv_arrowBack;
    private Utill utill;
    private ChatBusinessSharedPreference preference;
    private ProgressBar progressBar;
    private LinearLayout ll_subsPLan;
    private List<SubcriptionPlanListModel.SpPlanList> planList;
    private RecyclerView rv_planList;
    private LinearLayoutManager layoutManager;
    private AdapterForPlanDetails adapterForPlanDetails;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_plan);
        preference=new ChatBusinessSharedPreference(this);

        utill=new Utill();
        init();
        getSubscriptionPlanList();
    }
    public void init()
    {
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        tv_firmName=(TextView)findViewById(R.id.tv_firmName);
        tv_currentPlan=(TextView)findViewById(R.id.tv_currentPlan);
        tv_basicTab=(TextView)findViewById(R.id.tv_basicTab);
        tv_standardTab=(TextView)findViewById(R.id.tv_standardTab);
        tv_premiumTab=(TextView)findViewById(R.id.tv_premiumTab);
        tv_monthlyTarrif=(TextView)findViewById(R.id.tv_monthlyTarrif);
        tv_yearTarrif=(TextView)findViewById(R.id.tv_yearTarrif);
        btn_changePlan=(Button)findViewById(R.id.btn_changePlan);
        iv_arrowBack=(ImageView) findViewById(R.id.iv_arrowBack);
        ll_subsPLan=(LinearLayout) findViewById(R.id.ll_subsPLan);

        btn_changePlan.setOnClickListener(this);
        tv_premiumTab.setOnClickListener(this);
        tv_basicTab.setOnClickListener(this);
        tv_standardTab.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);

    }

    public void setPlanDetails(List<String> detailsList)
    {
        rv_planList=(RecyclerView)findViewById(R.id.rv_planList);
        layoutManager=new LinearLayoutManager(this);
        adapterForPlanDetails=new AdapterForPlanDetails(this,detailsList);
        rv_planList.setLayoutManager(layoutManager);
        rv_planList.setAdapter(adapterForPlanDetails);
    }

    public void setTabView(TextView tab, boolean b)
    {
        if(b==true)
        {
            tab.setBackgroundResource(R.drawable.promo_bg);
            tab.setTextColor(ContextCompat.getColor(this,R.color.themeColor));
        }else {
            tab.setBackgroundResource(R.drawable.promo_grey);
            tab.setTextColor(ContextCompat.getColor(this,R.color.lightblack));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_basicTab :
                setPlanDetails(planList.get(0).getFeatureList());
                setTabView(tv_basicTab,true);
                setTabView(tv_standardTab,false);
                setTabView(tv_premiumTab,false);
                tv_monthlyTarrif.setText(planList.get(0).getPriceList().get(0).getDisplayPrice());
                tv_yearTarrif.setText(planList.get(0).getPriceList().get(1).getDisplayPrice());
                break;
            case R.id.tv_standardTab :
                setPlanDetails(planList.get(1).getFeatureList());
                setTabView(tv_basicTab,false);
                setTabView(tv_standardTab,true);
                setTabView(tv_premiumTab,false);
                tv_monthlyTarrif.setText(planList.get(1).getPriceList().get(0).getDisplayPrice());
                tv_yearTarrif.setText(planList.get(1).getPriceList().get(1).getDisplayPrice());

                break;
            case R.id.tv_premiumTab :
                setPlanDetails(planList.get(2).getFeatureList());
                setTabView(tv_basicTab,false);
                setTabView(tv_standardTab,false);
                setTabView(tv_premiumTab,true);
                tv_monthlyTarrif.setText(planList.get(2).getPriceList().get(0).getDisplayPrice());
                tv_yearTarrif.setText(planList.get(2).getPriceList().get(1).getDisplayPrice());

                break;
            case R.id.btn_changePlan :

                break;
            case R.id.iv_arrowBack :
                onBackPressed();
                break;
        }
    }
    public void getSubscriptionPlanList()
    {
        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices= ApiClient.getClient().create(ApiServices.class);
        HashMap<String,String> param=new HashMap<>();
        param.put("encrypt_key", Constants.Encryption_Key);
        param.put("b_user_id",preference.getUserId());
        param.put("b_id",String.valueOf(preference.getBusinessId()));

        Call<SubcriptionPlanListModel> call=apiServices.getSubscriptionPlan(param);
        call.enqueue(new Callback<SubcriptionPlanListModel>() {
            @Override
            public void onResponse(Call<SubcriptionPlanListModel> call, Response<SubcriptionPlanListModel> response) {

                if(response.isSuccessful())
                {
                    if(response.body().getData().getResultCode().equalsIgnoreCase("1"))
                    {
                        planList = response.body().getData().getSpPlanList();
                        ll_subsPLan.setVisibility(View.VISIBLE);
                        setPlanDetails(planList.get(0).getFeatureList());
                        tv_monthlyTarrif.setText(planList.get(0).getPriceList().get(0).getDisplayPrice());
                        tv_yearTarrif.setText(planList.get(0).getPriceList().get(1).getDisplayPrice());
                        tv_currentPlan.setText(response.body().getData().getCurrentSubscriptionPlan().getSpName());
                        tv_firmName.setText(response.body().getData().getCurrentSubscriptionPlan().getBusinessName());

                    }else Toast.makeText(SubscriptionPlanActivity.this,response.body().getData().getMessage(),Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SubcriptionPlanListModel> call, Throwable t) {
                Toast.makeText(SubscriptionPlanActivity.this,"Check your internet connection",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });
    }


}
