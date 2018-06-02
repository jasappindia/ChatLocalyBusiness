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
public class UnApprovedProductFragment extends Fragment {
    List<ProductListModel.UnapprovedProduct> unapprovedProducts;
    private RecyclerView rv_galleryView;
    private GridLayoutManager layoutManager;
    private UnApprovedProductListAdapter unApprovedProductListAdapter;
    private TextView tv_noProducts;

    public UnApprovedProductFragment(List<ProductListModel.UnapprovedProduct> unapprovedProducts) {
        this.unapprovedProducts = unapprovedProducts;

    }

    private boolean _hasLoadedOnce = false; // your boolean field

    public void visibleFrag(boolean t, boolean f) {
        AllProductsActivity.unApproved = t;
        AllProductsActivity.publishedFrag = f;
        AllProductsActivity.unpublished = f;
        AllProductsActivity.blocked = f;
    }
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
                unApprovedProductListAdapter.getFilter().filter(s);
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
                _hasLoadedOnce = true;
                visibleFrag(true, false);
                 searchView();
                if (unapprovedProducts != null)
                    if (unapprovedProducts.size() > 0)
                        tv_noProducts.setText(unapprovedProducts.size() + "    Product");
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
        unApprovedProductListAdapter = new UnApprovedProductListAdapter(getActivity(), unapprovedProducts);
        rv_galleryView.setLayoutManager(layoutManager);
        rv_galleryView.setAdapter(unApprovedProductListAdapter);
    }

    public class UnApprovedProductListAdapter extends RecyclerView.Adapter<UnApprovedProductListAdapter.MyViewHolder> implements Filterable {
        private List<ProductListModel.UnapprovedProduct> filteredproductList;
        private List<ProductListModel.UnapprovedProduct> productList;
        private Context context;
        private BusinessInfoModelNew.BusinessDetail info;

        public UnApprovedProductListAdapter(Context context, List<ProductListModel.UnapprovedProduct> productList) {
            this.filteredproductList = productList;
            this.productList = productList;
            this.context = context;
        }

        @Override
        public UnApprovedProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_product, parent, false);

            return new UnApprovedProductListAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(UnApprovedProductListAdapter.MyViewHolder holder, int position) {
            holder.tv_productName.setText(filteredproductList.get(position).getProductName());
//            holder.tv_proPrice.setText("Rs " + filteredproductList.get(position).getPrice());
            if (filteredproductList.get(position).getStatus().equalsIgnoreCase("UNAPPROVED"))
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
                        ArrayList<ProductListModel.UnapprovedProduct> filterdList = new ArrayList<>();

                        for (ProductListModel.UnapprovedProduct product : productList) {
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
                    filteredproductList = (ArrayList<ProductListModel.UnapprovedProduct>) filterResults.values;
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
                iv_opacity = (ImageView) itemView.findViewById(R.id.iv_opacityUnapproved);
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
