package com.chatlocalybusiness.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.AdapterForAllProducts;
import com.chatlocalybusiness.adapter.AdapterForProductList;
import com.chatlocalybusiness.utill.BasicUtill;

/**
 * Created by windows on 1/18/2018.
 */

public class ProductListActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        ViewGroup rl_main=(ViewGroup)findViewById(R.id.rl_main);
        new BasicUtill().CheckStatus(ProductListActivity.this,0,rl_main);
    }
    private AdapterForProductList adapterForProductList;
    private LinearLayoutManager layoutManager;
    private RecyclerView rv_productList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_productlist);
        setRecycleView();
    }
    public void setRecycleView()
    {
        rv_productList=(RecyclerView)findViewById(R.id.rv_productList);
        layoutManager=new LinearLayoutManager(this);
        adapterForProductList=new AdapterForProductList(this);
        rv_productList.setLayoutManager(layoutManager);
        rv_productList.setAdapter(adapterForProductList);
    }
}
