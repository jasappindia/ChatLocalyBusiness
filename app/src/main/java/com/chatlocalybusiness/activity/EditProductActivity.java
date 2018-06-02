package com.chatlocalybusiness.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.BusinessPhotoAdapter;
import com.chatlocalybusiness.adapter.PagerForProductGallery;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.AddProductsModel;
import com.chatlocalybusiness.apiModel.BrandListModel;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.getterSetterModel.ProductDetailsModel;
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
 * Created by windows on 1/18/2018.
 */

public class EditProductActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewGroup rl_main;

    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(EditProductActivity.this, 0, rl_main);
    }

    private static final int REQUEST_PERMISSIONS = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int SELECT_PICTURE = 103;
    private static int EditImage = 104;
    public static int EditGallery = 105;

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
    private String chooserDialogTitle;
    private ArrayList<String> filePathList;
    private AlertDialog alertdialog;
    private ProgressBar progressBar;
    private String status = null;
    private String strSku, strDiscount;
    private List<BrandListModel.BrandList> brandList = null;
    //    private BusinessInfoModelNew.BusinessDetail info;
    private ProductDetailsModel info;
    private int position;
    private RadioButton rb_publish, rb_unPublish;
    private ViewPager vp_proGallery;
    private PagerForProductGallery pagerForProductGallery;
    private ArrayList<String> proImageList;

    private ArrayList<String> prothumbList;

    ArrayList<String> deletedImagesUrl;
    private int imagePosition = 0;
    private ArrayList<String> brandNameList;
    //    private List<BusinessInfoModelNew.ProductImage> infoIMageList;
    private List<ProductDetailsModel.ProductImages> infoIMageList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        rl_main = (ViewGroup) findViewById(R.id.rl_main);
        init();

//        getProductDetails();
        getProductDetailsTest();
        new GetBrandList().execute();
    }

    /*
        public void getProductDetails()
        {
            Bundle b = getIntent().getExtras();
            if (b != null) {
    //            info = (BusinessInfoModelNew.BusinessDetail) getIntent().getExtras().getSerializable(Constants.PRODUCT_DETAIL);
                info = (ProductDetailsModel) getIntent().getExtras().getSerializable(Constants.PRODUCT_DETAIL);
                position = b.getInt("POSITION");
                infoIMageList = info.getProducts().get(position).getProductImages();

                if (info.getProducts().get(position).getProductImage() != null)
                    prothumbList.add(info.getProducts().get(position).getProductImage());

                if (infoIMageList != null) {
                    if (infoIMageList.size() > 0) {
                        for (int i = 0; i < infoIMageList.size(); i++) {
                            proImageList.add(infoIMageList.get(i).getImageName());
                        }

                    }
                }
                setProductDetails();
            }
        }*/
    public void getProductDetailsTest() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            info = (ProductDetailsModel) getIntent().getExtras().getSerializable(Constants.PRODUCT_DETAIL);
            position = b.getInt("POSITION");
            infoIMageList = info.getProductImages();

            if (info.getProThumbnail() != null)
                prothumbList.add(info.getProThumbnail());

            if (infoIMageList != null) {
                if (infoIMageList.size() > 0) {
                    for (int i = 0; i < infoIMageList.size(); i++) {
                        proImageList.add(infoIMageList.get(i).getImageName());
                    }

                }
            }
            setProductDetails();
        }

    }


    public void setVpProGallery() {
        if (proImageList != null) {
            tv_galleryCount.setVisibility(View.VISIBLE);
            pagerForProductGallery = new PagerForProductGallery(EditProductActivity.this, proImageList);
            vp_proGallery.setAdapter(pagerForProductGallery);
//                vp_proGallery.setCurrentItem(imagePosition);
        }
        vp_proGallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // if you want the second page, for example
                tv_galleryCount.setText(String.valueOf(position + 1) + "/" + proImageList.size());  //Your code here

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setImageButton();
    }

    public void setImageButton() {
        ImageButton ib_left = (ImageButton) findViewById(R.id.ib_left);
        ImageButton ib_right = (ImageButton) findViewById(R.id.ib_right);
        if (proImageList.size() > 0) {
            tv_galleryCount.setVisibility(View.VISIBLE);
            tv_galleryCount.setText("1/" + proImageList.size());  //Your code here
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
                if (vp_proGallery.getCurrentItem() < proImageList.size())
                    vp_proGallery.setCurrentItem(vp_proGallery.getCurrentItem() + 1);
            }
        });
    }

    public void init() {
        utill = new Utill();
        filePathList = new ArrayList<>();
        proImageList = new ArrayList<>();
        prothumbList = new ArrayList<>();
        deletedImagesUrl = new ArrayList<>();

        preference = new ChatBusinessSharedPreference(EditProductActivity.this);
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
        TextView tv_header = (TextView) findViewById(R.id.tv_header);
        tv_header.setText("Edit Product");
        rb_publish = (RadioButton) findViewById(R.id.rb_publish);
        rb_unPublish = (RadioButton) findViewById(R.id.rb_unPublish);
        rg_status = (RadioGroup) findViewById(R.id.rg_status);
        iv_proThumb = (ImageView) findViewById(R.id.iv_serviceImage);
//        iv_proGallery=(ImageView)findViewById(R.id.iv_proGallery);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        vp_proGallery = (ViewPager) findViewById(R.id.vp_proGallery);
        Button btn_addPhotos = (Button) findViewById(R.id.btn_addPhotos);
        Button btn_addThumb = (Button) findViewById(R.id.btn_addThumb);
        ImageView iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);

        iv_arrowBack.setOnClickListener(this);
        btn_addThumb.setOnClickListener(this);
        btn_addPhotos.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
//        iv_proGallery.setOnClickListener(this);
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
                if (!et_price.getText().toString().equals("") && !charSequence.toString().equals("")) {
                    if (Double.parseDouble(charSequence.toString()) <= 100) {

                        double price = Double.parseDouble(et_price.getText().toString());
                        double discount = Double.parseDouble(charSequence.toString());
                        double value = (price * discount) / 100;
//                    et_discountValue.setEnabled(true);
                        et_discountValue.setText(String.valueOf(value));
                    } else
                        Toast.makeText(EditProductActivity.this, "Discount % can't be greater than 100", Toast.LENGTH_SHORT).show();

                } else {
                    et_discountValue.setEnabled(false);
                    et_discountValue.setText("Value");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void setProductDetails() {
        et_productName.setText(info.getProductName());
        et_price.setText(info.getPrice());
        if (!info.getSku().equalsIgnoreCase(""))
            et_sku.setText(info.getSku());
        if (!info.getDiscount().equals("")) {
            et_discountPercent.setText(info.getDiscount());
            et_discountPercent.setEnabled(true);

            double price = Double.parseDouble(et_price.getText().toString());
            double discount = Double.parseDouble(info.getDiscount());
            double value = (price * discount) / 100;
//                    et_discountValue.setEnabled(true);
            et_discountValue.setText(String.valueOf(value));
        }
        if (info.getProductStatus().equals("1")) {
            rb_publish.setChecked(true);
            status = "1";
        } else {
            rb_unPublish.setChecked(true);
            status = "0";
        }
        et_discription.setText(info.getDiscription());
        if (info.getBrandName().equalsIgnoreCase("unbrand")) {
            et_productType.setText("Unbranded");
            et_brandName.setEnabled(false);
            et_brandName.setHint("Brand Name");
        } else {
            et_productType.setText("Branded");
            et_brandName.setEnabled(true);
            et_brandName.setText(info.getBrandName());
        }
        if (prothumbList != null)
            if (prothumbList.size() > 0)
                Glide.with(EditProductActivity.this).load(prothumbList.get(0)).into(iv_proThumb);
        if (proImageList != null)
            setVpProGallery();
    }

    /*  public void setProductDetails() {
        et_productName.setText(info.getProducts().get(position).getProductName());
        et_price.setText(info.getProducts().get(position).getPrice());
        if (!info.getProducts().get(position).getSku().equalsIgnoreCase(""))
            et_sku.setText(info.getProducts().get(position).getSku());
        if (!info.getProducts().get(position).getDiscount().equals("")) {
            et_discountPercent.setText(info.getProducts().get(position).getDiscount());
            et_discountPercent.setEnabled(true);

            double price = Double.parseDouble(et_price.getText().toString());
            double discount = Double.parseDouble(info.getProducts().get(position).getDiscount());
            double value = (price * discount) / 100;
//                    et_discountValue.setEnabled(true);
            et_discountValue.setText(String.valueOf(value));
        }
        if (info.getProducts().get(position).getProductStatus().equals("1")) {
            rb_publish.setChecked(true);
            status = "1";
        } else {
            rb_unPublish.setChecked(true);
            status = "0";
        }
        et_discription.setText(info.getProducts().get(position).getDescription());
        if (info.getProducts().get(position).getBrandName().equalsIgnoreCase("unbrand")) {
            et_productType.setText("Unbranded");
            et_brandName.setEnabled(false);
            et_brandName.setHint("Brand Name");
        } else {
            et_productType.setText("Branded");
            et_brandName.setEnabled(true);
            et_brandName.setText(info.getProducts().get(position).getBrandName());
        }
        if (prothumbList != null)
            if (prothumbList.size() > 0)
                Glide.with(EditProductActivity.this).load(prothumbList.get(0)).into(iv_proThumb);
        if (proImageList != null)
            setVpProGallery();
    }*/

    public boolean isEmpty() {
        if (et_productName.getText().toString().isEmpty() || et_productName.getText().toString() == null || et_productName.getText().toString().equals("")) {
            et_productName.setError("Enter the product name");
            return true;
        } else if (et_price.getText().toString().isEmpty() || et_price.getText().toString() == null || et_price.getText().toString().equals("")) {
            et_price.setError("Enter the price");

            return true;
        } else if (et_productType.getText().toString().isEmpty() || et_productType.getText().toString() == null || et_productType.getText().toString().equals("")) {
            et_productType.setError("Select Product Type");

            return true;
        } else if (et_productType.getText().toString().equals("Branded") && et_brandName.getText().toString().equals("")) {
            et_brandName.setError("Enter Brand Name");
            return true;
        } else if (status == null) {
            Toast.makeText(getApplicationContext(), "Select status of Product", Toast.LENGTH_SHORT).show();
            return true;
        } else if (et_discription.getText().toString().isEmpty() || et_discription.getText().toString() == null || et_discription.getText().toString().equals("")) {
            et_discription.setError("Select Product Type");

            return true;
        } else
            return false;

    }

    //         if(info.getProducts().get(position).getProductImage()
    private class GetBrandList extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            getBrandList();

            return null;
        }
    }

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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProductActivity.this, android.R.layout.simple_dropdown_item_1line, brandArray);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.btn_submit:
                if (!isEmpty()) {
                    if (et_sku.getText().toString().isEmpty() || et_sku.getText().toString().equals(""))
                        strSku = "";
                    else strSku = et_sku.getText().toString();

                    if (et_discountPercent.getText().toString().isEmpty() || et_discountPercent.getText().toString().equals(""))
                        strDiscount = "0";
                    else strDiscount = et_discountPercent.getText().toString();

                    addProduct(filePathList, filePath, String.valueOf(preference.getBusinessId()),
                            preference.getUserId(), et_productName.getText().toString(),
                            et_price.getText().toString(), strSku, strDiscount, status,
                            et_discription.getText().toString(), info.getProductId());
                }
                break;
            case R.id.iv_serviceImage:
                if (prothumbList != null) {
                    if (prothumbList.size() > 0) {
                        Intent intent = new Intent(EditProductActivity.this, EditProductGalleryShowActivity.class);
                        intent.putExtra(Constants.PRODUCT_SERIALIZABLE, prothumbList);
                        intent.putExtra(Constants.POSITION, 0);
                        intent.putExtra("ProductImage", "Prothumb");
                        startActivityForResult(intent, EditImage);
                    }
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

        }
    }

    public Dialog productType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProductActivity.this);

        String[] array = {"Branded", "UnBranded"};

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
                    et_productType.setText("UnBranded");

                }

            }
        });

        return builder.create();
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

    public void takePhoto() {
        Constants.IMAGE_SELECT_CAPTURE = "camera";
        if (chooserDialogTitle.equalsIgnoreCase("ADD BANNER")) {
            Constants.limit = 1;
            startActivityForResult(new Intent(EditProductActivity.this, SingleImageSelectionActivity.class), 1);
        } else {
            Constants.limit = 10;
            startActivityForResult(new Intent(EditProductActivity.this, BusinessBannerCropActivity.class), 10);
        }
    }

    public void chooseFromGallery() {
        Constants.IMAGE_SELECT_CAPTURE = "gallery";
        if (chooserDialogTitle.equalsIgnoreCase("ADD BANNER")) {
            Constants.limit = 1;
            startActivityForResult(new Intent(EditProductActivity.this, SingleImageSelectionActivity.class), 1);
        } else {
            Constants.limit = 10;
            startActivityForResult(new Intent(EditProductActivity.this, BusinessBannerCropActivity.class), 10);
        }
    }

    public Dialog chooserDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProductActivity.this);

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
                prothumbList = new ArrayList<>();
                prothumbList.add(filePath);
//                iv_proThumb.setImageURI(Uri.parse("file://" + filePath));
                Glide.with(EditProductActivity.this).load(Uri.parse("file://" + filePath)).into(iv_proThumb);


            } else if (requestCode == 10) {
                filePathList = data.getStringArrayListExtra(Constants.IMAGE_PATH_LIST);
//                if (filePathList != null)
//                    if (filePathList.size() > 0)
//                        for (int i = 0; i < filePathList.size(); i++)
                if (proImageList != null) {
                    if ((filePathList.size() + proImageList.size()) < Constants.limit)
                        proImageList.addAll(filePathList);
                    else
                        Toast.makeText(this, "Images can't be more than " + Constants.limit, Toast.LENGTH_SHORT).show();
                } else proImageList.addAll(filePathList);

                setVpProGallery();
            } else if (requestCode == EditImage) {
                filePath = null;
                prothumbList.clear();
                iv_proThumb.setImageResource(R.drawable.uploadimage);
            } else if (requestCode == EditGallery) {
                deletedImagesUrl = data.getStringArrayListExtra(Constants.DELETED_IMAGES);
                if (deletedImagesUrl != null)
                    if (deletedImagesUrl.size() > 0)
                        for (int i = 0; i < deletedImagesUrl.size(); i++) {

                            proImageList.remove(deletedImagesUrl.get(i));
                        }
                setVpProGallery();
            }
        }
    }

    public void AlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditProductActivity.this);
//           builder.setTitle("Are you sure ?");
        builder.setMessage("Do you want to remove  image ?");
        builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                iv_proThumb.setImageBitmap(null);
//                tv_addBanner.setText("ADD");
//                rl_banner.setVisibility(View.GONE);
                filePath = "";
//                setBtn_doneClickable(false);
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
            btn_submit.setClickable(b);
            btn_submit.setFocusable(b);
            btn_submit.setTextColor(ContextCompat.getColor(EditProductActivity.this, R.color.white));
            btn_submit.setBackgroundResource(R.drawable.blue_btn_bg);

        } else {
            btn_submit.setClickable(b);
            btn_submit.setFocusable(b);
            btn_submit.setTextColor(ContextCompat.getColor(EditProductActivity.this, R.color.light_gray));
            btn_submit.setBackgroundResource(R.drawable.gray_btn_bg);

        }
    }

    public void addProduct(ArrayList<String> photosPathList, String bannerImage, String businessID,
                           String userId, String productName, String price, String sku, String strDiscount,
                           String status, String discription, String prodId) {

        progressBar.setVisibility(View.VISIBLE);
        String brandId = "0";
        String brandName = et_brandName.getText().toString().trim();
        if (!brandName.equals("")) {
            int i = brandNameList.indexOf(brandName);
            if (i == -1)
                brandId = "0";
            else brandId = brandList.get(i).getBrandId();

        } else brandName = "UnBranded";

        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        List<MultipartBody.Part> photosParam = new ArrayList<>();
        HashMap<String, RequestBody> deleteImageParam = new HashMap<>();
        if (deletedImagesUrl != null)
            if (deletedImagesUrl.size() > 0) {
                for (int j = 0; j < deletedImagesUrl.size(); j++) {
                    for (int k = 0; k < infoIMageList.size(); k++) {
                        if (infoIMageList.get(k).getImageName().equalsIgnoreCase(deletedImagesUrl.get(j))) {
                            String imageId = infoIMageList.get(k).getImageId();
                            RequestBody deletedImageId = RequestBody.create(MediaType.parse("multipart/form-data"), imageId);
                            deleteImageParam.put("delete_images[" + j + "]", deletedImageId);
                        }
                    }
                }
            }
        HashMap<String, RequestBody> param = new HashMap<>();

        if (photosPathList != null && photosPathList.size() > 0)
            for (int i = 0; i < photosPathList.size(); i++) {
                File file = new File(photosPathList.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

//              MultipartBody.Part is used to send also the actual file name
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
        RequestBody reportProductId = RequestBody.create(MediaType.parse("multipart/form-data"), prodId);

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
        param.put("product_id", reportProductId);

        Call<AddProductsModel> call = apiServices.update_product(photosParam, bannerbody, param, deleteImageParam);
        call.enqueue(new Callback<AddProductsModel>() {
            @Override
            public void onResponse(Call<AddProductsModel> call, Response<AddProductsModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        Toast.makeText(EditProductActivity.this, "Product updated succesfully", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else
                        Toast.makeText(EditProductActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<AddProductsModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(EditProductActivity.this, "Check your Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
