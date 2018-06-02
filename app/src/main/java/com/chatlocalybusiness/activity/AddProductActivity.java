package com.chatlocalybusiness.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.BusinessPhotoAdapter;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.AddProductsModel;
import com.chatlocalybusiness.apiModel.BrandListModel;
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
 * Created by windows on 1/10/2018.
 */
public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(AddProductActivity.this, 0, rl_main);
    }

    ViewGroup rl_main;
    private static final int REQUEST_PERMISSIONS = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int SELECT_PICTURE = 103;
    private ImageView iv_arrowBack;
    private TextView tv_galleryCount;
    private EditText et_productName, et_price, et_sku, et_discountPercent, et_discountValue,
            et_productType, et_discription;
    private AutoCompleteTextView et_brandName;
    private RadioGroup rg_status;
    private ImageView iv_proThumb, iv_proGallery;
    private Button btn_submit;
    private Utill utill;
    private ChatBusinessSharedPreference preference;
    private BusinessPhotoAdapter businessPhotoAdapter;
    public String filePath = null;
    private Bitmap bitmapUser;
    private Uri fileUri = null;
    private String chooserDialogTitle;
    private ArrayList<Bitmap> imageBitmapList;
    private ArrayList<String> filePathList;
    private ArrayList<String> imagePathList;
    private ProgressBar progressBar;
    private String status = null;
    private String strSku, strDiscount;
    private Button btn_addThumb, btn_addPhotos;
    private List<BrandListModel.BrandList> brandList = null;
    private ViewPager vp_proGallery;
    private PagerForProductGallery pagerForProductGallery;
    private ImageButton ib_left, ib_right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        rl_main = (ViewGroup) findViewById(R.id.rl_main);
        init();
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        iv_arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        new GetBrandList().execute();
        Bundle b = getIntent().getExtras();

    }

    public void setVpProGallery() {
        if (filePathList != null) {
            if (filePathList.size() > 0) {

                tv_galleryCount.setVisibility(View.VISIBLE);
                pagerForProductGallery = new PagerForProductGallery(AddProductActivity.this, filePathList);
                vp_proGallery.setAdapter(pagerForProductGallery);
            }
        }
        vp_proGallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // if you want the second page, for example

                tv_galleryCount.setText(String.valueOf(position + 1) + "/" + filePathList.size());  //Your code here

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setImageButton();
    }

    public void init() {
        utill = new Utill();
        imageBitmapList = new ArrayList<>();
        filePathList = new ArrayList<>();
        imagePathList = new ArrayList<>();
        preference = new ChatBusinessSharedPreference(AddProductActivity.this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        et_productName = (EditText) findViewById(R.id.et_productName);
        et_price = (EditText) findViewById(R.id.et_price);
        et_sku = (EditText) findViewById(R.id.et_sku);
        et_discountPercent = (EditText) findViewById(R.id.et_discountPercent);
        et_discountValue = (EditText) findViewById(R.id.et_discountValue);
        et_productType = (EditText) findViewById(R.id.et_productType);
        et_brandName = (AutoCompleteTextView) findViewById(R.id.et_brandName);
        et_discription = (EditText) findViewById(R.id.et_discription);
        tv_galleryCount = (TextView) findViewById(R.id.tv_galleryCount);
        vp_proGallery = (ViewPager) findViewById(R.id.vp_proGallery);
        rg_status = (RadioGroup) findViewById(R.id.rg_status);
        iv_proThumb = (ImageView) findViewById(R.id.iv_serviceImage);
//        iv_proGallery=(ImageView)findViewById(R.id.iv_proGallery);
        ib_left = (ImageButton) findViewById(R.id.ib_left);
        ib_right = (ImageButton) findViewById(R.id.ib_right);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_addPhotos = (Button) findViewById(R.id.btn_addPhotos);
        btn_addThumb = (Button) findViewById(R.id.btn_addThumb);

        btn_addThumb.setOnClickListener(this);
        btn_addPhotos.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        ib_right.setOnClickListener(this);
        ib_left.setOnClickListener(this);

        iv_proThumb.setOnClickListener(this);
        et_productType.setOnClickListener(this);


        et_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    et_discountPercent.setEnabled(true);

                } else {
                    et_discountPercent.setText("");
                    et_discountValue.setText("");
                    et_discountPercent.setEnabled(false);
                    et_discountValue.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_discountPercent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!et_price.getText().toString().trim().equals("") && !charSequence.toString().trim().equals("")) {

                    if (Double.parseDouble(charSequence.toString()) <= 100) {
                        double price = Double.parseDouble(et_price.getText().toString());
                        double discount = Double.parseDouble(charSequence.toString());

                        double value = (price * discount) / 100;
                        double finalValue = price - value;
//                  et_discountValue.setEnabled(true);
                        et_discountValue.setText(String.valueOf(finalValue));
                    }else
                        Toast.makeText(AddProductActivity.this, "Discount % can't be greater than 100", Toast.LENGTH_SHORT).show();
                    } else {
                        et_discountValue.setEnabled(false);
                        et_discountValue.setText("Price after discount");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_publish:
                status = "1";
                // Pirates are the best
                break;
            case R.id.rb_unPublish:
                status = "0";
                // Ninjas rule
                break;
        }
    }

    public Dialog productType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);

        String[] array = {"Branded", "Unbranded"};

        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    et_brandName.setEnabled(true);
                    et_brandName.setHint("Brand Name");
                    et_productType.setText("Branded");
                } else if (i == 1) {
                    et_brandName.setText("");
                    et_brandName.setHint("Brand Name");
                    et_brandName.setEnabled(false);

                    et_productType.setText("Unbranded");
                }
            }
        });

        return builder.create();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_submit:
                if (!isEmpty()) {
                    if (et_sku.getText().toString().trim().isEmpty() || et_sku.getText().toString().trim().equals(""))
                        strSku = "";
                    else strSku = et_sku.getText().toString().trim();

                    if (et_discountPercent.getText().toString().trim().isEmpty() || et_discountPercent.getText().toString().trim().equals(""))
                        strDiscount = "0";
                    else strDiscount = et_discountPercent.getText().toString().trim();

                    addProduct(filePathList, filePath, String.valueOf(preference.getBusinessId()),
                            preference.getUserId(), et_productName.getText().toString().trim(),
                            et_price.getText().toString().trim(), strSku, strDiscount, status,
                            et_discription.getText().toString());
                }
                break;
            case R.id.btn_addThumb:
                chooserDialogTitle = "ADD BANNER";
                Dialog dialog = chooserDialog(chooserDialogTitle);
                dialog.show();
                break;
            case R.id.btn_addPhotos:
                chooserDialogTitle = "ADD PHOTOS";
                Dialog dialog2 = chooserDialog(chooserDialogTitle);
                dialog2.show();
                break;
            case R.id.et_productType:
                Dialog dialog3 = productType();
                dialog3.show();
                break;
            case R.id.iv_serviceImage:
                if (filePath != null) {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(filePath);
                    Intent intent = new Intent(AddProductActivity.this, ProductGalleryShowActivity.class);
                    intent.putExtra(Constants.PRODUCT_SERIALIZABLE, list);
                    intent.putExtra(Constants.POSITION, 0);
                    startActivity(intent);
                }
                break;
           /* case R.id.vp_proGallery :
                if(filePathList!=null){
                    if(filePathList.size()>0){
                Intent intent2=new Intent(AddProductActivity.this,ProductGalleryShowActivity.class);
                intent2.putExtra(Constants.PRODUCT_SERIALIZABLE,filePathList);
                startActivity(intent2);
                }
                }
                break;*/
        }
    }

    public void takePhoto() {
        Constants.IMAGE_SELECT_CAPTURE = "camera";
        if (chooserDialogTitle.equalsIgnoreCase("ADD BANNER")) {
            Constants.limit = 1;
            startActivityForResult(new Intent(AddProductActivity.this, SingleImageSelectionActivity.class), 1);
        } else {
            Constants.limit = 10;
            startActivityForResult(new Intent(AddProductActivity.this, BusinessBannerCropActivity.class), 10);
        }
    }

    public void chooseFromGallery() {
        Constants.IMAGE_SELECT_CAPTURE = "gallery";
        if (chooserDialogTitle.equalsIgnoreCase("ADD BANNER")) {
            Constants.limit = 1;
            startActivityForResult(new Intent(AddProductActivity.this, SingleImageSelectionActivity.class), 1);
        } else {
            Constants.limit = 10;
            startActivityForResult(new Intent(AddProductActivity.this, BusinessBannerCropActivity.class), 10);
        }
    }

    public void setImageButton() {
        ImageButton ib_left = (ImageButton) findViewById(R.id.ib_left);
        ImageButton ib_right = (ImageButton) findViewById(R.id.ib_right);
        if (filePathList.size() > 0) {
            tv_galleryCount.setVisibility(View.VISIBLE);
            tv_galleryCount.setText("1/" + filePathList.size());  //Your code here
        } else tv_galleryCount.setVisibility(View.GONE);

        ib_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vp_proGallery.getCurrentItem() > 0)
                    vp_proGallery.setCurrentItem(vp_proGallery.getCurrentItem() - 1);
            }
        });
        ib_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vp_proGallery.getCurrentItem() < filePathList.size())
                    vp_proGallery.setCurrentItem(vp_proGallery.getCurrentItem() + 1);
            }
        });
    }

    public Dialog chooserDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
//                String strEditText = data.getStringExtra("editTextValue");
                filePath = data.getStringExtra(Constants.IMAGE_PATH);
//                iv_proThumb.setImageURI(Uri.parse("file://" + filePath));
                Glide.with(AddProductActivity.this).load(Uri.parse("file://" + filePath)).into(iv_proThumb);

            } else {
                imagePathList = data.getStringArrayListExtra(Constants.IMAGE_PATH_LIST);
                if (filePathList != null) {
                    if ((filePathList.size() + imagePathList.size()) < Constants.limit)
                        filePathList.addAll(imagePathList);
                    else
                        Toast.makeText(this, "Images can't be more than " + Constants.limit, Toast.LENGTH_SHORT).show();
                } else filePathList.addAll(imagePathList);

                setVpProGallery();
            }
        }

    }

    public void setBtn_doneClickable(boolean b) {
        if (b) {
            btn_submit.setClickable(b);
            btn_submit.setFocusable(b);
            btn_submit.setTextColor(ContextCompat.getColor(AddProductActivity.this, R.color.white));
            btn_submit.setBackgroundResource(R.drawable.blue_btn_bg);

        } else {
            btn_submit.setClickable(b);
            btn_submit.setFocusable(b);
            btn_submit.setTextColor(ContextCompat.getColor(AddProductActivity.this, R.color.light_gray));
            btn_submit.setBackgroundResource(R.drawable.gray_btn_bg);

        }
    }

    String brandId = "0";
    String brandName = "";

    public void addProduct(ArrayList<String> photosPathList, String bannerImage, String businessID,
                           String userId, String productName, String price, String sku, String strDiscount,
                           String status, String discription) {

        progressBar.setVisibility(View.VISIBLE);
        brandName = et_brandName.getText().toString().trim();

        if (!brandName.equals("")) {
         int i=-1;
            if(brandNameList!=null && brandNameList.size()>0)
             i = brandNameList.indexOf(brandName);
            if (i == -1)
                brandId = "0";
            else brandId = brandList.get(i).getBrandId();

        } else brandName = "UnBranded";

        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        List<MultipartBody.Part> photosParam = new ArrayList<>();
        HashMap<String, RequestBody> param = new HashMap<>();

        if (photosPathList != null && photosPathList.size() > 0)
            for (int i = 0; i < photosPathList.size(); i++) {
                File file = new File(photosPathList.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

//         MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part body = MultipartBody.Part.createFormData("product_galleries[" + i + "]", file.getName(), requestFile);
//                MultipartBody.Part body = MultipartBody.Part.create(requestFile);
                photosParam.add(body);
            }

        MultipartBody.Part bannerbody = null;
        if (bannerImage != null) {
            File bannerFile = new File(bannerImage);
            RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), bannerFile);
            bannerbody = MultipartBody.Part.createFormData("product_image", bannerFile.getName(), requestfile);
        }
        //      add another part within the multipart request
        RequestBody b_id = RequestBody.create(MediaType.parse("multipart/form-data"), businessID);
        RequestBody encrypt_key = RequestBody.create(MediaType.parse("multipart/form-data"), "df");
        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody reportProName = RequestBody.create(MediaType.parse("multipart/form-data"), productName);
        RequestBody reportPrice = RequestBody.create(MediaType.parse("multipart/form-data"), price);
        RequestBody reportSku = RequestBody.create(MediaType.parse("multipart/form-data"), sku);
        RequestBody reportstrDiscount = RequestBody.create(MediaType.parse("multipart/form-data"), strDiscount);
        RequestBody reportstatus = RequestBody.create(MediaType.parse("multipart/form-data"), status);
        RequestBody reportdiscription = RequestBody.create(MediaType.parse("multipart/form-data"), discription);
        RequestBody reportBrandId = RequestBody.create(MediaType.parse("multipart/form-data"), brandId);
        RequestBody reportBrandName = RequestBody.create(MediaType.parse("multipart/form-data"), brandName);

        param.put("encrypt_key", encrypt_key);
        param.put("b_user_id", user_id);
        param.put("b_id", b_id);
        param.put("product_name", reportProName);
        param.put("price", reportPrice);
        param.put("description", reportdiscription);
        param.put("sku", reportSku);
        param.put("discount", reportstrDiscount);
        param.put("product_status", reportstatus);
        param.put("brand_id", reportBrandId);
        param.put("brand_name", reportBrandName);

        Call<AddProductsModel> call = apiServices.addProduct(photosParam, bannerbody, param);
        call.enqueue(new Callback<AddProductsModel>() {
            @Override
            public void onResponse(Call<AddProductsModel> call, Response<AddProductsModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        Toast.makeText(AddProductActivity.this, "Product added succesfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(AddProductActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<AddProductsModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddProductActivity.this, "Check your Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isEmpty() {
        if (et_productName.getText().toString().trim().isEmpty() || et_productName.getText().toString().trim() == null || et_productName.getText().toString().trim().equals("")) {
            et_productName.setError("Enter the product name");
            return true;
        } else if (et_price.getText().toString().trim().isEmpty() || et_price.getText().toString().trim() == null || et_price.getText().toString().trim().equals("")) {
            et_price.setError("Enter the price");

            return true;
        } else if (et_productType.getText().toString().trim().isEmpty() || et_productType.getText().toString().trim() == null || et_productType.getText().toString().trim().equals("")) {
            et_productType.setError("Select Product Type");

            return true;
        } else if (et_productType.getText().toString().trim().equals("Branded") && et_brandName.getText().toString().trim().equals("")) {
            et_brandName.setError("Enter Brand Name");
            return true;
        } else if (status == null) {
            Toast.makeText(getApplicationContext(), "Select status of Product", Toast.LENGTH_SHORT).show();
            return true;
        } else if (et_discription.getText().toString().trim().isEmpty() || et_discription.getText().toString().trim() == null || et_discription.getText().toString().trim().equals("")) {
            et_discription.setError("Select Product Type");

            return true;
        } else
            return false;

    }

    private class GetBrandList extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            getBrandList();

            return null;

        }
    }

    ArrayList<String> brandNameList;

    public void getBrandList() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);

        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", Constants.Encryption_Key);
        param.put("b_user_id", preference.getUserId());
        Call<BrandListModel> call = apiServices.getBrandList(param);
        call.enqueue(new Callback<BrandListModel>() {
            @Override
            public void onResponse(Call<BrandListModel> call, Response<BrandListModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        brandList = response.body().getData().getBrandList();
                        if (brandList != null) {
                            String[] brandArray = new String[brandList.size()];
                            brandNameList = new ArrayList<String>();
                            for (int i = 0; i < brandList.size(); i++) {
                                brandArray[i] = brandList.get(i).getBrandName();
                                brandNameList.add(brandList.get(i).getBrandName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddProductActivity.this, android.R.layout.simple_dropdown_item_1line, brandArray);
                            et_brandName.setAdapter(adapter);
                        }
                        businessProfileApi = 1;
                    }
                }
            }

            @Override
            public void onFailure(Call<BrandListModel> call, Throwable t) {

            }
        });
    }

    private class PagerForProductGallery extends PagerAdapter {
        Context context;
        ArrayList<String> filePathList;
        LayoutInflater mLayoutInflater;

        public PagerForProductGallery(Context context, ArrayList<String> filePathList) {
            this.context = context;
            this.filePathList = filePathList;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return filePathList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {


            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View itemView = mLayoutInflater.inflate(R.layout.product_mage_pager, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            file:///storage/emulated/0/cropped1517381496911.jpg
//            Glide.with(context).load().into(imageView);

//            tv_galleryCount.setText(String.valueOf(vp_proGallery.getCurrentItem()) + "/" + filePathList.size());
//            imageView.setImageURI(Uri.parse("file://" + filePathList.get(position)));
            Glide.with(AddProductActivity.this).load(Uri.parse("file://" + filePathList.get(position))).into(imageView);

            container.addView(itemView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent2 = new Intent(AddProductActivity.this, ProductGalleryShowActivity.class);
                    intent2.putExtra(Constants.PRODUCT_SERIALIZABLE, filePathList);
                    intent2.putExtra(Constants.POSITION, position);
                    startActivity(intent2);

                }
            });

            return itemView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
