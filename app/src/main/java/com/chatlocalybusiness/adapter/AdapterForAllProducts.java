package com.chatlocalybusiness.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.ProductDetailActivity;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.utill.Constants;

import java.util.List;

/**
 * Created by windows on 12/20/2017.
 */
public class AdapterForAllProducts extends RecyclerView.Adapter<AdapterForAllProducts.MyViewHolder> {
    private List<BusinessInfoModelNew.Product> productList;
    private Context context;
    private BusinessInfoModelNew.BusinessDetail info;

    public AdapterForAllProducts(Context context, BusinessInfoModelNew.BusinessDetail info) {
        this.productList = info.getProducts();
        this.context = context;
        this.info=info;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_product, parent, false);

        return new AdapterForAllProducts.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_productName.setText(productList.get(position).getProductName());
        holder.tv_proPrice.setText("Rs "+productList.get(position).getPrice());
        if(productList.get(position).getProductStatus().equalsIgnoreCase("0"))
          holder.iv_opacity.setVisibility(View.VISIBLE);
        else  holder.iv_opacity.setVisibility(View.GONE);

        if(productList.get(position).getDiscount().equalsIgnoreCase("0")) {
            holder.tv_prodiscount.setVisibility(View.GONE);
        } else {
            holder.tv_prodiscount.setVisibility(View.VISIBLE);
            holder.tv_prodiscount.setText(productList.get(position).getDiscount() + "% off");
        }
        Glide.with(context).load(productList.get(position).getProductImage()).into(holder.iv_prodImage);
//        someTextView.setPaintFlags(someTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_oldValue.setPaintFlags(holder.tv_oldValue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


    }

    @Override
    public int getItemCount() {
        if (productList != null)
            return productList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_prodiscount, tv_proPrice, tv_productName,tv_oldValue;
        ImageView iv_prodImage,iv_opacity;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_productName = (TextView) itemView.findViewById(R.id.tv_productName);
            iv_opacity=(ImageView)itemView.findViewById(R.id.iv_opacityUnpublished);
            tv_proPrice = (TextView) itemView.findViewById(R.id.tv_proPrice);
            tv_oldValue=(TextView)itemView.findViewById(R.id.tv_oldValue);

            tv_prodiscount = (TextView) itemView.findViewById(R.id.tv_prodiscount);

            iv_prodImage = (ImageView) itemView.findViewById(R.id.iv_prodImage);

            iv_prodImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                     Intent intent=new Intent(context, ProductDetailActivity.class);
                     intent.putExtra("POSITION",getAdapterPosition());
                     intent.putExtra(Constants.PRODUCT_DETAIL,info);
                     context.startActivity(intent);
                }
            });

        }
    }
}
