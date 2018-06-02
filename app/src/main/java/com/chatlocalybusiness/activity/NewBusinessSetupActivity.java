package com.chatlocalybusiness.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.apiModel.BusinessDetailsModel;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.apiModel.OtpVerifyModel;
import com.chatlocalybusiness.fragments.BusinesCategoryFragment;
import com.chatlocalybusiness.fragments.BusinesDetailFragment;
import com.chatlocalybusiness.fragments.LocationFragment;
import com.chatlocalybusiness.getterSetterModel.BusinessSetupDetails;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.darsh.multipleimageselect.galleryModels.Image;

import static com.chatlocalybusiness.fragments.LocationFragment.MY_PERMISSIONS_REQUEST_LOCATION;

/**
 * Created by Shiv on 12/7/2017.
 */

public class NewBusinessSetupActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {

    public static int REQUEST_CODE=201;
    private Button btn_next;
    private TextView tv_editInfo;
    private LinearLayout ll_instruct;
    ImageView iv_star, iv_star2, iv_star3, iv_checkLocation, iv_checkCategory, iv_checkBusiness;
    public static final String BUSINESS_FRAG = "Business";
    public static final String CATEGORY_FRAG = "Category";
    public static final String LOCATION_FRAG = "Location";
    public static final String OVERVIEW_FRAG = "Overview";
    public static final String PRODUCTS_FRAG = "Products";
    public static final String SERVICES_FRAG = "Services";
    public static final String PAYTM_FRAG = "PaytmRegister";
    public BusinessInfoModelNew.BusinessDetail info;
    public static int businessCheck = 0;
    public static int categoryCheck = 0;
    public static int locationCheck = 0;
    public static int overviewCheck = 0;

    private RelativeLayout rl_business, rl_category, rl_location;
    private ChatBusinessSharedPreference preference;
    private ViewGroup rl_main;
    String edit = null;
    OtpVerifyModel.Data businessSetupDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_businesssetup);
        preference = new ChatBusinessSharedPreference(NewBusinessSetupActivity.this);
        init();
        getIntentData();
    }


    public void getIntentData() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            String step = b.getString("step");
            edit = b.getString(Constants.EDIT_BUSINESS);


            info = (BusinessInfoModelNew.BusinessDetail) b.getSerializable(Constants.BUSINESS_INFO);

            if (step.equals("4")) {
                iv_checkBusiness.setVisibility(View.VISIBLE);
                businessCheck = 1;
            } else if (step.equals("5")) {
                iv_checkBusiness.setVisibility(View.VISIBLE);
                businessCheck = 1;
                iv_checkCategory.setVisibility(View.VISIBLE);
                categoryCheck = 1;
            }/* else if (step.equals("7")) {
                iv_checkBusiness.setVisibility(View.VISIBLE);
                businessCheck = 1;
                iv_checkCategory.setVisibility(View.VISIBLE);
                categoryCheck = 1;

                iv_checkLocation.setVisibility(View.VISIBLE);

                locationCheck = 1;
                setBtn_nextEnabled(true);
            }*/
        }
    }

    public void init() {
        rl_main = (ViewGroup) findViewById(R.id.rl_main);
        tv_editInfo = (TextView) findViewById(R.id.tv_editInfo);
        iv_star = (ImageView) findViewById(R.id.iv_star);
        iv_star2 = (ImageView) findViewById(R.id.iv_star2);
        iv_star3 = (ImageView) findViewById(R.id.iv_star3);
        ll_instruct = (LinearLayout) findViewById(R.id.ll_instruct);
        rl_location = (RelativeLayout) findViewById(R.id.rl_location);
        rl_category = (RelativeLayout) findViewById(R.id.rl_category);
        rl_business = (RelativeLayout) findViewById(R.id.rl_business);
        iv_checkBusiness = (ImageView) findViewById(R.id.iv_checkBusiness);
        iv_checkLocation = (ImageView) findViewById(R.id.iv_checkLocation);
        iv_checkCategory = (ImageView) findViewById(R.id.iv_checkCategory);
        btn_next = (Button) findViewById(R.id.btn_next);

        setBtn_nextEnabled(false);
        rl_business.setOnClickListener(this);
        rl_category.setOnClickListener(this);
        rl_location.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_business:

                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.BUSINESS_INFO, info);

                BusinesDetailFragment fragment = new BusinesDetailFragment(NewBusinessSetupActivity.this);
                fragment.setArguments(bundle);
                replaceFragment(fragment, BUSINESS_FRAG);
                break;
            case R.id.rl_category:
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable(Constants.BUSINESS_INFO, info);
//                if(businessSetupDetails.getUserDetail().getBusinessDetail()!=null)
//                    bundle2.putSerializable(Constants.BUSINESS_SETUP_DETAILS, businessSetupDetails);

                BusinesCategoryFragment businesCategoryFragment = new BusinesCategoryFragment(NewBusinessSetupActivity.this);
                businesCategoryFragment.setArguments(bundle2);
                replaceFragment(businesCategoryFragment, CATEGORY_FRAG);

                break;
            case R.id.rl_location:
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable(Constants.BUSINESS_INFO, info);
//                if(businessSetupDetails.getUserDetail().getLocationDetail()!=null)
//                    bundle3.putSerializable(Constants.BUSINESS_SETUP_DETAILS, businessSetupDetails);

                LocationFragment locationFragment = new LocationFragment(NewBusinessSetupActivity.this);
                locationFragment.setArguments(bundle3);
                replaceFragment(locationFragment, LOCATION_FRAG);
                break;
            case R.id.btn_next:
                if (businessCheck == 1 && categoryCheck == 1 && locationCheck == 1) {
                    businessCheck = 0;
                    categoryCheck = 0;
                    locationCheck = 0;
                    Intent intent = new Intent(NewBusinessSetupActivity.this, HomeActivity.class);
                    preference.saveCompletedStep("6");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                break;
        }
    }

    public void setBtn_nextEnabled(boolean b) {
        if (b) {
            btn_next.setClickable(true);
            btn_next.setFocusable(true);
            btn_next.setTextColor(ContextCompat.getColor(this, R.color.white));
            btn_next.setBackgroundResource(R.drawable.blue_btn_bg);
        } else {
            btn_next.setBackgroundResource(R.drawable.gray_btn_bg);
            btn_next.setClickable(false);
            btn_next.setFocusable(false);
            btn_next.setTextColor(ContextCompat.getColor(this, R.color.light_gray));
        }
        btn_next.setClickable(b);

    }

    public void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)
                .replace(R.id.container_businessSetup, fragment, tag)
                .commit();

    }

    public void enableLayouts(boolean b) {
        rl_location.setClickable(b);
        rl_category.setClickable(b);
        rl_business.setClickable(b);
    }

    public void setData(BusinessInfoModelNew.BusinessDetail info)
    {
        this.info=info;
    }
    @Override
    public void onBackPressed() {
        enableLayouts(true);
        if (NewBusinessSetupActivity.locationCheck == 1 && NewBusinessSetupActivity.businessCheck == 1 && NewBusinessSetupActivity.categoryCheck == 1)
            {
                setBtn_nextEnabled(true);
            }
            Fragment fragment;
        if ((fragment = getSupportFragmentManager().findFragmentByTag(BUSINESS_FRAG)) != null) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();
        } else if ((fragment = getSupportFragmentManager().findFragmentByTag(CATEGORY_FRAG)) != null) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();
        } else if ((fragment = getSupportFragmentManager().findFragmentByTag(LOCATION_FRAG)) != null) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();
        } else if ((fragment = getSupportFragmentManager().findFragmentByTag(OVERVIEW_FRAG)) != null) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();
        } else super.onBackPressed();

    }

    LocationManager locationManager;
    String provider;

    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(NewBusinessSetupActivity.this, 0, rl_main);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                        Criteria criteria = new Criteria();
                        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                        provider = locationManager.getBestProvider(criteria, true);

                        if (provider == null) {
                            Log.e("TAG", "No location provider found!");
                            return;
                        }
                        //Request location updates:
                        locationManager.requestLocationUpdates(provider, 400, 1, this);

                    }

                } else {

                    // sees the explanation, try again to request the permission.
                    new AlertDialog.Builder(this)
                            .setTitle("Location permmission is required to complete this process")
//                        .setMessage(R.string.text_location_permission)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Prompt the user once explanation has been shown
                                    ActivityCompat.requestPermissions(NewBusinessSetupActivity.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                                }
                            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setCancelable(true)
                            .create()
                            .show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}

