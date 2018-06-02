package com.chatlocalybusiness.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.AdapterForAllServices;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.apiModel.ProductListModel;
import com.chatlocalybusiness.apiModel.ServiceListModel;
import com.chatlocalybusiness.fragments.BlockedServiceFragment;
import com.chatlocalybusiness.fragments.PublishedServiceFragment;
import com.chatlocalybusiness.fragments.UnApprovedServiceFragment;
import com.chatlocalybusiness.fragments.UnPublishedServiceFragment;
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
 * Created by windows on 12/29/2017.
 */
public class AllServicesActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private RecyclerView rv_galleryView;
    private GridLayoutManager layoutManager;
    private AdapterForAllServices adapterForAllProducts;
    private ImageView iv_arrowBack;
    private TextView tv_businessName,tv_noProducts;
    private BusinessInfoModelNew.BusinessDetail info;
    private ProgressBar progressBar;
    private ChatBusinessSharedPreference preference;
    private Utill utill;
    private TabLayout tabCategory;
    private ViewPager viewPager;
    private PagerForServiceCategory pagerForServiceCategory;
    private List<ServiceListModel.PublishService>    publishedService;
    private List<ServiceListModel.UnpublishService>  unpublishedService;
    private List<ServiceListModel.UnapprovedService> unapprovedService;
    private List<ServiceListModel.BlockedService>    blockedService;
    private ViewGroup rl_main;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allproducts);
        rl_main=(ViewGroup)findViewById(R.id.rl_main);

        utill=new Utill();
        preference=new ChatBusinessSharedPreference(AllServicesActivity.this);
        tv_businessName=(TextView)findViewById(R.id.tv_businessName);
        tv_noProducts=(TextView)findViewById(R.id.tv_noProducts);
        iv_arrowBack=(ImageView)findViewById(R.id.iv_arrowBack);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        tabCategory=(TabLayout)findViewById(R.id.tab_Categories);
        viewPager=(ViewPager)findViewById(R.id.view_pager);


        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            info = (BusinessInfoModelNew.BusinessDetail) getIntent().getExtras().getSerializable(Constants.PRODUCT_SERIALIZABLE);
            tv_businessName.setText(info.getBusinessName());
        }
        iv_arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getBusinessDetails();

    }

    public  void setCategoryTab()
    {
        tabCategory.addTab(tabCategory.newTab().setText("Published"));
        tabCategory.addTab(tabCategory.newTab().setText("Unpublished"));
        tabCategory.addTab(tabCategory.newTab().setText("Unapproved"));
        tabCategory.addTab(tabCategory.newTab().setText("Blocked"));

//        tabCategory.setTabGravity(TabLayout.GRAVITY_FILL);
        tabCategory.setTabMode(TabLayout.MODE_SCROLLABLE);
        pagerForServiceCategory = new PagerForServiceCategory(getSupportFragmentManager(), tabCategory.getTabCount());
        viewPager.setAdapter(pagerForServiceCategory);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabCategory));
        tabCategory.addOnTabSelectedListener(this);
    }


    public void setRecycleView(BusinessInfoModelNew.BusinessDetail info)
    {
        rv_galleryView=(RecyclerView)findViewById(R.id.rv_galleryView);
        layoutManager=new GridLayoutManager(this,2);
        adapterForAllProducts=new AdapterForAllServices(this,info);
        rv_galleryView.setLayoutManager(layoutManager);
        rv_galleryView.setAdapter(adapterForAllProducts);
    }
    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(AllServicesActivity.this,0,rl_main);
    }

    public void getBusinessDetails()
    {
        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices= ApiClient.getClient().create(ApiServices.class);
        HashMap<String,String> params=new HashMap<>();
        params.put("encrypt_key", Constants.Encryption_Key);
        params.put("b_user_id",preference.getUserId());
        params.put("b_id",String.valueOf(preference.getBusinessId()));

        Call<ServiceListModel> call=apiServices.getServiceList(params);
        call.enqueue(new Callback<ServiceListModel>() {
            @Override
            public void onResponse(Call<ServiceListModel> call, Response<ServiceListModel> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful())
                {
                    if(response.body().getData().getResultCode().equalsIgnoreCase("1"))
                    {
                        publishedService  = response.body().getData().getServiceList().getPublishService();
                        unpublishedService= response.body().getData().getServiceList().getUnpublishService();
                        unapprovedService= response.body().getData().getServiceList().getUnapprovedService();
                        blockedService  = response.body().getData().getServiceList().getBlockedService();
                        setCategoryTab();
                    }
                }
            }
            @Override
            public void onFailure(Call<ServiceListModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Check Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private class PagerForServiceCategory  extends FragmentStatePagerAdapter {

        int tabCount;

        public PagerForServiceCategory(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount=tabCount;
        }

        @Override
        public Fragment getItem(int position) {

            switch(position)
            {
                case 0:
                    PublishedServiceFragment publishedProductFragment=new PublishedServiceFragment(publishedService);
                    return  publishedProductFragment;
                case 1:
                    UnPublishedServiceFragment unPublishedServiceFragment=new UnPublishedServiceFragment (unpublishedService);
                    return  unPublishedServiceFragment;
                case 2:
                    UnApprovedServiceFragment unApprovedServiceFragment=new UnApprovedServiceFragment(unapprovedService);
                    return  unApprovedServiceFragment;
                case 3:
                    BlockedServiceFragment blockedServiceFragment=new BlockedServiceFragment(blockedService);
                    return  blockedServiceFragment;
            }
            return null;
        }

        @Override
        public int getCount() {

            return tabCount;
        }
    }
}
