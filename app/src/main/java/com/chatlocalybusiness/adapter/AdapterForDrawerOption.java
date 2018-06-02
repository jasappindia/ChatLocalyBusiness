package com.chatlocalybusiness.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.AboutActivity;
import com.chatlocalybusiness.activity.ListInvoiceShowActivity;
import com.chatlocalybusiness.activity.SettingsActivity;
import com.chatlocalybusiness.activity.StatsActivity;
import com.chatlocalybusiness.utill.Utill;

import java.util.List;

/**
 * Created by windows on 12/19/2017.
 */
public class AdapterForDrawerOption extends RecyclerView.Adapter<AdapterForDrawerOption.MyViewHolder> {
    String[] optionList = {"Bills", "Stats", "Settings", "Feedback", "Rate Us", "About Us"};
    int[] imageList = {R.drawable.bills, R.drawable.stats, R.drawable.settings, R.drawable.feedback, R.drawable.rateus, R.drawable.about};
    Context context;


    public AdapterForDrawerOption(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nav_drawer_row, parent, false);
        return new AdapterForDrawerOption.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
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

                }

            });

        }
    }

   /* public static void showRateDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Rate application")
                .setMessage("Please, rate the app at PlayMarket")
                .setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                })
                .setNegativeButton("CANCEL", null);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.dialog_bg));
        }
        dialog.show();
//        builder.show();
    }
*/
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
