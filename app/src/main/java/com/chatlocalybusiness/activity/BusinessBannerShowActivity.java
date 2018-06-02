package com.chatlocalybusiness.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.apiModel.DeleteImagesModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.ui.TouchImageView;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by windows on 1/11/2018.
 */
public class BusinessBannerShowActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup rl_main;

    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(BusinessBannerShowActivity.this, 0, rl_main);
    }

    private ViewPager viewpager;
    private BannerShowAdapter bannerShowAdapter;
    private BusinessInfoModelNew.BusinessDetail info;
    private ImageView iv_arrowBack, iv_deleteIcon, iv_share;
    private AlertDialog alertdialog;
    private ChatBusinessSharedPreference preference;
    private Utill utill;
    private int position = -1;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannershow);
        rl_main = (ViewGroup) findViewById(R.id.rl_main);
        utill = new Utill();
        preference = new ChatBusinessSharedPreference(BusinessBannerShowActivity.this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        iv_deleteIcon = (ImageView) findViewById(R.id.iv_deleteIcon);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        TextView tv_businessName=(TextView)findViewById(R.id.tv_businessName);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            info = (BusinessInfoModelNew.BusinessDetail) b.getSerializable(Constants.PRODUCT_SERIALIZABLE);
            position = b.getInt(Constants.POSITION);
            tv_businessName.setText(info.getBusinessName());
            setImages();
        }
        iv_arrowBack.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
        iv_share.setOnClickListener(this);
    }

    private void setImages() {
        viewpager = (ViewPager) findViewById(R.id.viewPager);
        bannerShowAdapter = new BannerShowAdapter(BusinessBannerShowActivity.this, info);
        viewpager.setAdapter(bannerShowAdapter);
        if (position > -1)
            viewpager.setCurrentItem(position);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.iv_deleteIcon:
                removeAlertDialog(viewpager.getCurrentItem());
                break;
            case R.id.iv_share:
                shareImageIntent(viewpager);
                break;
        }
    }

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
//        share.putExtra(Intent.EXTRA_TEXT, "Find this product on ChatLocaly " + "\nhttps://play.google.com/store/apps/details?id=" + getPackageName());

        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(sharefile));

        startActivity(Intent.createChooser(share, getString(R.string.app_name)));
        viewGroup.setDrawingCacheEnabled(false);

    }


    List<BusinessInfoModelNew.BusinessImage> businessImageList;

    public class BannerShowAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public BannerShowAdapter(Context context, BusinessInfoModelNew.BusinessDetail info) {
            mContext = context;
            businessImageList = info.getBusinessImages();
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            if (businessImageList != null)
                return businessImageList.size();
            else return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            TouchImageView imageView = (TouchImageView) itemView.findViewById(R.id.imageView);
            Glide.with(mContext).load(businessImageList.get(position).getImageName()).into(imageView);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

    }

    public void removeAlertDialog(final int adapterPositio) {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//           builder.setTitle("Are you sure ?");
        builder.setMessage("Do you want to remove this image ?");
        builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                position=viewpager.getCurrentItem();
                String deletedImageId = businessImageList.get(adapterPositio).getBimId();
                removeImageApi(deletedImageId, adapterPositio);

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

    public void removeImageApi(String imageId, final int adapterposition) {
        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "fhh");
        param.put("b_id", String.valueOf(preference.getBusinessId()));
        param.put("bim_ids[0]", imageId);


        Call<DeleteImagesModel> call = apiServices.deleteImages(param);
        call.enqueue(new Callback<DeleteImagesModel>() {
            @Override
            public void onResponse(Call<DeleteImagesModel> call, Response<DeleteImagesModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        businessImageList.remove(adapterposition);
                        if (businessImageList == null || businessImageList.size() < 1) {
                            finish();
//                            sendIntent();
                        } else if (adapterposition < (businessImageList.size() - 1)) {
                            position = adapterposition;
                            setImages();
                        } else {
                            position = 0;
                            setImages();

                        }
                    } else if (response.body().getData().getResultCode().equals("0")) {
                        Toast.makeText(BusinessBannerShowActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<DeleteImagesModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(BusinessBannerShowActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
