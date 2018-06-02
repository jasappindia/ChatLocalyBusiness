package com.chatlocalybusiness.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.AppealModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by windows on 2/3/2018.
 */
public class AppealActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1000;
    private ImageView iv_id, iv_arrowBack;
    private Button btn_chooseImage, btn_submit;
    private EditText et_discription;
    private Utill utill;
    private ChatBusinessSharedPreference preference;
    private String mCurrentPhotoPath;
    private String filePath;
    private ProgressBar progressBar;
    private AlertDialog alertdialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal);

        init();
    }

    public void init() {
        utill = new Utill();
        preference = new ChatBusinessSharedPreference(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        iv_id = (ImageView) findViewById(R.id.iv_id);
        btn_chooseImage = (Button) findViewById(R.id.btn_chooseImage);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_discription = (EditText) findViewById(R.id.et_discription);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);

        btn_chooseImage.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_chooseImage:
                Dialog dialog2 = chooserDialog();
                dialog2.show();
                break;

            case R.id.btn_submit:
                if (filePath != null && !et_discription.getText().toString().trim().equals(""))
                    submitAppealApi(filePath, String.valueOf(preference.getBusinessId()), preference.getUserId(), et_discription.getText().toString());
                else
                    Toast.makeText(AppealActivity.this,"Fields can't be left blank", Toast.LENGTH_SHORT).show();

                break;

            case R.id.iv_arrowBack:
                onBackPressed();
                break;
        }
    }

    public Dialog chooserDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AppealActivity.this);
        String[] array = {"Take Photo", "Choose from Gallery"};
        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    takePhoto();
                } else if (i == 1) {
                    chooseFromGallery();
                }
            }
        });

        return builder.create();
    }

    public void takePhoto() {
        Constants.IMAGE_SELECT_CAPTURE = "camera";

        Constants.limit = 1;
        startActivityForResult(new Intent(AppealActivity.this, MultiImageActivity.class), 1);

    }

    public void chooseFromGallery() {
        Constants.IMAGE_SELECT_CAPTURE = "gallery";
        Constants.limit = 1;
        startActivityForResult(new Intent(AppealActivity.this, MultiImageActivity.class), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
//                String strEditText = data.getStringExtra("editTextValue");
                filePath = data.getStringExtra(Constants.IMAGE_PATH);
//                iv_proThumb.setImageURI(Uri.parse("file://" + filePath));
                Glide.with(AppealActivity.this).load(Uri.parse("file://" + filePath)).into(iv_id);

            }
        }
    }

    public static Uri getImageContentUri(Context context, String filePath) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (filePath != null) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public void submitAppealApi(String imagePath, String businessID, String useId, String message) {

        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, RequestBody> param = new HashMap<>();
        MultipartBody.Part body = null;

        //pass it like this
        if (imagePath != null) {
            File file = new File(imagePath);

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//         MultipartBody.Part is used to send also the actual file name
            body = MultipartBody.Part.createFormData("identity_card", file.getName(), requestFile);
        }
        RequestBody bId = RequestBody.create(MediaType.parse("multipart/form-data"), businessID);
        RequestBody encrypt_key = RequestBody.create(MediaType.parse("multipart/form-data"), "df");
        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), useId);
        RequestBody messageInfo = RequestBody.create(MediaType.parse("multipart/form-data"), message);

        param.put("encrypt_key", encrypt_key);
        param.put("b_user_id", user_id);
        param.put("b_id", bId);
        param.put("appeal_message", messageInfo);

        Call<AppealModel> call = apiServices.appealApi(param, body);
        call.enqueue(new Callback<AppealModel>() {
            @Override
            public void onResponse(Call<AppealModel> call, Response<AppealModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        reviewAlert();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppealModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AppealActivity.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void reviewAlert() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//           builder.setTitle("Are you sure ?");
        builder.setTitle("Your appeal is successfully submitted ");
        builder.setMessage("We will reveiew your appeal and get back to you shortly");

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_BACK){
//                    dialog.dismiss(); // dismiss the dialog
//                    AppealActivity.this.finish(); // exits the activity
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

}
