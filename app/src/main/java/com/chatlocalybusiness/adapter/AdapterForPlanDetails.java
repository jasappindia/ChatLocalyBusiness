package com.chatlocalybusiness.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chatlocalybusiness.R;

import java.util.List;

/**
 * Created by windows on 12/19/2017.
 */
public class AdapterForPlanDetails extends RecyclerView.Adapter<AdapterForPlanDetails.MyViewHolder>{
    Context context;
    List<String> detailsList;

    public AdapterForPlanDetails(Context context, List<String> detailsList) {
        this.detailsList=detailsList;
    this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_planlist,parent,false);
        return new AdapterForPlanDetails.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.tv_plandetails.setText(detailsList.get(position));
    }

    @Override
    public int getItemCount() {
        if(detailsList!=null)
        return detailsList.size();
        else return  0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       TextView tv_plandetails;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_plandetails=(TextView)itemView.findViewById(R.id.tv_plandetails);
        }
    }
}
