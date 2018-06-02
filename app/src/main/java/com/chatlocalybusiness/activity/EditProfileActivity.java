package com.chatlocalybusiness.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;
import com.applozic.mobicomkit.api.account.user.PushNotificationTask;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.OtpVerifyModel;
import com.chatlocalybusiness.apiModel.UpdateProfileModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.ui.CircleImageView;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shiv on 12/6/2017.
 */

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private ViewGroup rl_main;

    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(EditProfileActivity.this, 0, rl_main);
    }

    private static final int REQUEST_PERMISSIONS = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int SELECT_PICTURE = 103;
    private Button btn_takePhoto, btn_next;
    private TextView tv_chooseGallery;
    private CircleImageView iv_profilePic;
    public String filePath = null;
    private Bitmap bitmapUser;
    public static String selectedImagePath;/*, filepath;*/
    private RequestListener target;
    private Uri fileUri = null;
    private EditText et_userName;
    private Utill util;
    private ChatBusinessSharedPreference preference;
    private ProgressBar progressBar;
    private AlertDialog alertdialog;
    public static int editProfile = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        rl_main = (ViewGroup) findViewById(R.id.rl_main);

        init();
        if (getIntent().getExtras() != null) {
            if (preference.getUserImage() != null)
                if (!preference.getUserImage().equalsIgnoreCase(""))
                    Glide.with(EditProfileActivity.this).load(preference.getUserImage()).into(iv_profilePic);
            et_userName.setText(preference.getUser_NAME());
            btn_next.setText("Submit");
        }
        target = new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
//                ivprofilepic.setBackgroundResource(R.drawable.user_not_available);
//                ivprofilepic.setImageResource(R.drawable.user_not_available);
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        };
    }

    public void init() {
        util = new Utill();
        preference = new ChatBusinessSharedPreference(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        iv_profilePic = (CircleImageView) findViewById(R.id.iv_profilePic);
        btn_takePhoto = (Button) findViewById(R.id.btn_takePhoto);
        btn_next = (Button) findViewById(R.id.btn_next);
        tv_chooseGallery = (TextView) findViewById(R.id.tv_chooseGallery);
        et_userName = (EditText) findViewById(R.id.et_userName);
        et_userName.addTextChangedListener(this);
        btn_takePhoto.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        tv_chooseGallery.setOnClickListener(this);
        btn_next.setFocusable(false);
        btn_next.setClickable(false);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_takePhoto:
                takePhoto();
                break;
            case R.id.btn_next:
                updateProfile(preference.getUserId(), et_userName.getText().toString().trim(), filePath);
                break;
            case R.id.tv_chooseGallery:
                chooseFromGallery();
                break;
        }
    }

    public void takePhoto() {
        Constants.IMAGE_SELECT_CAPTURE = "camera";

        Constants.limit = 1;
        startActivityForResult(new Intent(EditProfileActivity.this, SingleImageSelectionActivity.class), 1);
    }

    public void chooseFromGallery() {
        Constants.IMAGE_SELECT_CAPTURE = "gallery";
        Constants.limit = 1;
        startActivityForResult(new Intent(EditProfileActivity.this, SingleImageSelectionActivity.class), 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
//                String strEditText = data.getStringExtra("editTextValue");
                filePath = data.getStringExtra(Constants.IMAGE_PATH);
//                iv_proThumb.setImageURI(Uri.parse("file://" + filePath));
                Glide.with(EditProfileActivity.this).load(Uri.parse("file://" + filePath)).into(iv_profilePic);

            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().trim().length() > 2) {
            btn_next.setFocusable(true);
            btn_next.setClickable(true);
            btn_next.setTextColor(ContextCompat.getColor(this, R.color.white));
            btn_next.setBackgroundResource(R.drawable.blue_btn_bg);
        } else {
            btn_next.setFocusable(false);
            btn_next.setClickable(false);
            btn_next.setTextColor(ContextCompat.getColor(this, R.color.light_gray));
            btn_next.setBackgroundResource(R.drawable.gray_btn_bg);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void updateProfile(String userId, String fullname, String imagePath) {
        progressBar.setVisibility(View.VISIBLE);
         ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, RequestBody> param = new HashMap<>();
        MultipartBody.Part body = null;

        //pass it like this
        if (imagePath != null) {
            File file = new File(imagePath);

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

//         MultipartBody.Part is used to send also the actual file name
            body = MultipartBody.Part.createFormData("b_profile_image", file.getName(), requestFile);
        }

        RequestBody fullName = RequestBody.create(MediaType.parse("multipart/form-data"), fullname);
        RequestBody encrypt_key = RequestBody.create(MediaType.parse("multipart/form-data"), "df");
        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), userId);

        param.put("encrypt_key", encrypt_key);
        param.put("b_user_id", user_id);
        param.put("b_full_name", fullName);

//         param.put("b_profile_image",requestFile);

        Call<UpdateProfileModel> bodyCall = apiServices.updateProfile(param, body);
        bodyCall.enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        UpdateProfileModel.Data data = response.body().getData();
                        applogicLogin(data);

                    } else
                        Toast.makeText(getApplicationContext(), response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void businessSetupCompletionAlert() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//           builder.setTitle("Are you sure ?");
        builder.setTitle("Business has not been fully setup by admin.");
        builder.setMessage("Please ask your admin to complete the business setup first ");
        builder.setPositiveButton("Appeal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                preference.logout();
//                Toast.makeText(EditProfileActivity.this,"Business has not been fully setup by Admin. ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProfileActivity.this, GetStartedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    preference.logout();
//                  Toast.makeText(EditProfileActivity.this,"Business has not been fully setup by Admin. ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfileActivity.this, GetStartedActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }

                return true;
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

    public void applogicLogin(final UpdateProfileModel.Data data) {

        UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context) {
//After successful registration with Applozic server the callback will come here


                Log.d("TAG", "login success");
                preference.setDeviceKey(registrationResponse.getDeviceKey());
//                 getActivity().startActivity(new Intent(getActivity(),AdminContactActivty.class));

                if (MobiComUserPreference.getInstance(context).isRegistered()) {

                    PushNotificationTask pushNotificationTask = null;
                    PushNotificationTask.TaskListener listener = new PushNotificationTask.TaskListener() {
                        @Override
                        public void onSuccess(RegistrationResponse registrationResponse) {

//                            progressBar.setVisibility(View.GONE);
//                            progressDialog.dismiss();
//                            startIntent(data);
                            if (getIntent().getExtras() == null) {
                                Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_SHORT).show();
                                preference.saveImage(data.getBProfileImage());
                                preference.saveFirstName(data.getBFullName());
                                if (preference.getBusinessROlE().equalsIgnoreCase(Constants.ADMIN)) {
                                    preference.saveCompletedStep("3");
                                    startActivity(new Intent(EditProfileActivity.this, NewBusinessSetupActivity.class));
                                    finish();

                                } else {
                                    if (data.getBusiness_setup().equals("YES"))
                                        startActivity(new Intent(EditProfileActivity.this, HomeActivity.class));
                                    else {
                                        businessSetupCompletionAlert();
                                    }
                                }
                            } else {
                                progressBar.setVisibility(View.GONE);

                                preference.saveImage(data.getBProfileImage());
                                preference.saveFirstName(data.getBFullName());
                                onBackPressed();
                                editProfile = 1;
                            }

                        }

                        @Override
                        public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//                            progressBar.setVisibility(View.GONE);
//                            progressDialog.dismiss();
                            progressBar.setVisibility(View.GONE);


                            Toast.makeText(EditProfileActivity.this, R.string.str_something_went_wrong, Toast.LENGTH_SHORT).show();
                        }

                    };

                    pushNotificationTask = new PushNotificationTask(Applozic.getInstance(context).getDeviceRegistrationId(), listener, EditProfileActivity.this);
                    pushNotificationTask.execute((Void) null);
                }


            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//If any failure in registration the callback will come here
//                progressBar.setVisibility(View.GONE);
//                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);


                Toast.makeText(EditProfileActivity.this, R.string.str_something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        };

        String fullName = et_userName.getText().toString();
        String[] s = fullName.split(" ");
        String displayName = s[0];

        User user = new User();
        user.setUserId("0B" + preference.getUserId());

        user.setDisplayName(displayName + "(" + preference.getBusinessROlE() + ")");

//        Log.e("userId",""+chatlocalyshareprefrence.getUserId());N
        user.setAuthenticationTypeId(User.AuthenticationType.CLIENT.getValue());
        user.setPassword("chatlocaly123456");

        new UserLoginTask(user, listener, this).execute((Void) null);

    }
}
