package com.chatlocalybusiness.utill;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.AddItemBillActivity;
import com.chatlocalybusiness.activity.AddProductActivity;
import com.chatlocalybusiness.activity.AddServiceActivity;
import com.chatlocalybusiness.activity.AppealActivity;

import com.chatlocalybusiness.activity.EditProfileActivity;
import com.chatlocalybusiness.activity.GetStartedActivity;
import com.chatlocalybusiness.activity.HomeActivity;
import com.chatlocalybusiness.activity.JoinNowActivity;
import com.chatlocalybusiness.activity.NewBusinessSetupActivity;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.apiModel.CheckStatusModel;
import com.chatlocalybusiness.apiModel.OtpVerifyModel;
import com.chatlocalybusiness.apiModel.ProServiceCountModel;
import com.chatlocalybusiness.getterSetterModel.BusinessSetupDetails;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by windows on 3/12/2018.
 */

public class BasicUtill {
    ChatBusinessSharedPreference preference;
    private AlertDialog alertdialog;
    private AlertDialog addProdialog;


    public void CheckStatus(final Context context, final int i, final ViewGroup layout) {
        preference = new ChatBusinessSharedPreference(context);

//        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "hvdjv");
        param.put("b_user_id", preference.getUserId());
        param.put("b_id", String.valueOf(preference.getBusinessId()));
//


        //@ashok: 'BLOCKED','APPROVED','UNAPPROVED','DISABLED'


        Call<CheckStatusModel> call = apiServices.checkStatus(param);
        call.enqueue(new Callback<CheckStatusModel>() {
            @Override
            public void onResponse(Call<CheckStatusModel> call, Response<CheckStatusModel> response) {
//                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        CheckStatusModel.Data data = response.body().getData();
                        if (preference.getLoginKey() != null) {
                            if (!data.getLoginKey().equals(preference.getLoginKey())) {
                                loginAlert(context);
                                layout.setVisibility(View.INVISIBLE);
                            } else if (!data.getApp_version().equalsIgnoreCase(Constants.APP_VERSION)) {
                                appVersionAlert(context);
                            }
                            //  'BLOCKED', 'APPROVED', 'UNAPPROVED'
                         /*   else if (data.getBusinessStatus().equalsIgnoreCase("BLOCKED")) {
                                // if business is blocked 3 case 1 appeal , appeal again , logout
                               *//* if(data.getChanges().equalsIgnoreCase(Constants.BUSINESS_STATUS_APPEAL))
                                {



                                }
                                else if(data.getChanges().equalsIgnoreCase(Constants.BUSINESS_STATUS_APPEAL_AGAIN))
                                {

                                }
                                else if(data.getChanges().equalsIgnoreCase(Constants.BUSINESS_STATUS_LOGOUT))
                                {

                                }
*//*
                              //  blockAlert(data.getStatus_comment(), context);
                                layout.setVisibility(View.INVISIBLE);

                            } else if (data.getBusinessStatus().equalsIgnoreCase("UNAPPROVED")) {


*//*
                                if(data.getChanges().equalsIgnoreCase(Constants.BUSINESS_STATUS_BUS_INFO))
                                {

                                }
                                else if(data.getChanges().equalsIgnoreCase(Constants.BUSINESS_STATUS_OVERVIEW))
                                {

                                }
                                else if(data.getChanges().equalsIgnoreCase(Constants.BUSINESS_STATUS_BOTH))
                                {

                                }
                                else if(data.getChanges().equalsIgnoreCase(Constants.BUSINESS_STATUS_NOCHANGES))
                                {

                                }*//*

                                reviewAlert(context);
                                layout.setVisibility(View.INVISIBLE);

                            }
                            else if (data.getBusinessStatus().equalsIgnoreCase("DISABLED")) {

                                // if business is blocked 3 case 1 appeal , appeal again , logout
                                layout.setVisibility(View.INVISIBLE);

                            }
*/
                            else if (data.getBusinessStatus().equalsIgnoreCase("APPROVED") && i == 1) {

                                if (preference.getBusinessROlE().equals(Constants.ADMIN))
                                    onApprovedAdmin(context);
                                else
                                    onApprovedWorker(context);
                            }

                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<CheckStatusModel> call, Throwable t) {

            }
        });

    }

    public void loginAlert(final Context context) {
        preference = new ChatBusinessSharedPreference(context);
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
//           builder.setTitle("Are you sure ?");
        builder.setTitle("You have been logged out!");
        builder.setMessage("You are already logged in with some other device");

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss(); // dismiss the dialog
                    preference.logout();
                    alertdialog.dismiss();
//                    android.os.Process.killProcess(android.os.Process.myPid());
                    Intent intent = new Intent(context, GetStartedActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }

                return true;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                preference.logout();
                alertdialog.dismiss();
                Intent intent = new Intent(context, GetStartedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        alertdialog = builder.create();
        alertdialog.setCancelable(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertdialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.dialog_bg));
        }


        /*alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        */
        alertdialog.show();
    }

    public void onApprovedAdmin(Context context) {
        if (preference.getCompletedStep().equals("1")) {
            context.startActivity(new Intent(context, JoinNowActivity.class));
            ((Activity) context).finish();
        }
        if (preference.getCompletedStep().equals("2")) {
            context.startActivity(new Intent(context, EditProfileActivity.class));
            ((Activity) context).finish();

        }
        if (preference.getCompletedStep().equals("3")) {
            context.startActivity(new Intent(context, NewBusinessSetupActivity.class));
            ((Activity) context).finish();

        }
        if (preference.getCompletedStep().equals("4")) {
            Intent intent2 = new Intent(context, NewBusinessSetupActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent2.putExtra("step", "4");
            context.startActivity(intent2);
            ((Activity) context).finish();
        }
        if (preference.getCompletedStep().equals("5")) {
            Intent intent2 = new Intent(context, NewBusinessSetupActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent2.putExtra("step", "5");
            context.startActivity(intent2);
            ((Activity) context).finish();

        }
        if (preference.getCompletedStep().equals("6")) {
            Intent intent2 = new Intent(context, HomeActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent2);
            ((Activity) context).finish();

        }
        if (preference.getCompletedStep().equals("7")) {
            Intent intent2 = new Intent(context, NewBusinessSetupActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent2.putExtra("step", "7");
            context.startActivity(intent2);
            ((Activity) context).finish();

        }
    }

    public void appVersionAlert(final Context context) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
//           builder.setTitle("Are you sure ?");
        builder.setTitle("You are using an older version!");
        builder.setMessage("Please get the latest version from playstore.");

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss(); // dismiss the dialog
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

                return true;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent sendtoApp = new Intent(android.content.Intent.ACTION_VIEW);
                sendtoApp.setData(Uri.parse("market://details?id=" + "co.inlook"));
                context.startActivity(sendtoApp);
            }
        });
        alertdialog = builder.create();
        alertdialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertdialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.dialog_bg));
        }

        /*alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        */
        alertdialog.show();
    }

    public void blockAlert(String blockMessage, final Context context, final int  type) {
        // for 1 -> appeal
        // 2-> appeal again
        // 3 -> logout

        String title="You are blocked out!",action_title;

        if(type==1)
        {
            title="You are blocked out!";
            action_title="Appeal";
        }
        else if(type==2)
        {
            title="You are blocked out!";
            action_title="Appeal again";

        }
        else
        {
            title="You are blocked out!";
            action_title="Logout";
        }




        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
//           builder.setTitle("Are you sure ?");
        builder.setTitle(title);
        builder.setMessage(blockMessage);

        builder.setPositiveButton(action_title, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                if(type==1)
                {
                    context.startActivity(new Intent(context, AppealActivity.class));
                    ((Activity) context).finish();
                }
                else if(type==2)
                {
                    context.startActivity(new Intent(context, AppealActivity.class));
                    ((Activity) context).finish();

                }
                else
                {
                    preference.logout();
                    ((Activity) context).finish();

                }


            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss(); // dismiss the dialog
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

                return true;
            }
        });

        alertdialog = builder.create();
        alertdialog.setCancelable(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertdialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.dialog_bg));
        }

        /*alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        */
        alertdialog.show();
    }


/*



    public void businessSetupCompletionAlert(final Context context) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
//           builder.setTitle("Are you sure ?");
        builder.setMessage("Do you want to logged out");
        builder.setPositiveButton("Appeal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                preference.logout();
//                Toast.makeText(EditProfileActivity.this,"Business has not been fully setup by Admin. ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, GetStartedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

            }
        });

        alertdialog = builder.create();
        alertdialog.setCancelable(false);

        */
/*alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        *//*

        alertdialog.show();
    }
*/

    public void reviewAlert(Context context) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
//           builder.setTitle("Are you sure ?");
        builder.setTitle("You have been blocked out!");
        builder.setMessage("We will reveiew your appeal and get back to you shortly");

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss(); // dismiss the dialog
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

                return true;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
        alertdialog = builder.create();
        alertdialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertdialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.dialog_bg));
        }

        /*alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        */
        alertdialog.show();
    }

    public void onApprovedWorker(Context context) {
        if (preference.getCompletedStep() != null) {
            if (preference.getCompletedStep().equals("1")) {
                Intent intent = new Intent(context, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } else {
            Intent intent = new Intent(context, EditProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }


    public static String getDateFromString(String str) {
//       String dtStart = "2010-10-15T09:27:37Z";
        SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yy");
        Date date = null;
        try {
            date = format.parse(str);
            format2.format(date);

            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format2.format(date);
    }

    public static Date getDateFormat(long milliseconds) {
//        long yourmilliseconds = System.currentTimeMillis();
//        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm");
        Date resultdate = new Date(milliseconds);
        System.out.println(sdf.format(resultdate));
        return resultdate;
    }

    public static String getDate(Long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd/MM/yy");
        return fullDateFormat.format(date);
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static void sendIntent(Context context) {
        Intent intent = new Intent();
        ((Activity) context).setResult(RESULT_OK, intent);
        ((Activity) context).finish();
    }

    public static void sendCancelIntent(Context context) {

        Intent intent = new Intent();
        ((Activity) context).setResult(RESULT_CANCELED, intent);
        ((Activity) context).finish();
    }

    public static Date getWeekDate(int weeks) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, weeks * 7);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date getMonthStartDate(int months) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, months);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date getMonthEndDate(int months) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, months);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static String getDateFormatLocal(String str) {
//      SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
            format2.format(date);

            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format2.format(date);

    }

    public void getProServiceCount(final Context context) {
        preference = new ChatBusinessSharedPreference(context);

//        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "hvdjv");
        param.put("b_user_id", preference.getUserId());
        param.put("b_id", String.valueOf(preference.getBusinessId()));
        Call<ProServiceCountModel> call = apiServices.getProServiceCount(param);
        call.enqueue(new Callback<ProServiceCountModel>() {
            @Override
            public void onResponse(Call<ProServiceCountModel> call, Response<ProServiceCountModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        if (response.body().getData().getProductsCount().equals("0")) {
                            if (!preference.getDontShowAddProduct())
                                addProductAlert(context, "Product");
                        } else if (response.body().getData().getServicesCount().equals("0")) {
                            if (!preference.getDontShowAddProduct())
                                addProductAlert(context, "Service");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProServiceCountModel> call, Throwable t) {

            }
        });
    }

    public void addProductAlert(final Context context, final String tag) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_addproduct_alert, null);
        builder.setView(dialogView);

        TextView tv_alertHeading = (TextView) dialogView.findViewById(R.id.tv_alertHeading);
        TextView tv_addNow = (TextView) dialogView.findViewById(R.id.tv_addNow);
        TextView tv_later = (TextView) dialogView.findViewById(R.id.tv_later);
        CheckBox cb_dont = (CheckBox) dialogView.findViewById(R.id.cb_dontShow);
        if (tag.equalsIgnoreCase("Product"))
            tv_alertHeading.setText(R.string.str_addProductAlert);
        else
            tv_alertHeading.setText(R.string.str_addServiceAlert);

        tv_addNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag.equalsIgnoreCase("Product"))
                    context.startActivity(new Intent(context, AddProductActivity.class));
                else context.startActivity(new Intent(context, AddServiceActivity.class));
                addProdialog.dismiss();
            }
        });

        tv_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProdialog.dismiss();

            }
        });

        cb_dont.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (tag.equalsIgnoreCase("Product"))
                    preference.setDontShowAddProduct(b);
                else preference.setDontShowAddService(b);
            }
        });
        addProdialog = builder.create();
        addProdialog.show();
    }

    public BusinessSetupDetails saveBusinessDetails(OtpVerifyModel.Data data) {

        BusinessSetupDetails businessSetupDetails = new BusinessSetupDetails();
        BusinessSetupDetails.BusinessInfo businessInfo = businessSetupDetails.new BusinessInfo();
        BusinessSetupDetails.BusinessCategory businessCategory = businessSetupDetails.new BusinessCategory();
        BusinessSetupDetails.BusinessLocation businessLocation = businessSetupDetails.new BusinessLocation();


        OtpVerifyModel.BusinessDetail businessDetail = data.getUserDetail().getBusinessDetail();
        OtpVerifyModel.LocationDetail locationDetail = data.getUserDetail().getLocationDetail();
        OtpVerifyModel.CurrentSubscriptionPlan currentSubscriptionPlan = data.getUserDetail().getCurrentSubscriptionPlan();


        if (data.getComplatedStep().equalsIgnoreCase("4")) {
            businessInfo.setBusinessLogo(data.getUserDetail().getBusinessLogo());
            businessInfo.setBusinessName(data.getUserDetail().getBusinessName());
            businessInfo.setHomeService(businessDetail.getHomeServices());
            if (businessInfo.getHomeService().equalsIgnoreCase("yes"))
                businessInfo.setDistance(businessDetail.getServicesKm());
            else businessInfo.setDistance(" ");
        } else if (data.getComplatedStep().equalsIgnoreCase("5")) {
            businessInfo.setBusinessLogo(data.getUserDetail().getBusinessLogo());
            businessInfo.setBusinessName(data.getUserDetail().getBusinessName());

            businessInfo.setHomeService(businessDetail.getHomeServices());
            if (businessInfo.getHomeService().equalsIgnoreCase("yes"))
                businessInfo.setDistance(businessDetail.getServicesKm());
            else businessInfo.setDistance(" ");

            businessCategory.setSubscriptionPlan(currentSubscriptionPlan.getSpName());
            businessCategory.setCat1(currentSubscriptionPlan.getCategoryOne());
            businessCategory.setCat2(currentSubscriptionPlan.getCategoryTwo());
        } else if (data.getComplatedStep().equalsIgnoreCase("6")) {
            businessInfo.setBusinessLogo(data.getUserDetail().getBusinessLogo());
            businessInfo.setBusinessName(data.getUserDetail().getBusinessName());

            businessInfo.setHomeService(businessDetail.getHomeServices());
            if (businessInfo.getHomeService().equalsIgnoreCase("yes"))
                businessInfo.setDistance(businessDetail.getServicesKm());
            else businessInfo.setDistance(" ");

            businessCategory.setSubscriptionPlan(currentSubscriptionPlan.getSpName());
            businessCategory.setCat1(currentSubscriptionPlan.getCategoryOne());
            businessCategory.setCat2(currentSubscriptionPlan.getCategoryTwo());


            businessLocation.setCity(locationDetail.getCityName());
            businessLocation.setPincode(locationDetail.getPinCode());
            businessLocation.setLocality(locationDetail.getLocalityName());
            businessLocation.setAddress(locationDetail.getAddress());
            businessLocation.setLandmark(locationDetail.getLandmark());
        }

         return businessSetupDetails;
    }

    public BusinessInfoModelNew.BusinessDetail saveBusinessInfo(OtpVerifyModel.Data data)
    {
        BusinessInfoModelNew businessInfoModelNew=new BusinessInfoModelNew();
        BusinessInfoModelNew.BusinessDetail businessDetail=businessInfoModelNew.new BusinessDetail();
        BusinessInfoModelNew.CurrentSubscriptionPlan currentSubscriptionPlan=businessInfoModelNew.new CurrentSubscriptionPlan();


        OtpVerifyModel.BusinessDetail otpBusinessDetails = data.getUserDetail().getBusinessDetail();
        OtpVerifyModel.LocationDetail locationDetail = data.getUserDetail().getLocationDetail();
        OtpVerifyModel.CurrentSubscriptionPlan subscriptionPlan = data.getUserDetail().getCurrentSubscriptionPlan();

        if(data.getComplatedStep().equalsIgnoreCase("4"))
        {
            businessDetail.setBusinessLogo(data.getUserDetail().getBusinessLogo());
            businessDetail.setBusinessName(data.getUserDetail().getBusinessName());
            businessDetail.setHomeServices(otpBusinessDetails.getHomeServices());
            if (businessDetail.getHomeServices().equalsIgnoreCase("yes"))
                businessDetail.setServicesKm(otpBusinessDetails.getServicesKm());
            else businessDetail.setServicesKm(" ");
        }
          if(data.getComplatedStep().equalsIgnoreCase("5"))
        {
            businessDetail.setBusinessLogo(data.getUserDetail().getBusinessLogo());
            businessDetail.setBusinessName(data.getUserDetail().getBusinessName());
            businessDetail.setHomeServices(otpBusinessDetails.getHomeServices());
            if (businessDetail.getHomeServices().equalsIgnoreCase("yes"))
                businessDetail.setServicesKm(otpBusinessDetails.getServicesKm());
            else businessDetail.setServicesKm(" ");

            currentSubscriptionPlan.setSpName(subscriptionPlan.getSpName());
            currentSubscriptionPlan.setSpId(subscriptionPlan.getSpId());
            currentSubscriptionPlan.setCategoryOne(subscriptionPlan.getCategoryOne());
            currentSubscriptionPlan.setCategoryTwo(subscriptionPlan.getCategoryTwo());
            businessDetail.setCurrentSubscriptionPlan(currentSubscriptionPlan);
        }
     /*     if(data.getComplatedStep().equalsIgnoreCase("6"))
        {
            businessDetail.setBusinessLogo(data.getUserDetail().getBusinessLogo());
            businessDetail.setBusinessName(data.getUserDetail().getBusinessName());
            businessDetail.setHomeServices(otpBusinessDetails.getHomeServices());
            if (businessDetail.getHomeServices().equalsIgnoreCase("yes"))
                businessDetail.setServicesKm(otpBusinessDetails.getServicesKm());
            else businessDetail.setServicesKm(" ");

            currentSubscriptionPlan.setSpName(subscriptionPlan.getSpName());
            currentSubscriptionPlan.setSpId(subscriptionPlan.getSpId());
            currentSubscriptionPlan.setCategoryOne(subscriptionPlan.getCategoryOne());
            currentSubscriptionPlan.setCategoryTwo(subscriptionPlan.getCategoryTwo());

            businessDetail.setCityName(locationDetail.getCityName());
//            businessDetail.setPincode(locationDetail.getPinCode());
            businessDetail.setLocalityName(locationDetail.getLocalityName());
            businessDetail.setAddress(locationDetail.getAddress());
            businessDetail.setLandmark(locationDetail.getLandmark());

        }*/
        return businessDetail;
    }


    public static void setImageRatio(ImageView view,Context context)
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
        int width = pxToDp(displayMetrics.widthPixels);
//        double height=displayMetrics.widthPixels*(0.523);
        double height=displayMetrics.widthPixels*(0.523);
        int height1=(int)height;

//        iv_banner.setLayoutParams(new RelativeLayout.LayoutParams(width,height));
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.height = height1;
        params.width =displayMetrics.widthPixels ;

    }
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);

    }
}


