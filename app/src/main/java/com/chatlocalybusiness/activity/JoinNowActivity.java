package com.chatlocalybusiness.activity;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.SendPromoModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by windows on 12/6/2017.
 */

public class JoinNowActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private EditText et_promocode;
    private Button btn_joinNow;
    private ImageView iv_checkIcon;
    private  TextView tv_alreadyMember;
    private  TextView tv_congoLine;
    private Utill utill;
    private ChatBusinessSharedPreference preference;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinscreen);

        utill=new Utill();
        preference=new ChatBusinessSharedPreference(JoinNowActivity.this);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        et_promocode=(EditText)findViewById(R.id.et_promocode);
        btn_joinNow=(Button)findViewById(R.id.btn_joinNow);
        iv_checkIcon=(ImageView) findViewById(R.id.iv_checkIcon);
        tv_alreadyMember=(TextView)findViewById(R.id.tv_alreadyMember);
        tv_congoLine=(TextView)findViewById(R.id.tv_congoLine);

        et_promocode.addTextChangedListener(this);

        tv_alreadyMember.setOnClickListener(this);
        btn_joinNow.setOnClickListener(this);
        btn_joinNow.setClickable(false);
        btn_joinNow.setFocusable(false);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.toString().trim().equals("JOINNOW"))
        {
            iv_checkIcon.setVisibility(View.VISIBLE);
            btn_joinNow.setClickable(true);
            btn_joinNow.setFocusable(true);
            btn_joinNow.setTextColor(ContextCompat.getColor(this,R.color.white));
            btn_joinNow.setBackgroundResource(R.drawable.blue_btn_bg);

            tv_congoLine.setText(this.getResources().getString(R.string.str_congoLine));
        }
        else{
            iv_checkIcon.setVisibility(View.GONE);
//            btn_joinNow.setBackgroundColor(ContextCompat.getColor(this, R.color.grey));
            btn_joinNow.setBackgroundResource(R.drawable.gray_btn_bg);

            btn_joinNow.setClickable(false);
            btn_joinNow.setFocusable(false);
            btn_joinNow.setTextColor(ContextCompat.getColor(this,R.color.light_gray));
            tv_congoLine.setText(this.getResources().getString(R.string.str_promoLine));
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.tv_alreadyMember:
                Intent intent=new Intent(JoinNowActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
         case R.id.btn_joinNow:
             getMembership();
                break;
        }
    }
    public void getMembership()
    {
//        progressBar.setVisibility(View.VISIBLE);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        ApiServices apiServices= ApiClient.getClient().create(ApiServices.class);
        HashMap<String,String> param=new HashMap<>();
        param.put("encrypt_key", Constants.Encryption_Key);
        param.put("b_user_id",preference.getUserId());
        param.put("b_id",String.valueOf(preference.getBusinessId()));
        param.put("promo_code",et_promocode.getText().toString().toUpperCase().trim());

//        param.put("b_user_id","1");

        Call<SendPromoModel> call=apiServices.getMembership(param);
        call.enqueue(new Callback<SendPromoModel>() {
            @Override
            public void onResponse(Call<SendPromoModel> call, Response<SendPromoModel> response) {

                if(response.isSuccessful())
                {
                    if(response.body().getData().getResultCode().equalsIgnoreCase("1"))
                    {
                      /*  planList = response.body().getData().getSpPlanList();
                        ll_category.setVisibility(View.VISIBLE);*/
                        Intent intent2=new Intent(JoinNowActivity.this,EditProfileActivity.class);
                        preference.saveCompletedStep("2");
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent2);
                    }else Toast.makeText(JoinNowActivity.this,response.body().getData().getMessage(),Toast.LENGTH_SHORT).show();
                }
//                progressBar.setVisibility(View.GONE);

            progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SendPromoModel> call, Throwable t) {
                Toast.makeText(JoinNowActivity.this,"Check your internet connection",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

//                progressBar.setVisibility(View.GONE);

            }
        });

    }
}
