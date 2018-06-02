package com.chatlocalybusiness.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.ResponseModel;
import com.chatlocalybusiness.chat.ApplozicBridge;
import com.chatlocalybusiness.getterSetterModel.AddItemBillModel;
import com.chatlocalybusiness.getterSetterModel.BuyerInfoModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by windows on 2/22/2018.
 */

public class InvoiceActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        ViewGroup rl_main=(ViewGroup)findViewById(R.id.rl_main);
        new BasicUtill().CheckStatus(InvoiceActivity.this,0,rl_main);
    }

    private RecyclerView rv_bill;
    private AdapterForInvoice adapterForInvoice;
    private LinearLayoutManager layoutManager;
    private ImageView iv_businessIcon;
    private TextView tv_businessName,tv_buyerName,tv_contact,tv_billNo,tv_billType,
            tv_BillDate,tv_dueDate,tv_finalAmount,tv_sendBill;
    private LinearLayout ll_billDate,ll_dueDate;
    private List<AddItemBillModel.ItemDetails> itemDetailsList;
    private BuyerInfoModel buyerInfoModel;
    private ProgressBar progressBar;
    double totalAmount=0;
    HashMap<String,Boolean> switches;
    ChatBusinessSharedPreference preference;
    ImageView iv_arrowBack;
    RelativeLayout rl_bill;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        preference=new ChatBusinessSharedPreference(this);
      if(getIntent().getExtras()!=null){
        getValues();
        init();
        setRv_bill();}
    }
    public void getValues() {
        itemDetailsList = (List<AddItemBillModel.ItemDetails>) getIntent().getExtras().getSerializable(Constants.PRODUCT_DETAIL);
        buyerInfoModel = (BuyerInfoModel) getIntent().getExtras().getSerializable(Constants.BUYER_INFO);
        switches = (HashMap<String, Boolean>) getIntent().getExtras().get(Constants.ROUND_OFF_SCREEN_DATA);
        totalAmount=getIntent().getExtras().getDouble(Constants.TOTAL_AMOUNT);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BasicUtill.sendCancelIntent(InvoiceActivity.this);
    }
    public  void setswitches()
    {
      if(switches.get(Constants.SHOW_BILL_DATE))
          ll_billDate.setVisibility(View.VISIBLE);
        else ll_billDate.setVisibility(View.GONE);
        if(switches.get(Constants.SHOW_DUE_DATE))
            ll_dueDate.setVisibility(View.VISIBLE);
        else ll_dueDate.setVisibility(View.GONE);
        if(switches.get(Constants.SHOW_BUSINESS_ICON)){
            iv_businessIcon.setVisibility(View.VISIBLE);
           iv_businessIcon.setImageResource(R.drawable.logo);
        }
        else iv_businessIcon.setVisibility(View.GONE);
       /* if(switches.get(Constants.SHOW_BUSINESS_ICON))
            tv_dueDate.setVisibility(View.VISIBLE);
        else tv_dueDate.setVisibility(View.GONE);
*/

    }
    private void init() {
        ll_billDate=(LinearLayout)findViewById(R.id.ll_billDate);
        ll_dueDate=(LinearLayout)findViewById(R.id.ll_dueDate);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        iv_businessIcon=(ImageView)findViewById(R.id.iv_businessIcon);
        iv_arrowBack=(ImageView)findViewById(R.id.iv_arrowBack);
        tv_businessName=(TextView)findViewById(R.id.tv_businessName);
        tv_buyerName=(TextView)findViewById(R.id.tv_buyerName);
        tv_contact=(TextView)findViewById(R.id.tv_contact);
        tv_billNo=(TextView)findViewById(R.id.tv_billNo);
        tv_billType=(TextView)findViewById(R.id.tv_billType);
        tv_BillDate=(TextView)findViewById(R.id.tv_BillDate);
        tv_dueDate=(TextView)findViewById(R.id.tv_dueDate);
        tv_finalAmount=(TextView)findViewById(R.id.tv_finalAmount);
        tv_sendBill = (TextView) findViewById(R.id.tv_sendBill);
        rl_bill = (RelativeLayout) findViewById(R.id.rl_bill);


        iv_arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_sendBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preference.getSendBill().equalsIgnoreCase("1"))
                sendApi();
                else Toast.makeText(InvoiceActivity.this, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();
            }
        });
        setswitches();
        setBuyerINfo();

//        BasicUtill.getImageUri(InvoiceActivity.this, BasicUtill.getBitmapFromView(rl_bill));
    }


    public void shareImage(View viewGroup,String confirmationCode,int billOrderID) {

        viewGroup.setDrawingCacheEnabled(true);
       Bitmap bitmap= BasicUtill.getBitmapFromView(viewGroup);
//        Bitmap bitmap = viewGroup.getDrawingCache();

        File cacha = this.getApplicationContext().getExternalCacheDir();
        File sharefile = new File(cacha, "share.png");
        try {
            FileOutputStream out = new FileOutputStream(sharefile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();

        }
        viewGroup.setDrawingCacheEnabled(false);
     //now send it out to share
        int chatGroup=  Integer.parseInt(preference.getChatGroupId());
        String messgae="Please click to see bill in detail";
        BasicUtill.sendIntent(this);
        ApplozicBridge.sendCustomMessage(InvoiceActivity.this,messgae, chatGroup ,sharefile.getAbsolutePath(),confirmationCode,String.valueOf(billOrderID));
//        ApplozicBridge.launchIndividualGroupChat(InvoiceActivity.this,preference.getChatGroupId(),"","","0C"+preference.getChatClientId());

    }

    public void setBuyerINfo()
    {
        tv_buyerName.setText(buyerInfoModel.getBuyerName());
        tv_contact.setText(buyerInfoModel.getBuyerMobile());
        tv_finalAmount.setText("Total: "+ totalAmount );
        tv_BillDate.setText(buyerInfoModel.getBillDate());
        tv_dueDate.setText(buyerInfoModel.getDueDate());
        tv_billNo.setText(String.valueOf(getIntent().getExtras().getInt(Constants.BILL_ORDER_ID)));

        tv_businessName.setText(preference.getBusinessName());

    }

    public void setRv_bill()
    {
        rv_bill=(RecyclerView)findViewById(R.id.rv_bill);
        adapterForInvoice=new AdapterForInvoice(InvoiceActivity.this,itemDetailsList);
        layoutManager=new LinearLayoutManager(InvoiceActivity.this);
        rv_bill.setLayoutManager(layoutManager);
        rv_bill.setAdapter(adapterForInvoice);

    }

    private class AdapterForInvoice extends RecyclerView.Adapter<AdapterForInvoice.MyHolder> {
        Context context;
        List<AddItemBillModel.ItemDetails> itemDetailsList;
       public AdapterForInvoice(Context context, List<AddItemBillModel.ItemDetails> itemDetailsList)
       {
        this.context=context;
        this.itemDetailsList=itemDetailsList;
       }
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View view= LayoutInflater.from(context).inflate(R.layout.card_invoice,parent,false);

            return new AdapterForInvoice.MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
         holder.tv_itemName.setText(itemDetailsList.get(position).getItemName());
         holder.tv_Rate.setText(itemDetailsList.get(position).getRate());
         holder.tv_discount.setText(itemDetailsList.get(position).getDiscount());
         holder.tv_qty.setText(itemDetailsList.get(position).getQty());
         holder.tv_amount.setText(itemDetailsList.get(position).getTotalPrice());
        }

        @Override
        public int getItemCount() {
           if(itemDetailsList!=null)
            return itemDetailsList.size();
       else return 0;
       }

        public class MyHolder extends RecyclerView.ViewHolder {

            private TextView tv_itemName,tv_Rate,tv_discount,tv_qty,tv_amount;

            public MyHolder(View itemView) {
                super(itemView);
                tv_itemName=(TextView)itemView.findViewById(R.id.tv_itemName);
                tv_Rate=(TextView)itemView.findViewById(R.id.tv_Rate);
                tv_discount=(TextView)itemView.findViewById(R.id.tv_discount);
                tv_qty=(TextView)itemView.findViewById(R.id.tv_qty);
                tv_amount=(TextView)itemView.findViewById(R.id.tv_amount);
            }
        }
    }

    public void sendApi()
    {

        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices= ApiClient.getClient().create(ApiServices.class);
//        encrypt_key=dfg&b_user_id=2&b_id=2&order_id=1&c_user_id=1
        HashMap<String,String> param=new HashMap<>();
        param.put("encrypt_key","sjfh");
        param.put("b_user_id",preference.getUserId());
        param.put("b_id",String.valueOf(preference.getBusinessId()));
        param.put("order_id", String.valueOf(getIntent().getExtras().getInt(Constants.BILL_ORDER_ID)));
        param.put("c_user_id",preference.getChatClientId());

        Call<ResponseModel> call=apiServices.sendOrderApi(param);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if(response.isSuccessful())
                {
                    if(response.body().getData().getResultCode().equalsIgnoreCase("1"))
                    {
                        shareImage(rl_bill,getIntent().getExtras().getString(Constants.BILL_CONFIRMATION_CODE),getIntent().getExtras().getInt(Constants.BILL_ORDER_ID));
                    }
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InvoiceActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

            }
        });

    }
}
