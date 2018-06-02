package com.chatlocalybusiness.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.AllProductsActivity;
import com.chatlocalybusiness.activity.ProductDetailActivity;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.apiModel.ProductListModel;
import com.chatlocalybusiness.getterSetterModel.ProductDetailsModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windows on 3/12/2018.
 */

@SuppressLint("ValidFragment")
public class PublishedProductFragment extends Fragment{
    private RecyclerView rv_galleryView;
    private GridLayoutManager layoutManager;
    private PublishProductlistAdapter publishProductlistAdapter;
    List<ProductListModel.PublishProduct> publishedProductList;
    private TextView tv_noProducts;

    public PublishedProductFragment(List<ProductListModel.PublishProduct> publishedProductList) {
    this.publishedProductList=publishedProductList;
           }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.tablayout_published,container,false);
        tv_noProducts=(TextView)(getActivity()).findViewById(R.id.tv_noProducts);

        if(publishedProductList!=null)
            if(publishedProductList.size()>0)
                tv_noProducts.setText(publishedProductList.size()+"    Product");
            else tv_noProducts.setText("No Product");

            visibleFrag(true,false);
           setRecycleView(view);
         if(AllProductsActivity.publishedFrag)
         {
             searchView();
         }
        return view;
    }
    private boolean _hasLoadedOnce= false; // your boolean field


    public void searchView()
    {
        SearchView searchView=(SearchView)(getActivity()).findViewById(R.id.searchView) ;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(),"submit",Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Toast.makeText(getActivity(),"searchView",Toast.LENGTH_SHORT).show();
                publishProductlistAdapter.getFilter().filter(s);
                return false;
            }
        });
    }


    public void visibleFrag(boolean t,boolean f )
    {
        AllProductsActivity.publishedFrag=t;
        AllProductsActivity.unpublished=f;
        AllProductsActivity.unApproved=f;
        AllProductsActivity.blocked=f;
    }
    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);


        if (this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                tv_noProducts=(TextView)(getActivity()).findViewById(R.id.tv_noProducts);
                visibleFrag(true,false);

                _hasLoadedOnce = true;
                if(publishedProductList!=null)
                    if(publishedProductList.size()>0)
                        tv_noProducts.setText(publishedProductList.size()+"    Product");
                    else tv_noProducts.setText("No Product");

            }
        }
    }
    public void setRecycleView(View view)
    {
        rv_galleryView=(RecyclerView)view.findViewById(R.id.rv_galleryView);
        layoutManager=new GridLayoutManager(getActivity(),2);
        publishProductlistAdapter=new PublishProductlistAdapter(getActivity(),publishedProductList);
        rv_galleryView.setLayoutManager(layoutManager);
        rv_galleryView.setAdapter(publishProductlistAdapter);

    }



   public class PublishProductlistAdapter extends RecyclerView.Adapter<PublishProductlistAdapter.MyViewHolder> implements Filterable {
        private List<ProductListModel.PublishProduct> filteredProductList;
        private List<ProductListModel.PublishProduct> productList;

        private Context context;
        private BusinessInfoModelNew.BusinessDetail info;

        public PublishProductlistAdapter(Context context, List<ProductListModel.PublishProduct> productList) {
            this.filteredProductList = productList;
            this.productList = productList;

            this.context = context;
        }

        @Override
        public PublishProductlistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_product, parent, false);

            return new PublishProductlistAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PublishProductlistAdapter.MyViewHolder holder, int position) {
            holder.tv_productName.setText(filteredProductList.get(position).getProductName());

            if(filteredProductList.get(position).getProductStatus().equalsIgnoreCase("0"))
                holder.iv_opacity.setVisibility(View.VISIBLE);
            else  holder.iv_opacity.setVisibility(View.GONE);

            if (filteredProductList.get(position).getDiscount().equalsIgnoreCase("0")) {
                holder.tv_prodiscount.setVisibility(View.GONE);
                holder.tv_oldValue.setVisibility(View.GONE);
                holder.tv_proPrice.setText(Utill.getPriceFormatted( filteredProductList.get(position).getPrice()));
            } else {
                holder.tv_prodiscount.setVisibility(View.VISIBLE);
                holder.tv_oldValue.setVisibility(View.VISIBLE);
                holder.tv_proPrice.setText(Utill.getPriceFormatted(String.valueOf(Utill.calculateNewValue(Double.parseDouble(filteredProductList.get(position).getPrice()),Double.parseDouble(filteredProductList.get(position).getDiscount())))));
                holder.tv_prodiscount.setText(filteredProductList.get(position).getDiscount() + "% off");
                holder.tv_oldValue.setText(Utill.getPriceFormatted( filteredProductList.get(position).getPrice()));
                holder.tv_oldValue.setPaintFlags(holder.tv_oldValue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            Glide.with(context).load(filteredProductList.get(position).getProductImage()).into(holder.iv_prodImage);
//          someTextView.setPaintFlags(someTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        }

        @Override
        public int getItemCount() {
            if (filteredProductList != null)
                return filteredProductList.size();
            else return 0;
        }

       @Override
       public Filter getFilter() {
           return new Filter() {
               @Override
               protected FilterResults performFiltering(CharSequence charSequence) {
                    String str=charSequence.toString();
                    if(str.isEmpty())
                    {
                        filteredProductList=productList;

                    }else {
                        ArrayList<ProductListModel.PublishProduct> filteredList = new ArrayList<>();

                        for (ProductListModel.PublishProduct product : productList) {

                            if (product.getProductName().toLowerCase().contains(str) || product.getBrandName().toLowerCase().contains(str) ) {

                                filteredList.add(product);
                            }
                        }

                        filteredProductList = filteredList;
                    }

                   FilterResults filterResults = new FilterResults();
                   filterResults.values = filteredProductList;
                   return filterResults;
               }

               @Override
               protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                   filteredProductList = (ArrayList<ProductListModel.PublishProduct>) filterResults.values;
                   notifyDataSetChanged();
               }
           };
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


                new BasicUtill().setImageRatio(iv_prodImage,context);
                iv_prodImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProductDetailsModel productDetailsModel=setProductDetails(getAdapterPosition());
                        Intent intent=new Intent(context, ProductDetailActivity.class);
                        intent.putExtra("POSITION",getAdapterPosition());
                        intent.putExtra(Constants.PRODUCT_DETAIL,productDetailsModel);
                        context.startActivity(intent);
                    }
                });

            }
            public ProductDetailsModel setProductDetails(int position)
            {
                ProductDetailsModel productDetailsModel=new ProductDetailsModel();
                ArrayList<ProductDetailsModel.ProductImages> imageList=new ArrayList<>();
                productDetailsModel.setBusinessName(new ChatBusinessSharedPreference(getActivity()).getBusinessName());
                productDetailsModel.setProductId(filteredProductList.get(position).getProductId());
                productDetailsModel.setProductName(filteredProductList.get(position).getProductName());
                productDetailsModel.setDiscription(filteredProductList.get(position).getDescription());
                productDetailsModel.setPrice(filteredProductList.get(position).getPrice());
                productDetailsModel.setDiscount(filteredProductList.get(position).getDiscount());


                productDetailsModel.setBrandName(filteredProductList.get(position).getBrandName());
                productDetailsModel.setSku(filteredProductList.get(position).getSku());
                productDetailsModel.setProThumbnail(filteredProductList.get(position).getProductImage());
                productDetailsModel.setProductStatus(filteredProductList.get(position).getProductStatus());

                for(int i = 0; i< filteredProductList.get(position).getProductImages().size(); i++){
                    ProductDetailsModel.ProductImages productImages=new ProductDetailsModel().new ProductImages();
                    productImages.setImageName(filteredProductList.get(position).getProductImages().get(i).getProductImage());
                    productImages.setImageId(filteredProductList.get(position).getProductImages().get(i).getPimId());
                    imageList.add(productImages);
                }

                productDetailsModel.setProductImages(imageList);
                return productDetailsModel;
            }
        }
    }

}
