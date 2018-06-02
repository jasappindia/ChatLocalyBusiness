package com.chatlocalybusiness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;


import com.chatlocalybusiness.R;
import com.chatlocalybusiness.apiModel.ProductListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windows on 4/9/2018.
 */

public class AdapterForAutoComplete extends ArrayAdapter<ProductListModel.PublishProduct> {

    Context context;
    int resource, textViewResourceId;
    List<ProductListModel.PublishProduct> items, tempItems, suggestions;



//    public AdapterForAutoComplete(Context context, int resource, int textViewResourceId, List<ProductListModel.PublishProduct> items) {
    public AdapterForAutoComplete(Context context, int resource, int textViewResourceId, List<ProductListModel.PublishProduct> items) {
        super(context,resource,textViewResourceId,items);
        this.context=context;
        this.resource=resource;
        this.textViewResourceId=textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<ProductListModel.PublishProduct>(items); // this makes the difference.
        suggestions = new ArrayList<ProductListModel.PublishProduct>();
    }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_product, parent, false);
            }
            ProductListModel.PublishProduct people = items.get(position);
            if (people != null) {
                TextView lblName = (TextView) view.findViewById(R.id.tv_filtered);
                if (lblName != null)
                    lblName.setText(people.getProductName());
            }
            return view;
        }

        @Override
        public Filter getFilter() {
            return nameFilter;
        }

        /**
         * Custom Filter implementation for custom suggestions we provide.
         */
        Filter nameFilter = new Filter() {
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                String str = ((ProductListModel.PublishProduct) resultValue).getProductName();
                return str;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    suggestions.clear();
                    for (ProductListModel.PublishProduct people : tempItems) {
                        if (people.getProductName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            suggestions.add(people);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                List<ProductListModel.PublishProduct> filterList = (ArrayList<ProductListModel.PublishProduct>) results.values;
                if (results != null && results.count > 0) {
                    clear();
                    for (ProductListModel.PublishProduct people : filterList) {
                        add(people);
                        notifyDataSetChanged();
                    }

                }
            }
        };


    public interface OnItemClickListener{
        void onItemClick(AdapterView<?> var1, View var2, int var3, long var4);
    }
}





