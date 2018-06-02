package com.chatlocalybusiness.activity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chatlocalybusiness.R;

import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;

import java.util.ArrayList;

/**
 * Created by windows on 1/11/2018.
 */
public class ProductGalleryShowActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        ViewGroup rl_main=(ViewGroup)findViewById(R.id.rl_main);
        new BasicUtill().CheckStatus(ProductGalleryShowActivity.this,0,rl_main);
    }
    private ViewPager viewpager;
    private ProductGalleryAdapter bannerShowAdapter;
    private ArrayList<String> imageList;
    private int position;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannershow);
        Bundle b = getIntent().getExtras();
        imageList = new ArrayList<>();

        if (b != null) {
            imageList = b.getStringArrayList(Constants.PRODUCT_SERIALIZABLE);
            position = b.getInt(Constants.POSITION);
            setImages();
        }
        init();
    }

    public void init() {
        ImageView  iv_arrowBack=(ImageView)findViewById(R.id.iv_arrowBack);

        iv_arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ImageView  iv_deleteIcon=(ImageView)findViewById(R.id.iv_deleteIcon);


        iv_deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setImages() {
        viewpager = (ViewPager) findViewById(R.id.viewPager);
        bannerShowAdapter = new ProductGalleryAdapter(ProductGalleryShowActivity.this, imageList);
        viewpager.setAdapter(bannerShowAdapter);
        viewpager.setCurrentItem(position);
    }
    public class ProductGalleryAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;
        ArrayList<String> imageList;
        public ProductGalleryAdapter(Context context, ArrayList<String> imageList) {
            mContext = context;
            this.imageList=imageList;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

            imageView.setImageURI(Uri.parse("file://"+imageList.get(position)));
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

    }


    public void deleteImage()
    {
        if(imageList.size()<=1)
        {
          finish();
        } else if(imageList.size()>1)
        {
            imageList.remove(viewpager.getCurrentItem());
        }


    }
}
