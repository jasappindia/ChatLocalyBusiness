package com.chatlocalybusiness.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.EditProfileActivity;
import com.chatlocalybusiness.activity.GetStartedActivity;
import com.chatlocalybusiness.activity.HomeActivity;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.AdminVerifyModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by windows on 12/6/2017.
 */
public class VerifyAdminFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private EditText et_otp;
    private Button btn_continueLogin;
    private TextView tv_resend, tv_otpverifyLine;
    private Utill utill;
    private ChatBusinessSharedPreference prefrence;
    private ProgressBar progressBar;
    private String loginAs;
    private String utId;
    private AlertDialog alertdialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify_admin, container, false);

        utill = new Utill();
        prefrence = new ChatBusinessSharedPreference(getActivity());
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        et_otp = (EditText) view.findViewById(R.id.et_otp);
        btn_continueLogin = (Button) view.findViewById(R.id.btn_continueLogin);
        tv_otpverifyLine = (TextView) view.findViewById(R.id.tv_otpverifyLine);
//        tv_resend=(TextView)view.findViewById(R.id.tv_resend);
//        tv_otpverifyLine=(TextView)view.findViewById(R.id.tv_otpverifyLine);
        btn_continueLogin.setOnClickListener(this);
//        tv_resend.setOnClickListener(this);
        setBtn_continueLogin(false);
        Bundle b = getArguments();
        if (b != null) {
            tv_otpverifyLine.setText("A text message was sent to admin's phone " + b.getString(Constants.USER_MOBILE));
            loginAs = b.getString(Constants.LOGIN_AS);
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
                    if (utill.isConnected(getActivity()))
                        otpverify(getArguments().getString(Constants.USER_MOBILE), et_otp.getText().toString());
                    else
                        Toast.makeText(getActivity(), "Check internet connection", Toast.LENGTH_SHORT).show();
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
                if (utill.isConnected(getActivity()))
                    otpverify(getArguments().getString(Constants.USER_MOBILE), et_otp.getText().toString());
                else
                    Toast.makeText(getActivity(), "Check internet connection", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_resend:

                break;
        }
    }

    public void otpverify(String mobile, String otpcode) {

//        progressBar.setVisibility(View.VISIBLE);
        final ProgressDialog progressDialog = Utill.showloader(getActivity());
        progressDialog.show();

        final ApiServices apiService = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "df");
        param.put("w_b_user_id", prefrence.getUserId());
        param.put("password", otpcode);

        Call<AdminVerifyModel> callList = apiService.adminPassVerify(param);
        callList.enqueue(new Callback<AdminVerifyModel>() {
            @Override
            public void onResponse(Call<AdminVerifyModel> call, Response<AdminVerifyModel> response) {
//                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    AdminVerifyModel.Data data = response.body().getData();
                    if (data.getResultCode().equals("1")) {
                        prefrence.saveUserId(response.body().getData().getWBUserId());
                        prefrence.saveBusinessId(Integer.parseInt(response.body().getData().getBId()));
                        prefrence.saveLoginKey(response.body().getData().getLoginKey());
                        prefrence.setBusinessName(response.body().getData().getBusinessName());
                        prefrence.setBusinessLogo(response.body().getData().getBusinessLogo());
                        if (data.getComplatedSteps().equals("1")) {
                            if (data.getBusinessSetup().equalsIgnoreCase("Yes")) {
                                prefrence.saveImage(data.getBProfileImage());
                                prefrence.saveFirstName(data.getBFullName());
                                prefrence.saveCompletedStep(data.getComplatedSteps());

                                Utill.setPermissions(getActivity(), data);

                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                businessSetupCompletionAlert();
                            }
                        } else {
                            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<AdminVerifyModel> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "check internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void businessSetupCompletionAlert() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
//           builder.setTitle("Are you sure ?");
        builder.setTitle("Business has not been fully setup by admin.");
        builder.setMessage("Please ask your admin to complete the business setup first ");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                prefrence.logout();
//                Toast.makeText(EditProfileActivity.this,"Business has not been fully setup by Admin. ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), GetStartedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    prefrence.logout();
//                Toast.makeText(EditProfileActivity.this,"Business has not been fully setup by Admin. ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), GetStartedActivity.class);
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

}