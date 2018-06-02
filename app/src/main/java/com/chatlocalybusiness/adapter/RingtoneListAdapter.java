package com.chatlocalybusiness.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.apiModel.RingToneListModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anjani on 27/7/17.
 */

public class RingtoneListAdapter extends RecyclerView.Adapter<RingtoneListAdapter.ViewHolder> {
    Context context;
    List<RingToneListModel> list;
    ChatBusinessSharedPreference preference;
    private Ringtone r;


    public RingtoneListAdapter(Context context, List<RingToneListModel> list) {
        this.context = context;
        preference = new ChatBusinessSharedPreference(context);
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ringtoe_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.tv_name.setText("" + list.get(position).getRingtoneTitle());

        if (list.get(position).getRingtoneTitle().equalsIgnoreCase(preference.getRingtoneTitle()))
            holder.iv_selected.setVisibility(View.VISIBLE);
            else
                holder.iv_selected.setVisibility(View.INVISIBLE);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void notifydatasetChange(List<RingToneListModel> list) {
        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_code, tv_name,tv_audio;
        ImageView iv_selected;
        RelativeLayout rl_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_audio = (TextView)((Activity)context).findViewById(R.id.tv_audio);
            tv_name = (TextView) itemView.findViewById(R.id.tv_ringtone);
            iv_selected = (ImageView) itemView.findViewById(R.id.iv_ringtone_selected);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.relative_country_list_row);


            rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preference.setRingToneUri(list.get(getAdapterPosition()).getRingtoneListUri());
                    preference.setRingtoneTilte(list.get(getAdapterPosition()).getRingtoneTitle());
                    tv_audio.setText(list.get(getAdapterPosition()).getRingtoneTitle());

                    if (list.get(getAdapterPosition()).getRingtoneTitle().equalsIgnoreCase(preference.getRingtoneTitle()))
                        iv_selected.setVisibility(View.VISIBLE);
                    else
                        iv_selected.setVisibility(View.INVISIBLE);
                       notifyDataSetChanged();
                }
            });
        }
    }


}

