package com.chatlocalybusiness.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.InvoiceModel;
import com.chatlocalybusiness.getterSetterModel.AddItemBillModel;
import com.chatlocalybusiness.getterSetterModel.BuyerInfoModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by windows on 2/12/2018.
 */

public class BillGenerateActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ViewGroup rl_main;

    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(BillGenerateActivity.this,0,rl_main);
    }
    private Switch switch_roundOff, switch_useIcon, switch_useLocation, switch_buyerInfo, switch_BillDate,switch_dueDate;
    private ImageView iv_paid, iv_partiallyPaid, iv_unPaid, iv_none, iv_arrowBack, line1, line2, line3;
    private LinearLayout ll_next, ll_previous;
    private TextView tv_BillDate;
    private List<AddItemBillModel.ItemDetails> itemDetailsList;
    private BuyerInfoModel buyerInfoModel;
    private double totalAmount=0;
    private HashMap<String,Boolean> switches;
    private ChatBusinessSharedPreference preference;
    private Utill utill;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billgenerate);
        rl_main=(ViewGroup)findViewById(R.id.rl_main);

        utill=new Utill();
        preference=new ChatBusinessSharedPreference(BillGenerateActivity.this);
        setSwitchesValue();
        init();
        getValues();

    }

    public void getValues() {
        itemDetailsList = (List<AddItemBillModel.ItemDetails>) getIntent().getExtras().getSerializable(Constants.PRODUCT_DETAIL);
        buyerInfoModel = (BuyerInfoModel) getIntent().getExtras().getSerializable(Constants.BUYER_INFO);
        totalAmount=getIntent().getExtras().getDouble(Constants.TOTAL_AMOUNT);
    }

    private void init() {
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        tv_BillDate = (TextView) findViewById(R.id.tv_BillDate);
        switch_roundOff = (Switch) findViewById(R.id.switch_roundOff);
        switch_useIcon = (Switch) findViewById(R.id.switch_useIcon);
        switch_useLocation = (Switch) findViewById(R.id.switch_useLocation);
        switch_dueDate = (Switch) findViewById(R.id.switch_dueDate);
//        switch_buyerInfo = (Switch) findViewById(R.id.switch_buyerInfo);
        switch_BillDate = (Switch) findViewById(R.id.switch_BillDate);
        line1 = (ImageView) findViewById(R.id.line1);
        line2 = (ImageView) findViewById(R.id.line2);
        line3 = (ImageView) findViewById(R.id.line3);
        iv_paid = (ImageView) findViewById(R.id.iv_paid);
        iv_partiallyPaid = (ImageView) findViewById(R.id.iv_partiallyPaid);
        iv_unPaid = (ImageView) findViewById(R.id.iv_unPaid);
        iv_none = (ImageView) findViewById(R.id.iv_none);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        ll_next = (LinearLayout) findViewById(R.id.ll_next);
        ll_previous = (LinearLayout) findViewById(R.id.ll_previous);

        iv_paid.setOnClickListener(this);
        iv_partiallyPaid.setOnClickListener(this);
        iv_none.setOnClickListener(this);
        iv_unPaid.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
        ll_next.setOnClickListener(this);
        ll_previous.setOnClickListener(this);


        switch_useIcon.setOnCheckedChangeListener(this);
        switch_BillDate.setOnCheckedChangeListener(this);
        switch_dueDate.setOnCheckedChangeListener(this);
        switch_useLocation.setOnCheckedChangeListener(this);
        switch_roundOff.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_none:
                setPaid_UnPaid(iv_none, 0, new ImageView[]{iv_paid, iv_unPaid, iv_partiallyPaid});
                setLinePaid(new ImageView[]{line1, line2, line3}, 0, new ImageView[]{});

                break;
            case R.id.iv_unPaid:
                setPaid_UnPaid(iv_unPaid, 1, new ImageView[]{iv_paid, iv_none, iv_partiallyPaid});
                setLinePaid(new ImageView[]{line2, line3}, 1, new ImageView[]{line1});

                break;
            case R.id.iv_partiallyPaid:
                setPaid_UnPaid(iv_partiallyPaid, 2, new ImageView[]{iv_paid, iv_none, iv_unPaid});
                setLinePaid(new ImageView[]{line3}, 2, new ImageView[]{line1, line2});

                break;
            case R.id.iv_paid:
                setPaid_UnPaid(iv_paid, 3, new ImageView[]{iv_unPaid, iv_none, iv_partiallyPaid});
                setLinePaid(new ImageView[]{}, 3, new ImageView[]{line1, line2, line3});
                break;

            case R.id.ll_next:
                syncInvoiceData();
                break;
            case R.id.ll_previous:
                onBackPressed();
                break;
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
        }
    }

    int[] dotPay = {R.drawable.paid_circle_white, R.drawable.paid_circle_red,
            R.drawable.paid_circle_lightgreen, R.drawable.paid_circle_green};
    int[] lineColor = {R.color.white, R.color.red, R.color.color_18, R.color.green};

    public void setPaid_UnPaid(ImageView dot, int position, ImageView[] imageViews) {
        dot.setImageDrawable(ContextCompat.getDrawable(this, dotPay[position]));
        for (ImageView dotImage : imageViews) {
            dotImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.paid_circle_white));
        }
    }

    public void setLinePaid(ImageView[] dotImages, int position, ImageView[] imageViews) {
        for (ImageView dotImage : dotImages) {
            dotImage.setBackgroundColor(getResources().getColor(R.color.white));


        }
        for (ImageView dotImage : imageViews) {
            dotImage.setBackgroundColor(getResources().getColor(lineColor[position]));

        }
    }
    public void setSwitchesValue()
    {
        switches=new HashMap<>();
        switches.put(Constants.SHOW_BUSINESS_ICON,false);
        switches.put(Constants.SHOW_BUSINESS_LOCATION,false);
        switches.put(Constants.SHOW_BILL_DATE,false);
        switches.put(Constants.SHOW_DUE_DATE,false);
        switches.put(Constants.ROUND_OFF,false);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId())
        {
            case R.id.switch_useIcon:
                if(b)
                     switches.put(Constants.SHOW_BUSINESS_ICON,true);
                else switches.put(Constants.SHOW_BUSINESS_ICON,false);
                break;
            case R.id.switch_useLocation:
                if(b)
                     switches.put(Constants.SHOW_BUSINESS_LOCATION,true);
                else switches.put(Constants.SHOW_BUSINESS_LOCATION,false);
                break;
            case R.id.switch_BillDate:
                if(b)
                     switches.put(Constants.SHOW_BILL_DATE,true);
                else switches.put(Constants.SHOW_BILL_DATE,false);

                break;
            case R.id.switch_dueDate:
                if(b)
                    switches.put(Constants.SHOW_DUE_DATE,true);
                else switches.put(Constants.SHOW_DUE_DATE,false);
                break;
                case R.id.switch_roundOff:
                if(b)
                     switches.put(Constants.ROUND_OFF,true);
                else switches.put(Constants.ROUND_OFF,false);
                break;
        }
    }

    public void syncInvoiceData()
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Generating invoice");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        ApiServices apiServices=ApiClient.getClient().create(ApiServices.class);
        HashMap<String,String> param=new HashMap<>();
        param=getParams();
        Call<InvoiceModel> call=apiServices.postInvoiceData(param);
        call.enqueue(new Callback<InvoiceModel>() {
          @Override
          public void onResponse(Call<InvoiceModel> call, Response<InvoiceModel> response) {
              progressDialog.dismiss();

              if(response.isSuccessful())
              {
                  if(response.body().getData().getResultCode().equals("1"))
                  {
                      Intent intent = new Intent(BillGenerateActivity.this, InvoiceActivity.class);

                      intent.putExtra(Constants.PRODUCT_DETAIL,(Serializable)itemDetailsList);
                      intent.putExtra(Constants.BUYER_INFO,(Serializable)buyerInfoModel);
                      intent.putExtra(Constants.ROUND_OFF_SCREEN_DATA,switches);
                      intent.putExtra(Constants.BILL_CONFIRMATION_CODE,response.body().getData().getOrderToken());
                      intent.putExtra(Constants.BILL_ORDER_ID,response.body().getData().getOrderId());
                      intent.putExtra(Constants.TOTAL_AMOUNT, totalAmount);
                      startActivityForResult(intent,Constants.FINISH);

                  }
              }
          }

          @Override
          public void onFailure(Call<InvoiceModel> call, Throwable t) {
              progressDialog.dismiss();
              Toast.makeText(BillGenerateActivity.this, "Check internet connection", Toast.LENGTH_SHORT).show();
          }
      });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BasicUtill.sendCancelIntent(BillGenerateActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if(resultCode==RESULT_OK)
         {
             if(requestCode==Constants.FINISH)
                 BasicUtill.sendIntent(BillGenerateActivity.this);
         }

    }

    public HashMap<String,String> getParams()
    {
        HashMap<String,String> param=new HashMap<>();
        param.put("encrypt_key","sdjhfu");
        param.put("b_user_id",preference.getUserId());
        param.put("b_id",String.valueOf(preference.getBusinessId()));
        param.put("c_user_id",buyerInfoModel.getBuyerId());
        param.put("buyer_mobile",buyerInfoModel.getBuyerMobile());
        param.put("buyer_name",buyerInfoModel.getBuyerName());
        param.put("address_1",buyerInfoModel.getBuyerAddressLine1());
        param.put("address_2",buyerInfoModel.getBuyerAddressLine2());
        param.put("city",buyerInfoModel.getBuyerCity());
        param.put("pincode",buyerInfoModel.getBuyerPincode());
        param.put("state",buyerInfoModel.getBuyerState());
        param.put("bill_date",buyerInfoModel.getBillDate());
        param.put("bill_due_date",buyerInfoModel.getDueDate());
        param.put("sub_total", String.valueOf(totalAmount));
        param.put("total",String.valueOf(totalAmount));
        if(switches.get(Constants.ROUND_OFF))
        param.put("show_rounding_off","1");
        else  param.put("show_rounding_off","0");
        if(switches.get(Constants.SHOW_BUSINESS_ICON))
        param.put("use_icon","1");
        else param.put("use_icon","0");
        if(switches.get(Constants.SHOW_BUSINESS_LOCATION))
        param.put("use_location","1");
        else param.put("use_location","0");
        if(switches.get(Constants.SHOW_BILL_DATE))
        param.put("show_bill_date","1");
        else  param.put("show_bill_date","0");
        if(switches.get(Constants.SHOW_DUE_DATE))
        param.put("show_bill_due_date","1");
        else param.put("show_bill_due_date","0");

        for (int i=0;i<itemDetailsList.size();i++)
        {
            param.put("products["+i+"][product_id]",itemDetailsList.get(i).getItemId());
            param.put("products["+i+"][product_name]",itemDetailsList.get(i).getItemName());
            param.put("products["+i+"][product_description]",itemDetailsList.get(i).getDiscription());
            param.put("products["+i+"][product_qty]",itemDetailsList.get(i).getQty());
            param.put("products["+i+"][product_unit]",itemDetailsList.get(i).getUnit());
            param.put("products["+i+"][product_price]",itemDetailsList.get(i).getProductPrice());
            param.put("products["+i+"][discount]",itemDetailsList.get(i).getDiscountPercentage());
            param.put("products["+i+"][discount_price]",itemDetailsList.get(i).getDiscount());
            param.put("products["+i+"][cgst]",itemDetailsList.get(i).getCgst());
            param.put("products["+i+"][cgst_price]","");
            param.put("products["+i+"][sgst]",itemDetailsList.get(i).getSgst());
            param.put("products["+i+"][sgst_price]","");
            param.put("products["+i+"][tax_incusive]",String.valueOf(itemDetailsList.get(i).getTaxinclusive()));
            param.put("products["+i+"][total_price]",itemDetailsList.get(i).getTotalPrice());
        }
          return param;
    }
}
