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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.fragments.BusinesCategoryFragment;
import com.chatlocalybusiness.fragments.BusinesDetailFragment;
import com.chatlocalybusiness.fragments.LocationFragment;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;

import static com.chatlocalybusiness.fragments.LocationFragment.MY_PERMISSIONS_REQUEST_LOCATION;

/**
 * Created by Shiv on 12/7/2017.
 */

public class BusinessSetupActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener ,LocationListener{

      private CheckBox check_businessCat,check_Category,check_location,check_Overview,check_products,check_Services;
      private Button btn_next;
      private TextView tv_editInfo;
      private LinearLayout ll_instruct;
      ImageView iv_star,iv_star2,iv_star3;
      public static final String BUSINESS_FRAG="Business";
      public static final String CATEGORY_FRAG="Category" ;
      public static final String LOCATION_FRAG="Location";
      public static final String OVERVIEW_FRAG="Overview";
      public static final String PRODUCTS_FRAG="Products" ;
      public static final String SERVICES_FRAG="Services";
      public static final String PAYTM_FRAG="PaytmRegister";
      public static  int businessCheck=0;
      public static  int categoryCheck=0;
      public static  int locationCheck=0;
      public static  int overviewCheck=0;
    private RelativeLayout rl_business,rl_category,rl_location;
    private ChatBusinessSharedPreference preference;
    private ViewGroup rl_main;
    String edit=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businesssetup);
        rl_main=(ViewGroup)findViewById(R.id.rl_main);
        tv_editInfo=(TextView) findViewById(R.id.tv_editInfo);
       iv_star=(ImageView)findViewById(R.id.iv_star);
       iv_star2=(ImageView)findViewById(R.id.iv_star2);
       iv_star3=(ImageView)findViewById(R.id.iv_star3);
        ll_instruct=(LinearLayout) findViewById(R.id.ll_instruct);
        preference=new ChatBusinessSharedPreference(BusinessSetupActivity.this);
        btn_next=(Button)findViewById(R.id.btn_next);
        check_businessCat=(CheckBox)findViewById(R.id.check_businessCat);
        check_Category=(CheckBox)findViewById(R.id.check_Category);
        check_location=(CheckBox)findViewById(R.id.check_location);
        rl_location=(RelativeLayout) findViewById(R.id.rl_location);
        rl_category=(RelativeLayout) findViewById(R.id.rl_category);
        rl_business=(RelativeLayout) findViewById(R.id.rl_business);
        setBtn_nextEnabled(false);

        Bundle b=getIntent().getExtras();
        if(b!=null) {
            String step = b.getString("step");
            edit = b.getString(Constants.EDIT_BUSINESS);
            if (edit != null) {
                setBtn_nextEnabled(true);
                tv_editInfo.setText("Edit Business Info");
                ll_instruct.setVisibility(View.INVISIBLE);
                iv_star.setVisibility(View.GONE);
                iv_star2.setVisibility(View.GONE);
                iv_star3.setVisibility(View.GONE);
                btn_next.setText("Back");
            }else {
                if (step.equals("4")) {
                    check_businessCat.setChecked(true);
                    check_businessCat.setClickable(false);
                    businessCheck = 1;
                } else if (step.equals("5")) {
                    check_businessCat.setChecked(true);
                    check_businessCat.setClickable(false);
                    businessCheck = 1;
                    check_Category.setChecked(true);
                    check_Category.setClickable(false);
                    categoryCheck = 1;
                } else if (step.equals("7")) {
                    check_businessCat.setChecked(true);
                    check_businessCat.setClickable(false);
                    businessCheck = 1;

                    check_Category.setChecked(true);
                    check_Category.setClickable(false);
                    categoryCheck = 1;

                    check_location.setChecked(true);
                    check_location.setClickable(false);
                    locationCheck = 1;
                    setBtn_nextEnabled(true);

                }
            }
        }
        check_businessCat.setOnCheckedChangeListener(this);
        check_Category.setOnCheckedChangeListener(this);
        check_location.setOnCheckedChangeListener(this);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(businessCheck==1&&categoryCheck==1&&locationCheck==1){
                    businessCheck = 0;
                    categoryCheck = 0;
                    locationCheck = 0;
                    Intent intent=new Intent(BusinessSetupActivity.this, HomeActivity.class);
                    preference.saveCompletedStep("6");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
              }else if(edit!=null)
                {
                    onBackPressed();
                }

            }
        });
    }

    public void setBtn_nextEnabled(boolean b)
    {
        if(b) {
            btn_next.setClickable(true);
            btn_next.setFocusable(true);
            btn_next.setTextColor(ContextCompat.getColor(this, R.color.white));
            btn_next.setBackgroundResource(R.drawable.blue_btn_bg);
        }
        else{
            btn_next.setBackgroundResource(R.drawable.gray_btn_bg);

            btn_next.setClickable(false);
            btn_next.setFocusable(false);
            btn_next.setTextColor(ContextCompat.getColor(this,R.color.light_gray));
        }
        btn_next.setClickable(b);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
     if(edit==null)
         addbusinessDetails(compoundButton,b);
     else editBusinessDetails(compoundButton,b);

    }
    public void addbusinessDetails(CompoundButton compoundButton, boolean b)
    {
        switch (compoundButton.getId())
        {
            case R.id.check_businessCat:

                if(b&&businessCheck==0) {
//                    replaceFragment(new BusinesDetailFragment(),BUSINESS_FRAG);
                }
                break;
            case R.id.check_Category:
/*

                if(b&&categoryCheck==0&&businessCheck==1) {
                    replaceFragment(new BusinesCategoryFragment(),CATEGORY_FRAG);
                }else
                    Toast.makeText(getApplicationContext(), "Fill Business Detail First", Toast.LENGTH_SHORT).show();
*/

                break;
            case R.id.check_location:
               /* if(b&&categoryCheck==1&&businessCheck==1) {
                    replaceFragment(new LocationFragment(),LOCATION_FRAG);
                }else
                    Toast.makeText(getApplicationContext(), "Fill above details first", Toast.LENGTH_SHORT).show();
*/
                break;
          }

    }
    public void editBusinessDetails(CompoundButton compoundButton, boolean b)
    {

        switch (compoundButton.getId())
        {
            case R.id.check_businessCat:

                if(b&&businessCheck==0) {
//                    replaceFragment(new BusinesDetailFragment(),BUSINESS_FRAG);
                }
                break;
            case R.id.check_Category:
/*
                if(b&&categoryCheck==0) {
                    replaceFragment(new BusinesCategoryFragment(),CATEGORY_FRAG);
                }*/
                break;
            case R.id.check_location:
                /*if(b&&locationCheck==0) {
                    replaceFragment(new LocationFragment(),LOCATION_FRAG);
                }*/
                break;
        }
    }

    public void replaceFragment( Fragment fragment,String tag)
    {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)
                .replace(R.id.container_businessSetup, fragment,tag)
                .commit();

    }
    public void enableLayouts(boolean b)
    {
        rl_location.setClickable(b);
        rl_category.setClickable(b);
        rl_business.setClickable(b);
    }
    @Override
    public void onBackPressed() {
        enableLayouts(true);
        Fragment fragment;
    if((fragment=getSupportFragmentManager().findFragmentByTag(BUSINESS_FRAG))!=null){
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();
       check_businessCat.setChecked(false);
    }
    else if((fragment=getSupportFragmentManager().findFragmentByTag(CATEGORY_FRAG))!=null){
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();
       check_Category.setChecked(false);
    }
    else if((fragment=getSupportFragmentManager().findFragmentByTag(LOCATION_FRAG))!=null)
    {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();
           check_location.setChecked(false);
    } else if((fragment=getSupportFragmentManager().findFragmentByTag(OVERVIEW_FRAG))!=null)
    {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();
           check_location.setChecked(false);
    }

    /*else if((fragment=getSupportFragmentManager().findFragmentByTag(PAYTM_FRAG))!=null)
    {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();
           check_location.setChecked(false);
    }*/
    else {
        super.onBackPressed();
        preference.logout();
    }
    }
    LocationManager locationManager;
    String provider;
    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(BusinessSetupActivity.this,0,rl_main);

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
                        locationManager = (LocationManager)getSystemService( Context.LOCATION_SERVICE );

                        Criteria criteria = new Criteria();
                        criteria.setAccuracy( Criteria.ACCURACY_COARSE );
                        provider = locationManager.getBestProvider( criteria, true );

                        if ( provider == null ) {
                            Log.e( "TAG", "No location provider found!" );
                            return;
                        }
                        //Request location updates:
                        locationManager.requestLocationUpdates(provider, 400, 1,this);

                        /* try {
                           LocationFragment.lat = locationManager.getLastKnownLocation(provider).getLatitude();
                           LocationFragment.lng = locationManager.getLastKnownLocation(provider).getLongitude();
                       }
                       catch(Exception ex)
                       {
                           Toast.makeText(this,"Enable your Location",Toast.LENGTH_SHORT).show();
                       }*/
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
                                    ActivityCompat.requestPermissions(BusinessSetupActivity.this,
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

