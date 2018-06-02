package com.chatlocalybusiness.utill;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.applozic.mobicomkit.uiwidgets.conversation.chatLocaly.ChatLocallyAppLozicPrefrence;
import com.chatlocalybusiness.apiModel.AdminVerifyModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by windows on 12/8/2017.
 */

public class Utill {
    private static final int REQUEST_PERMISSIONS = 101;
    ConnectivityManager connectivityManager;
    boolean connected = false;
    public  boolean isConnected(Context context) {

        try {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;

        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }


    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(path, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void requestMultiplePermissions(Context context) {

        String camera_permission = Manifest.permission.CAMERA;
        int hascampermission = context.checkSelfPermission(camera_permission);

        String storage_permission_group = Manifest.permission.READ_EXTERNAL_STORAGE;
        int hasStoragePermission = context.checkSelfPermission(storage_permission_group);

        String storage_writepermission_group = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int hasstroage = context.checkSelfPermission(storage_permission_group);

        List<String> permissions = new ArrayList<String>();

        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(storage_permission_group);
        }
        if (hascampermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(camera_permission);
        }

        if (hasstroage != PackageManager.PERMISSION_GRANTED) {
            permissions.add(storage_writepermission_group);
        }

        if (!permissions.isEmpty()) {
            String[] params = permissions.toArray(new String[permissions.size()]);
            ActivityCompat.requestPermissions((Activity) context,params, REQUEST_PERMISSIONS);
        }
    }

    private String getRealPathFromURI(Context context, Uri contentUri)
    {
        Cursor cursor = null;
        try
        {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
    }
    public static boolean isNoFieldEmpty(EditText[] fields){
        int count = 0;
        for (EditText currentField : fields) {
            if (TextUtils.isEmpty(currentField.getText().toString())) {
                count = 1;
                currentField.setError("This field cannot be empty");
            }else {
                currentField.setError(null);
            }
        }
        return count == 0;
    }

    public  static void callMapIntent(Context context,String yourAddress)
    {
        String map = "http://maps.google.co.in/maps?q=" + yourAddress;
        if(yourAddress!=null && yourAddress.length()>4) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
            context.startActivity(intent);
        }
    }

    public static double calculateNewValue(double value, double discount)
    {
        double oldValue=value-(value*discount/100);
        double roudoff=Math.round((oldValue*100.00)/100.00);
        return roudoff;
    }


    public static void setPermissions(Context context, AdminVerifyModel.Data data)
    {
         ChatBusinessSharedPreference preference=new ChatBusinessSharedPreference(context);
//         preference.setSendVideo(data.getSendVideo());
//         preference.setSendAudio(data.getSendAudio());
//         preference.setSendPhoto(data.getSendPhoto());
         preference.setEditBill(data.getEditBill());
         preference.setSendBill(data.getSendBill());
//         preference.setSendMessage(data.getSendMessage());
         preference.setEditBusiness(data.getEditBusinessInfo());
         preference.setEditBusinessOverview(data.getEditOverview());
         preference.setAddProducts(data.getAddProducts());
         preference.setAddSevice(data.getAddServices());
         preference.setBlockThread(data.getBlockThead());


        ChatLocallyAppLozicPrefrence appLozicPrefrence=new ChatLocallyAppLozicPrefrence(context);
        appLozicPrefrence.setSendVideo(data.getSendVideo());
        appLozicPrefrence.setSendAudio(data.getSendAudio());
        appLozicPrefrence.setSendPhoto(data.getSendPhoto());
        appLozicPrefrence.setSendMessage(data.getSendMessage());
        appLozicPrefrence.setEditBill(data.getEditBill());


    }

    public static void setAdminPermissions(Context context)
    {
        ChatBusinessSharedPreference preference=new ChatBusinessSharedPreference(context);
//        preference.setSendVideo("1");
//        preference.setSendAudio("1");
//        preference.setSendPhoto("1");
        preference.setEditBill("1");
        preference.setSendBill("1");
//        preference.setSendMessage("1");
        preference.setEditBusiness("1");
        preference.setEditBusinessOverview("1");
        preference.setAddProducts("1");
        preference.setAddSevice("1");
        preference.setBlockThread("1");

        ChatLocallyAppLozicPrefrence appLozicPrefrence=new ChatLocallyAppLozicPrefrence(context);
        appLozicPrefrence.setSendVideo("1");
        appLozicPrefrence.setSendAudio("1");
        appLozicPrefrence.setSendPhoto("1");
        appLozicPrefrence.setSendMessage("1");
        appLozicPrefrence.setEditBill("1");
    }

    public static  String getPriceFormatted(String price)
    {
        String priceStr=price;
        double value =Double.parseDouble(price);
        if(value>100000)
        {
           value= Math.round((value/100000)*100)/100;
            priceStr=value+" Lac";
        }else
        {
            Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            priceStr =format.format(new BigDecimal(price));

        }
         return priceStr;
    }
    public static ProgressDialog showloader(Context context)
    {
        final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait... ");
        return progressDialog;

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestMultiplePermissionsOtp(Context context)
    {

        String readSms = Manifest.permission.READ_SMS;
        String camera = Manifest.permission.CAMERA;
        String readContact = Manifest.permission.READ_CONTACTS;
        String readStorage = Manifest.permission.READ_EXTERNAL_STORAGE;
        String writeStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;


        int hasreadSms = context.checkSelfPermission(readSms);
        int hasreadcontact = context.checkSelfPermission(readContact);
        int hascamera = context.checkSelfPermission(camera);
        int hasreadstorage = context.checkSelfPermission(readStorage);
        int haswritestorage = context.checkSelfPermission(writeStorage);

        List<String> permissions = new ArrayList<String>();
        if (hasreadSms != PackageManager.PERMISSION_GRANTED) {
            permissions.add(readSms);
        }
        if (hasreadcontact != PackageManager.PERMISSION_GRANTED) {
            permissions.add(readContact);
        }
        if (hascamera != PackageManager.PERMISSION_GRANTED) {
            permissions.add(camera);
        }
        if (haswritestorage != PackageManager.PERMISSION_GRANTED) {
            permissions.add(writeStorage);
        }
        if (hasreadstorage != PackageManager.PERMISSION_GRANTED) {
            permissions.add(readStorage);
        }


        if (!permissions.isEmpty()) {
            String[] params = permissions.toArray(new String[permissions.size()]);
            ((Activity)context).requestPermissions(params, REQUEST_PERMISSIONS);

        }

    }
}
