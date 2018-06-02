package com.chatlocalybusiness.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.BusinessProfileActivity;
import com.chatlocalybusiness.activity.NotificationSettingsActivity;
import com.chatlocalybusiness.activity.SettingsInternalActivity;
import com.chatlocalybusiness.activity.SubscriptionActivity;
import com.chatlocalybusiness.activity.SubscriptionInfoActivity;
import com.chatlocalybusiness.activity.UserProfileActivity;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.Constants;

/**
 * Created by windows on 12/19/2017.
 */
public class AdapterForSettingsOption extends RecyclerView.Adapter<AdapterForSettingsOption.MyViewHolder>{
//     String[] optionList={"Business Profile","Receive Payments","Internal",
//             "Subscription","Your Profile","Notification","Chat Backup"};
    String[] optionList={"Business Profile","Internal",
             "Subscription","Your Profile","Notification"};
    int[] iconList={R.drawable.business_icon,R.drawable.internal_icon,R.drawable.subscribe_icon,
            R.drawable.your_profile,R.drawable.notifications_icon};
     Context context;
 ChatBusinessSharedPreference preference;

    public AdapterForSettingsOption(Context context)
    {
        this.context=context;
        preference=new ChatBusinessSharedPreference(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_settings,parent,false);
        return new AdapterForSettingsOption.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_settingsOptions.setText(optionList[position]);
        holder.iv_icon.setImageResource(iconList[position]);

    }

    @Override
    public int getItemCount() {
        return optionList.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_settingsOptions;
        ImageView iv_icon;
        LinearLayout ll_optionDrawer;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_settingsOptions=(TextView)itemView.findViewById(R.id.tv_settingsOptions);
            iv_icon=(ImageView)itemView.findViewById(R.id.iv_icon);
//            ll_optionDrawer=(LinearLayout)itemView.findViewById(R.id.ll_optionDrawer);
            tv_settingsOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(getAdapterPosition()==0) {
                            Intent intent=new Intent(context, BusinessProfileActivity.class);
                            intent.putExtra("Activity","settings_BsinessSetup");
                            context.startActivity(intent);
                   }/*else if(getAdapterPosition()==1) {
                          Intent intent2 = new Intent(context, RegisterPaytmActivity.class);
                          intent2.putExtra("Activity", "settings_PaytmRegister");
                          context.startActivity(intent2);
                   }*/
                    else if(getAdapterPosition()==1) {
                        if(preference.getBusinessROlE().equalsIgnoreCase(Constants.WORKER))
                            Toast.makeText(context, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();
                            else
                       context.startActivity(new Intent(context, SettingsInternalActivity.class));
                   }
                   else if(getAdapterPosition()==2)
                       context.startActivity(new Intent(context, SubscriptionInfoActivity.class));
                   else if(getAdapterPosition()==3) {
                       Intent intent3 = new Intent(context, UserProfileActivity.class);
                       intent3.putExtra("Activity", "settings_ProfileActivity");
                       context.startActivity(intent3);
                   }
                   else if(getAdapterPosition()==4)
                       context.startActivity(new Intent(context, NotificationSettingsActivity.class));
                }
            });

        }
    }
}
