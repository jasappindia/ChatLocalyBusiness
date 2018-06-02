package com.chatlocalybusiness.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicommons.commons.image.ImageCache;
import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.InvoiceOrderDetailModel;
import com.chatlocalybusiness.apiModel.ResponseModel;
import com.chatlocalybusiness.apiModel.UpdateOrderStatusModel;
import com.chatlocalybusiness.apiModel.InvoiceOrderListModel;
import com.chatlocalybusiness.chat.ApplozicBridge;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by windows on 4/4/2018.
 */

public class InvoiceDetailShowActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv_bill;
    private AdapterForBillShow adapterForBillShow;
    private LinearLayoutManager layoutManager;
    private ImageView iv_businessIcon;
    private TextView tv_businessName, tv_buyerName, tv_contact, tv_billNo, tv_billType,
            tv_BillDate, tv_dueDate, tv_finalAmount, tv_sendBill;
    LinearLayout ll_billDate, ll_dueDate, ll_confirmation;
    private EditText et_confirmation;
    double totalAmount = 0;
    ChatBusinessSharedPreference preference;
    RelativeLayout rl_bill;
    InvoiceOrderListModel.OrderList order;
    String orderid;
    private ImageCache imageCache;
    private ProgressBar progressBar;
    private RelativeLayout rl_chat_bill;
    private ImageView iv_arrowBack, iv_deleteIcon, iv_resendIcon, iv_chatIcon;
    private TextView tv_paymentStatus, tv_billNumber, tv_billTotal, tv_billDate, tv_paidate, tv_customerName;
    private RecyclerView rv_sentList;
    private LinearLayoutManager linearLayoutManager;
    private InvoiceSentListAdapter sentListAdapter;
    private AlertDialog alertdialog;
    public static int billDelete = 0;
    private TextView tv_confirmation;
    private TextView tv_submit;
    public static int paymentStatus = 0;

    @Override
    protected void onResume() {
        super.onResume();

        ViewGroup rl_main = (ViewGroup) findViewById(R.id.rl_main);
        new BasicUtill().CheckStatus(InvoiceDetailShowActivity.this, 0, rl_main);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billshow);
        init();
        getValues();


    }

    private void getValues() {
        if (getIntent().getExtras() != null) {
            order = (InvoiceOrderListModel.OrderList) getIntent().getExtras().getSerializable(Constants.ORDER_DETAILS);
            if (order != null) {

                setRv_bill();
                setBillDetails(order);
                setswitches();
                setInvoiceRecyclerView(order.getSendOrders());
                setBuyerINfo();
            }
        }

    }

    private void init() {
        preference = new ChatBusinessSharedPreference(this);
        ll_confirmation = (LinearLayout) findViewById(R.id.ll_confirmation);
        ll_billDate = (LinearLayout) findViewById(R.id.ll_billDate);
        ll_dueDate = (LinearLayout) findViewById(R.id.ll_dueDate);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        iv_businessIcon = (ImageView) findViewById(R.id.iv_businessIcon);
        tv_businessName = (TextView) findViewById(R.id.tv_businessName);
        tv_buyerName = (TextView) findViewById(R.id.tv_buyerName);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        tv_billNo = (TextView) findViewById(R.id.tv_billNo);
        tv_billType = (TextView) findViewById(R.id.tv_billType);
        tv_BillDate = (TextView) findViewById(R.id.tv_BillDate);
        tv_dueDate = (TextView) findViewById(R.id.tv_dueDate);
        tv_finalAmount = (TextView) findViewById(R.id.tv_finalAmount);
        tv_confirmation = (TextView) findViewById(R.id.tv_confirmation);
        et_confirmation = (EditText) findViewById(R.id.et_confirmation);
        rl_bill = (RelativeLayout) findViewById(R.id.rl_bill);
        tv_submit = (TextView) findViewById(R.id.tv_submit);

        rv_sentList = (RecyclerView) findViewById(R.id.rv_sentList);
        rl_chat_bill = (RelativeLayout) findViewById(R.id.rl_chat_bill);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        iv_deleteIcon = (ImageView) findViewById(R.id.iv_deleteIcon);
        iv_resendIcon = (ImageView) findViewById(R.id.iv_resendIcon);
        iv_chatIcon = (ImageView) findViewById(R.id.iv_chatIcon);
        tv_paymentStatus = (TextView) findViewById(R.id.tv_paymentStatus);
        tv_billNumber = (TextView) findViewById(R.id.tv_billNumber);
        tv_billTotal = (TextView) findViewById(R.id.tv_billTotal);
        tv_billDate = (TextView) findViewById(R.id.tv_billDate);
        tv_paidate = (TextView) findViewById(R.id.tv_paidate);
        tv_customerName = (TextView) findViewById(R.id.tv_customerName);

        iv_resendIcon.setOnClickListener(this);
        iv_deleteIcon.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        iv_chatIcon.setOnClickListener(this);

//        BasicUtill.getImageUri(InvoiceActivity.this, BasicUtill.getBitmapFromView(rl_bill));
    }

    public void setswitches() {
        if (order.getShowBillDate().equals("1")) {
            ll_billDate.setVisibility(View.VISIBLE);
//            tv_BillDate.setText(order.getBillDate());
        } else ll_billDate.setVisibility(View.GONE);

        if (order.getShowBillDueDate().equals("1")) {
            ll_dueDate.setVisibility(View.VISIBLE);
//            tv_dueDate.setText(order.getBillDueDate());
        } else ll_dueDate.setVisibility(View.GONE);

        if (order.getUseIcon().equals("1")) {
            iv_businessIcon.setVisibility(View.VISIBLE);
            Glide.with(InvoiceDetailShowActivity.this).load(order.getBusinessLogo()).into(iv_businessIcon);
        } else iv_businessIcon.setVisibility(View.GONE);



/*
        if (order.getUseLocation().equals("1")) {

        } else
*/

    }


    public void shareImage(View viewGroup, String confirmationCode, int billOrderID) {

        viewGroup.setDrawingCacheEnabled(true);
        Bitmap bitmap = BasicUtill.getBitmapFromView(viewGroup);
//        Bitmap bitmap = viewGroup.getDrawingCache();

        File cacha = this.getApplicationContext().getExternalCacheDir();
        File sharefile = new File(cacha, "share.png");
        try {
            FileOutputStream out = new FileOutputStream(sharefile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        viewGroup.setDrawingCacheEnabled(false);
        //now send it out to share
        String messgae = "Please click to see bill in detail";
//        BasicUtill.sendIntent(this);
        ApplozicBridge.sendCustomMessage(InvoiceDetailShowActivity.this, messgae, Integer.parseInt(order.getApplozicGroupId()), sharefile.getAbsolutePath(), confirmationCode, String.valueOf(billOrderID));
//

    }

    public void sendApi() {

        final ProgressDialog dialog = Utill.showloader(this);
        dialog.show();
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
//        encrypt_key=dfg&b_user_id=2&b_id=2&order_id=1&c_user_id=1
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "sjfh");
        param.put("b_user_id", preference.getUserId());
        param.put("b_id", String.valueOf(preference.getBusinessId()));
        param.put("order_id", String.valueOf(order.getOrderId()));
        param.put("c_user_id", order.getCUserId());

        Call<ResponseModel> call = apiServices.sendOrderApi(param);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        paymentStatus = 1;
                        getInvoiceOrder();
                        shareImage(rl_bill, order.getOrderToken(), Integer.parseInt(order.getOrderId()));
                        Toast.makeText(InvoiceDetailShowActivity.this, "Bill sent successfully", Toast.LENGTH_SHORT).show();

                    }
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(InvoiceDetailShowActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void setBuyerINfo() {

        tv_businessName.setText(order.getBusinessName());
        tv_buyerName.setText(order.getBuyerName());
        tv_contact.setText(order.getBuyerMobile());
        tv_billNo.setText(order.getOrderId());
        tv_finalAmount.setText("Total: Rs " + order.getTotal());
        tv_BillDate.setText(BasicUtill.getDateFromString(order.getBillDate()));
        tv_dueDate.setText(BasicUtill.getDateFromString(order.getBillDueDate()));

        if (order.getOrderStatus().equalsIgnoreCase("paid"))
            iv_deleteIcon.setVisibility(View.GONE);
        else iv_deleteIcon.setVisibility(View.VISIBLE);
    }

    public void setRv_bill() {
        rv_bill = (RecyclerView) findViewById(R.id.rv_bill);
        adapterForBillShow = new AdapterForBillShow(InvoiceDetailShowActivity.this, order);
        layoutManager = new LinearLayoutManager(InvoiceDetailShowActivity.this);
        rv_bill.setLayoutManager(layoutManager);
        rv_bill.setAdapter(adapterForBillShow);

    }

    public void setInvoiceRecyclerView(List<InvoiceOrderListModel.SendOrder> sendOrders) {
        linearLayoutManager = new LinearLayoutManager(this);
        sentListAdapter = new InvoiceSentListAdapter(this, sendOrders);
        rv_sentList.setAdapter(sentListAdapter);
        rv_sentList.setLayoutManager(linearLayoutManager);


    }

    public void setBillDetails(InvoiceOrderListModel.OrderList orderDetail) {
        tv_customerName.setText(orderDetail.getCFullName());
        tv_paymentStatus.setText("Payment Status : " + orderDetail.getOrderStatus());
        tv_billNumber.setText("Bill No : " + orderDetail.getOrderId());
        tv_billTotal.setText("Bill Total : Rs " + orderDetail.getTotal());
        tv_billDate.setText("Bill Date : " + orderDetail.getBillDate());
        if (orderDetail.getOrderStatus().equalsIgnoreCase("Paid")) {
            tv_paidate.setVisibility(View.VISIBLE);
            ll_confirmation.setVisibility(View.GONE);
            tv_paidate.setText("Paid Date : " + orderDetail.getPaidDate());
        } else tv_paidate.setVisibility(View.GONE);

        if (order.getOrderStatus().equalsIgnoreCase("Unpaid") && order.getConfirmationCodeStatus().equalsIgnoreCase("1")) {
            et_confirmation.setText(order.getOrderToken());
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.tv_submit:
                if (et_confirmation.getText().toString() != null)
                    sendConfirmationId();
                break;
            case R.id.iv_chatIcon:
                ApplozicBridge.launchIndividualGroupChat(this, order.getApplozicGroupId(),
                        preference.getUserId(), order.getCFullName(), "0C" + order.getCUserId(),
                        order.getApplozicGroupId());
                break;
            case R.id.iv_resendIcon:
                if (order.getBillExpired().equalsIgnoreCase("no"))
                    resendBill("Do you want to send the bill again !", "Yes", "Cancel");
                else Toast.makeText(this, "Expired bill can't be sent", Toast.LENGTH_SHORT).show();

                break;
            case R.id.iv_deleteIcon:
                if (order.getOrderStatus().equalsIgnoreCase("unpaid"))
                    resendBill("Do you want to delete the bill !", "Yes", "Cancel");
                else Toast.makeText(this, "Paid bill can't be deleted", Toast.LENGTH_SHORT).show();
                break;

        }
    }


  /*  public void resendBill(final String title)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_logout, null);
        builder.setView(dialogView);

        TextView tv_alertHeading = (TextView) dialogView.findViewById(R.id.tv_alertHeading);
        TextView tv_logout = (TextView) dialogView.findViewById(R.id.tv_logout);
        TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
        tv_alertHeading.setText(title);

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.equalsIgnoreCase("Do you want to delete the bill !"))
                    deleteInvoiceApi();
                else sendApi();

            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog.dismiss();

            }
        });

        alertdialog = builder.create();
        alertdialog.show();
        alertdialog.setCancelable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        }

    }
*/

    public void resendBill(final String title, String positive, String negative) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(title).setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (title.equalsIgnoreCase("Do you want to delete the bill !"))
                    deleteInvoiceApi();
                else sendApi();

            }
        }).setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertdialog.dismiss();
            }
        });
        alertdialog = builder.create();
        alertdialog.show();
        alertdialog.setCancelable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        }
    }

    public void getInvoiceOrder() {

        final ProgressDialog dialog = Utill.showloader(this);
        dialog.show();
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "jfhvb");
        param.put("b_user_id", preference.getUserId());
        param.put("order_id", order.getOrderId());
        Call<InvoiceOrderDetailModel> call = apiServices.getInvoiceOderDetail(param);
        call.enqueue(new Callback<InvoiceOrderDetailModel>() {
            @Override
            public void onResponse(Call<InvoiceOrderDetailModel> call, Response<InvoiceOrderDetailModel> response) {
//                progressBar.setVisibility(View.GONE);
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        rl_chat_bill.setVisibility(View.VISIBLE);
                        InvoiceOrderDetailModel.OrderDetail orderDetail = response.body().getData().getOrderDetail();

                        List<InvoiceOrderListModel.SendOrder> sendOrders = new ArrayList<>();
                        InvoiceOrderListModel invoiceOrderListModel = new InvoiceOrderListModel();
                        InvoiceOrderListModel.SendOrder sendBy;
                        for (int i = 0; i < orderDetail.getSendOrders().size(); i++) {
                             sendBy = invoiceOrderListModel.new SendOrder();
                            sendBy.setSendBy(orderDetail.getSendOrders().get(i).getSendBy());
                            sendOrders.add(sendBy);
                        }

                        setRv_bill();
                        setBillDetails(order);
                        setswitches();
                        setInvoiceRecyclerView(sendOrders);
                        setBuyerINfo();

                    }
                }
            }

            @Override
            public void onFailure(Call<InvoiceOrderDetailModel> call, Throwable t) {
                dialog.dismiss();
//                progressBar.setVisibility(View.GONE);
                Toast.makeText(InvoiceDetailShowActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class AdapterForBillShow extends RecyclerView.Adapter<AdapterForBillShow.MyHolder> {
        InvoiceOrderListModel.OrderList order;
        Context context;

        public AdapterForBillShow(Context context, InvoiceOrderListModel.OrderList order) {
            this.context = context;
            this.order = order;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_invoice, parent, false);
            return new AdapterForBillShow.MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.tv_itemName.setText(order.getProducts().get(position).getProductName());
            holder.tv_Rate.setText((order.getProducts().get(position).getProductPrice()));
            holder.tv_discount.setText((order.getProducts().get(position).getDiscount()));
            holder.tv_qty.setText((order.getProducts().get(position).getProductQty()));
            holder.tv_amount.setText((order.getProducts().get(position).getTotalPrice()));
        }

        @Override
        public int getItemCount() {
            if (order.getProducts() != null)
                return order.getProducts().size();
            else
                return 0;
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            private TextView tv_itemName, tv_Rate, tv_discount, tv_qty, tv_amount;

            public MyHolder(View itemView) {
                super(itemView);
                tv_itemName = (TextView) itemView.findViewById(R.id.tv_itemName);
                tv_Rate = (TextView) itemView.findViewById(R.id.tv_Rate);
                tv_discount = (TextView) itemView.findViewById(R.id.tv_discount);
                tv_qty = (TextView) itemView.findViewById(R.id.tv_qty);
                tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            }
        }
    }

    private class InvoiceSentListAdapter extends RecyclerView.Adapter<InvoiceSentListAdapter.MyViewHolder> {
        Context context;
        List<InvoiceOrderListModel.SendOrder> sendOrders;

        public InvoiceSentListAdapter(Context context, List<InvoiceOrderListModel.SendOrder> sendOrders) {
            this.context = context;
            this.sendOrders = sendOrders;

        }

        @Override
        public InvoiceSentListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_sentinvoice, parent, false);
            return new InvoiceSentListAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(InvoiceSentListAdapter.MyViewHolder holder, int position) {
            holder.tv_sentOn.setText(sendOrders.get(position).getSendBy());
        }

        @Override
        public int getItemCount() {
            if (sendOrders != null)
                if (sendOrders.size() > 0)
                    return sendOrders.size();
                else return 0;
            else return 0;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_sentOn;

            public MyViewHolder(View itemView) {
                super(itemView);

                tv_sentOn = (TextView) itemView.findViewById(R.id.tv_sentOn);
            }
        }
    }

    private void deleteInvoiceApi() {
        progressBar.setVisibility(View.VISIBLE);
        //encrypt_key=df&order_id=2&b_user_id=2
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "jhbk");
        param.put("order_id", order.getOrderId());
        param.put("b_user_id", preference.getUserId());
        Call<ResponseModel> call = apiServices.deleteOrder(param);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        billDelete = 1;
                        finish();
                    } else
                        Toast.makeText(InvoiceDetailShowActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InvoiceDetailShowActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void sendConfirmationId() {
        progressBar.setVisibility(View.VISIBLE);
        //update_order_detail?encrypt_key=fgsdfd&order_status=paid&b_user_id=2&order_token=AJ9JLY2XN7W6NXD&order_id=30
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "jhbk");
        param.put("order_id", order.getOrderId());
        param.put("b_user_id", preference.getUserId());
        param.put("order_status", "paid");
        param.put("order_token", et_confirmation.getText().toString());
        Call<UpdateOrderStatusModel> call = apiServices.sendConfirmationId(param);
        call.enqueue(new Callback<UpdateOrderStatusModel>() {
            @Override
            public void onResponse(Call<UpdateOrderStatusModel> call, Response<UpdateOrderStatusModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        Toast.makeText(InvoiceDetailShowActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                        tv_paymentStatus.setText("Payment Status : Paid");
                        tv_paidate.setVisibility(View.VISIBLE);
                        tv_paidate.setText("Paid Date : " + response.body().getData().getOrderDetail().getPaidDate());
                        ll_confirmation.setVisibility(View.GONE);
                        paymentStatus = 1;
                    } else
                        Toast.makeText(InvoiceDetailShowActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateOrderStatusModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InvoiceDetailShowActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
