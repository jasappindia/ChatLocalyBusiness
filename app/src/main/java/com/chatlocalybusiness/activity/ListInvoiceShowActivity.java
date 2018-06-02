package com.chatlocalybusiness.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.AdapterForBills;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.InvoiceOrderListModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Utill;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shiv on 12/19/2017.
 */
public class ListInvoiceShowActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onResume() {
        super.onResume();
        if(InvoiceDetailShowActivity.billDelete==1|| InvoiceDetailShowActivity.paymentStatus==1){
            getOrderList();
        }
        else
        {           ViewGroup rl_main=(ViewGroup)findViewById(R.id.rl_main);
            new BasicUtill().CheckStatus(ListInvoiceShowActivity.this,0,rl_main);

        }
    }
    private RecyclerView rv_bills;
    private LinearLayoutManager layoutManager;
    private AdapterForBills adapterForBills;
    private LinearLayout ll_filter,ll_sort;
    private ImageView iv_arrowBack;
    private TextView tv_filter,tv_sort;
    private Dialog dialog;
    private ProgressBar progressBar;
    private LinearLayout ll_billLayout;
    private ChatBusinessSharedPreference preference;
    RelativeLayout rl_noInternet;
    TextView tv_tryAgain;
    private ImageView iv_noInternet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
            init();
            getOrderList();
    }


    public void init()
    {
        preference=new ChatBusinessSharedPreference(this);
        iv_arrowBack=(ImageView)findViewById(R.id.iv_arrowBack);
        ll_filter=(LinearLayout)findViewById(R.id.ll_filter);
        ll_sort=(LinearLayout)findViewById(R.id.ll_sort);
        tv_filter=(TextView) findViewById(R.id.tv_filter);
        tv_sort=(TextView) findViewById(R.id.tv_sort);
        ll_billLayout=(LinearLayout)findViewById(R.id.ll_billLayout);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        rl_noInternet=(RelativeLayout)findViewById(R.id.rl_noInternet);
        tv_tryAgain=(TextView)findViewById(R.id.tv_tryAgain);
        iv_noInternet=(ImageView)findViewById(R.id.iv_noInternet);
        tv_tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOrderList();
            }
        });

        rv_bills=(RecyclerView)findViewById(R.id.rv_bills);
        layoutManager=new LinearLayoutManager(this);

        ll_filter.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
        ll_sort.setOnClickListener(this);


    }
    public void setRecycleView(List<InvoiceOrderListModel.OrderList> orderList)
    {

           adapterForBills=new AdapterForBills( ListInvoiceShowActivity.this,orderList);
           rv_bills.setLayoutManager(layoutManager);
           rv_bills.setAdapter(adapterForBills);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.ll_filter:
                      dialog =filterDialog();

                break;
            case R.id.ll_sort:
                   dialog=  sortDialog();
                break;
            case R.id.iv_arrowBack:
                onBackPressed();
                break;

        }
    }



   public Dialog sortDialog()
   {
       AlertDialog.Builder builder=new AlertDialog.Builder(this);

       final String[] array={"Recent First","Highest Total","Lowest Total"};
        builder.setTitle("Sort").setCancelable(true);
       int position=0;

       if(tv_sort.getText().toString().trim().equalsIgnoreCase(array[0]))
           position=0;
       else if(tv_sort.getText().toString().trim().equalsIgnoreCase(array[1]))
           position=1;

       else if(tv_sort.getText().toString().trim().equalsIgnoreCase(array[2]))
           position=2;

       builder.setSingleChoiceItems(array,position, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               dialog.dismiss();
               tv_sort.setText(array[i]);
               getOrderList();
           }
       });
       Dialog dialog=builder.create();
       dialog.show();
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
       }
       return dialog;
   }
   public Dialog filterDialog()
   {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final String[] array={"All Bills","Unpaid","Paid"};
        builder.setTitle("Filter").setCancelable(true);
       int position=0;

       if(tv_filter.getText().toString().trim().equalsIgnoreCase(array[0]))
           position=0;
       else if(tv_filter.getText().toString().trim().equalsIgnoreCase(array[1]))
           position=1;

       else if(tv_filter.getText().toString().trim().equalsIgnoreCase(array[2]))
           position=2;

        builder.setSingleChoiceItems(array,position, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {

               dialog.dismiss();
               tv_filter.setText(array[i]);
               getOrderList();

           }
       });
       Dialog dialog=builder.create();
       dialog.show();
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
       }
       return dialog;
   }

   public void getOrderList()
   { final ProgressDialog dialog = Utill.showloader(this);
       dialog.show();
//       encrypt_key=fsdf&b_user_id=2&b_id=2&current_page=1
       ApiServices apiServices= ApiClient.getClient().create(ApiServices.class);
       HashMap<String ,String> param=new HashMap<>();
       param.put("encrypt_key","shvfk");
       param.put("b_user_id",preference.getUserId());
       param.put("b_id",String.valueOf(preference.getBusinessId()));
       param.put("current_page","1");
       param.put("sort_by",tv_sort.getText().toString());
       param.put("filter_by",tv_filter.getText().toString());

       Call<InvoiceOrderListModel> call=apiServices.getInvoiceOderLst(param);
       call.enqueue(new Callback<InvoiceOrderListModel>() {
           @Override
           public void onResponse(Call<InvoiceOrderListModel> call, Response<InvoiceOrderListModel> response) {

               rl_noInternet.setVisibility(View.GONE);

               if(response.isSuccessful())
                   {
                       if(response.body().getData().getResultCode().equals("1"))
                       {
                           ll_billLayout.setVisibility(View.VISIBLE);
                           List<InvoiceOrderListModel.OrderList> orderList=response.body().getData().getOrderList();
                           setRecycleView(orderList);
                           InvoiceDetailShowActivity.billDelete=0;
                           InvoiceDetailShowActivity.paymentStatus=0;

                           if(orderList!=null)
                               if(orderList.size()<1)
                               {
                                   rl_noInternet.setVisibility(View.VISIBLE);
                                   iv_noInternet.setImageResource(R.drawable.no_bills);
                               }
                       }
                       else
                           Toast.makeText(ListInvoiceShowActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                   }
               dialog.dismiss();

           }

           @Override
           public void onFailure(Call<InvoiceOrderListModel> call, Throwable t) {
               dialog.dismiss();
               rl_noInternet.setVisibility(View.VISIBLE);

               Toast.makeText(ListInvoiceShowActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
           }
       });
   }


}
