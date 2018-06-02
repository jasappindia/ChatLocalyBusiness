package com.chatlocalybusiness.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.EditProductActivity;
import com.chatlocalybusiness.activity.EditProductGalleryShowActivity;
import com.chatlocalybusiness.utill.Constants;

import java.util.ArrayList;

/**
 * Created by windows on 1/31/2018.
 */

public class PagerForProductGallery extends PagerAdapter {
    Context context;
    ArrayList<String> imageList;
    LayoutInflater mLayoutInflater;

    public PagerForProductGallery(Context context, ArrayList<String> imageList) {
        this.context = context;
        this.imageList = imageList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {


        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.product_mage_pager, container, false);

        final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            file:///storage/emulated/0/cropped1517381496911.jpg
        if(imageList.get(position).contains("emulated"))
            imageView.setImageURI(Uri.parse("file://"+imageList.get(position)));
         else
         Glide.with(context).load(imageList.get(position)).into(imageView);

//        imageView.setImageURI(Uri.parse("file://"+filePathList.get(position)));
        container.addView(itemView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageList!=null)
                    if(imageList.size()>0){
                Intent intent2 = new Intent(context, EditProductGalleryShowActivity.class);
                intent2.putExtra(Constants.PRODUCT_SERIALIZABLE, imageList);
                intent2.putExtra(Constants.POSITION, position);
                intent2.putExtra("ProductImage", "ProGallery");
                ((Activity) context).startActivityForResult(intent2,EditProductActivity.EditGallery);
              }
            }
        });


        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
