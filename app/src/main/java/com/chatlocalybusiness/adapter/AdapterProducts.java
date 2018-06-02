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
import com.chatlocalybusiness.getterSetterModel.ProductDetailsModel;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windows on 12/19/2017.
 */
public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.MyViewHolder>{
    Context context;
    ProductDetailsModel productDetailsModel;
    List<BusinessInfoModelNew.Product> productList;
    BusinessInfoModelNew.BusinessDetail info;
    ArrayList<ProductDetailsModel.ProductImages> imageList;
    public AdapterProducts(Context context, BusinessInfoModelNew.BusinessDetail info) {
        this.productList=info.getProducts();
        this.context=context;
        this.info=info;
        imageList=new ArrayList<>();
        productDetailsModel=new ProductDetailsModel();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_product_profile,parent,false);
        return new AdapterProducts.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_productName.setText(productList.get(position).getProductName());
        if(productList.get(position).getDiscount().equalsIgnoreCase("0")) {
            holder.tv_prodiscount.setVisibility(View.GONE);
            holder.tv_oldValue.setVisibility(View.GONE);
            holder.tv_proPrice.setText(Utill.getPriceFormatted(productList.get(position).getPrice()));


        } else {
            holder.tv_prodiscount.setVisibility(View.VISIBLE);
            holder.tv_prodiscount.setText(productList.get(position).getDiscount() + "% off");
            holder.tv_oldValue.setVisibility(View.VISIBLE);
            holder.tv_proPrice.setText( Utill.getPriceFormatted(String.valueOf(Utill.calculateNewValue(Double.parseDouble(productList.get(position).getPrice()),
                    Double.parseDouble(productList.get(position).getDiscount())))));
            holder.tv_oldValue.setText(Utill.getPriceFormatted(productList.get(position).getPrice()));

            holder.tv_oldValue.setPaintFlags(holder.tv_oldValue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
        Glide.with(context).load(productList.get(position).getProductImage()).into(holder.iv_prodImage);
//          someTextView.setPaintFlags(someTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    @Override
    public int getItemCount() {

        if(productList!=null) {
            if (productList.size() > 0 && productList.size() < 10){
                return productList.size();
            }
            else
                return 10;
        }
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_prodiscount,tv_proPrice,tv_productName,tv_oldValue;
        ImageView iv_prodImage,iv_opacity;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_productName = (TextView) itemView.findViewById(R.id.tv_productName);
//            iv_opacity=(ImageView)itemView.findViewById(R.id.iv_opacityUnpublished);
            tv_proPrice = (TextView) itemView.findViewById(R.id.tv_proPrice);
            tv_oldValue=(TextView)itemView.findViewById(R.id.tv_oldValue);

            tv_prodiscount = (TextView) itemView.findViewById(R.id.tv_prodiscount);

            iv_prodImage = (ImageView) itemView.findViewById(R.id.iv_prodImage);

            iv_prodImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    setProductDetails(position);
                    Intent intent=new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("POSITION",getAdapterPosition());
                    intent.putExtra(Constants.PRODUCT_DETAIL,productDetailsModel);
                    context.startActivity(intent);
                }
            });
        }

      public void setProductDetails(int position)
      {
          if(imageList!=null)
              imageList.clear();
          productDetailsModel.setBusinessName(info.getBusinessName());
          productDetailsModel.setProductId(info.getProducts().get(position).getProductId());
          productDetailsModel.setProductName(info.getProducts().get(position).getProductName());
          productDetailsModel.setDiscription(info.getProducts().get(position).getDescription());
          productDetailsModel.setPrice(info.getProducts().get(position).getPrice());
          productDetailsModel.setDiscount(info.getProducts().get(position).getDiscount());
          productDetailsModel.setProductStatus(info.getProducts().get(position).getProductStatus());
          productDetailsModel.setProThumbnail(info.getProducts().get(position).getProductImage());
          productDetailsModel.setSku(info.getProducts().get(position).getSku());
          productDetailsModel.setBrandName(info.getProducts().get(position).getBrandName());

          int size=info.getProducts().get(position).getProductImages().size();
          for(int i=0;i<size;i++){
              ProductDetailsModel.ProductImages productImages=new ProductDetailsModel().new ProductImages();
              productImages.setImageName(info.getProducts().get(position).getProductImages().get(i).getProductImage());
              productImages.setImageId(info.getProducts().get(position).getProductImages().get(i).getPimId());
              imageList.add(productImages);
          }

          productDetailsModel.setProductImages(imageList);
      }
    }
}
