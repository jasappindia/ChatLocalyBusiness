package com.chatlocalybusiness.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;

import com.chatlocalybusiness.activity.EditBusinessSetupActivity;
import com.chatlocalybusiness.activity.NewBusinessSetupActivity;
import com.chatlocalybusiness.activity.SingleImageSelectionActivity;
import com.chatlocalybusiness.activity.UserProfileActivity;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.BusinessDetailsModel;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.getterSetterModel.BusinessSetupDetails;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.ui.CircleImageView;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.chatlocalybusiness.activity.BusinessProfileActivity.businessProfileApi;

/**
 * Created by windows on 12/7/2017.
 */
@SuppressLint("ValidFragment")
public class BusinesDetailFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_PERMISSIONS = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int SELECT_PICTURE = 103;
    private Button btn_done;
    private EditText et_homeService, et_kilometers, et_business;
    private String plan;
    private CircleImageView iv_businessIcon;
    private Utill utill;
    private ChatBusinessSharedPreference prefence;
    public String filePath = null;
    private Bitmap bitmapUser;
    public static String selectedImagePath;/*, filepath;*/
    private Uri fileUri = null;
    private int businessNAmeflag = 0, homeServiceflag = 0, kilometerflag = 0, businessIconflag = 0;
    private ProgressBar progressBar;
    private BusinessInfoModelNew.BusinessDetail info;
    ImageView iv_checkBusiness,iv_star_km;
    private RelativeLayout rl_business, rl_category, rl_location;
    BusinessSetupDetails businessSetupDetails;
    NewBusinessSetupActivity newBusinessSetupActivity;
    EditBusinessSetupActivity editBusinessSetupActivity;

    public BusinesDetailFragment(NewBusinessSetupActivity newBusinessSetupActivity) {
        this.newBusinessSetupActivity = newBusinessSetupActivity;
    }

    public BusinesDetailFragment(EditBusinessSetupActivity editBusinessSetupActivity) {
        this.editBusinessSetupActivity = editBusinessSetupActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_businessdetails, container, false);
        init(view);

        return view;
    }


    public void init(View view) {

        utill = new Utill();
        prefence = new ChatBusinessSharedPreference(getActivity());
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        btn_done = (Button) view.findViewById(R.id.btn_done);
        iv_businessIcon = (CircleImageView) view.findViewById(R.id.iv_businessIcon);
        et_business = (EditText) view.findViewById(R.id.et_business);
        et_homeService = (EditText) view.findViewById(R.id.et_homeService);
        et_kilometers = (EditText) view.findViewById(R.id.et_kilometers);
        iv_checkBusiness = (ImageView) getActivity().findViewById(R.id.iv_checkBusiness);
        iv_star_km = (ImageView)view.findViewById(R.id.iv_star_km);

        rl_business = (RelativeLayout) getActivity().findViewById(R.id.rl_business);
        rl_category = (RelativeLayout) getActivity().findViewById(R.id.rl_category);
        rl_location = (RelativeLayout) getActivity().findViewById(R.id.rl_location);

        btn_done.setOnClickListener(this);
        et_homeService.setOnClickListener(this);
        iv_businessIcon.setOnClickListener(this);
        et_kilometers.setOnClickListener(this);
        setBtn_doneClickable(false);
        addTextWacher();
        setValues();
        disableLayouts(false);
    }

    public void setValues() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            info = (BusinessInfoModelNew.BusinessDetail) bundle.getSerializable(Constants.BUSINESS_INFO);

            if (info != null) {
                Glide.with(getActivity()).load(info.getBusinessLogo()).into(iv_businessIcon);
                et_business.setText(info.getBusinessName());
                et_homeService.setText(info.getHomeServices());
                if (info.getServicesKm() != null) {
                    et_kilometers.setText(info.getServicesKm());
                    homeServiceflag = 1;
                    iv_star_km.setImageResource(R.drawable.star);
                    et_kilometers.setEnabled(true);
                    plan = info.getHomeServices();
                }
                setBtn_doneClickable(true);
            }


        }
    }

    public void disableLayouts(boolean b) {
        rl_location.setClickable(b);
        rl_category.setClickable(b);
        rl_business.setClickable(b);
    }

    public void addTextWacher() {
        et_business.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 3) {
                    businessNAmeflag = 1;
//                    if(plan!=null&&businessIconflag==1)  uncomment this code if businesIcon is mandatory and comment below code
                    if (plan != null)
                        /////////////////////////////to set  submit button enable and  disable only/////////////

                        setBtnafterBusinessIcon();

///////////////////////////////////////////////////////////////////////////

                } else {
                    businessNAmeflag = 0;
                    setBtn_doneClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_kilometers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    kilometerflag = 1;
//              if (businessNAmeflag == 1&&businessIconflag==1) { uncomment this code if businesIcon is mandatory and comment below code
                    if (businessNAmeflag == 1) {
                        setBtn_doneClickable(true);
                    } else setBtn_doneClickable(false);
                } else {
                    kilometerflag = 0;
                    setBtn_doneClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void setBtn_doneClickable(boolean b) {
        if (b) {
            btn_done.setClickable(b);
            btn_done.setFocusable(b);
            btn_done.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            btn_done.setBackgroundResource(R.drawable.blue_btn_bg);

        } else {
            btn_done.setClickable(b);
            btn_done.setFocusable(b);
            btn_done.setTextColor(ContextCompat.getColor(getActivity(), R.color.light_gray));
            btn_done.setBackgroundResource(R.drawable.gray_btn_bg);

        }
    }

    public Dialog onCreateDialogSingleChoice() {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//Source of the data in the DIalog
        String[] array = {"YES", "NO", "EVERYWHERE"};

        builder.setTitle("Home Service ")
// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (which == 0)
                            plan = "YES";
                        else if (which == 1)
                            plan = "NO";
                        else if (which == 2)
                            plan = "EVERYWHERE";

                    }
                })

// Set the action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
// User clicked OK, so save the result somewhere
// or return them to the component that opened the dialog
                        if (plan != null) {
                            homeServiceflag = 1;
                            et_homeService.setText(plan);
                            if (plan.equalsIgnoreCase("YES")) {
                                setEt_kilometersFocused(true);
                                setBtn_doneClickable(false);
                                iv_star_km.setImageResource(R.drawable.star);
                            } else {
                                setEt_kilometersFocused(false);
                                iv_star_km.setImageResource(R.drawable.star_gray);
//                             if (businessNAmeflag == 1&&businessIconflag==1) uncomment this code if businesIcon is mandatory and comment below code
                                if (businessNAmeflag == 1)
                                    setBtn_doneClickable(true);
                                else setBtn_doneClickable(false);
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    public void setEt_kilometersFocused(boolean b) {
        if (!b) {
            et_kilometers.setText(null);
            et_kilometers.setHint("Type");

        }
        et_kilometers.setEnabled(b);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_homeService:
                Dialog dialog = onCreateDialogSingleChoice();
                dialog.show();
                break;
            case R.id.btn_done:
                String km = "";
                if (et_homeService.getText().toString().equalsIgnoreCase("YES")) {
                    km = et_kilometers.getText().toString().trim();
                    if (Integer.parseInt(km) > 0)
                        registerBusiness(et_business.getText().toString().trim(), et_homeService.getText().toString().trim(), km, filePath);
                    else
                        Toast.makeText(getActivity(), "Distance must be greater than 0 !", Toast.LENGTH_SHORT).show();
                }
                else
                registerBusiness(et_business.getText().toString().trim(), et_homeService.getText().toString().trim(), km, filePath);
                break;
            case R.id.iv_businessIcon:
                Dialog dialog2 = chooserDialog("Add Business Icon");
                dialog2.show();
                break;

        }
    }

    public void done() {
        Fragment fragment = getFragmentManager().findFragmentByTag(NewBusinessSetupActivity.BUSINESS_FRAG);
        if (fragment != null)
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();

//            getFragmentManager().beginTransaction()
//                    .setCustomAnimations(R.anim.rotate_in,R.anim.rotate_out).
//                    remove(fragment)
//                    .commit();

        NewBusinessSetupActivity.businessCheck = 1;
//        CheckBox  check_businessCat=(CheckBox)getActivity().findViewById(R.id.check_businessCat);
//        check_businessCat.setClickable(false);
        if (NewBusinessSetupActivity.locationCheck == 1 && NewBusinessSetupActivity.businessCheck == 1 && NewBusinessSetupActivity.categoryCheck == 1) {
            Button btn_next = (Button) getActivity().findViewById(R.id.btn_next);
            btn_next.setClickable(true);
            btn_next.setFocusable(true);
            btn_next.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            btn_next.setBackgroundResource(R.drawable.blue_btn_bg);
        }
    }

    public Dialog chooserDialog(String title) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

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

    public void takePhoto() {
        Constants.IMAGE_SELECT_CAPTURE = "camera";

        Constants.limit = 1;
        startActivityForResult(new Intent(getActivity(), SingleImageSelectionActivity.class), 1);
    }

    public void chooseFromGallery() {
        Constants.IMAGE_SELECT_CAPTURE = "gallery";
        Constants.limit = 1;
        startActivityForResult(new Intent(getActivity(), SingleImageSelectionActivity.class), 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
//                String strEditText = data.getStringExtra("editTextValue");
                filePath = data.getStringExtra(Constants.IMAGE_PATH);
//                iv_proThumb.setImageURI(Uri.parse("file://" + filePath));
                Glide.with(getActivity()).load(Uri.parse("file://" + filePath)).into(iv_businessIcon);

            }
        }
    }
 /*   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == SELECT_PICTURE) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    filePath = cursor.getString(columnIndex);
                    cursor.close();

                    bitmapUser = BitmapFactory.decodeFile(filePath);
                    iv_businessIcon.setImageBitmap(bitmapUser);
                    businessIconflag = 1;
/////////////////////////////to set  submit button enable and  disable only/////////////

                    setBtnafterBusinessIcon();

///////////////////////////////////////////////////////////////////////////
                } else if (requestCode == CAMERA_REQUEST) {

                    Uri imageUri = null;
                    try {
                        if (data != null) {
                            imageUri = data.getData();
                            filePath = getPath(imageUri);
                        } else {
                            imageUri = fileUri;
                            filePath = imageUri.getPath();
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }

                    if (filePath != null) {
                        bitmapUser = Utill.decodeSampledBitmapFromResource(filePath, 600, 600);
                    } else {
                        Bundle extras = data.getExtras();
                        bitmapUser = (Bitmap) extras.get("data");
                    }

                    if (bitmapUser != null) {
                        try {
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmapUser.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                            File destination = new File(Environment.getExternalStorageDirectory(),
                                    System.currentTimeMillis() + ".jpg");
                            FileOutputStream fo;
                            filePath = destination.getAbsolutePath();
                            try {
                                destination.createNewFile();
                                fo = new FileOutputStream(destination);
                                fo.write(bytes.toByteArray());
                                fo.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iv_businessIcon.setImageBitmap(bitmapUser);
                        businessIconflag = 1;
                        /////////////////////////////to set  submit button enable and  disable only/////////////

                        setBtnafterBusinessIcon();

///////////////////////////////////////////////////////////////////////////

                    } else {
//                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_fetch), Toast.LENGTH_LONG).show();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    public void setBtnafterBusinessIcon() {
        if (plan != null)
            if (plan.equalsIgnoreCase("YES")) {
                if (kilometerflag == 1 && businessNAmeflag == 1) {
                    setBtn_doneClickable(true);
                } else setBtn_doneClickable(false);
            } else {
                if (homeServiceflag == 1 && businessNAmeflag == 1) {
                    setBtn_doneClickable(true);
                } else setBtn_doneClickable(false);
            }

    }

    public String getPath(Uri uri) {
        if (uri == null) {
            return null;
        }

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor;
        if (Build.VERSION.SDK_INT > 19) {
            // Will return "image:x*"
            String wholeID = DocumentsContract.getDocumentId(uri);
            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];
            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, sel, new String[]{id}, null);
        } else {
            cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        }
        String path = null;
        try {
            int column_index = cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index).toString().trim();
            cursor.close();
        } catch (NullPointerException e) {

        }
        return path;
    }

    public void registerBusiness(String businessName, String homeservice, String km, String imagePath) {
        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiservices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, RequestBody> param = new HashMap<>();
        MultipartBody.Part body = null;
        if (filePath != null) {
            File file = new File(imagePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

//         MultipartBody.Part is used to send also the actual file name
            body = MultipartBody.Part.createFormData("business_logo", file.getName(), requestFile);
        }
        //      add another part within the multipart request
        RequestBody busi_NAme = RequestBody.create(MediaType.parse("multipart/form-data"), businessName);
        RequestBody encrypt_key = RequestBody.create(MediaType.parse("multipart/form-data"), "df");
        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), prefence.getUserId());
        RequestBody home_service = RequestBody.create(MediaType.parse("multipart/form-data"), homeservice);
        RequestBody km_ = RequestBody.create(MediaType.parse("multipart/form-data"), km);
        RequestBody completedStep = RequestBody.create(MediaType.parse("multipart/form-data"), prefence.getCompletedStep());

        param.put("encrypt_key", encrypt_key);
        param.put("b_user_id", user_id);
        param.put("business_name", busi_NAme);
        param.put("home_services", home_service);
        param.put("services_km", km_);
        param.put("complated_steps", completedStep);

        Call<BusinessDetailsModel> call = apiservices.registerBusinessDetails(param, body);
        call.enqueue(new Callback<BusinessDetailsModel>() {
            @Override
            public void onResponse(Call<BusinessDetailsModel> call, Response<BusinessDetailsModel> response) {

                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    disableLayouts(true);
                    BusinessDetailsModel.Data data = response.body().getData();

                    if (data.getResultCode().equalsIgnoreCase("1")) {
//                        prefence.saveBusinessId(response.body().getData().getBId());
                        businessProfileApi = 1;

//                        prefence.saveCompletedStep("4");
                        prefence.setBusinessName(data.getBusinessName());
                        prefence.setBusinessLogo(data.getBusinessLogo());
                        if (info == null) {

                            BusinessInfoModelNew businessInfoModelNew = new BusinessInfoModelNew();
                            info = businessInfoModelNew.new BusinessDetail();

                        }
                        info.setBusinessName(data.getBusinessName());
                        info.setBusinessLogo(data.getBusinessLogo());
                        info.setHomeServices(et_homeService.getText().toString().trim());
                        if (info.getHomeServices().equalsIgnoreCase("yes"))
                            info.setServicesKm(et_kilometers.getText().toString());
                        if (newBusinessSetupActivity != null)
                            newBusinessSetupActivity.setData(info);
                        else editBusinessSetupActivity.setData(info);
                        iv_checkBusiness.setVisibility(View.VISIBLE);
                        done();

                    } else
                        Toast.makeText(getActivity(), response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BusinessDetailsModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "check internet connection", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
