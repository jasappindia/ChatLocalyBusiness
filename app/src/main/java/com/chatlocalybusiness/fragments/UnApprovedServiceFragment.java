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
import com.chatlocalybusiness.activity.ServiceDetailActivity;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.apiModel.ServiceListModel;
import com.chatlocalybusiness.getterSetterModel.ServiceDetailsModel;
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
public class UnApprovedServiceFragment extends Fragment {
    List<ServiceListModel.UnapprovedService> unapprovedServices;
    private RecyclerView rv_galleryView;
    private GridLayoutManager layoutManager;
    private UnApprovedProductListAdapter unApprovedProductListAdapter;
    private TextView tv_noProducts;

    public UnApprovedServiceFragment(List<ServiceListModel.UnapprovedService> unapprovedServices) {
        this.unapprovedServices = unapprovedServices;
    }

    private boolean _hasLoadedOnce = false; // your boolean field
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
                searchView();
                if (unapprovedServices != null)
                    if (unapprovedServices.size() > 0)
                        tv_noProducts.setText(unapprovedServices.size() + "    Service");
                    else tv_noProducts.setText("No Service");

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
        unApprovedProductListAdapter = new UnApprovedProductListAdapter(getActivity(), unapprovedServices);
        rv_galleryView.setLayoutManager(layoutManager);
        rv_galleryView.setAdapter(unApprovedProductListAdapter);
    }

    public class UnApprovedProductListAdapter extends RecyclerView.Adapter<UnApprovedProductListAdapter.MyViewHolder> implements Filterable{
        private List<ServiceListModel.UnapprovedService> filteredServiceList;
        private List<ServiceListModel.UnapprovedService> serviceList;
        private Context context;
        private BusinessInfoModelNew.BusinessDetail info;

        public UnApprovedProductListAdapter(Context context, List<ServiceListModel.UnapprovedService> productList) {
            this.filteredServiceList = productList;
            this.serviceList = productList;

            this.context = context;
        }

        @Override
        public UnApprovedProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_service, parent, false);

            return new UnApprovedProductListAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(UnApprovedProductListAdapter.MyViewHolder holder, int position) {
            holder.tv_productName.setText(filteredServiceList.get(position).getServiceName());
            holder.tv_proPrice.setText("Rs " + filteredServiceList.get(position).getServicePrice());
            if (filteredServiceList.get(position).getStatus().equalsIgnoreCase("UNAPPROVED"))
                holder.iv_opacity.setVisibility(View.VISIBLE);
            else holder.iv_opacity.setVisibility(View.GONE);

            if (filteredServiceList.get(position).getDiscount().equalsIgnoreCase("0")) {
                holder.tv_prodiscount.setVisibility(View.GONE);
                holder.tv_oldValue.setVisibility(View.GONE);
                holder.tv_proPrice.setText(Utill.getPriceFormatted(filteredServiceList.get(position).getServicePrice()));

            } else {
                holder.tv_prodiscount.setVisibility(View.VISIBLE);
                holder.tv_oldValue.setVisibility(View.VISIBLE);
                holder.tv_proPrice.setText( String.valueOf(Utill.calculateNewValue(Double.parseDouble(filteredServiceList.get(position).getServicePrice()),Double.parseDouble(filteredServiceList.get(position).getDiscount()))));
                holder.tv_prodiscount.setText(filteredServiceList.get(position).getDiscount() + "% off");
                holder.tv_oldValue.setText(Utill.getPriceFormatted(filteredServiceList.get(position).getServicePrice()));
                holder.tv_oldValue.setPaintFlags(holder.tv_oldValue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }


            if(filteredServiceList.get(position).getServiceLabel()!=null&&!filteredServiceList.get(position).getServiceLabel().equalsIgnoreCase(""))
            {
                holder.tv_caption.setVisibility(View.VISIBLE);
                holder.tv_caption.setText(filteredServiceList.get(position).getServiceLabel());
            }
            else{
                holder.tv_caption.setVisibility(View.GONE);
            }
            Glide.with(context).load(filteredServiceList.get(position).getServiceImage()).into(holder.iv_prodImage);
//        someTextView.setPaintFlags(someTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_oldValue.setPaintFlags(holder.tv_oldValue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);



        }

        @Override
        public int getItemCount() {
            if (filteredServiceList != null)
                return filteredServiceList.size();
            else return 0;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {

                    String str = charSequence.toString();

                    if (str.isEmpty()) {
                        filteredServiceList = serviceList;
                    } else {
                        ArrayList<ServiceListModel.UnapprovedService> filterdList = new ArrayList<>();

                        for (ServiceListModel.UnapprovedService product : serviceList) {
                            if (product.getServiceBrands().toString().toLowerCase().contains(str) || product.getServiceName().toLowerCase().contains(str)) {
                                filterdList.add(product);
                            }
                        }
                        filteredServiceList = filterdList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filteredServiceList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    filteredServiceList = (ArrayList<ServiceListModel.UnapprovedService>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_prodiscount, tv_proPrice, tv_productName, tv_oldValue,tv_caption;
            ImageView iv_prodImage, iv_opacity;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_productName = (TextView) itemView.findViewById(R.id.tv_productName);
                tv_caption = (TextView) itemView.findViewById(R.id.tv_caption);
                iv_opacity = (ImageView) itemView.findViewById(R.id.iv_opacityUnapproved);
                tv_proPrice = (TextView) itemView.findViewById(R.id.tv_proPrice);
                tv_oldValue = (TextView) itemView.findViewById(R.id.tv_oldValue);

                tv_prodiscount = (TextView) itemView.findViewById(R.id.tv_prodiscount);

                iv_prodImage = (ImageView) itemView.findViewById(R.id.iv_prodImage);
                new BasicUtill().setImageRatio(iv_prodImage,context);

                iv_prodImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ServiceDetailsModel serviceDetailsModel = setProductDetails(getAdapterPosition());
                        Intent intent = new Intent(context, ServiceDetailActivity.class);
                        intent.putExtra("POSITION", getAdapterPosition());
                        intent.putExtra(Constants.PRODUCT_DETAIL, serviceDetailsModel);
                        context.startActivity(intent);
                    }
                });

            }

            public ServiceDetailsModel setProductDetails(int position) {
                ServiceDetailsModel serviceListModel = new ServiceDetailsModel();
                ArrayList<ServiceDetailsModel.ServiceImages> imageList = new ArrayList<>();
                ArrayList<ServiceDetailsModel.ServiceBrands> brandsList = new ArrayList<>();

                serviceListModel.setBusinessName(new ChatBusinessSharedPreference(getActivity()).getBusinessName());
                serviceListModel.setServiceId(filteredServiceList.get(position).getServiceId());
                serviceListModel.setServiceName(filteredServiceList.get(position).getServiceName());
                serviceListModel.setDiscription(filteredServiceList.get(position).getDescription());
                serviceListModel.setServicePrice(filteredServiceList.get(position).getServicePrice());
                serviceListModel.setDiscount(filteredServiceList.get(position).getDiscount());
                serviceListModel.setCaption(filteredServiceList.get(position).getServiceLabel());


                serviceListModel.setProThumbnail(filteredServiceList.get(position).getServiceImage());
                serviceListModel.setServiceStatus(filteredServiceList.get(position).getServiceStatus());

                for (int i = 0; i < filteredServiceList.get(position).getServiceImages().size(); i++) {
                    ServiceDetailsModel.ServiceImages serviceImages = new ServiceDetailsModel().new ServiceImages();
                    serviceImages.setImageName(filteredServiceList.get(position).getServiceImages().get(i).getImageName());
                    serviceImages.setImageId(filteredServiceList.get(position).getServiceImages().get(i).getSimId());
                    imageList.add(serviceImages);
                }
                for (int i = 0; i < filteredServiceList.get(position).getServiceBrands().size(); i++) {
                    ServiceDetailsModel.ServiceBrands serviceBrands = new ServiceDetailsModel().new ServiceBrands();
                    serviceBrands.setBrandName(filteredServiceList.get(position).getServiceBrands().get(i).getBrandName());
                    serviceBrands.setBrandId(filteredServiceList.get(position).getServiceBrands().get(i).getBrandId());
                    brandsList.add(serviceBrands);
                }
                serviceListModel.setProductImages(imageList);
                serviceListModel.setServiceBrands(brandsList);
                return serviceListModel;
            }

        }
    }
}
