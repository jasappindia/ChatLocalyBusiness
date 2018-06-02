package com.chatlocalybusiness.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.fragments.VerifyAdminFragment;
import com.chatlocalybusiness.apiModel.AdminContactMOdel;
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
 * Created by windows on 2/5/2018.
 */

public class AdminContactActivty extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(AdminContactActivty.this, 0, rl_main);
    }

    ViewGroup rl_main;
    private EditText et_phoneNo;
    private Button btn_continueLogin;
    private ProgressBar progressBar;
    private Utill utill;
    private ChatBusinessSharedPreference prefrence;
    private String loginAs;
    private String utId, registerFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_contact);
        rl_main = (ViewGroup) findViewById(R.id.rl_main);

        utill = new Utill();
        prefrence = new ChatBusinessSharedPreference(this);
        init();
        prefrence.saveToken_Id(FirebaseInstanceId.getInstance().getToken());
        Bundle b = getIntent().getExtras();
        if (b != null) {
            loginAs = b.getString(Constants.LOGIN_AS);
//            registerFlag = b.getString(Constants.REGISTER_FLAG);
//            if (registerFlag.equalsIgnoreCase(Constants.ALREADY_REGISTER)) {
//            }
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

        btn_continueLogin = (Button) findViewById(R.id.btn_continueLogin);

        btn_continueLogin.setOnClickListener(this);

      /*  btn_continueLogin.setFocusable(false);
        btn_continueLogin.setClickable(false);*/
        et_phoneNo.addTextChangedListener(this);
        et_phoneNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
    }

    public void setDOneClickable(boolean b) {
//        btn_continueLogin.setFocusable(b);
//        btn_continueLogin.setClickable(b);
        if (b) {
            btn_continueLogin.setBackgroundResource(R.drawable.blue_btn_bg);
            btn_continueLogin.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            btn_continueLogin.setBackgroundResource(R.drawable.gray_btn_bg);
            btn_continueLogin.setTextColor(ContextCompat.getColor(this, R.color.light_gray));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continueLogin:
                clickonNext();
                break;
        }
    }

    public void clickonNext() {
        if (!et_phoneNo.getText().toString().equalsIgnoreCase("")) {
            if (et_phoneNo.getText().toString().length() == 10 && !et_phoneNo.getText().toString().startsWith("0")) {

                if (utill.isConnected(this)) {
            if (!et_phoneNo.getText().toString().trim().equals(""))
                loginAsAdmin(et_phoneNo.getText().toString(), "91", prefrence.getUserId());
            else et_phoneNo.setError("Enter Admin's Phone number");

        } else
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
        if (!charSequence.toString().startsWith("0") && charSequence.toString().trim().length() == 10)
            setDOneClickable(true);
        else
            setDOneClickable(false);

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void loginAsAdmin(final String mobile, String countryCode, String userID) {
//        progressBar.setVisibility(View.VISIBLE);

        final ProgressDialog progressDialog = Utill.showloader(this);
        progressDialog.show();

        /*
         Parameters
                 encrypt_key=jvnkfn&w_b_user_id=59&b_mobile_number=8888888888&b_cm_code=91

*/
        final ApiServices apiService = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "df");
        param.put("b_mobile_number", mobile);
        param.put("b_cm_code", countryCode);
        param.put("w_b_user_id", userID);
        param.put("b_ut_id", getUtId());


        Call<AdminContactMOdel> callList = apiService.adminContactVerify(param);
        callList.enqueue(new Callback<AdminContactMOdel>() {
            @Override
            public void onResponse(Call<AdminContactMOdel> call, Response<AdminContactMOdel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        if (response.body().getData().getMessage().equalsIgnoreCase("Password sent on business admin mobile no.")) {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.USER_MOBILE, mobile);
                            VerifyAdminFragment otpVerifyFragment = new VerifyAdminFragment();
                            otpVerifyFragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.container_login, otpVerifyFragment).addToBackStack("admin").commit();
                            enableAll(false);
                        }
                    } else if (response.body().getData().getResultCode().equalsIgnoreCase("0"))
                        Toast.makeText(getApplicationContext(), response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.GONE);
                   progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AdminContactMOdel> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(), "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enableAll(boolean b) {

        btn_continueLogin.setEnabled(b);

        et_phoneNo.setEnabled(b);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            getSupportFragmentManager().popBackStackImmediate();
        } else super.onBackPressed();
    }
}
