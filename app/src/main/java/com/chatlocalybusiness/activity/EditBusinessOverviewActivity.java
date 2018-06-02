package com.chatlocalybusiness.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.BusinessPhotoAdapter;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.BusinessBannerModel;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chatlocalybusiness.activity.BusinessProfileActivity.businessProfileApi;

/**
 * Created by windows on 12/13/2017.
 */

public class EditBusinessOverviewActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewGroup rl_main;

    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(EditBusinessOverviewActivity.this,0,rl_main);
    }
    private static final int REQUEST_PERMISSIONS = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int SELECT_PICTURE = 103;
    private Button btn_done;
    private ImageView iv_banner, iv_bannerCancel, iv_arrowBack;
    private TextView tv_addPhotos, tv_addBanner;
    private RelativeLayout rl_banner;
    private RecyclerView rv_photos;
    private LinearLayoutManager layoutManager;
    private Utill utill;
    private ChatBusinessSharedPreference preference;
    private BusinessPhotoAdapter businessPhotoAdapter;
    public String filePath = null;
    private Bitmap bitmapUser;
    public static String selectedImagePath;/*, filepath;*/
    //
    private Uri fileUri = null;
    public static String chooserDialogTitle="";
    ArrayList<String> filePathList;
    private AlertDialog alertdialog;
    private ProgressBar progressBar;
    private BusinessInfoModelNew.BusinessDetail info;
    private ArrayList<String> imagePathList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_overview);
        rl_main=(ViewGroup)findViewById(R.id.rl_main);

        Bundle b=getIntent().getExtras();
        init();
        if(b!=null)
        {
            String editOverview=b.getString("Edit");
            info=(BusinessInfoModelNew.BusinessDetail)b.getSerializable(Constants.BUSINESS_SETUP_DETAILS);

            if(editOverview.equalsIgnoreCase("Edit"))
            {
                setValues();

            }
        }
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        iv_arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void setValues()
    {
      Glide.with(this).load(info.getBusinessBanner()).into(iv_banner);

    }
    public void init() {
        filePathList = new ArrayList<>();

        utill = new Utill();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        preference = new ChatBusinessSharedPreference(EditBusinessOverviewActivity.this);
        rl_banner = (RelativeLayout) findViewById(R.id.rl_banner);
        btn_done = (Button) findViewById(R.id.btn_done);
        iv_banner = (ImageView) findViewById(R.id.iv_banner);
        iv_bannerCancel = (ImageView) findViewById(R.id.iv_bannerCancel);
        tv_addPhotos = (TextView) findViewById(R.id.tv_addPhotos);
        tv_addBanner = (TextView) findViewById(R.id.tv_addBanner);
        rv_photos = (RecyclerView) findViewById(R.id.rv_photos);

        tv_addBanner.setOnClickListener(this);
        tv_addPhotos.setOnClickListener(this);
        iv_bannerCancel.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        setBtn_doneClickable(false);

    }

    public void setRv_photos(ArrayList<String> list) {
        rv_photos.setVisibility(View.VISIBLE);
        businessPhotoAdapter = new BusinessPhotoAdapter(EditBusinessOverviewActivity.this, list);
        layoutManager = new LinearLayoutManager(EditBusinessOverviewActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rv_photos.setLayoutManager(layoutManager);
        rv_photos.setAdapter(businessPhotoAdapter);
    }

    public void takePhoto() {
        Constants.IMAGE_SELECT_CAPTURE = "camera";
        if (chooserDialogTitle.equalsIgnoreCase("ADD BANNER")) {
            Constants.limit = 1;
            startActivityForResult(new Intent(EditBusinessOverviewActivity.this, SingleImageSelectionActivity.class), 1);
        } else {
            Constants.limit = 10;
            startActivityForResult(new Intent(EditBusinessOverviewActivity.this, BusinessBannerCropActivity.class), 10);
        }
    }

    public void chooseFromGallery() {
        Constants.IMAGE_SELECT_CAPTURE = "gallery";
        if (chooserDialogTitle.equalsIgnoreCase("ADD BANNER")) {
            Constants.limit = 1;
            startActivityForResult(new Intent(EditBusinessOverviewActivity.this, SingleImageSelectionActivity.class), 1);
        } else {
            Constants.limit = 10;
            startActivityForResult(new Intent(EditBusinessOverviewActivity.this, BusinessBannerCropActivity.class), 10);
        }
    }

    public Dialog chooserDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditBusinessOverviewActivity.this);

        String[] array = {"Take Photo", "Choose from Gallery"};

        builder.setTitle(title).setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    takePhoto();

                } else if (i == 1) {
                    chooseFromGallery();
                }

            }
        });

        return builder.create();
    }

    public void setImageRatio() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
        int width = pxToDp(displayMetrics.widthPixels);
        double height = displayMetrics.widthPixels * (0.523);
        int height1 = (int) height;

//        iv_banner.setLayoutParams(new RelativeLayout.LayoutParams(width,height));
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_banner.getLayoutParams();
        params.height = height1;
        params.width = displayMetrics.widthPixels;

    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            chooserDialogTitle="";
            if (requestCode == 1) {
//                String strEditText = data.getStringExtra("editTextValue");
                filePath = data.getStringExtra(Constants.IMAGE_PATH);
//                iv_proThumb.setImageURI(Uri.parse("file://" + filePath));
                rl_banner.setVisibility(View.VISIBLE);
                setImageRatio();
                Glide.with(EditBusinessOverviewActivity.this).load(Uri.parse("file://" + filePath)).into(iv_banner);
                tv_addBanner.setText("Edit");
                setBtn_doneClickable(true);

            } else {
                imagePathList = data.getStringArrayListExtra(Constants.IMAGE_PATH_LIST);
                if (filePathList != null) {
                    if ((filePathList.size() + imagePathList.size()) < Constants.limit)
                        filePathList.addAll(imagePathList);
                    else
                        Toast.makeText(this, "Images can't be more than " + Constants.limit, Toast.LENGTH_SHORT).show();
                } else filePathList.addAll(imagePathList);

                setRv_photos(filePathList);

            }
        } else if (resultCode == RESULT_CANCELED && filePath == null) {
            rl_banner.setVisibility(View.GONE);
            setBtn_doneClickable(false);
        }

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_addBanner:
                chooserDialogTitle = "ADD BANNER";
                Dialog dialog = chooserDialog(chooserDialogTitle);
                dialog.show();
                break;

            case R.id.tv_addPhotos:
                chooserDialogTitle = "ADD PHOTOS";
                Dialog dialog2 = chooserDialog(chooserDialogTitle);
                dialog2.show();
                break;
            case R.id.iv_bannerCancel:
                AlertDialog();
                break;
            case R.id.btn_done:

                postBusinessImages(filePathList, filePath, String.valueOf(preference.getBusinessId()), preference.getUserId());
//                replacefragment();
                break;
        }
    }

    public void AlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditBusinessOverviewActivity.this);
//           builder.setTitle("Are you sure ?");
        builder.setMessage("Do you want to remove this image ?");
        builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                iv_banner.setImageBitmap(null);
                tv_addBanner.setText("ADD");
                rl_banner.setVisibility(View.GONE);
                filePath = "";
                setBtn_doneClickable(false);
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

    public void setBtn_doneClickable(boolean b) {
        if (b) {
            btn_done.setClickable(b);
            btn_done.setFocusable(b);
            btn_done.setTextColor(ContextCompat.getColor(EditBusinessOverviewActivity.this, R.color.white));
            btn_done.setBackgroundResource(R.drawable.blue_btn_bg);

        } else {
            btn_done.setClickable(b);
            btn_done.setFocusable(b);
            btn_done.setTextColor(ContextCompat.getColor(EditBusinessOverviewActivity.this, R.color.light_gray));
            btn_done.setBackgroundResource(R.drawable.gray_btn_bg);

        }
    }

    public void postBusinessImages(ArrayList<String> photosPathList, String bannerImage, final String businessID, String userId) {

        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
//        HashMap<String , MultipartBody.Part> photosParam=new HashMap<>();
        List<MultipartBody.Part> photosParam = new ArrayList<MultipartBody.Part>();
        HashMap<String, RequestBody> param = new HashMap<>();
//        MultipartBody.Part body=null;
        if (photosPathList != null && photosPathList.size() > 0)
            for (int i = 0; i < photosPathList.size(); i++) {
                File file = new File(photosPathList.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

//         MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part body = MultipartBody.Part.createFormData("business_images[" + i + "]", file.getName(), requestFile);
//            body= MultipartBody.Part.create(requestFile);
                photosParam.add(body);
            }

        MultipartBody.Part bannerbody = null;
        if (bannerImage != null) {
            File bannerFile = new File(bannerImage);
            RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), bannerFile);
            bannerbody = MultipartBody.Part.createFormData("business_banner", bannerFile.getName(), requestfile);
        }
        //      add another part within the multipart request
        RequestBody b_id = RequestBody.create(MediaType.parse("multipart/form-data"), businessID);
        RequestBody encrypt_key = RequestBody.create(MediaType.parse("multipart/form-data"), "df");
        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), userId);

        param.put("encrypt_key", encrypt_key);
        param.put("b_user_id", user_id);
        param.put("b_id", b_id);

        Call<BusinessBannerModel> call = apiServices.postBusinessPhotos(photosParam, bannerbody, param);
        call.enqueue(new Callback<BusinessBannerModel>() {
            @Override
            public void onResponse(Call<BusinessBannerModel> call, Response<BusinessBannerModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        Toast.makeText(EditBusinessOverviewActivity.this, "Business Banner updated succesfully", Toast.LENGTH_SHORT).show();
                        businessProfileApi=1;

                        finish();
                    } else if (response.body().getData().getResultCode().equals("0")) {
                        Toast.makeText(EditBusinessOverviewActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<BusinessBannerModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(EditBusinessOverviewActivity.this, "check internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class BusinessPhotoAdapter extends RecyclerView.Adapter<BusinessPhotoAdapter.ViewHolder> {
        Context context;
        ArrayList<String> list;

        public BusinessPhotoAdapter(Context context, ArrayList<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public BusinessPhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_business_photos,parent, false);
           BusinessPhotoAdapter.ViewHolder viewHolder = new BusinessPhotoAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(BusinessPhotoAdapter.ViewHolder holder, final int position) {

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

}
