package com.chatlocalybusiness.fragments;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatlocalybusiness.R;

import com.chatlocalybusiness.activity.NewBusinessSetupActivity;
import com.chatlocalybusiness.adapter.BusinessPhotoAdapter;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.Utill;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by windows on 12/13/2017.
 */

public class BusinessOverviewFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_PERMISSIONS = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int SELECT_PICTURE = 103;
    private Button btn_done;
    private ImageView iv_banner,iv_bannerCancel;
    private TextView tv_addPhotos,tv_addBanner;
    private RelativeLayout rl_banner;
    private RecyclerView rv_photos;
    private LinearLayoutManager layoutManager;
    private Utill utill;
    private ChatBusinessSharedPreference preference;
    private BusinessPhotoAdapter businessPhotoAdapter;
    public String filePath=null;
    private Bitmap bitmapUser;
    public static String selectedImagePath;/*, filepath;*/
//    private RequestListener target;
    private Uri fileUri=null;
    private String chooserDialogTitle;
    ArrayList<Bitmap> imageBitmapList;
    ArrayList<String> filePathList;
    private AlertDialog alertdialog;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_overview,container,false);
        imageBitmapList=new ArrayList<>();
        filePathList=new ArrayList<>();

        utill=new Utill();
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        preference=new ChatBusinessSharedPreference(getActivity());
        rl_banner=(RelativeLayout)view. findViewById(R.id.rl_banner);
        btn_done=(Button)view. findViewById(R.id.btn_done);
        iv_banner=(ImageView)view. findViewById(R.id.iv_banner);
        iv_bannerCancel=(ImageView)view. findViewById(R.id.iv_bannerCancel);
        tv_addPhotos=(TextView)view. findViewById(R.id.tv_addPhotos);
        tv_addBanner=(TextView)view. findViewById(R.id.tv_addBanner);
        rv_photos=(RecyclerView)view. findViewById(R.id.rv_photos);

        tv_addBanner.setOnClickListener(this);
        tv_addPhotos.setOnClickListener(this);
        iv_bannerCancel.setOnClickListener(this);
        return view;
    }

    public void setRv_photos(ArrayList<Bitmap> list)
    {
       /* businessPhotoAdapter=new BusinessPhotoAdapter(getActivity(),list);
        layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rv_photos.setLayoutManager(layoutManager);
        rv_photos.setAdapter(businessPhotoAdapter);
  */  }

    public void takePhoto()
    {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkCallingOrSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(CameraIntent, CAMERA_REQUEST);
            } else {
                utill.requestMultiplePermissions(getActivity());
            }
        } else {
            Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(CameraIntent, CAMERA_REQUEST);
        }
    }

    public void chooseFromGallery()
    {
//        if (Build.VERSION.SDK_INT >= 23) {

            if (getActivity().checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECT_PICTURE);
            } else {
                utill.requestMultiplePermissions(getActivity());
            }
//        }
    }
    public Dialog chooserDialog(String title)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        String[] array={"Take Photo","Choose from Gallery"};

        builder.setTitle(title).setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0)
                {
                    takePhoto();

                }
                else if(i==1)
                {
                    chooseFromGallery();
                }

            }
        });

     return builder.create();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_PICTURE) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    filePath = cursor.getString(columnIndex);
                    cursor.close();

                    bitmapUser = BitmapFactory.decodeFile(filePath);
                   if(chooserDialogTitle.equalsIgnoreCase("ADD BANNER")) {
                       rl_banner.setVisibility(View.VISIBLE);
                       iv_banner.setImageBitmap(bitmapUser);
                       tv_addBanner.setText("CHANGE");
                       setBtn_doneClickable(true);

                   }else {
                       imageBitmapList.add(bitmapUser);
                       filePathList.add(filePath);
                       setRv_photos(imageBitmapList);

                   }

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
                        if(chooserDialogTitle.equalsIgnoreCase("ADD BANNER")) {
                            rl_banner.setVisibility(View.VISIBLE);
                            iv_banner.setImageBitmap(bitmapUser);
                            tv_addBanner.setText("CHANGE");
                            setBtn_doneClickable(true);

                        }
                        else {
                            imageBitmapList.add(bitmapUser);
                            filePathList.add(filePath);
                            setRv_photos(imageBitmapList);

                        }
                    } else {
//                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_fetch), Toast.LENGTH_LONG).show();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
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

            cursor =getActivity(). getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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
    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.tv_addBanner :
                chooserDialogTitle="ADD BANNER";
                Dialog dialog=chooserDialog(chooserDialogTitle);
                dialog.show();
                break;

            case R.id.tv_addPhotos :
                chooserDialogTitle="ADD PHOTOS";
                Dialog dialog2=chooserDialog(chooserDialogTitle);
                dialog2.show();
                break;
            case R.id.iv_bannerCancel :
                AlertDialog();
                break;
            case R.id.btn_done :

//                postBusinessImages(filePathList,filePath,String.valueOf(preference.getBusinessId()),preference.getUserId());
//                replacefragment();
                break;
        }
    }
    private void replacefragment() {
        Fragment fragment;
        if((fragment=getFragmentManager().findFragmentByTag(NewBusinessSetupActivity.CATEGORY_FRAG))!=null){

//            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.rotate_in,R.anim.rotate_out).
                    remove(fragment)
                    .commit();
            NewBusinessSetupActivity.overviewCheck=1;
//            CheckBox check_Overview=(CheckBox)getActivity().findViewById(R.id.check_Overview);
//            check_Overview.setClickable(false);
        }
    }
    public void AlertDialog()
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//           builder.setTitle("Are you sure ?");
        builder.setMessage("Do you want to remove this image ?");
        builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                iv_banner.setImageBitmap(null);
                tv_addBanner.setText("ADD");
                rl_banner.setVisibility(View.GONE);
                filePath="";
                setBtn_doneClickable(false);
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
    public void setBtn_doneClickable(boolean b)
    {
        if(b)
        {
            btn_done.setClickable(b);
            btn_done.setFocusable(b);
            btn_done.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
            btn_done.setBackgroundResource(R.drawable.blue_btn_bg);

        }else{
            btn_done.setClickable(b);
            btn_done.setFocusable(b);
            btn_done.setTextColor(ContextCompat.getColor(getActivity(),R.color.light_gray));
            btn_done.setBackgroundResource(R.drawable.gray_btn_bg);

        }
    }
   /* public void postBusinessImages(ArrayList<String> photosPathList,String bannerImage,String businessID,String userId)
    {

        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices= ApiClient.getClient().create(ApiServices.class);
        HashMap<String ,  MultipartBody.Part> photosParam=new HashMap<>();
        HashMap<String , RequestBody> param=new HashMap<>();

        if(photosPathList!=null&&photosPathList.size()>0)
            for (int i = 0; i < photosPathList.size(); i++) {
                File file = new File(photosPathList.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

//         MultipartBody.Part is used to send also the actual file name
//                MultipartBody.Part body = MultipartBody.Part.createFormData("business_logo", file.getName(), requestFile);
                MultipartBody.Part body = MultipartBody.Part.create(requestFile);
                photosParam.put("business_images[" + i + "]", body);
            }

        MultipartBody.Part bannerbody=null;
        if(bannerImage!=null) {
            File bannerFile = new File(bannerImage);
            RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), bannerFile);
             bannerbody = MultipartBody.Part.createFormData("business_banner", bannerFile.getName(), requestfile);
        }
        //      add another part within the multipart request
        RequestBody b_id = RequestBody.create(MediaType.parse("multipart/form-data"), businessID);
        RequestBody encrypt_key = RequestBody.create(MediaType.parse("multipart/form-data"), "df");
        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), userId);

        param.put("encrypt_key", encrypt_key);
        param.put("b_user_id",user_id);
        param.put("b_id",b_id);

        Call<BusinessBannerModel> call=apiServices.postBusinessPhotos(photosParam,bannerbody,param);
        call.enqueue(new Callback<BusinessBannerModel>() {
            @Override
            public void onResponse(Call<BusinessBannerModel> call, Response<BusinessBannerModel> response) {

                if(response.isSuccessful())
                {

                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<BusinessBannerModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
