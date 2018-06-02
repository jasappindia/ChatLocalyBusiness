package com.chatlocalybusiness.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.getterSetterModel.ProductDetailsModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by windows on 5/3/2018.
 */

public class ChatProductActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onResume() {
        super.onResume();
        ViewGroup rl_main=(ViewGroup)findViewById(R.id.rl_main);
        new BasicUtill().CheckStatus(ChatProductActivity.this,0,rl_main);
    }
    private ViewPager viewPager;
    private ProductImageAdapter productImageAdapter;
    private ImageView iv_arrowBack,iv_edit,iv_share;
    private TextView tv_businessName,tv_proPrice,tv_discription,tv_productName,tv_discountTag,tv_oldPrice;
    private Utill utill;
    private ChatBusinessSharedPreference prefrence;
    private ProductDetailsModel info;
    private int position;
    private LinearLayout ll_share;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showproduct);
        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            info=(ProductDetailsModel)b.getSerializable(Constants.PRODUCT_DETAIL);
            position=b.getInt("POSITION");
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        productImageAdapter=new ChatProductActivity.ProductImageAdapter(ChatProductActivity.this,info);
        viewPager.setAdapter(productImageAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
        init();
        setImageRatio();
    }

    public void setImageRatio()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
        int width = pxToDp(displayMetrics.widthPixels);
        double height=displayMetrics.widthPixels*(0.523);
        int height1=(int)height;

//        iv_banner.setLayoutParams(new RelativeLayout.LayoutParams(width,height));
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewPager.getLayoutParams();
        params.height = height1;
        params.width =displayMetrics.widthPixels ;

    }
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);

    }
    public void init()
    {
        utill=new Utill();
        prefrence=new ChatBusinessSharedPreference(ChatProductActivity.this);
        tv_discription=(TextView)findViewById(R.id.tv_discription);
        tv_proPrice=(TextView)findViewById(R.id.tv_proPrice);
        tv_oldPrice=(TextView)findViewById(R.id.tv_oldPrice);
        tv_productName=(TextView)findViewById(R.id.tv_productName);
        tv_businessName=(TextView)findViewById(R.id.tv_businessName);
        tv_discountTag=(TextView)findViewById(R.id.tv_discountTag);
        iv_arrowBack=(ImageView)findViewById(R.id.iv_arrowBack);
        iv_edit=(ImageView)findViewById(R.id.iv_edit);
        iv_share=(ImageView)findViewById(R.id.iv_share);
        ll_share=(LinearLayout) findViewById(R.id.ll_share);


        tv_businessName.setText(info.getBusinessName());
        tv_discription.setText(info.getDiscription());
        tv_productName.setText(info.getProductName());
        if(info.getDiscount().equals("0")){
            tv_discountTag.setVisibility(View.GONE);
            tv_proPrice.setText(Utill.getPriceFormatted(info.getPrice()));
            tv_oldPrice.setVisibility(View.GONE);}
        else {
            tv_discountTag.setVisibility(View.VISIBLE);
            tv_discountTag.setText(info.getDiscount() + "% off");
            tv_oldPrice.setVisibility(View.VISIBLE);
            tv_oldPrice.setText(Utill.getPriceFormatted(info.getPrice()));
            tv_oldPrice.setPaintFlags(tv_oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            tv_proPrice.setText(Utill.getPriceFormatted( String.valueOf(Utill.calculateNewValue(Double.parseDouble(info.getPrice()),
                    Double.parseDouble(info.getDiscount())))));
        }
        iv_arrowBack.setOnClickListener(this);
        iv_edit.setOnClickListener(this);
        iv_share.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.iv_share:
              /*  Intent shareintent = new Intent(Intent.ACTION_SEND);
                shareintent.setType("text/plain");
                shareintent.putExtra(Intent.EXTRA_TEXT, "http://www.techrepublic.com");
                shareintent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this site!");
                startActivity(Intent.createChooser(shareintent, "Share"));
//                share_bitMap_to_Apps();*/
                shareImageIntent(viewPager);

                break;
            case R.id.iv_edit:
                if(prefrence.getAddProducts().equalsIgnoreCase("1")){
                    Intent intent=new Intent(ChatProductActivity.this, EditProductActivity.class);
                    intent.putExtra("POSITION",position);
                    intent.putExtra(Constants.PRODUCT_DETAIL,info);
                    startActivityForResult(intent,1);}
                else Toast.makeText(this, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /////////////////////////////////whatsapp  share////////////
    public void shareImageIntent(View viewGroup) {
        viewGroup.setDrawingCacheEnabled(true);
        Bitmap bitmap = viewGroup.getDrawingCache();

        File cacha = getApplicationContext().getExternalCacheDir();
        File sharefile = new File(cacha, "share.png");
        try {
            FileOutputStream out = new FileOutputStream(sharefile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

        // Toast.makeText(context, "Saved in Gallery", Toast.LENGTH_SHORT).show();
        viewGroup.setDrawingCacheEnabled(false);
        //now send it out to share
        Intent share = new Intent(Intent.ACTION_SEND);
//        Log.e("image path", Uri.fromFile(sharefile).toString());
        share.setType("*/*");

        share.putExtra(Intent.EXTRA_TITLE, getString(R.string.app_name));
        share.putExtra(Intent.EXTRA_TEXT, "Find this product on ChatLocaly " + "\nhttps://play.google.com/store/apps/details?id=" + getPackageName());

        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(sharefile));

        startActivity(Intent.createChooser(share, getString(R.string.app_name)));
        viewGroup.setDrawingCacheEnabled(false);

    }



    /////////////////////////////////
    public void share_bitMap_to_Apps() {

        Intent i = new Intent(Intent.ACTION_SEND);

        i.setType("image/*");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        i.putExtra(Intent.EXTRA_STREAM, getImageUri(ChatProductActivity.this, getBitmapFromView(ll_share)));
        try {
            startActivity(Intent.createChooser(i, "My Profile ..."));
        } catch (android.content.ActivityNotFoundException ex) {

            ex.printStackTrace();
        }


    }
    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),      view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    //    BusinessInfoModel
    private class ProductImageAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;
        List<ProductDetailsModel.ProductImages> imageList;
        public ProductImageAdapter(Context context , ProductDetailsModel info) {

            mContext = context;
            this.imageList=info.getProductImages();
//            this.businessImageList= info.getBusinessImages();
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
            View itemView = mLayoutInflater.inflate(R.layout.product_pager_item, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            Glide.with(mContext).load(imageList.get(position).getImageName()).into(imageView);
//                 imageView.setImageResource(R.drawable.banner);
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }



}
