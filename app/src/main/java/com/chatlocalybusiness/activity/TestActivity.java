package com.chatlocalybusiness.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.AdapterProducts;
import com.chatlocalybusiness.adapter.AdapterServices;
import com.chatlocalybusiness.utill.BasicUtill;

/**
 * Created by windows on 12/22/2017.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_location,rl_products,rl_services,rl_address;
    private RecyclerView rv_products,rv_services;
    private LinearLayoutManager layoutManagerservices,layoutManagerProducts;
    private AdapterServices adapterservices;
    private AdapterProducts adapterProducts;
    private ViewGroup rl_productContainer,rl_sericesContainer;
    private ViewGroup newViewProducts,newViewServices;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        rl_productContainer=(ViewGroup)findViewById(R.id.rl_productContainer);
        rl_sericesContainer=(ViewGroup)findViewById(R.id.rl_sericesContainer);
        rl_address=(RelativeLayout)findViewById(R.id.rl_address);
        rl_location=(RelativeLayout)findViewById(R.id.rl_location);
        rl_products=(RelativeLayout)findViewById(R.id.rl_products);
        rl_services=(RelativeLayout)findViewById(R.id.rl_services);
//        rv_products=(RecyclerView)findViewById(R.id.rv_products);
//        rv_services=(RecyclerView)findViewById(R.id.rv_services);


        rl_location.setOnClickListener(this);
        rl_products.setOnClickListener(this);
        rl_services.setOnClickListener(this);
    }
    
    public void setRecycleServices()
    {
//        rv_services=(RecyclerView)newViewServices.findViewById(R.id.rv_services);
        layoutManagerservices=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        adapterservices=new AdapterServices(this, info);
        rv_services.setLayoutManager(layoutManagerservices);
        rv_services.setAdapter(adapterservices);

    }
    public void setRecycleProducts()
    {
//        rv_products.setVisibility(View.VISIBLE);
//        rv_products=(RecyclerView)newViewProducts.findViewById(R.id.rv_products);

        layoutManagerProducts=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        adapterProducts=new AdapterProducts(this, info);
        rv_products.setLayoutManager(layoutManagerProducts);
        rv_products.setAdapter(adapterProducts);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.rl_location :
                if(rl_address.getVisibility()==View.VISIBLE)
                    rl_address.setVisibility(View.GONE);
                else rl_address.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_products :
                if (rl_productContainer.getChildCount() == 0) {
                    addProducts();
                }else    rl_productContainer.removeView(newViewProducts);
//                    setRecycleProducts();
                break;
            case R.id.rl_services :
                if (rl_sericesContainer.getChildCount() == 0) {
                    addServices();
                }else    rl_sericesContainer.removeView(newViewServices);
//
               /* if(rv_services.getVisibility()==View.VISIBLE)
                rv_services.setVisibility(View.GONE);
                else setRecycleServices();*/
                  break;
        }
    }
    private void addProducts() {
        // Instantiate a new "row" view.
        newViewProducts = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.list_product, rl_productContainer, false);
        setRecycleProducts();
        rl_productContainer.addView(newViewProducts);

    }
    private void addServices() {
        // Instantiate a new "row" view.
        newViewServices = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.list_services, rl_sericesContainer, false);
        setRecycleServices();
        rl_sericesContainer.addView(newViewServices);

    }
}
