package com.chatlocalybusiness.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.EditBusinessOverviewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anjani on 27/7/17.
 */

public class BusinessPhotoAdapter extends RecyclerView.Adapter<BusinessPhotoAdapter.ViewHolder> {
    Context context;
    ArrayList<String> list;

    public BusinessPhotoAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_business_photos,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

//        holder.tv_title.setText(""+list.get(position).getId());
//        Utill.imageShowLarge(context,holder.iv_email_attechedImage,""+list.get(position).getPath());

//        holder.iv_attechedImage.setImageBitmap(list.get(position));
        Glide.with(context).load(Uri.parse("file://" + list.get(position))).into(holder.iv_attechedImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView iv_attechedImage,iv_cancelBanner;

        public ViewHolder(View itemView) {
            super(itemView);
//            tv_title = (TextView) itemView.findViewById(R.id.tv_imagename);
            iv_attechedImage = (ImageView) itemView.findViewById(R.id.iv_attached_image);
            iv_cancelBanner = (ImageView) itemView.findViewById(R.id.iv_cancelBanner);
            iv_cancelBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   AlertDialog();
                     }
            });
        }
       AlertDialog alertdialog;
       public void AlertDialog()
       {
           final AlertDialog.Builder builder=new AlertDialog.Builder(context);
//           builder.setTitle("Are you sure ?");
           builder.setMessage("Do you want to remove this image ?");
           builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   list.remove(getAdapterPosition());
                   notifyDataSetChanged();

               }
           }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   alertdialog .dismiss();
               }
           });
          alertdialog =builder.create();

           alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
               @Override
               public void onShow(DialogInterface dialog) {

                 /*  TextView titleText = (TextView) alertdialog.findViewById(R.id.alertTitle);
                   if(titleText != null) {
                       titleText.setGravity(Gravity.CENTER);
                   }
*/
                   TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                   if(messageText != null) {
                       messageText.setGravity(Gravity.CENTER);
                   }
               }
           });
           alertdialog.show();

       }
    }


}

