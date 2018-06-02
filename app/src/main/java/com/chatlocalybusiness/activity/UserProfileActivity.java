package com.chatlocalybusiness.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.applozic.mobicomkit.uiwidgets.conversation.adapter.DetailedConversationAdapter;
import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.UpdateProfileModel;
import com.chatlocalybusiness.apiModel.UserProfileModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.ui.CircleImageView;
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
 * Created by windows on 3/29/2018.
 */

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_editProfile, iv_arrowBack, iv_changePhoto;
    private CircleImageView iv_user_profile;
    private TextView tv_username, tv_Designation;
    private Button btn_logout;
    //    private RelativeLayout rl_profileLayout;
    private LinearLayout ll_designation, ll_profile;
    ChatBusinessSharedPreference preference;
    private String userID;
    private ProgressBar progressBar;
    private AlertDialog alertdialog;
    private AlertDialog logoutdialog;
    public String filePath = null;


    @Override
    protected void onResume() {
        super.onResume();
        if (EditProfileActivity.editProfile == 1) {
//           setImageRatio();
            setOwnProfile();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        preference = new ChatBusinessSharedPreference(this);
        init();
        if (getIntent().getExtras() != null)
            userID = getIntent().getExtras().getString(DetailedConversationAdapter.CHAT_USER_ID);
        if (userID != null) {
            ll_profile.setVisibility(View.GONE);
            userUpdate();
        } else {
            ll_profile.setVisibility(View.VISIBLE);
            setOwnProfile();
        }
    }

    private void setOwnProfile() {
//        rl_profileLayout.setVisibility(View.VISIBLE);
        iv_editProfile.setVisibility(View.VISIBLE);

        btn_logout.setVisibility(View.VISIBLE);
        if (preference.getUserImage() != null)
            if (!preference.getUserImage().equalsIgnoreCase(""))
                Glide.with(UserProfileActivity.this).load(preference.getUserImage()).into(iv_user_profile);

        tv_username.setText(preference.getUser_NAME());
        if(preference.getBusinessROlE()!=null)
        {
            ll_designation.setVisibility(View.VISIBLE);
            tv_Designation.setText(preference.getBusinessROlE());
        }


        /*if(data.getUserData().getUserType().equalsIgnoreCase("customer"))
        {
            ll_designation.setVisibility(View.GONE);
        }
        else
        {
            ll_designation.setVisibility(View.VISIBLE);
            tv_Designation.setText(data.getUserData().getDesignation());
        }
*/

    }

    public void init() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        iv_changePhoto = (ImageView) findViewById(R.id.iv_changePhoto);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        iv_user_profile = (CircleImageView) findViewById(R.id.iv_user_profile);
        iv_editProfile = (ImageView) findViewById(R.id.iv_editProfile);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_Designation = (TextView) findViewById(R.id.tv_Designation);
        btn_logout = (Button) findViewById(R.id.btn_logout);
//        rl_profileLayout = (RelativeLayout) findViewById(R.id.rl_profileLayout);
        ll_designation = (LinearLayout) findViewById(R.id.ll_designation);
        ll_profile = (LinearLayout) findViewById(R.id.ll_profile);
//        setImageRatio();

        btn_logout.setOnClickListener(this);
        iv_editProfile.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
        iv_user_profile.setOnClickListener(this);
        iv_changePhoto.setOnClickListener(this);
    }

    public void setImageRatio() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
        int width = pxToDp(displayMetrics.widthPixels);
//        double height=displayMetrics.widthPixels*(0.523);
        double height = displayMetrics.widthPixels;
        int height1 = (int) height;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_user_profile.getLayoutParams();
        params.height = height1;
//        params.width =displayMetrics.widthPixels ;
        params.width = height1;

    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_logout:
                loginAlert(UserProfileActivity.this);
                break;
            case R.id.iv_editProfile:
                if(new Utill().isConnected(this))
                editNAmeAlert();
                else Toast.makeText(this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.iv_user_profile:
                startActivity(new Intent(UserProfileActivity.this, ProfileImageActivity.class));
                break;

            case R.id.iv_changePhoto:
                if(new Utill().isConnected(this)){
                Dialog dialog = chooserDialog("Change Profile Image");
                dialog.show();
                dialog.setCancelable(true);}
                else Toast.makeText(this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

                break;
        }

    }

    public void editNAmeAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_designation, null);
        builder.setView(view);

        Button btn_done = (Button) view.findViewById(R.id.btn_done);
        final EditText et_designation = (EditText) view.findViewById(R.id.et_designation);
        et_designation.setText(tv_username.getText().toString());

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_designation.getText().toString().trim() != null && !et_designation.getText().toString().trim().equals("")) {
                    tv_username.setText(et_designation.getText().toString());
                    updateProfile(preference.getUserId(), tv_username.getText().toString().trim(), filePath);
                }

                alertdialog.dismiss();

            }
        });

        alertdialog = builder.create();
        alertdialog.show();
        alertdialog.setCancelable(true);
    }
    public Dialog chooserDialog(String title) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        String[] array = {"Take Photo", "Choose from Gallery"};

        builder.setTitle(title).setItems(array, new DialogInterface.OnClickListener() {
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
        startActivityForResult(new Intent(UserProfileActivity.this, SingleImageSelectionActivity.class), 1);
    }

    public void chooseFromGallery() {
        Constants.IMAGE_SELECT_CAPTURE = "gallery";
        Constants.limit = 1;
        startActivityForResult(new Intent(UserProfileActivity.this, SingleImageSelectionActivity.class), 1);

    }


    public void loginAlert(Context context)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setMessage("Are you sure? You will be logged out!").setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(UserProfileActivity.this, GetStartedActivity.class);
                    preference.logout();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            logoutdialog = builder.create();
            logoutdialog.show();
            logoutdialog.setCancelable(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                logoutdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
            }
        }


/*
    public void loginAlert(final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_logout, null);
        builder.setView(dialogView);

        TextView tv_alertHeading = (TextView) dialogView.findViewById(R.id.tv_alertHeading);
        TextView tv_logout = (TextView) dialogView.findViewById(R.id.tv_logout);
        TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, GetStartedActivity.class);
                preference.logout();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutdialog.dismiss();

            }
        });

        logoutdialog = builder.create();
        logoutdialog.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            logoutdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        }
    }
*/

    public void userUpdate() {
        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "kgki");
        param.put("b_user_id", preference.getUserId());
        param.put("user_id", userID);

        Call<UserProfileModel> call = apiServices.getUserProfile(param);
        call.enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
//                        rl_profileLayout.setVisibility(View.VISIBLE);
                        ll_profile.setVisibility(View.VISIBLE);

                        setData(response.body().getData());
                    }
                }

            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UserProfileActivity.this, "check internet connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setData(UserProfileModel.Data data) {
        btn_logout.setVisibility(View.GONE);
        if (data.getUserData().getProfileImage() != null || !data.getUserData().getProfileImage().equals("")) {
            Glide.with(UserProfileActivity.this).load(data.getUserData().getProfileImage()).into(iv_user_profile);
        }
        tv_username.setText(data.getUserData().getFullName());
        if (data.getUserData().getUserType().equalsIgnoreCase("customer")) {
            ll_designation.setVisibility(View.GONE);
        } else {
            ll_designation.setVisibility(View.VISIBLE);
            tv_Designation.setText(data.getUserData().getDesignation());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
//                String strEditText = data.getStringExtra("editTextValue");
                filePath = data.getStringExtra(Constants.IMAGE_PATH);
//                iv_proThumb.setImageURI(Uri.parse("file://" + filePath));
                Glide.with(UserProfileActivity.this).load(Uri.parse("file://" + filePath)).into(iv_user_profile);

                updateProfile(preference.getUserId(), tv_username.getText().toString().trim(), filePath);
            }
        }
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

    public void applogicLogin(final UpdateProfileModel.Data data) {

        UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context) {
//After successful registration with Applozic server the callback will come here


                Log.d("TAG", "login success");
                preference.setDeviceKey(registrationResponse.getDeviceKey());
//                 getActivity().startActivity(new Intent(getActivity(),AdminContactActivty.class));
                          progressBar.setVisibility(View.GONE);

                            Toast.makeText(getApplicationContext(), " Profile updated successfully", Toast.LENGTH_SHORT).show();
                            preference.saveImage(data.getBProfileImage());
                            preference.saveFirstName(data.getBFullName());
                            if (preference.getBusinessROlE().equalsIgnoreCase(Constants.ADMIN)) {
                                preference.saveCompletedStep("3");
                            }
            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//If any failure in registration the callback will come here
//                progressBar.setVisibility(View.GONE);
//                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);


                Toast.makeText(UserProfileActivity.this, "something went wrong, try again", Toast.LENGTH_SHORT).show();
            }
        };

        String fullName = tv_username.getText().toString();
        String[] s = fullName.split(" ");
        String displayName = s[0];

        User user = new User();
        user.setUserId("0B" + preference.getUserId());

        user.setDisplayName(displayName+"("+preference.getBusinessROlE()+")");
//        Log.e("userId",""+chatlocalyshareprefrence.getUserId());N
        user.setAuthenticationTypeId(User.AuthenticationType.CLIENT.getValue());
        user.setPassword("chatlocaly123456");

        new UserLoginTask(user, listener, this).execute((Void) null);

    }
}
