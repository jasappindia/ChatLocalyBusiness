package com.chatlocalybusiness.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.Applozic;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.fragments.OtpVerifyFragment;
import com.chatlocalybusiness.apiModel.RegisterModel;
import com.chatlocalybusiness.apiModel.WorkerRegisterModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shiv on 12/6/2017.
 */

public class OtpGenerateActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText et_phoneNo;
    private TextView tv_privacyPolicy, tv_terms, tv_alreadyMember;
    private Button btn_continueLogin;
    private ProgressBar progressBar;
    private Utill utill;
    private ChatBusinessSharedPreference prefrence;
    private String loginAs;
    private String utId, registerFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_genrate);
        Applozic.init(this, Constants.applicationKey);
        utill = new Utill();
        prefrence = new ChatBusinessSharedPreference(this);
        init();
        prefrence.saveToken_Id(FirebaseInstanceId.getInstance().getToken());
        Bundle b = getIntent().getExtras();
        if (b != null) {
            loginAs = b.getString(Constants.LOGIN_AS);
            registerFlag = b.getString(Constants.REGISTER_FLAG);
            if (registerFlag.equalsIgnoreCase(Constants.ALREADY_REGISTER)) {
                tv_alreadyMember.setVisibility(View.GONE);
            }
        }
    }

    public String getUtId() {
        if (loginAs.equalsIgnoreCase(Constants.ADMIN))
            utId = "1";
        else if (loginAs.equalsIgnoreCase(Constants.SUB_ADMIN))
            utId = "2";
        else if (loginAs.equalsIgnoreCase(Constants.WORKER))
            utId = "3";
        return utId;
    }

    public void init() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        et_phoneNo = (EditText) findViewById(R.id.et_phoneNo);
        tv_terms = (TextView) findViewById(R.id.tv_terms);
        tv_alreadyMember = (TextView) findViewById(R.id.tv_alreadyMember);
        tv_privacyPolicy = (TextView) findViewById(R.id.tv_privacyPolicy);
        btn_continueLogin = (Button) findViewById(R.id.btn_continueLogin);
        tv_privacyPolicy.setOnClickListener(this);
        tv_terms.setOnClickListener(this);
        btn_continueLogin.setOnClickListener(this);
        tv_alreadyMember.setOnClickListener(this);
     /*   btn_continueLogin.setFocusable(false);
        btn_continueLogin.setClickable(false);*/
        et_phoneNo.addTextChangedListener(this);
        et_phoneNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                  /* Write your logic here that will be executed when user taps next button */
                    checkOtpReadPermission();

                    handled = true;
                }
                return handled;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continueLogin:
                checkOtpReadPermission();
//                clickonNext();
                /*
                if (utill.isConnected(this)&&getUtId().equalsIgnoreCase("1"))
                    loginAsAdmin(et_phoneNo.getText().toString(), "91", getUtId(), prefrence.getGCM_Token_Id(), registerFlag);
                else if(utill.isConnected(this)&&!getUtId().equalsIgnoreCase("1"))
                    loginAsWorker(et_phoneNo.getText().toString(), "91", getUtId(), prefrence.getGCM_Token_Id(), registerFlag);
                    else Toast.makeText(getApplicationContext(), "Check internet connection", Toast.LENGTH_SHORT).show();
*/

                break;
            case R.id.tv_privacyPolicy:

                break;
            case R.id.tv_terms:

                break;
            case R.id.tv_alreadyMember:
                Intent intent = new Intent(OtpGenerateActivity.this, LoginActivity.class);
//              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

        }
    }

    public void checkOtpReadPermission()
    {
        if (OtpGenerateActivity.this.checkCallingOrSelfPermission(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            clickonNext();
        }
        else new Utill().requestMultiplePermissionsOtp(OtpGenerateActivity.this);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         clickonNext();

    }

    public void clickonNext() {
        if (!et_phoneNo.getText().toString().equalsIgnoreCase("")) {
            if (et_phoneNo.getText().toString().length() == 10 && !et_phoneNo.getText().toString().startsWith("0")) {

                if (utill.isConnected(this) && getUtId().equalsIgnoreCase("1"))

                    loginAsAdmin(et_phoneNo.getText().toString(), "91", getUtId(), prefrence.getGCM_Token_Id(), registerFlag);
                else if (utill.isConnected(this) && !getUtId().equalsIgnoreCase("1"))
                    loginAsWorker(et_phoneNo.getText().toString(), "91", getUtId(), prefrence.getGCM_Token_Id(), registerFlag);
                else
                    Toast.makeText(getApplicationContext(), "Check internet connection", Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(getApplicationContext(), "Enter 10 digits mobile number", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "Enter 10 digits mobile number", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!charSequence.toString().startsWith("0") && charSequence.toString().trim().length() == 10) {
            btn_continueLogin.setBackgroundResource(R.drawable.blue_btn_bg);
            btn_continueLogin.setTextColor(ContextCompat.getColor(this, R.color.white));
//            btn_continueLogin.setFocusable(true);
//            btn_continueLogin.setClickable(true);
        } else {
            btn_continueLogin.setBackgroundResource(R.drawable.gray_btn_bg);
            btn_continueLogin.setTextColor(ContextCompat.getColor(this, R.color.light_gray));
//            btn_continueLogin.setFocusable(false);
//            btn_continueLogin.setClickable(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void loginAsAdmin(final String mobile, String countryCode, String utID, String deviceId, String registerFlag) {
//        progressBar.setVisibility(View.VISIBLE);
        final ProgressDialog progressDialog = Utill.showloader(this);
        progressDialog.show();

        /*
         Parameters
                 encrypt_key=df&
                 b_mobile_number=9460405321&
                 b_cm_code=91&
                 b_ut_id=1&
                 b_device_id=gfjdghfdkg&
                 b_device_type=ANDROID

*/
        prefrence.saveToken_Id(FirebaseInstanceId.getInstance().getToken());

        final ApiServices apiService = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "df");
        param.put("b_mobile_number", mobile);
        param.put("b_cm_code", countryCode);
        param.put("b_ut_id", utID);
        param.put("b_device_id", deviceId);
        param.put("b_device_type", "ANDROID");
        param.put("register_flag", registerFlag);

        Call<RegisterModel> callList = apiService.userRegister(param);
        callList.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        if (response.body().getData().getMessage().equalsIgnoreCase("OTP sent on mobile no")) {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.USER_MOBILE, mobile);
                            bundle.putString(Constants.LOGIN_AS, loginAs);
                            OtpVerifyFragment otpVerifyFragment = new OtpVerifyFragment();
                            otpVerifyFragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.container_login, otpVerifyFragment).addToBackStack("Admin").commit();
                            enableAll(false);
                        }
                    } else if (response.body().getData().getResultCode().equalsIgnoreCase("0"))
                        Toast.makeText(getApplicationContext(), response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(getApplicationContext(), R.string.str_something_went_wrong, Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
                prefrence.saveToken_Id(FirebaseInstanceId.getInstance().getToken());

            }
        });
    }

    public void loginAsWorker(final String mobile, String countryCode, String utID, String deviceId, String registerFlag) {
        progressBar.setVisibility(View.VISIBLE);
/*              Parameters
                 encrypt_key=df&
                 b_mobile_number=9460405321&
                 b_cm_code=91&
                 b_ut_id=1&
                 b_device_id=gfjdghfdkg&
                 b_device_type=ANDROID
*/
        final ApiServices apiService = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "df");
        param.put("b_mobile_number", mobile);
        param.put("b_cm_code", countryCode);
        param.put("b_ut_id", utID);
        param.put("b_device_id", deviceId);
        param.put("b_device_type", "ANDROID");
//       param.put("register_flag", registerFlag);

        Call<WorkerRegisterModel> callList = apiService.workerRegister(param);
        callList.enqueue(new Callback<WorkerRegisterModel>() {
            @Override
            public void onResponse(Call<WorkerRegisterModel> call, Response<WorkerRegisterModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        if (response.body().getData().getMessage().equalsIgnoreCase("OTP sent on mobile no.")) {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.USER_MOBILE, mobile);
                            bundle.putString(Constants.LOGIN_AS, loginAs);
                            OtpVerifyFragment otpVerifyFragment = new OtpVerifyFragment();
                            otpVerifyFragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().add(R.id.container_login, otpVerifyFragment).addToBackStack("worker").commit();
                            enableAll(false);
                            overridePendingTransition(R.anim.rotate_in,R.anim.rotate_out);
                        }
                    } else if (response.body().getData().getResultCode().equalsIgnoreCase("0"))
                        Toast.makeText(getApplicationContext(), response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<WorkerRegisterModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
                prefrence.saveToken_Id(FirebaseInstanceId.getInstance().getToken());
            }
        });
    }

    private void enableAll(boolean b) {
        tv_privacyPolicy.setEnabled(b);
        tv_terms.setEnabled(b);
        btn_continueLogin.setEnabled(b);
        tv_alreadyMember.setEnabled(b);
        et_phoneNo.setEnabled(b);

    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            getSupportFragmentManager().popBackStackImmediate();
            enableAll(true);
        } else super.onBackPressed();
    }
}
