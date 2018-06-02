package com.chatlocalybusiness.activity;

import android.content.Context;
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
import com.chatlocalybusiness.apiModel.InvoiceOrderListModel;
import com.chatlocalybusiness.apiModel.UpdateOrderStatusModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by windows on 3/30/2018.
 */

public class ChatInvoiceActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv_bill;
    private AdapterForBillShow adapterForBillShow;
    private LinearLayoutManager layoutManager;
    private ImageView iv_businessIcon;
    private TextView tv_businessName, tv_buyerName, tv_contact, tv_billNo, tv_billType,
            tv_BillDate, tv_dueDate, tv_finalAmount, tv_sendBill;
    LinearLayout ll_billDate, ll_dueDate;
    double totalAmount = 0;
    RelativeLayout rl_bill;
    ChatBusinessSharedPreference preference;
    String orderid;
    private ImageCache imageCache;
    private ProgressBar progressBar;
    private RelativeLayout rl_chat_bill;
    private ImageView preview,iv_arrowBack;
    private TextView tv_paymentStatus, tv_billNumber, tv_billTotal, tv_billDate, tv_paidate, tv_customerName;
    private RecyclerView rv_sentList;
    private LinearLayoutManager linearLayoutManager;
    private InvoiceSentListAdapter sentListAdapter;
    private LinearLayout ll_confirmation;
    private TextView tv_confirmation;
    private TextView tv_submit;
    private EditText et_confirmation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_invoice);
        preference = new ChatBusinessSharedPreference(this);

        init();
        if (getIntent().getExtras() != null) {
            orderid = getIntent().getExtras().getString(Constants.BILL_ORDER_ID);

            if (orderid != null)
                getInvoiceOrder();
        }

    }

    public void init() {
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
        rl_bill = (RelativeLayout) findViewById(R.id.rl_bill);
        tv_confirmation = (TextView) findViewById(R.id.tv_confirmation);
        et_confirmation = (EditText) findViewById(R.id.et_confirmation);
        tv_submit = (TextView) findViewById(R.id.tv_submit);



        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rv_sentList=(RecyclerView)findViewById(R.id.rv_sentList);
        rl_chat_bill = (RelativeLayout) findViewById(R.id.rl_chat_bill);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        tv_paymentStatus=(TextView)findViewById(R.id.tv_paymentStatus);
        tv_billNumber =(TextView)findViewById(R.id.tv_billNumber);
        tv_billTotal=(TextView)findViewById(R.id.tv_billTotal);
        tv_billDate=(TextView)findViewById(R.id.tv_billDate);
        tv_paidate=(TextView)findViewById(R.id.tv_paidate);
        tv_customerName =(TextView)findViewById(R.id.tv_customerName);

        iv_arrowBack.setOnClickListener(this);
        tv_submit.setOnClickListener(this);

    }
    public void setswitches(InvoiceOrderDetailModel.OrderDetail orderDetail) {
        if (orderDetail.getShowBillDate().equals("1")) {
            ll_billDate.setVisibility(View.VISIBLE);
            tv_BillDate.setText(orderDetail.getBillDate());
        } else ll_billDate.setVisibility(View.GONE);

        if (orderDetail.getShowBillDueDate().equals("1")) {
            ll_dueDate.setVisibility(View.VISIBLE);
            tv_dueDate.setText(orderDetail.getBillDueDate());
        } else ll_dueDate.setVisibility(View.GONE);

        if (orderDetail.getUseIcon().equals("1")) {
            iv_businessIcon.setVisibility(View.VISIBLE);
            Glide.with(ChatInvoiceActivity.this).load(orderDetail.getBusinessLogo()).into(iv_businessIcon);
        } else iv_businessIcon.setVisibility(View.GONE);
        tv_businessName.setText(orderDetail.getBusinessName());

        /*
        if (order.getUseLocation().equals("1")) {

        } else
*/

    }

    public void setBuyerINfo(InvoiceOrderDetailModel.OrderDetail orderDetail) {
        tv_buyerName.setText(orderDetail.getBuyerName());
        tv_contact.setText(orderDetail.getBuyerMobile());
        tv_billNo.setText(orderDetail.getOrderId());
        tv_finalAmount.setText("Total: Rs " + orderDetail.getTotal() );
        tv_BillDate.setText(BasicUtill.getDateFromString(orderDetail.getBillDate()));
        tv_dueDate.setText(BasicUtill.getDateFromString(orderDetail.getBillDueDate()));
    }

    public void setRv_bill(InvoiceOrderDetailModel.OrderDetail orderDetail) {
        rv_bill = (RecyclerView) findViewById(R.id.rv_bill);
        adapterForBillShow = new AdapterForBillShow(ChatInvoiceActivity.this, orderDetail);
        layoutManager = new LinearLayoutManager(ChatInvoiceActivity.this);
        rv_bill.setLayoutManager(layoutManager);
        rv_bill.setAdapter(adapterForBillShow);

    }

    public void setInvoiceRecyclerView(List<InvoiceOrderDetailModel.SendOrder> sendOrders) {
            linearLayoutManager=new LinearLayoutManager(this);
            sentListAdapter=new InvoiceSentListAdapter(this,sendOrders);
            rv_sentList.setAdapter(sentListAdapter);
            rv_sentList.setLayoutManager(linearLayoutManager);


    }
  public void setBillDetails(InvoiceOrderDetailModel.OrderDetail orderDetail)
  {
      tv_customerName.setText(orderDetail.getCFullName());
      tv_paymentStatus.setText("Payment Status : "+orderDetail.getOrderStatus());
      tv_billNumber.setText("Bill No : "+orderDetail.getOrderId());
      tv_billTotal.setText("Bill Total : Rs "+orderDetail.getTotal());
      tv_billDate.setText("Bill Date : "+orderDetail.getBillDate());
      if(orderDetail.getOrderStatus().equalsIgnoreCase("paid")) {
          tv_paidate.setVisibility(View.VISIBLE);
          ll_confirmation.setVisibility(View.GONE);
       tv_paidate.setText("Paid Date : "+orderDetail.getPaidDate());
      }
      else  tv_paidate.setVisibility(View.GONE);

      if(orderDetail.getOrderStatus().equalsIgnoreCase("unpaid")&&orderDetail.getConfirmationCodeStatus().equalsIgnoreCase("1")) {
          et_confirmation.setText(orderDetail.getOrderToken());

      }
  }
        public void getInvoiceOrder() {

        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "jfhvb");
        param.put("b_user_id", preference.getUserId());
        param.put("order_id", orderid);
        Call<InvoiceOrderDetailModel> call = apiServices.getInvoiceOderDetail(param);
        call.enqueue(new Callback<InvoiceOrderDetailModel>() {
            @Override
            public void onResponse(Call<InvoiceOrderDetailModel> call, Response<InvoiceOrderDetailModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        rl_chat_bill.setVisibility(View.VISIBLE);
                      InvoiceOrderDetailModel.OrderDetail orderDetail=response.body().getData().getOrderDetail();
                        setBillDetails(orderDetail);
                        setInvoiceRecyclerView(orderDetail.getSendOrders());
                        setswitches(orderDetail);
                        setBuyerINfo(orderDetail);
                        setRv_bill(orderDetail);

                    }
                }
            }

            @Override
            public void onFailure(Call<InvoiceOrderDetailModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ChatInvoiceActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sendConfirmationId()
    {
        progressBar.setVisibility(View.VISIBLE);
        //update_order_detail?encrypt_key=fgsdfd&order_status=paid&b_user_id=2&order_token=AJ9JLY2XN7W6NXD&order_id=30
        ApiServices apiServices= ApiClient.getClient().create(ApiServices.class);
        HashMap<String,String> param=new HashMap<>();
        param.put("encrypt_key","jhbk");
        param.put("order_id",orderid);
        param.put("b_user_id",preference.getUserId());
        param.put("order_status","paid");
        param.put("order_token",et_confirmation.getText().toString());
        Call<UpdateOrderStatusModel> call=apiServices.sendConfirmationId(param);
        call.enqueue(new Callback<UpdateOrderStatusModel>() {
            @Override
            public void onResponse(Call<UpdateOrderStatusModel> call, Response<UpdateOrderStatusModel> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful())
                {
                    if(response.body().getData().getResultCode().equalsIgnoreCase("1"))
                    {
                        Toast.makeText(ChatInvoiceActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                        tv_paymentStatus.setText("Payment Status : Paid");
                        tv_paidate.setVisibility(View.VISIBLE);
                        tv_paidate.setText("Paid Date : "+response.body().getData().getOrderDetail().getPaidDate());
                        ll_confirmation.setVisibility(View.GONE);
                    }
                    else Toast.makeText(ChatInvoiceActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateOrderStatusModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ChatInvoiceActivity.this,R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.tv_submit:
                sendConfirmationId();
        }
    }

    private class InvoiceSentListAdapter extends RecyclerView.Adapter<InvoiceSentListAdapter.MyViewHolder> {
        Context context;
        List<InvoiceOrderDetailModel.SendOrder> sendOrders;
        public InvoiceSentListAdapter(Context context, List<InvoiceOrderDetailModel.SendOrder> sendOrders) {
            this.context=context;
            this.sendOrders=sendOrders;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.card_sentinvoice,parent,false);
            return new InvoiceSentListAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
         holder.tv_sentOn.setText(sendOrders.get(position).getSendBy());
        }

        @Override
        public int getItemCount() {
            if(sendOrders!=null)
                if(sendOrders.size()>0)
            return sendOrders.size();
            else return 0;
            else return 0;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
         TextView tv_sentOn;
            public MyViewHolder(View itemView) {
                super(itemView);

                tv_sentOn=(TextView)itemView.findViewById(R.id.tv_sentOn);
            }
        }
    }

    private class AdapterForBillShow extends RecyclerView.Adapter<AdapterForBillShow.MyHolder> {
        InvoiceOrderDetailModel.OrderDetail order;
        Context context;

        public AdapterForBillShow(Context context, InvoiceOrderDetailModel.OrderDetail order) {
            this.context = context;
            this.order = order;
        }

        @Override
        public AdapterForBillShow.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_invoice, parent, false);
            return new AdapterForBillShow.MyHolder(view);
        }

        @Override
        public void onBindViewHolder(AdapterForBillShow.MyHolder holder, int position) {
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
}
