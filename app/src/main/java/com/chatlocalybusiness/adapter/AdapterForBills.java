package com.chatlocalybusiness.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.InvoiceDetailShowActivity;
import com.chatlocalybusiness.apiModel.InvoiceOrderListModel;
import com.chatlocalybusiness.utill.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * Created by windows on 12/20/2017.
 */
public class AdapterForBills  extends RecyclerView.Adapter<AdapterForBills.MyViewHolder> {

    Context context;
    List<InvoiceOrderListModel.OrderList> orderList;
    private boolean isLoadingAdded = false;

    public AdapterForBills(Context context, List<InvoiceOrderListModel.OrderList> orderList) {
    this.context=context;
    this.orderList=orderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(context).inflate(R.layout.card_bills,parent,false);

        return new AdapterForBills.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_payeeName.setText(orderList.get(position).getCFullName());
        holder.tv_paidStatus.setText(orderList.get(position).getOrderStatus());
        if(orderList.get(position).getOrderStatus().equalsIgnoreCase("Unpaid"))
            holder.tv_paidStatus.setTextColor(ContextCompat.getColor(context,R.color.red));
        else holder.tv_paidStatus.setTextColor(ContextCompat.getColor(context,R.color.green));

        holder.tv_paymentAmount.setText("Rs "+orderList.get(position).getTotal());
        holder.tv_dueDate.setText(" "+orderList.get(position).getBillDueDate());
        holder.tv_sentOn.setText(" "+orderList.get(position).getSentDate());
    }
    @Override
    public int getItemCount() {
        if(orderList!=null)
            return orderList.size();
        else
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_sentOn,tv_paymentAmount,tv_paidStatus,tv_payeeName,tv_dueDate;
        LinearLayout ll_billLayout;
        public MyViewHolder(View itemView) {
            super(itemView);

            ll_billLayout=(LinearLayout)itemView.findViewById(R.id.ll_billLayout);
            tv_sentOn=(TextView)itemView.findViewById(R.id.tv_sentOn);
            tv_paymentAmount=(TextView)itemView.findViewById(R.id.tv_paymentAmount);
            tv_paidStatus=(TextView)itemView.findViewById(R.id.tv_paidStatus);
            tv_payeeName=(TextView)itemView.findViewById(R.id.tv_payeeName);
            tv_dueDate=(TextView)itemView.findViewById(R.id.tv_dueDate);

            ll_billLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, InvoiceDetailShowActivity.class);
                    intent.putExtra(Constants.ORDER_DETAILS, (Serializable) orderList.get(getAdapterPosition()));
                    context.startActivity(intent);

                }
            });
        }
    }


   /* public void add(InvoiceOrderListModel.OrderList order) {
        orderList.add(order);
        notifyItemInserted(orderList.size() - 1);
    }

    public void addAll(List<InvoiceOrderListModel.OrderList> orderLists) {
        for (InvoiceOrderListModel.OrderList order : orderLists) {
            add(order);
        }
    }

    public void remove(InvoiceOrderListModel.OrderList order) {
        int position = orderList.indexOf(order);
        if (position > -1) {
            movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movies.size() - 1;
        Movie item = getItem(position);

        if (item != null) {
            movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Movie getItem(int position) {
        return movies.get(position);
    }*/
}
