package com.chatlocalybusiness.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.AdminContactActivty;
import com.chatlocalybusiness.activity.EditProfileActivity;
import com.chatlocalybusiness.activity.HomeActivity;
import com.chatlocalybusiness.activity.JoinNowActivity;
import com.chatlocalybusiness.activity.NewBusinessSetupActivity;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.apiModel.OtpVerifyModel;
import com.chatlocalybusiness.apiModel.WorkerOtpVerifyModel;
import com.chatlocalybusiness.getterSetterModel.BusinessSetupDetails;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by windows on 12/6/2017.
 */
public class OtpVerifyFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private EditText et_otp;
    private Button btn_continueLogin;
    private TextView tv_resend, tv_otpverifyLine;
    private Utill utill;
    private ChatBusinessSharedPreference prefrence;
    private ProgressBar progressBar;
    private String loginAs;
    private String utId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify_otp, container, false);
        utill = new Utill();
        prefrence = new ChatBusinessSharedPreference(getActivity());
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        et_otp = (EditText) view.findViewById(R.id.et_otp);
        btn_continueLogin = (Button) view.findViewById(R.id.btn_continueLogin);
        tv_resend = (TextView) view.findViewById(R.id.tv_resend);
        tv_otpverifyLine = (TextView) view.findViewById(R.id.tv_otpverifyLine);
        btn_continueLogin.setOnClickListener(this);
        tv_resend.setOnClickListener(this);
        setBtn_continueLogin(false);
        Bundle b = getArguments();
        if (b != null) {
            loginAs = b.getString(Constants.LOGIN_AS);
            tv_otpverifyLine.setText("OTP was sent to " + b.getString(Constants.USER_MOBILE));
        }
      /*  btn_continueLogin.setFocusable(false);
        btn_continueLogin.setClickable(false);*/
        et_otp.addTextChangedListener(this);
        et_otp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                  /* Write your logic here that will be executed when user taps next button */
                    clickonNext();

                    handled = true;
                }
                return handled;
            }
        });
        return view;
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().trim().length() == 6)
            setBtn_continueLogin(true);
        else setBtn_continueLogin(false);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void setBtn_continueLogin(boolean b) {
        if (b) {
            btn_continueLogin.setBackgroundResource(R.drawable.blue_btn_bg);
            btn_continueLogin.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            btn_continueLogin.setFocusable(true);
            btn_continueLogin.setClickable(true);

        } else {
            btn_continueLogin.setBackgroundResource(R.drawable.gray_btn_bg);
            btn_continueLogin.setTextColor(ContextCompat.getColor(getActivity(), R.color.light_gray));
            btn_continueLogin.setFocusable(false);
            btn_continueLogin.setClickable(false);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continueLogin:
                clickonNext();
                break;
            case R.id.tv_resend:

                break;
        }
    }

    public void clickonNext() {
        if (utill.isConnected(getActivity()) && getUtId().equals("1"))
            otpverify(getArguments().getString(Constants.USER_MOBILE), et_otp.getText().toString());
        else if (utill.isConnected(getActivity()) && !getUtId().equals("1"))
            otpWorkerVeifyApi(getArguments().getString(Constants.USER_MOBILE), et_otp.getText().toString());
        else Toast.makeText(getActivity(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

    }

    ProgressDialog progressDialog;

    public void otpverify(String mobile, String otpcode) {

//        progressBar.setVisibility(View.VISIBLE);
        progressDialog = Utill.showloader(getActivity());
        progressDialog.show();
        final ApiServices apiService = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "df");
        param.put("b_mobile_number", mobile);
        param.put("otp_code", otpcode);

        Call<OtpVerifyModel> callList = apiService.otpVeifyApi(param);
        callList.enqueue(new Callback<OtpVerifyModel>() {
            @Override
            public void onResponse(Call<OtpVerifyModel> call, Response<OtpVerifyModel> response) {

                if (response.isSuccessful()) {
                     OtpVerifyModel.Data data = response.body().getData();
                    if (data.getResultCode().equalsIgnoreCase("1")) {
                        OtpVerifyModel.UserDetail userDetail = data.getUserDetail();
                        prefrence.saveUserId(userDetail.getBUserId());
                        prefrence.saveUserMobile(userDetail.getBMobileNumber());
                        prefrence.saveBusinessId(Integer.parseInt(userDetail.getBId()));
                        prefrence.saveLoginKey(data.getUserDetail().getLoginKey());
                        prefrence.saveBusinessRole(loginAs);
                        prefrence.setBusinessName(userDetail.getBusinessName());
                        prefrence.setBusinessLogo(userDetail.getBusinessLogo());

                        Utill.setAdminPermissions(getActivity());
                        applogicLogin(data);

                    } else if (data.getResultCode().equalsIgnoreCase("0")) {
                        Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<OtpVerifyModel> call, Throwable t) {
//                 progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();

                Toast.makeText(getActivity(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void otpWorkerVeifyApi(String mobile, String otpcode) {
//        progressBar.setVisibility(View.VISIBLE);
        progressDialog = Utill.showloader(getActivity());
        progressDialog.show();

        final ApiServices apiService = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "df");
        param.put("b_mobile_number", mobile);
        param.put("otp_code", otpcode);
        param.put("b_ut_id", getUtId());


        Call<WorkerOtpVerifyModel> callList = apiService.otpWorkerVeifyApi(param);
        callList.enqueue(new Callback<WorkerOtpVerifyModel>() {
            @Override
            public void onResponse(Call<WorkerOtpVerifyModel> call, Response<WorkerOtpVerifyModel> response) {

                if (response.isSuccessful()) {
                    WorkerOtpVerifyModel.Data data = response.body().getData();
                    if (data.getResultCode().equalsIgnoreCase("1")) {
                        prefrence.saveUserId(data.getW_b_user_id());
                        prefrence.saveBusinessRole(loginAs);
                        applogicLoginWorker();
                    } else if (data.getResultCode().equalsIgnoreCase("0")) {
                        Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<WorkerOtpVerifyModel> call, Throwable t) {
//                 progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();

                Toast.makeText(getActivity(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startIntent(OtpVerifyModel.Data data) {

        BusinessInfoModelNew.BusinessDetail businessSetupDetails = new BasicUtill().saveBusinessInfo(data);

        if (data.getComplatedStep().equalsIgnoreCase("1")) {
            prefrence.saveCompletedStep("1");
            Intent intent = new Intent(getActivity(), JoinNowActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (data.getComplatedStep().equals("2")) {
            prefrence.saveCompletedStep("2");
            Intent intent2 = new Intent(getActivity(), EditProfileActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        } else if (data.getComplatedStep().equals("3")) {
            prefrence.saveCompletedStep("3");
            prefrence.saveImage(data.getUserDetail().getBProfileImage());
            prefrence.saveFirstName(data.getUserDetail().getBFullName());
            Intent intent2 = new Intent(getActivity(), NewBusinessSetupActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        } else if (data.getComplatedStep().equals("4")) {
            prefrence.saveCompletedStep("4");
            prefrence.saveImage(data.getUserDetail().getBProfileImage());
            prefrence.saveFirstName(data.getUserDetail().getBFullName());
            Intent intent2 = new Intent(getActivity(), NewBusinessSetupActivity.class);
            intent2.putExtra(Constants.BUSINESS_INFO, businessSetupDetails);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent2.putExtra("step", "4");
            startActivity(intent2);
        } else if (data.getComplatedStep().equals("5")) {
            prefrence.saveCompletedStep("5");
            prefrence.saveImage(data.getUserDetail().getBProfileImage());
            prefrence.saveFirstName(data.getUserDetail().getBFullName());
            Intent intent2 = new Intent(getActivity(), NewBusinessSetupActivity.class);
            intent2.putExtra(Constants.BUSINESS_INFO, businessSetupDetails);

            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent2.putExtra("step", "5");
            startActivity(intent2);
        } else if (data.getComplatedStep().equals("6")) {
            prefrence.saveCompletedStep("6");
            prefrence.saveImage(data.getUserDetail().getBProfileImage());
            prefrence.saveFirstName(data.getUserDetail().getBFullName());

            Intent intent2 = new Intent(getActivity(), HomeActivity.class);
            intent2.putExtra(Constants.BUSINESS_INFO, businessSetupDetails);

            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        }
    }

    public void applogicLogin(final OtpVerifyModel.Data data) {

        Map<String, String> metadata = new HashMap<>();
//        metadata.put("Department" , "Engineering");
        if (utId.equals("1"))
            metadata.put("Designation", "Admin");
        else if (utId.equals("2"))
            metadata.put("Designation", "Sub Admin");
        else
            metadata.put("Designation", "Worker");

        //        metadata.put("Team" , "Device Team");

        UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context) {
//After successful registration with Applozic server the callback will come here


                Log.d("TAG", "login success");
                prefrence.setDeviceKey(registrationResponse.getDeviceKey());
//                 getActivity().startActivity(new Intent(getActivity(),AdminContactActivty.class));

                if (MobiComUserPreference.getInstance(context).isRegistered()) {

                    PushNotificationTask pushNotificationTask = null;
                    PushNotificationTask.TaskListener listener = new PushNotificationTask.TaskListener() {
                        @Override
                        public void onSuccess(RegistrationResponse registrationResponse) {

//                            progressBar.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            startIntent(data);
                        }

                        @Override
                        public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//                            progressBar.setVisibility(View.GONE);
                            progressDialog.dismiss();

                            Toast.makeText(getActivity(), "something went wrong, try again", Toast.LENGTH_SHORT).show();
                        }

                    };

                    pushNotificationTask = new PushNotificationTask(Applozic.getInstance(context).getDeviceRegistrationId(), listener, getActivity());
                    pushNotificationTask.execute((Void) null);
                }


            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//If any failure in registration the callback will come here
//                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();

                Toast.makeText(getActivity(), "something went wrong, try again", Toast.LENGTH_SHORT).show();
            }
        };


        User user = new User();
        user.setUserId("0B" + prefrence.getUserId());
//        Log.e("userId",""+chatlocalyshareprefrence.getUserId());N
        user.setAuthenticationTypeId(User.AuthenticationType.CLIENT.getValue());
        user.setPassword("chatlocaly123456");

        new UserLoginTask(user, listener, getActivity()).execute((Void) null);

    }

    public void applogicLoginWorker() {

        UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context) {
//After successful registration with Applozic server the callback will come here
                progressBar.setVisibility(View.GONE);

                Log.d("TAG", "login success");

                if (MobiComUserPreference.getInstance(context).isRegistered()) {

                    PushNotificationTask pushNotificationTask = null;
                    PushNotificationTask.TaskListener listener = new PushNotificationTask.TaskListener() {
                        @Override
                        public void onSuccess(RegistrationResponse registrationResponse) {
                            progressDialog.dismiss();
                            prefrence.setDeviceKey(registrationResponse.getDeviceKey());
                            Intent intent = new Intent(getActivity(), AdminContactActivty.class);
                            if (getUtId().equalsIgnoreCase("2"))
                                intent.putExtra(Constants.LOGIN_AS, Constants.SUB_ADMIN);
                            else intent.putExtra(Constants.LOGIN_AS, Constants.WORKER);

                            getActivity().startActivity(intent);
                        }

                        @Override
                        public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                            progressDialog.dismiss();

                        }

                    };

                    pushNotificationTask = new PushNotificationTask(Applozic.getInstance(context).getDeviceRegistrationId(), listener, getActivity());
                    pushNotificationTask.execute((Void) null);
                }


            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//If any failure in registration the callback will come here
//                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();

                Toast.makeText(getActivity(), "something went wrong, try again", Toast.LENGTH_SHORT).show();
            }
        };


        User user = new User();
        user.setUserId("0B" + prefrence.getUserId());
//        Log.e("userId",""+chatlocalyshareprefrence.getUserId());N
        user.setAuthenticationTypeId(User.AuthenticationType.CLIENT.getValue());
        user.setPassword("chatlocaly123456");

//User.AuthenticationType.APPLOZIC.getValue() for password verification from Applozic server and User.AuthenticationType.CLIENT.getValue() for access Token verification from your server set access token as password
//userId it can be any unique user identifier NOTE : +,*,? are not allowed chars in userId.
/* user.setDisplayName(displayName); //displayName is the name of the user which will be shown in chat messages
user.setEmail(email); //optional
user.setAuthenticationTypeId(User.AuthenticationType.APPLOZIC.getValue()); //User.AuthenticationType.APPLOZIC.getValue() for password verification from Applozic server and User.AuthenticationType.CLIENT.getValue() for access Token verification from your server set access token as password
user.setPassword(""); //optional, leave it blank for testing purpose, read this if you want to add additional security by verifying password from your server https://www.applozic.com/docs/configuration.html#access-token-url
user.setImageLink("");//optional, set your image link if you have
// */
        new UserLoginTask(user, listener, getActivity()).execute((Void) null);

    }

    //String emp_num =getArguments().getString("number");
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String str = intent.getStringExtra("message");
            if (str.length() > 0) {
                str = str.replaceAll("\\D+", "");
                try {
                    //  edttxt_confrmation_code.setText(str);
                } catch (Exception e) {
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mMessageReceiver, new IntentFilter("OTP"));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mMessageReceiver);
    }


}