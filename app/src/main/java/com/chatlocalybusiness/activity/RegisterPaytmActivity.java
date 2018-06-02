package com.chatlocalybusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.RegisterPaytmModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shiv on 12/18/2017.
 */

public class RegisterPaytmActivity extends AppCompatActivity implements View.OnClickListener {

    private Utill utill;
    private ChatBusinessSharedPreference preference;
    private EditText et_phoneNo;
    private Button btn_skip,btn_finish;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm_number);
        utill=new Utill();
        preference=new ChatBusinessSharedPreference(this);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        et_phoneNo=(EditText)findViewById(R.id.et_phoneNo);
        btn_skip=(Button)findViewById(R.id.btn_skip);
        btn_finish=(Button)findViewById(R.id.btn_finish);

        btn_finish.setOnClickListener(this);
        setBtn_finishClickable(false);
        btn_skip.setOnClickListener(this);

        et_phoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.toString().trim().length()==10)
                    setBtn_finishClickable(true);
                else setBtn_finishClickable(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

   
    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.btn_finish :
                       registerPaytmMobile(Constants.Encryption_Key,
                        preference.getUserId(),et_phoneNo.getText().toString().trim(),String.valueOf(preference.getBusinessId()));

             break;
            case R.id.btn_skip :
                if(getIntent().getExtras()==null)
                startActivity(new Intent(this, HomeActivity.class));
                else onBackPressed();
                break;


        }
    }
    public void setBtn_finishClickable(boolean b)
    {
        if(b)
        {
            btn_finish.setClickable(b);
            btn_finish.setFocusable(b);
            btn_finish.setTextColor(ContextCompat.getColor(this,R.color.white));
            btn_finish.setBackgroundResource(R.drawable.blue_btn_bg);

        }else{
            btn_finish.setClickable(b);
            btn_finish.setFocusable(b);
            btn_finish.setTextColor(ContextCompat.getColor(this,R.color.light_gray));
            btn_finish.setBackgroundResource(R.drawable.gray_btn_bg);

        }
    }
    public void registerPaytmMobile(String encryptKey,String userId,String paytmNo,String businessId)
    {
//        encrypt_key=dfghgvf&b_user_id=1&ptm_cm_code=91&ptm_mobile_number=9460405321&b_id=3
        progressBar.setVisibility(View.VISIBLE);

        ApiServices apiServices= ApiClient.getClient().create(ApiServices.class);
        HashMap<String ,String> param=new HashMap<>();
        param.put("encrypt_key",encryptKey);
        param.put("b_user_id",userId);
        param.put("ptm_cm_code","91");
        param.put("ptm_mobile_number",paytmNo);
        param.put("b_id",businessId);

        Call<RegisterPaytmModel> call=apiServices.registerPaytm(param);
        call.enqueue(new Callback<RegisterPaytmModel>() {
            @Override
            public void onResponse(Call<RegisterPaytmModel> call, Response<RegisterPaytmModel> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful())
                {
                    if(response.body().getData().getResultCode().equalsIgnoreCase("1"))
                    {
                       if(getIntent().getExtras()==null){
                           Intent intent=new Intent(RegisterPaytmActivity.this,HomeActivity.class);
                           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                           startActivity(intent);}else {
                           onBackPressed();
                       }
                    }
                    else
                    Toast.makeText(RegisterPaytmActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterPaytmModel> call, Throwable t) {
              progressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterPaytmActivity.this,"check internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
