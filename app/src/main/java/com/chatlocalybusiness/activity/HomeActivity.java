package com.chatlocalybusiness.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.api.MobiComKitConstants;
import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.applozic.mobicomkit.broadcast.BroadcastService;
import com.applozic.mobicomkit.uiwidgets.notification.MTNotificationBroadcastReceiver;
import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.AdapterForDrawerOption;
import com.chatlocalybusiness.adapter.HomePagerAdapter;

import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;
import com.crashlytics.android.Crashlytics;

import java.util.List;

import static com.chatlocalybusiness.R.id.appBar;
import static com.chatlocalybusiness.R.id.tabLayout;

public class HomeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener {


    protected MTNotificationBroadcastReceiver mtNotificationBroadcastReceiver;


    BroadcastReceiver unreadCountBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MobiComKitConstants.APPLOZIC_UNREAD_COUNT.equals(intent.getAction())) {
                int unreadCount  =  new MessageDatabaseService(context).getUnreadConversationCount();
                //Update unread count in UI
//                setThreadTabCount(unreadCount);
            }
        }
    };
    public static   void setThreadTabCount(int count)
    {
       /* if(count==0)
            tv_tabcounter.setVisibility(View.GONE);
        else {
            tv_tabcounter.setVisibility(View.VISIBLE);
            tv_tabcounter.setText(""+count);
        }*/
        tv_tabcounter.setVisibility(View.GONE);


    }


    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(unreadCountBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mtNotificationBroadcastReceiver);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(HomeActivity.this).load(preference.getBusinessLogo()).into(iv_userImage);
          tv_username.setText(preference.getBusinessName());
        ViewGroup drawer_layout=(ViewGroup) findViewById(R.id.drawer_layout);
        new BasicUtill().CheckStatus(HomeActivity.this,0,drawer_layout);
        if(EditProfileActivity.editProfile==1)
        {
            setProfile();
            EditProfileActivity.editProfile=0;
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(unreadCountBroadcastReceiver, new IntentFilter(MobiComKitConstants.APPLOZIC_UNREAD_COUNT));
        LocalBroadcastManager.getInstance(this).registerReceiver(mtNotificationBroadcastReceiver,  new IntentFilter(MobiComKitConstants.MESSAGE_JSON_INTENT));

        setThreadTabCount(new MessageDatabaseService(HomeActivity.this).getUnreadConversationCount());

    }
    private TabLayout tab;
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private HomePagerAdapter pagerAdapter;
    private RecyclerView rv_drawerList;
    private static TextView tv_tabcounter;
    private TextView tv_threadstab,tv_threadstab2,tv_tabcounter2,tv_threadstab3,tv_tabcounter3,tv_username;
    private AdapterForDrawerOption adapterForDrawerOption;
    private ImageView iv_toggle;
    private com.chatlocalybusiness.ui.CircleImageView iv_userImage;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private Utill utill;
     private ChatBusinessSharedPreference preference;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);
        preference=new ChatBusinessSharedPreference(this);
        preference.setLoginStatus(Constants.LOGIN);
        mtNotificationBroadcastReceiver=new  MTNotificationBroadcastReceiver();

        utill=new Utill();
        tab = (TabLayout) findViewById(tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        iv_toggle=(ImageView)findViewById(R.id.iv_toggle);
        iv_userImage=(com.chatlocalybusiness.ui.CircleImageView)findViewById(R.id.iv_userImage);
        tv_username=(TextView)findViewById(R.id.tv_username);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        setProfile();

        tab.addTab(tab.newTab().setText("Threads"));
//        tab.addTab(tab.newTab().setText("Internal"));
        tab.addTab(tab.newTab().setText("Tags"));
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        pagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), tab.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        setTab();

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tab));
        tab.addOnTabSelectedListener(this);

        setRecyclerView();

//        iv_toggle.setOnClickListener(this);
        iv_userImage.setOnClickListener(this);

        mDrawerToggle=  new ActionBarDrawerToggle(this, drawer, toolbar,  R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); //
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); //

            }
        };
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    public void setProfile()
    {
        if(preference.getBusinessLogo()!=null) {
            if(!preference.getBusinessLogo().equals(""))
                Glide.with(this).load(preference.getBusinessLogo()).into(iv_userImage);
            else iv_userImage.setImageResource(R.drawable.user_icon);

        }
        else iv_userImage.setImageResource(R.drawable.user_icon);
        if(preference.getBusinessName()!=null)
            tv_username.setText(preference.getBusinessName());
    }

    public void setTab()
    {
        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tab.getTabCount(); i++) {
            if (i == 0) {
                TabLayout.Tab tabs = tab.getTabAt(i);
                tabs.setCustomView(R.layout.tabicon1_layout);
            }if (i == 1) {
                TabLayout.Tab tabs2 = tab.getTabAt(i);
                tabs2.setCustomView(R.layout.tabicon2_layout);
            }
            /*if (i == 2) {
                TabLayout.Tab tabs3 = tab.getTabAt(i);
                tabs3.setCustomView(R.layout.tabicon3_layout);
            }*/
        }
        tv_threadstab = (TextView) tab.findViewById(R.id.tab_title);
        tv_tabcounter = (TextView) tab.findViewById(R.id.tabcounter);

        tv_threadstab2 = (TextView) tab.findViewById(R.id.tab_title2);
        tv_tabcounter2 = (TextView) tab.findViewById(R.id.tabcounter2);

//        tv_threadstab3 = (TextView) tab.findViewById(R.id.tab_title3);
//        tv_tabcounter3 = (TextView) tab.findViewById(R.id.tabcounter3);

                tv_threadstab .setText("Threads");
//                tv_threadstab2.setText("Internal");
               tv_threadstab2.setText("Tags");
//                tv_threadstab3.setText("Tags");
    }

    public void setRecyclerView()
    {
        rv_drawerList=(RecyclerView)findViewById(R.id.rv_drawerList);
        LinearLayoutManager  layoutManager=new LinearLayoutManager(this);
        adapterForDrawerOption=new AdapterForDrawerOption(this);
        rv_drawerList.setAdapter(adapterForDrawerOption);
        rv_drawerList.setLayoutManager(layoutManager);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show();

            mHandler.postDelayed(mRunnable, 2000);
        }
    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
         updateTabs(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void updateTabs(int count)
    {
        if(count==0)
        {   tv_threadstab.setTextColor(ContextCompat.getColor(this,R.color.themeColor));
            tv_tabcounter.setBackgroundResource(R.drawable.notify_circle_blue);
        }
        else{tv_threadstab.setTextColor(ContextCompat.getColor(this,R.color.light_gray));
            tv_tabcounter.setBackgroundResource(R.drawable.notify_circle_grey);
        }
        if(count==1)
        {tv_threadstab2.setTextColor(ContextCompat.getColor(this,R.color.themeColor));
            tv_tabcounter2.setBackgroundResource(R.drawable.notify_circle_blue);
        }
        else{tv_threadstab2.setTextColor(ContextCompat.getColor(this,R.color.light_gray));
            tv_tabcounter2.setBackgroundResource(R.drawable.notify_circle_grey);
        }
        /*if(count==2)
        {tv_threadstab3.setTextColor(ContextCompat.getColor(this,R.color.themeColor));
            tv_tabcounter3.setBackgroundResource(R.drawable.notify_circle_blue);
        }
        else{tv_threadstab3.setTextColor(ContextCompat.getColor(this,R.color.light_gray));
            tv_tabcounter3.setBackgroundResource(R.drawable.notify_circle_grey);
        }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
           /* case R.id.iv_toggle:
                drawer.openDrawer(GravityCompat.START);
//                Glide.with(HomeActivity.this).load(preference.getBusinessLogo()).into(iv_userImage);
                break;*/


                case R.id.iv_userImage:
                    drawer.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(HomeActivity.this,BusinessProfileActivity.class));

                    break;
        }
    }

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

    public class AdapterForDrawerOption extends RecyclerView.Adapter<AdapterForDrawerOption.MyViewHolder> {
        String[] optionList = {"Bills", "Stats", "Settings", "Feedback", "Rate Us", "About Us"};
        int[] imageList = {R.drawable.bills, R.drawable.stats, R.drawable.settings, R.drawable.feedback, R.drawable.rateus, R.drawable.about};
        Context context;


        public AdapterForDrawerOption(Context context) {
            this.context = context;
        }

        @Override
        public AdapterForDrawerOption.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.nav_drawer_row, parent, false);
            return new AdapterForDrawerOption.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AdapterForDrawerOption.MyViewHolder holder, int position) {
            holder.tv_option.setText(optionList[position]);

        }

        @Override
        public int getItemCount() {
            return 6;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_option;
            LinearLayout ll_optionDrawer;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_option = (TextView) itemView.findViewById(R.id.title);



//            ll_optionDrawer=(LinearLayout)itemView.findViewById(R.id.ll_optionDrawer);

                tv_option.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (getAdapterPosition() == 0)
                            context.startActivity(new Intent(context, ListInvoiceShowActivity.class));
                        else if (getAdapterPosition() == 1)
                            context.startActivity(new Intent(context, StatsActivity.class));
                        else if (getAdapterPosition() == 2)
                            context.startActivity(new Intent(context, SettingsActivity.class));
                        else if (getAdapterPosition() == 3) {
                            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                            intent.setType("text/html");
                            final PackageManager pm = context.getPackageManager();
                            final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                            ResolveInfo best = null;
                            for (final ResolveInfo info : matches) {
                                if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
                                    best = info;
                                    break;
                                }
                            }
                            if (best != null) {
                                intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                            }
                            intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"feedback@chatlocaly.com"});

                            context.startActivity(intent);

                        } else if (getAdapterPosition() == 4) {
                            showRateDialog(context);
                        } else if (getAdapterPosition() == 5)
                            context.startActivity(new Intent(context, AboutActivity.class));
                        drawer.closeDrawer(GravityCompat.START);
                    }

                });

            }
        }

        private void showRateDialog(final Context context) {

            final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
            dialog.setContentView(R.layout.dialog_rate_us);
            TextView textView = (TextView) dialog.findViewById(R.id.tv_ok);
            RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.rating_bar);

            RelativeLayout relativeLayout=dialog.findViewById(R.id.rl_rate);
            // rate bar change color

            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(ratingBar.getContext().getResources().getColor(R.color.themeColor), PorterDuff.Mode.SRC_ATOP);


            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context != null) {
                        String link = "market://details?id=";
                        try {
                            // play market available
                            context.getPackageManager()
                                    .getPackageInfo("com.android.vending", 0);
                            // not available
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                            // should use browser
                            link = "https://play.google.com/store/apps/details?id=";
                        }
                        // starts external action
                        context.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(link + context.getPackageName())));
                    }
                }
            });
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            dialog.show();
            dialog.setCanceledOnTouchOutside(true);

        }

    }

}