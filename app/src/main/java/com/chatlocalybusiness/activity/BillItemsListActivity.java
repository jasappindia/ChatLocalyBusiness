package com.chatlocalybusiness.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.getterSetterModel.AddItemBillModel;
import com.chatlocalybusiness.apiModel.ProductListModel;
import com.chatlocalybusiness.getterSetterModel.BuyerInfoModel;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * Created by windows on 2/16/2018.
 */

public class BillItemsListActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewGroup rl_main;

    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(BillItemsListActivity.this,0,rl_main);
    }
    private RecyclerView rv_itemList;
    private LinearLayoutManager linearLayoutManager;
    private AdapterForBillITem adapterForBillITem;
    private TextView tv_additems,tv_finalAmount;
    private LinearLayout ll_next, ll_previous;
    private ImageView iv_arrowBack;
    private List<AddItemBillModel.ItemDetails> itemDetailsList;
    private List<ProductListModel.ProductList> productLists;
    private BuyerInfoModel buyerInfoModel;
    private String add_Edit;
    double totalAmount =0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_bill);
        rl_main=(ViewGroup)findViewById(R.id.rl_main);

        getValues();
        setRecycleView();
        init();
    }

    public void getValues() {
        itemDetailsList = (List<AddItemBillModel.ItemDetails>) getIntent().getExtras().getSerializable(Constants.PRODUCT_DETAIL);
        productLists = (List<ProductListModel.ProductList>) getIntent().getExtras().getSerializable(Constants.PRODUCT_SERIALIZABLE);
        buyerInfoModel = (BuyerInfoModel) getIntent().getExtras().getSerializable(Constants.BUYER_INFO);

    }
    @Override
    public void onBackPressed() {
        BuyerInfoActivity.buyerInfo=1;
        BasicUtill.sendIntent(BillItemsListActivity.this);
    }
    public void setRecycleView() {

        rv_itemList = (RecyclerView) findViewById(R.id.rv_itemList);
        linearLayoutManager = new LinearLayoutManager(this);
        adapterForBillITem = new AdapterForBillITem(this, itemDetailsList);
        rv_itemList.setLayoutManager(linearLayoutManager);
        rv_itemList.setAdapter(adapterForBillITem);

    }

    public void init() {
        tv_additems = (TextView) findViewById(R.id.tv_additems);
        ll_next = (LinearLayout) findViewById(R.id.ll_next);
        ll_previous = (LinearLayout) findViewById(R.id.ll_previous);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        tv_finalAmount = (TextView)findViewById(R.id.tv_finalAmount);

        iv_arrowBack.setOnClickListener(this);
        tv_additems.setOnClickListener(this);
        ll_next.setOnClickListener(this);
        ll_previous.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_next:
                Intent intent=new Intent(BillItemsListActivity.this,BillGenerateActivity.class);
//                Intent intent=new Intent(BillItemsListActivity.this,InvoiceActivity.class);
                intent.putExtra(Constants.PRODUCT_DETAIL,(Serializable)itemDetailsList);
                intent.putExtra(Constants.BUYER_INFO, (Serializable) buyerInfoModel);
                intent.putExtra(Constants.TOTAL_AMOUNT, totalAmount);

                startActivityForResult(intent,Constants.FINISH);
                break;
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.ll_previous:
                onBackPressed();
                break;
            case R.id.tv_additems:
                sendIntent("ADD_ITEM",-1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(requestCode==Constants.FINISH)
                BasicUtill.sendIntent(BillItemsListActivity.this);
        }
    }

    public void sendIntent(String add_Edit, int position)
        {
            Intent intent = new Intent(BillItemsListActivity.this, AddItemBillActivity.class);
            intent.putExtra(Constants.POSITION,position);
            intent.putExtra(Constants.ADD_EDIT_ITEM,add_Edit);
            intent.putExtra(Constants.PRODUCT_DETAIL, (Serializable) itemDetailsList);
            intent.putExtra(Constants.PRODUCT_SERIALIZABLE, (Serializable) productLists);
            intent.putExtra(Constants.BUYER_INFO, (Serializable) buyerInfoModel);
            startActivity(intent);
        }
    public class AdapterForBillITem extends RecyclerView.Adapter<AdapterForBillITem.MyHolder> {

        Context context;
        List<AddItemBillModel.ItemDetails> itemDetailsList;

        public AdapterForBillITem(Context context, List<AddItemBillModel.ItemDetails> itemDetailsList) {
            this.context = context;
            this.itemDetailsList = itemDetailsList;
        }

        @Override
        public AdapterForBillITem.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_itembill, parent, false);


            return new AdapterForBillITem.MyHolder(view);
        }

        @Override
        public void onBindViewHolder(AdapterForBillITem.MyHolder holder, int position) {
            holder.tv_item.setText(itemDetailsList.get(position).getItemName());
            holder.tv_price.setText(itemDetailsList.get(position).getRate());
            holder.tv_qty.setText(itemDetailsList.get(position).getQty());
            holder.tv_amount.setText(itemDetailsList.get(position).getTotalPrice());
            totalAmount = totalAmount +Double.parseDouble(itemDetailsList.get(position).getTotalPrice());
            if(position==(itemDetailsList.size()-1))
//            holder.tv_finalAmount.setText("Total: "+itemDetailsList.get(position).getMrp()+"/-");
            tv_finalAmount.setText("Total: "+ totalAmount );
//            holder.tv_totalTax.setText("Tax: "+itemDetailsList.get(position).getTaxTotal()+"/-");
        }
        @Override
        public int getItemCount() {
            return itemDetailsList.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tv_item, tv_price, tv_qty, tv_amount, tv_finalAmount,tv_totalTax;
            ImageView iv_cancel,iv_edit;

            public MyHolder(View itemView) {
                super(itemView);
//                tv_finalAmount = (TextView)((Activity)context).findViewById(R.id.tv_finalAmount);
                tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
                tv_qty = (TextView) itemView.findViewById(R.id.tv_qty);
                tv_price = (TextView) itemView.findViewById(R.id.tv_price);
                tv_item = (TextView) itemView.findViewById(R.id.tv_item);
                iv_edit=(ImageView)itemView.findViewById(R.id.iv_edit);
                iv_cancel=(ImageView)itemView.findViewById(R.id.iv_cancel);

                iv_cancel.setOnClickListener(this);
                iv_edit.setOnClickListener(this);

//                tv_totalTax = (TextView) itemView.findViewById(R.id.tv_totalTax);
            }


            @Override
            public void onClick(View view) {
                switch(view.getId())
                {
                    case R.id.iv_cancel :
                        itemDetailsList.remove(getAdapterPosition());
                        notifyDataSetChanged();
                        break;
                    case R.id.iv_edit :

                        sendIntent("EDIT_ITEM",getAdapterPosition());

                        break;

                }


            }
        }
    }
}
