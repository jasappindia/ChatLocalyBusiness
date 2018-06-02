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
import com.chatlocalybusiness.activity.ServiceDetailActivity;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.getterSetterModel.ServiceDetailsModel;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windows on 12/19/2017.
 */
public class AdapterServices extends RecyclerView.Adapter<AdapterServices.MyViewHolder>{

    Context context;
    List<BusinessInfoModelNew.Service> productList;
    BusinessInfoModelNew.BusinessDetail info;
    private ServiceDetailsModel serviceDetailsModel;
    private ArrayList<ServiceDetailsModel.ServiceImages> imageList;
    private ArrayList<ServiceDetailsModel.ServiceBrands> brandsList;

    public AdapterServices(Context context, BusinessInfoModelNew.BusinessDetail info) {
        this.productList=info.getServices();
        this.context=context;
        this.info=info;
        imageList=new ArrayList<>();
        brandsList=new ArrayList<>();
        serviceDetailsModel=new ServiceDetailsModel();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_service_profile,parent,false);
        return new AdapterServices.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_productName.setText(productList.get(position).getServiceName());

        if (productList.get(position).getDiscount().equalsIgnoreCase("0")) {
            holder.tv_prodiscount.setVisibility(View.GONE);
            holder.tv_oldValue.setVisibility(View.GONE);
            holder.tv_proPrice.setText( Utill.getPriceFormatted(productList.get(position).getServicePrice()));

        } else {
            holder.tv_prodiscount.setVisibility(View.VISIBLE);
            holder.tv_prodiscount.setText(productList.get(position).getDiscount() + "% off");
            holder.tv_oldValue.setVisibility(View.VISIBLE);
            holder.tv_proPrice.setText(Utill.getPriceFormatted(String.valueOf(Utill.calculateNewValue(Double.parseDouble(productList.get(position).getServicePrice()),
                    Double.parseDouble(productList.get(position).getDiscount())))));
            holder.tv_oldValue.setText(Utill.getPriceFormatted(productList.get(position).getServicePrice()));

            holder.tv_oldValue.setPaintFlags(holder.tv_oldValue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }

        if(productList.get(position).getServiceLabel()!=null&&!productList.get(position).getServiceLabel().equalsIgnoreCase(""))
        {
            holder.tv_caption.setVisibility(View.VISIBLE);
            holder.tv_caption.setText(productList.get(position).getServiceLabel());
        }
        else{
            holder.tv_caption.setVisibility(View.GONE);
        }
        Glide.with(context).load(productList.get(position).getServiceImage()).into(holder.iv_prodImage);
//        someTextView.setPaintFlags(someTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
       

    }

    @Override
    public int getItemCount() {
       if(productList!=null) {
           if (productList.size() > 0 && productList.size() < 6){
               return productList.size();}
           else return 6;
       }else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_prodiscount,tv_proPrice,tv_productName,tv_caption,tv_oldValue;
        ImageView iv_prodImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_productName = (TextView) itemView.findViewById(R.id.tv_productName);
            tv_caption = (TextView) itemView.findViewById(R.id.tv_caption);
            tv_proPrice = (TextView) itemView.findViewById(R.id.tv_proPrice);
            tv_oldValue = (TextView) itemView.findViewById(R.id.tv_oldValue);

            tv_prodiscount = (TextView) itemView.findViewById(R.id.tv_prodiscount);

            iv_prodImage = (ImageView) itemView.findViewById(R.id.iv_prodImage);
            iv_prodImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    setProductDetails(position);
                    Intent intent=new Intent(context, ServiceDetailActivity.class);
                    intent.putExtra("POSITION",getAdapterPosition());
                    intent.putExtra(Constants.PRODUCT_DETAIL,serviceDetailsModel);
                    context.startActivity(intent);
                }
            });
        }
    }
    public void setProductDetails(int position)
    {
        if(imageList!=null)
            imageList.clear();
        if (brandsList!=null)
            brandsList.clear();
        serviceDetailsModel.setBusinessName(info.getBusinessName());
        serviceDetailsModel.setServiceId(info.getServices().get(position).getServiceId());
        serviceDetailsModel.setServiceName(info.getServices().get(position).getServiceName());
        serviceDetailsModel.setDiscription(info.getServices().get(position).getDescription());
        serviceDetailsModel.setServicePrice(info.getServices().get(position).getServicePrice());
        serviceDetailsModel.setDiscount(info.getServices().get(position).getDiscount());
        serviceDetailsModel.setServiceStatus(info.getServices().get(position).getServiceStatus());
        serviceDetailsModel.setProThumbnail(info.getServices().get(position).getServiceImage());
        serviceDetailsModel.setCaption(info.getServices().get(position).getServiceLabel());

//        serviceDetailsModel.setSku(info.getServices().get(position).getSku());

        int size=info.getServices().get(position).getServiceImages().size();
        for(int i=0;i<size;i++){
            ServiceDetailsModel.ServiceImages serviceImages=new ServiceDetailsModel().new ServiceImages();
            serviceImages.setImageName(info.getServices().get(position).getServiceImages().get(i).getImageName());
            serviceImages.setImageId(info.getServices().get(position).getServiceImages().get(i).getSimId());
            imageList.add(serviceImages);
        }
        serviceDetailsModel.setProductImages(imageList);

        int brandsize=info.getServices().get(position).getServiceBrands().size();
        for(int i=0;i<brandsize;i++){
            ServiceDetailsModel.ServiceBrands serviceBrands=new ServiceDetailsModel().new ServiceBrands();
            serviceBrands.setBrandName(info.getServices().get(position).getServiceBrands().get(i).getBrandName());
            serviceBrands.setBrandId(info.getServices().get(position).getServiceBrands().get(i).getBrandId());
            brandsList.add(serviceBrands);
        }
        serviceDetailsModel.setServiceBrands(brandsList);


    }

}
