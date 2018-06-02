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
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.AdapterForAllProducts;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.fragments.BlockedProductFragment;
import com.chatlocalybusiness.fragments.PublishedProductFragment;
import com.chatlocalybusiness.fragments.UnApprovedProductFragment;
import com.chatlocalybusiness.fragments.UnPublishedProductFragment;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.apiModel.ProductListModel;
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
public class  AllProductsActivity  extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private RecyclerView rv_galleryView;
    private GridLayoutManager layoutManager;
    private ImageView iv_arrowBack;
    private TextView tv_businessName,tv_noProducts;
    private BusinessInfoModelNew.BusinessDetail info;
    private ProgressBar progressBar;
    private ChatBusinessSharedPreference preference;
    private Utill utill;
    private TabLayout tabCategory;
    private ViewPager viewPager;
    private PagerForPoductsCategory pagerForPoductsCategory;
    List<ProductListModel.PublishProduct> publishedProductList;
    List<ProductListModel.UnapprovedProduct> unapprovedProducts;
    List<ProductListModel.UnpublishProduct> unpublishProducts;
    List<ProductListModel.BlockedProduct> blockedProducts;
    private SearchView searchView;
    public static boolean publishedFrag;
    public static boolean unpublished;
    public static boolean unApproved;
    public static boolean blocked;
    ViewGroup rl_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allproducts);
        rl_main=(ViewGroup)findViewById(R.id.rl_main);

        utill=new Utill();
        preference=new ChatBusinessSharedPreference(AllProductsActivity.this);
        searchView=(SearchView)findViewById(R.id.searchView);
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
//            tv_noProducts.setText(info.getProducts().size()+" Products");
        }
        iv_arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getProductList();


    }
    public  void setCategoryTab()
    {
        tabCategory.addTab(tabCategory.newTab().setText("Published"));
        tabCategory.addTab(tabCategory.newTab().setText("Unpublished"));
        tabCategory.addTab(tabCategory.newTab().setText("Unapproved"));
        tabCategory.addTab(tabCategory.newTab().setText("Blocked"));

//        tabCategory.setTabGravity(TabLayout.GRAVITY_FILL);
        tabCategory.setTabMode(TabLayout.MODE_SCROLLABLE);
        pagerForPoductsCategory = new PagerForPoductsCategory(getSupportFragmentManager(), tabCategory.getTabCount());
        viewPager.setAdapter(pagerForPoductsCategory);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabCategory));
        tabCategory.addOnTabSelectedListener(this);
    }


  public void getProductList() {
      progressBar.setVisibility(View.VISIBLE);
      ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
      HashMap<String, String> params = new HashMap<>();
      params.put("encrypt_key", Constants.Encryption_Key);
        params.put("b_user_id", preference.getUserId());
      params.put("b_id", String.valueOf(preference.getBusinessId()));


      Call<ProductListModel> call = apiServices.getProductList(params);
      call.enqueue(new Callback<ProductListModel>() {
          @Override
          public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {
              progressBar.setVisibility(View.GONE);

              if (response.isSuccessful()) {
                  if (response.body().getData().getResultCode().equals("1")) {
               publishedProductList  = response.body().getData().getProductList().getPublishProduct();
               unapprovedProducts  = response.body().getData().getProductList().getUnapprovedProduct();
               unpublishProducts   = response.body().getData().getProductList().getUnpublishProduct();
               blockedProducts       = response.body().getData().getProductList().getBlockedProduct();
                      setCategoryTab();
                  }
              }
          }

          @Override
          public void onFailure(Call<ProductListModel> call, Throwable t) {
              progressBar.setVisibility(View.GONE);
              Toast.makeText(AllProductsActivity.this,"Check internet connection",Toast.LENGTH_SHORT).show();

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

    private class PagerForPoductsCategory  extends FragmentStatePagerAdapter{

        int tabCount;
        public PagerForPoductsCategory(FragmentManager fm,int tabCount) {
            super(fm);
            this.tabCount=tabCount;
        }

        @Override
        public Fragment getItem(int position) {

        switch(position)
        {
                case 0:
                PublishedProductFragment publishedProductFragment=new PublishedProductFragment(publishedProductList);
                return  publishedProductFragment;
                case 1:
                UnPublishedProductFragment unpublishedProductFragment=new UnPublishedProductFragment(unpublishProducts);
                return  unpublishedProductFragment;
                case 2:
                UnApprovedProductFragment unApprovedProductFragment=new UnApprovedProductFragment(unapprovedProducts);
                return  unApprovedProductFragment;
                case 3:
                BlockedProductFragment blockedProductFragment=new BlockedProductFragment(blockedProducts);
                return  blockedProductFragment;
        }
        return null;
        }

        @Override
        public int getCount() {

            return tabCount;
        }
    }
}
