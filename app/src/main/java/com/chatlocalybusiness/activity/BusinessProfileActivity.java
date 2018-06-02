package com.chatlocalybusiness.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.AdapterProducts;
import com.chatlocalybusiness.adapter.AdapterServices;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shiv on 12/22/2017.
 */

public class BusinessProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_location, rl_products, rl_services, rl_address, rl_ratings;
    private RecyclerView rv_products, rv_service;
    private LinearLayoutManager layoutManagerservices, layoutManagerProducts;
    private AdapterServices adapterservices;
    private AdapterProducts adapterProducts;
    private ViewGroup rl_productContainer, rl_sericesContainer, rl_ratingsContainer;
    private ViewGroup newViewProducts, newViewServices, newViewRatings;
    private TextView tv_businessName, tv_homeservice, tv_contact, tv_ImageNo, tv_address, tv_mainRating;

    private TextView tv_viewAllProducts, tv_viewAllServices;

    private ImageView iv_filledRating1, iv_filledRating2, iv_filledRating3, iv_filledRating4, iv_filledRating5;
    private TextView tv_noRatings1, tv_noRatings2, tv_noRatings3, tv_noRatings4, tv_noRatings5, tv_avgRatings, tv_totalRatings;
    private ImageView iv_editBusiness, iv_banner, iv_businessIcon, iv_arrowBack, iv_fwdMapIcon,iv_mainRatings;
    private ImageView iv_ratingsDown, iv_seviceDown, iv_locationDown, iv_productsDown, iv_home;
    private Utill utill;
    private ChatBusinessSharedPreference preference;
    private ProgressBar progressBar;
    private LinearLayout ll_info;
    private BusinessInfoModelNew.BusinessDetail info;
    private ViewPager vp_banner;
    private ViewGroup rl_main;
    private PagerForBanner pagerForBanner;
    private ViewPager viewpager;
    public static int businessProfileApi = 0;
    private LinearLayout ll_noRating, ll_ratings;
    private ScrollView scrollView;

//    private PagerForBanner pagerForBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);

        rl_main = (ViewGroup) findViewById(R.id.rl_main);
        viewpager = (ViewPager) findViewById(R.id.vp_banner);

        preference = new ChatBusinessSharedPreference(this);
        utill = new Utill();
        init();

//        ll_info.setVisibility(View.VISIBLE);

    }

    public void setViewPager() {
        pagerForBanner = new PagerForBanner(this, info.getBusinessImages());
        viewpager.setAdapter(pagerForBanner);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_ImageNo.setText(String.valueOf(position + 1) + "/" + info.getBusinessImages().size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(BusinessProfileActivity.this, 0, rl_main);
        if (businessProfileApi == 1)
            getBusinessDetails();

    }

    public void init() {
        rl_productContainer = (ViewGroup) findViewById(R.id.rl_productContainer);
        rl_sericesContainer = (ViewGroup) findViewById(R.id.rl_sericesContainer);
        rl_ratingsContainer = (ViewGroup) findViewById(R.id.rl_ratingsContainer);

//      vp_banner=(ViewPager)findViewById(R.id.vp_banner);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        ll_info = (LinearLayout) findViewById(R.id.ll_info);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rl_address = (RelativeLayout) findViewById(R.id.rl_address);
        rl_location = (RelativeLayout) findViewById(R.id.rl_location);
        rl_products = (RelativeLayout) findViewById(R.id.rl_products);
        rl_services = (RelativeLayout) findViewById(R.id.rl_services);
        rl_ratings = (RelativeLayout) findViewById(R.id.rl_ratings);
        iv_editBusiness = (ImageView) findViewById(R.id.iv_editBusiness);
        iv_banner = (ImageView) findViewById(R.id.iv_banner);
        iv_businessIcon = (ImageView) findViewById(R.id.iv_businessIcon);
        iv_mainRatings = (ImageView) findViewById(R.id.iv_mainRatings);
        tv_businessName = (TextView) findViewById(R.id.tv_businessName);
        tv_homeservice = (TextView) findViewById(R.id.tv_homeservice);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        tv_ImageNo = (TextView) findViewById(R.id.tv_ImageNo);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_mainRating = (TextView) findViewById(R.id.tv_mainRating);

        iv_ratingsDown = (ImageView) findViewById(R.id.iv_ratingsDown);
        iv_seviceDown = (ImageView) findViewById(R.id.iv_seviceDown);
        iv_locationDown = (ImageView) findViewById(R.id.iv_locationDown);
        iv_productsDown = (ImageView) findViewById(R.id.iv_productsDown);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        iv_fwdMapIcon = (ImageView) findViewById(R.id.iv_fwdMapIcon);
        iv_home = (ImageView) findViewById(R.id.iv_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rl_location.setOnClickListener(this);
        rl_products.setOnClickListener(this);
        rl_services.setOnClickListener(this);
        rl_ratings.setOnClickListener(this);
        iv_editBusiness.setOnClickListener(this);
        iv_banner.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
        iv_fwdMapIcon.setOnClickListener(this);

        setImageRatio();
        getBusinessDetails();
    }

    public void setImageRatio() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
        int width = pxToDp(displayMetrics.widthPixels);
        double height = displayMetrics.widthPixels * (0.53);
        int height1 = (int) height;

//        iv_banner.setLayoutParams(new RelativeLayout.LayoutParams(width,height));
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_banner.getLayoutParams();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewpager.getLayoutParams();
        params.height = height1;
        params.width = displayMetrics.widthPixels;

    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fwdMapIcon:
                Utill.callMapIntent(this, info.getAddress());
                break;
            case R.id.rl_location:
                if (rl_address.getVisibility() == View.VISIBLE) {
                    rl_address.setVisibility(View.GONE);
                    iv_locationDown.setImageResource(R.drawable.down_arrow);

                } else {
                    rl_address.setVisibility(View.VISIBLE);
                    tv_address.setText(info.getAddress());
                    iv_locationDown.setImageResource(R.drawable.up_arrow);
                }
                break;
            case R.id.rl_products:
                if (info.getProducts() != null) {
                    if (info.getProducts().size() < 1)
                        Toast.makeText(this, "No Product Uploaded", Toast.LENGTH_SHORT).show();
//                        rl_products.setVisibility(View.GONE);
                    else {
                        if (rl_productContainer.getChildCount() == 0) {
                            addProducts();
                        } else {
                            rl_productContainer.removeView(newViewProducts);
                            iv_productsDown.setImageResource(R.drawable.down_arrow);
                        }
                    }
                }

// setRecycleProducts();
                break;
            case R.id.rl_services:

                if (info.getServices() != null) {
                    if (info.getServices().size()<1)
                    {
                        Toast.makeText(this, "No Service Uploaded", Toast.LENGTH_SHORT).show();

                    } else {
                        if (rl_sericesContainer.getChildCount() == 0) {
                            addServices();
                        } else {
                            rl_sericesContainer.removeView(newViewServices);
                            iv_seviceDown.setImageResource(R.drawable.down_arrow);
                        }
                    }
                }
                break;
            case R.id.iv_editBusiness:
                if(new Utill().isConnected(this))
                   editBusinessProfile();
                else Toast.makeText(this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_banner:
                Intent intent = new Intent(BusinessProfileActivity.this, BusinessBannerShowActivity.class);
                intent.putExtra(Constants.PRODUCT_SERIALIZABLE, info);
                startActivity(intent);
                break;
            case R.id.rl_ratings:
                if (rl_ratingsContainer.getChildCount() == 0) {
                    addRatingsBar();
                } else {
                    rl_ratingsContainer.removeView(newViewRatings);
                    iv_ratingsDown.setImageResource(R.drawable.down_arrow);
                }
                break;
            case R.id.iv_arrowBack:
                onBackPressed();
                break;


        }
    }

    public void editBusinessProfile() {
        PopupMenu popupMenu = new PopupMenu(BusinessProfileActivity.this, iv_editBusiness);

        popupMenu.getMenuInflater().inflate(R.menu.edit_business_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_editBusinessInfo:
                        if (preference.getEditBusiness().equalsIgnoreCase("1")) {
                            Intent editbusiness = new Intent(BusinessProfileActivity.this, EditBusinessSetupActivity.class);
                            editbusiness.putExtra(Constants.EDIT_BUSINESS, "YES");
                            editbusiness.putExtra(Constants.BUSINESS_INFO, info);
                            startActivity(editbusiness);
                        } else
                            Toast.makeText(BusinessProfileActivity.this, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_editOverview:
                        if (preference.getEditBusinessOverview().equalsIgnoreCase("1")) {

                            Intent editbusinessintent = new Intent(BusinessProfileActivity.this, EditBusinessOverviewActivity.class);


                            editbusinessintent.putExtra("Edit", "Edit");
                            editbusinessintent.putExtra(Constants.BUSINESS_SETUP_DETAILS,info);
                            startActivity(editbusinessintent);
                        } else
                            Toast.makeText(BusinessProfileActivity.this, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.action_AddProducts:
                        if (preference.getAddProducts().equalsIgnoreCase("1")) {
                            Intent addProductintent = new Intent(BusinessProfileActivity.this, AddProductActivity.class);
                            startActivity(addProductintent);
                        } else {
                            Toast.makeText(BusinessProfileActivity.this, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.action_AddSevices:

                        if (preference.getAddSevice().equalsIgnoreCase("1")) {
                            Intent addserviceintent = new Intent(BusinessProfileActivity.this, AddServiceActivity.class);
                            startActivity(addserviceintent);
                        } else
                            Toast.makeText(BusinessProfileActivity.this, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();

                        break;
                }
                return true;
            }
        });
        popupMenu.show(); //showing popup menu
    }

    private void addProducts() {
        // Instantiate a new "row" view.
        newViewProducts = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.list_product, rl_productContainer, false);
        setRecycleProducts();

//        productsView();
        rl_productContainer.addView(newViewProducts);
        iv_productsDown.setImageResource(R.drawable.up_arrow);
//        scrollView.fullScroll(ScrollView.FOCUS_DOWN);


    }

    private void addRatingsBar() {
        newViewRatings = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.ratings_layout, rl_ratingsContainer, false);
        showRatingBar();
        if (info.getRatings().getOneStar() == 0 && info.getRatings().getTwoStar() == 0 && info.getRatings().getThreeStar() == 0 &&
                info.getRatings().getFourStar() == 0 && info.getRatings().getFiveStar() == 0) {
            ll_noRating.setVisibility(View.VISIBLE);
            ll_ratings.setVisibility(View.GONE);
        } else {
            ll_noRating.setVisibility(View.GONE);
            ll_ratings.setVisibility(View.VISIBLE);
        }
        rl_ratingsContainer.addView(newViewRatings);
        iv_ratingsDown.setImageResource(R.drawable.up_arrow);


        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //    scrollView.smoothScrollTo(0,ll_info.getBottom());
                //  scrollView.fullScroll(View.FOCUS_DOWN);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                // y = sView.getScrollY();
                //    scrollView.smoothScrollTo(0, newViewRatings.getBottom());
            }
        }, 200);
    }

    public void showRatingBar() {

        ll_ratings = (LinearLayout) newViewRatings.findViewById(R.id.ll_ratings);
        ll_noRating = (LinearLayout) newViewRatings.findViewById(R.id.ll_noRating);
        iv_filledRating5 = (ImageView) newViewRatings.findViewById(R.id.iv_filledRating5);
        iv_filledRating4 = (ImageView) newViewRatings.findViewById(R.id.iv_filledRating4);
        iv_filledRating3 = (ImageView) newViewRatings.findViewById(R.id.iv_filledRating3);
        iv_filledRating2 = (ImageView) newViewRatings.findViewById(R.id.iv_filledRating2);
        iv_filledRating1 = (ImageView) newViewRatings.findViewById(R.id.iv_filledRating1);

        tv_noRatings5 = (TextView) newViewRatings.findViewById(R.id.tv_noRatings5);
        tv_noRatings4 = (TextView) newViewRatings.findViewById(R.id.tv_noRatings4);
        tv_noRatings3 = (TextView) newViewRatings.findViewById(R.id.tv_noRatings3);
        tv_noRatings2 = (TextView) newViewRatings.findViewById(R.id.tv_noRatings2);
        tv_noRatings1 = (TextView) newViewRatings.findViewById(R.id.tv_noRatings1);
        tv_totalRatings = (TextView) newViewRatings.findViewById(R.id.tv_totalRatings);
        tv_avgRatings = (TextView) newViewRatings.findViewById(R.id.tv_avgRatings);

        tv_avgRatings.setText(info.getAvgRating());
        if (info.getRatingCount().equals("1"))
            tv_totalRatings.setText(info.getRatingCount() + " Rating");
        else
            tv_totalRatings.setText(info.getRatingCount() + " Ratings");
        try {
            setRatingPercentage();
            setRatingBars();
        } catch (Exception ex) {
        }
    }

    ArrayList<Float> ratingArray = new ArrayList<>();
    float max;

    public void setRatingPercentage() {
        if (ratingArray != null)
            ratingArray.clear();
        ratingArray.add(Float.valueOf(info.getRatings().getOneStar()));
        ratingArray.add(Float.valueOf(info.getRatings().getTwoStar()));
        ratingArray.add(Float.valueOf(info.getRatings().getThreeStar()));
        ratingArray.add(Float.valueOf(info.getRatings().getFourStar()));
        ratingArray.add(Float.valueOf(info.getRatings().getFiveStar()));
        Collections.sort(ratingArray);
        max = ratingArray.get(4);

        ratingArray.clear();
        if (max > 0) {
            ratingArray.add((info.getRatings().getOneStar()) / max);
            ratingArray.add((info.getRatings().getTwoStar()) / max);
            ratingArray.add((info.getRatings().getThreeStar()) / max);
            ratingArray.add((info.getRatings().getFourStar()) / max);
            ratingArray.add((info.getRatings().getFiveStar()) / max);
        } else {
            ratingArray.add(Float.valueOf(0));
            ratingArray.add(Float.valueOf(0));
            ratingArray.add(Float.valueOf(0));
            ratingArray.add(Float.valueOf(0));
            ratingArray.add(Float.valueOf(0));
        }
        tv_noRatings5.setText("(" + info.getRatings().getFiveStar() + ")");
        tv_noRatings4.setText("(" + info.getRatings().getFourStar() + ")");
        tv_noRatings3.setText("(" + info.getRatings().getThreeStar() + ")");
        tv_noRatings2.setText("(" + info.getRatings().getTwoStar() + ")");
        tv_noRatings1.setText("(" + info.getRatings().getOneStar() + ")");

        Log.e("aarrray", ratingArray + "");
    }

    public void setRatingBars() {
        LinearLayout.LayoutParams params5 = (LinearLayout.LayoutParams) iv_filledRating5.getLayoutParams();
        LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams) iv_filledRating4.getLayoutParams();
        LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) iv_filledRating3.getLayoutParams();
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) iv_filledRating2.getLayoutParams();
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) iv_filledRating1.getLayoutParams();


        params5.weight = ratingArray.get(4);
//          iv_filledRating5.setLayoutParams(params5);
        params4.weight = ratingArray.get(3);
//          iv_filledRating5.setLayoutParams(params4);
        params3.weight = ratingArray.get(2);
//          iv_filledRating5.setLayoutParams(params3);
        params2.weight = ratingArray.get(1);
//          iv_filledRating5.setLayoutParams(params2);
        params1.weight = ratingArray.get(0);
//          iv_filledRating5.setLayoutParams(params1);
    }

    ImageView iv_right, iv_left;

    private void setRecycleProducts() {
        if (info != null) {
            if (info.getProducts().size() > 0) {
                rv_products = (RecyclerView) newViewProducts.findViewById(R.id.rv_products);
                layoutManagerProducts = new LinearLayoutManager(BusinessProfileActivity.this, LinearLayoutManager.HORIZONTAL, true);
                adapterProducts = new AdapterProducts(BusinessProfileActivity.this, info);
                rv_products.setLayoutManager(layoutManagerProducts);
                rv_products.setAdapter(adapterProducts);


                tv_viewAllProducts = (TextView) newViewProducts.findViewById(R.id.tv_viewAllProducts);
                iv_right = (ImageView) newViewProducts.findViewById(R.id.iv_right);
                iv_left = (ImageView) newViewProducts.findViewById(R.id.iv_left);
                tv_viewAllProducts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                    startActivity(new Intent(BusinessProfileActivity.this, AllProductsActivity.class));
                        Intent intent = new Intent(BusinessProfileActivity.this, AllProductsActivity.class);

                        intent.putExtra(Constants.PRODUCT_SERIALIZABLE, (Serializable) info);
                        startActivity(intent);
                    }
                });
            } else
                Toast.makeText(getApplicationContext(), " No Products Uploaded", Toast.LENGTH_SHORT).show();
        }
    }

    private void addServices() {
        // Instantiate a new "row" view.
        newViewServices = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.list_services, rl_sericesContainer, false);
        setRecycleServices();
//        servicesView();
        rl_sericesContainer.addView(newViewServices);
        iv_seviceDown.setImageResource(R.drawable.up_arrow);

        scrollView.fullScroll(ScrollView.FOCUS_DOWN);

    }

    private void setRecycleServices() {
        if (info != null) {
            if (info.getProducts().size() > 0) {
                rv_service = (RecyclerView) newViewServices.findViewById(R.id.rv_service);
                layoutManagerservices = new LinearLayoutManager(BusinessProfileActivity.this, LinearLayoutManager.HORIZONTAL, true);
                adapterservices = new AdapterServices(BusinessProfileActivity.this, info);
                rv_service.setLayoutManager(layoutManagerservices);
                rv_service.setAdapter(adapterservices);
                tv_viewAllServices = (TextView) newViewServices.findViewById(R.id.tv_viewAllServices);
                tv_viewAllServices.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(BusinessProfileActivity.this, AllServicesActivity.class);

                        intent.putExtra(Constants.PRODUCT_SERIALIZABLE, info);
                        startActivity(intent);
                    }
                });
            } else
                Toast.makeText(getApplicationContext(), " No Products Uploaded", Toast.LENGTH_SHORT).show();

        }
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
        getMenuInflater().inflate(R.menu.edit_business_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_editBusinessInfo:
                if (preference.getEditBusiness().equalsIgnoreCase("1")) {
                    Intent editbusiness = new Intent(BusinessProfileActivity.this, BusinessSetupActivity.class);
                    editbusiness.putExtra(Constants.EDIT_BUSINESS, "YES");
                    startActivity(editbusiness);
                } else
                    Toast.makeText(this, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_editOverview:
                if (preference.getEditBusinessOverview().equalsIgnoreCase("1")) {

                    Intent editbusinessintent = new Intent(BusinessProfileActivity.this, EditBusinessOverviewActivity.class);
                    startActivity(editbusinessintent);
                } else
                    Toast.makeText(this, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();

                break;
            case R.id.action_AddProducts:
                if (preference.getAddProducts().equalsIgnoreCase("1")) {
                    Intent addProductintent = new Intent(BusinessProfileActivity.this, AddProductActivity.class);
                    startActivity(addProductintent);
                } else
                    Toast.makeText(this, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();

                break;

            case R.id.action_AddSevices:

                if (preference.getAddSevice().equalsIgnoreCase("1")) {
                    Intent addserviceintent = new Intent(BusinessProfileActivity.this, AddServiceActivity.class);
                    startActivity(addserviceintent);
                } else
                    Toast.makeText(this, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    public void getBusinessDetails() {
        final ProgressDialog dialog=Utill.showloader(this);
        dialog.show();
        dialog.setCancelable(false);
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", Constants.Encryption_Key);
        params.put("b_user_id", preference.getUserId());
      /*  params.put("b_user_id","2");
        params.put("b_id","2");*/
        params.put("b_id", String.valueOf(preference.getBusinessId()));

        Call<BusinessInfoModelNew> call = apiServices.getBusiessDetail(params);
        call.enqueue(new Callback<BusinessInfoModelNew>() {
            @Override
            public void onResponse(Call<BusinessInfoModelNew> call, Response<BusinessInfoModelNew> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        ll_info.setVisibility(View.VISIBLE);
                        info = response.body().getData().getBusinessDetail();
                        setViewPager();
                        if (!info.getBusinessBanner().equalsIgnoreCase(""))
                            Glide.with(BusinessProfileActivity.this).load(info.getBusinessBanner()).into(iv_banner);
                        if (!info.getBusinessLogo().equalsIgnoreCase(""))
                            Glide.with(BusinessProfileActivity.this).load(info.getBusinessLogo()).into(iv_businessIcon);
                        tv_businessName.setText(info.getBusinessName());
                        preference.setBusinessName(info.getBusinessName());
                        tv_contact.setText(info.getBMobileNumber());
                        tv_ImageNo.setText("1/" + info.getBusinessImages().size());
                        if (info.getHomeServices().equalsIgnoreCase("YES")) {
                            tv_homeservice.setText("Home Service upto " + info.getServicesKm()+" Km");
                            iv_home.setImageResource(R.drawable.home_service);
                        } else {
                            tv_homeservice.setText("No Home Service");
                            iv_home.setImageResource(R.drawable.home_service_grey);
                        }
                        businessProfileApi = 0;
                        if (info.getAvgRating().equalsIgnoreCase("0")||info.getAvgRating().equalsIgnoreCase("0.0")||info.getAvgRating().equalsIgnoreCase("0.00")) {
                            tv_mainRating.setText("No ratings yet");
                            iv_mainRatings.setImageResource(R.drawable.star_gray);

                        }else tv_mainRating.setText(info.getAvgRating() + " ("+ info.getRatingCount()+" Ratings)");
                        /*if(info.getProducts()!=null)
                        {
                            if(info.getProducts().size()<1)
                            rl_products.setVisibility(View.GONE);
                            else rl_products.setVisibility(View.VISIBLE);

                        }   if(info.getServices()!=null)
                        {
                            if(info.getServices().size()<1)
                            rl_services.setVisibility(View.GONE);
                            else rl_services.setVisibility(View.VISIBLE);

                        }*/
                    }
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<BusinessInfoModelNew> call, Throwable t) {
                dialog.dismiss();

                //                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class PagerForBanner extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;
        List<BusinessInfoModelNew.BusinessImage> businessImages;

        public PagerForBanner(Context context, List<BusinessInfoModelNew.BusinessImage> businessImages) {
            mContext = context;
            this.businessImages = businessImages;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            if (businessImages != null)
                return businessImages.size();
            else return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {


            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View itemView = mLayoutInflater.inflate(R.layout.product_mage_pager, container, false);

            final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            file:///storage/emulated/0/cropped1517381496911.jpg

            Glide.with(mContext).load(businessImages.get(position).getImageName()).into(imageView);

//        imageView.setImageURI(Uri.parse("file://"+filePathList.get(position)));
            container.addView(itemView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BusinessProfileActivity.this, BusinessBannerShowActivity.class);
                    intent.putExtra(Constants.PRODUCT_SERIALIZABLE, info);
                    intent.putExtra(Constants.POSITION, position);
                    startActivity(intent);
                }
            });

            return itemView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
