package com.chatlocalybusiness.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

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
public class BlockedProductFragment extends Fragment {
    List<ProductListModel.BlockedProduct> blockedProducts;
    private RecyclerView rv_galleryView;
    private GridLayoutManager layoutManager;
    private BlockedProductListAdapter blockedProductListAdapter;
    private TextView tv_noProducts;

    public BlockedProductFragment(List<ProductListModel.BlockedProduct> blockedProducts) {
        this.blockedProducts = blockedProducts;

    }

    private boolean _hasLoadedOnce = false; // your boolean field

    public void visibleFrag(boolean t, boolean f) {
        AllProductsActivity.blocked = t;
        AllProductsActivity.publishedFrag = f;
        AllProductsActivity.unpublished = f;
        AllProductsActivity.unApproved = f;
    }
    public void searchView()
    {
        SearchView searchView=(SearchView)(getActivity()).findViewById(R.id.searchView) ;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                blockedProductListAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);


        if (this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                tv_noProducts = (TextView) (getActivity()).findViewById(R.id.tv_noProducts);
                visibleFrag(true, false);
                searchView();
                _hasLoadedOnce = true;
                if (blockedProducts != null)
                    if (blockedProducts.size() > 0)
                        tv_noProducts.setText(blockedProducts.size() + "    Product");
                    else tv_noProducts.setText("No Product");


            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablayout_published, container, false);

        setRecycleView(view);
        return view;
    }

    public void setRecycleView(View view) {
        rv_galleryView = (RecyclerView) view.findViewById(R.id.rv_galleryView);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        blockedProductListAdapter = new BlockedProductListAdapter(getActivity(), blockedProducts);
        rv_galleryView.setLayoutManager(layoutManager);
        rv_galleryView.setAdapter(blockedProductListAdapter);
    }

    public class BlockedProductListAdapter extends RecyclerView.Adapter<BlockedProductListAdapter.MyViewHolder> implements Filterable{
        private List<ProductListModel.BlockedProduct> filteredproductList;
        private List<ProductListModel.BlockedProduct> productList;
        private Context context;
        private BusinessInfoModelNew.BusinessDetail info;

        public BlockedProductListAdapter(Context context, List<ProductListModel.BlockedProduct> productList) {
            this.filteredproductList = productList;
            this.productList = productList;
            this.context = context;
        }

        @Override
        public BlockedProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_product, parent, false);

            return new BlockedProductListAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BlockedProductListAdapter.MyViewHolder holder, int position) {
            holder.tv_productName.setText(filteredproductList.get(position).getProductName());
//            holder.tv_proPrice.setText("Rs " + filteredproductList.get(position).getPrice());
            if (filteredproductList.get(position).getStatus().equalsIgnoreCase("BLOCKED"))
                holder.iv_opacity.setVisibility(View.VISIBLE);
            else holder.iv_opacity.setVisibility(View.GONE);

            if (filteredproductList.get(position).getDiscount().equalsIgnoreCase("0")) {
                holder.tv_prodiscount.setVisibility(View.GONE);
                holder.tv_oldValue.setVisibility(View.GONE);
                holder.tv_proPrice.setText(Utill.getPriceFormatted( filteredproductList.get(position).getPrice()));
            } else {
                holder.tv_prodiscount.setVisibility(View.VISIBLE);
                holder.tv_oldValue.setVisibility(View.VISIBLE);
                holder.tv_proPrice.setText(Utill.getPriceFormatted(String.valueOf(Utill.calculateNewValue(Double.parseDouble(filteredproductList.get(position).getPrice()),Double.parseDouble(filteredproductList.get(position).getDiscount())))));
                holder.tv_prodiscount.setText(filteredproductList.get(position).getDiscount() + "% off");
                holder.tv_oldValue.setText(Utill.getPriceFormatted( filteredproductList.get(position).getPrice()));
                holder.tv_oldValue.setPaintFlags(holder.tv_oldValue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            Glide.with(context).load(filteredproductList.get(position).getProductImage()).into(holder.iv_prodImage);
//        someTextView.setPaintFlags(someTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            holder.tv_oldValue.setPaintFlags(holder.tv_oldValue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        }

        @Override
        public int getItemCount() {
            if (filteredproductList != null)
                return filteredproductList.size();
            else return 0;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String str = charSequence.toString();

                    if (str.isEmpty()) {
                        filteredproductList = productList;
                    } else {
                        ArrayList<ProductListModel.BlockedProduct> filterdList = new ArrayList<>();

                        for (ProductListModel.BlockedProduct product : filteredproductList) {
                            if (product.getBrandName().toLowerCase().contains(str) || product.getProductName().toLowerCase().contains(str)) {
                                filterdList.add(product);
                            }
                        }
                        filteredproductList = filterdList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filteredproductList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    filteredproductList = (ArrayList<ProductListModel.BlockedProduct>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_prodiscount, tv_proPrice, tv_productName, tv_oldValue;
            ImageView iv_prodImage, iv_opacity;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_productName = (TextView) itemView.findViewById(R.id.tv_productName);
                iv_opacity = (ImageView) itemView.findViewById(R.id.iv_opacityBlocked);
                tv_proPrice = (TextView) itemView.findViewById(R.id.tv_proPrice);
                tv_oldValue = (TextView) itemView.findViewById(R.id.tv_oldValue);

                tv_prodiscount = (TextView) itemView.findViewById(R.id.tv_prodiscount);

                iv_prodImage = (ImageView) itemView.findViewById(R.id.iv_prodImage);
                new BasicUtill().setImageRatio(iv_prodImage,context);

                iv_prodImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProductDetailsModel productDetailsModel = setProductDetails(getAdapterPosition());

                        Intent intent = new Intent(context, ProductDetailActivity.class);
                        intent.putExtra("POSITION", getAdapterPosition());
                        intent.putExtra(Constants.PRODUCT_DETAIL, productDetailsModel);
                        context.startActivity(intent);
                    }
                });

            }

            public ProductDetailsModel setProductDetails(int position) {
                ProductDetailsModel productDetailsModel = new ProductDetailsModel();
                ArrayList<ProductDetailsModel.ProductImages> imageList = new ArrayList<>();
                productDetailsModel.setBusinessName(new ChatBusinessSharedPreference(getActivity()).getBusinessName());
                productDetailsModel.setProductId(filteredproductList.get(position).getProductId());
                productDetailsModel.setProductName(filteredproductList.get(position).getProductName());
                productDetailsModel.setDiscription(filteredproductList.get(position).getDescription());
                productDetailsModel.setPrice(filteredproductList.get(position).getPrice());
                productDetailsModel.setDiscount(filteredproductList.get(position).getDiscount());


                productDetailsModel.setBrandName(filteredproductList.get(position).getBrandName());
                productDetailsModel.setSku(filteredproductList.get(position).getSku());
                productDetailsModel.setProThumbnail(filteredproductList.get(position).getProductImage());
                productDetailsModel.setProductStatus(filteredproductList.get(position).getProductStatus());

                for (int i = 0; i < filteredproductList.get(position).getProductImages().size(); i++) {
                    ProductDetailsModel.ProductImages productImages = new ProductDetailsModel().new ProductImages();
                    productImages.setImageName(filteredproductList.get(position).getProductImages().get(i).getProductImage());
                    productImages.setImageId(filteredproductList.get(position).getProductImages().get(i).getPimId());

                    imageList.add(productImages);
                }

                productDetailsModel.setProductImages(imageList);
                return productDetailsModel;
            }
        }
    }

}
