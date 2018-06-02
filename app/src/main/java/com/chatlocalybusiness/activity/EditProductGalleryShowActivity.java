package com.chatlocalybusiness.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;

import java.util.ArrayList;

/**
 * Created by windows on 1/11/2018.
 */
public class EditProductGalleryShowActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private PagerForEditProductGallery bannerShowAdapter;
//    List<BusinessInfoModel.ProductImage> info;
//    ArrayList<BusinessInfoModel.ProductImage> info1;
    ArrayList<String> info;
    ArrayList<String> imageList;
    ArrayList<String> info1;
    private int position;
    private ImageView iv_arrowBack;
    private ImageView iv_deleteIcon;
    private   String productImage;
    ArrayList<String> imagesPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannershow);

         init();
         Bundle b = getIntent().getExtras();
         if (b != null) {
             imageList = b.getStringArrayList(Constants.PRODUCT_SERIALIZABLE);
             info.addAll(imageList);
            position = b.getInt(Constants.POSITION);
            info1 = new ArrayList<>();
            info1.add(info.get(0));
             productImage = b.getString("ProductImage");
            setImages();
        }

    }

    public void init() {
        info=new ArrayList<>();
        iv_arrowBack=(ImageView)findViewById(R.id.iv_arrowBack);
        iv_deleteIcon=(ImageView)findViewById(R.id.iv_deleteIcon);
        imagesPosition=new ArrayList<>();
        iv_arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        iv_deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAlertDialog(viewpager.getCurrentItem());

            }
        });

    }

     private void setImages() {
        viewpager = (ViewPager) findViewById(R.id.viewPager);
        if (productImage.equalsIgnoreCase("ProGallery"))
            bannerShowAdapter = new PagerForEditProductGallery(EditProductGalleryShowActivity.this, info);
        else
            bannerShowAdapter = new PagerForEditProductGallery(EditProductGalleryShowActivity.this, info1);

        viewpager.setAdapter(bannerShowAdapter);
        viewpager.setCurrentItem(position);

    }
    AlertDialog alertdialog;

    public void removeAlertDialog(final int currentItem) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//           builder.setTitle("Are you sure ?");
        builder.setMessage("Do you want to remove this image ?");
        builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!productImage.equalsIgnoreCase("ProGallery"))
                {
                    setResult(RESULT_OK);
                    finish();
                }
                else{

                    position=viewpager.getCurrentItem();
                    imagesPosition.add(info.get(position));
                    info.remove(position);
//                    setImages();
                     if(info==null||info.size()<1) {
                         sendIntent();
                     }
                   else  if(position<(info.size()-1)) {
                          setImages();
                     }else{
                         position = 0;
                         setImages();

                     }
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertdialog.dismiss();
            }
        });
        alertdialog = builder.create();

        alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                TextView messageText = (TextView) alertdialog.findViewById(android.R.id.message);
                if (messageText != null) {
                    messageText.setGravity(Gravity.CENTER);
                }
            }
        });
        alertdialog.show();
    }

    @Override
    public void onBackPressed() {
        sendIntent();
    }
    public void sendIntent() {
        Intent intent = new Intent();
        if (imagesPosition!=null)
            if(imagesPosition.size()>0)
            intent.putStringArrayListExtra(Constants.DELETED_IMAGES, imagesPosition);
        setResult(RESULT_OK, intent);
        finish();
    }

        public class PagerForEditProductGallery extends PagerAdapter {
        Context context;
        ArrayList<String> info;
        LayoutInflater mLayoutInflater;
        public PagerForEditProductGallery(Context context, ArrayList<String> info)
        {
            this.context=context;
            this.info=info;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            if(info!=null)
                return info.size();
            else return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {


            return view==((LinearLayout)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            file:///storage/emulated/0/cropped1517381496911.jpg
            if(info.get(position).contains("emulated"))
            imageView.setImageURI(Uri.parse("file://"+info.get(position)));
            else
            Glide.with(context).load(info.get(position)).into(imageView);
            container.addView(itemView);
            return itemView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }



}
