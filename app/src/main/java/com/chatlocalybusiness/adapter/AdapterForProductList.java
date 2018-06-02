package com.chatlocalybusiness.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chatlocalybusiness.R;

/**
 * Created by windows on 1/18/2018.
 */
public class AdapterForProductList extends RecyclerView.Adapter<AdapterForProductList.MyViewHolder>{

    Context context;
    public AdapterForProductList(Context context)
    {
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_productlist,parent,false);

        return new AdapterForProductList.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
