package com.chatlocalybusiness.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.api.MobiComKitConstants;
import com.applozic.mobicommons.commons.core.utils.Utils;
import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.imagecrop.Crop;
import com.chatlocalybusiness.apiModel.CameraImageUriModel;
import com.chatlocalybusiness.apiModel.GalleryImageUriModel;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;
import com.darsh.multipleimageselect.galleryAcctivities.AlbumSelectActivity;
import com.darsh.multipleimageselect.galleryModels.Image;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by windows on 1/24/2018.
 */

public class MultiImageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PERMISSIONS = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int SELECT_PICTURE = 103;
    private ImageView iv_arrowBack, iv_crop, iv_done;
    private ImageView iv_imageSelcet, iv_addImages;
    private RecyclerView recyclerView;
    private AdapterForMultiImageGallery adapterForMultiImageGallery;
    private LinearLayoutManager layoutManager;
    private Utill utill;
    //    private ArrayList<Uri> uriArrayListGAllery;
    private ArrayList<GalleryImageUriModel> uriArrayListGAllery;
    private ArrayList<CameraImageUriModel> uriArrayListCamera;
    private int position = 0;
    private android.app.AlertDialog alertdialog;
    private int imageUriPosition;
    private LinearLayout ll_imagelist;
    private String filePath;
    private Uri fileUri = null;
    private ArrayList<Bitmap> imageBitmapList;
    private ArrayList<String> filePathList;
    private Bitmap bitmapUser;
    private String mCurrentPhotoPath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        utill = new Utill();
        init();
        choosefunction();

        if (Constants.limit == 1) {
            iv_addImages.setVisibility(View.GONE);
            ll_imagelist.setVisibility(View.GONE);
        } else {
            iv_addImages.setVisibility(View.VISIBLE);
            ll_imagelist.setVisibility(View.VISIBLE);
        }
    }
    public void choosefunction()
    {
        if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("gallery"))
            openImagePicker();
        else openCamera();
    }

    public void openImagePicker() {
        if (MultiImageActivity.this.checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MultiImageActivity.this, AlbumSelectActivity.class);
            intent.putExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_LIMIT, Constants.limit);
            startActivityForResult(intent, com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE);
        } else {
            utill.requestMultiplePermissions(MultiImageActivity.this);
        }
    }

    public void openCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (MultiImageActivity.this.checkCallingOrSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File

                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(this, Utils.getMetaDataValue(this, MobiComKitConstants.PACKAGE_NAME) + ".provider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                    }
                }
            } else {
                utill.requestMultiplePermissions(MultiImageActivity.this);
            }
        } else {
//            Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(CameraIntent, CAMERA_REQUEST);
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, Utils.getMetaDataValue(this, MobiComKitConstants.PACKAGE_NAME) + ".provider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
           choosefunction();
        }
        else if(grantResults[1] == PackageManager.PERMISSION_GRANTED)
        {
            choosefunction();

        }
        else finish();


    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Event" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void init() {
        uriArrayListGAllery = new ArrayList<>();
        uriArrayListCamera = new ArrayList<>();
        imageBitmapList = new ArrayList<>();
        filePathList = new ArrayList<>();

        ll_imagelist = (LinearLayout) findViewById(R.id.ll_imagelist);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        iv_imageSelcet = (ImageView) findViewById(R.id.iv_imageSelcet);
        iv_crop = (ImageView) findViewById(R.id.iv_crop);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        iv_done = (ImageView) findViewById(R.id.iv_done);
        iv_addImages = (ImageView) findViewById(R.id.iv_addImages);

        iv_done.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
        iv_crop.setOnClickListener(this);
        iv_addImages.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_crop:
                if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("gallery")) {
                    beginCrop(uriArrayListGAllery.get(position).getImageUri());
                } else beginCrop(uriArrayListCamera.get(position).getImageUri());
                break;
            case R.id.iv_done:
                    doneCropping();
                break;
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.iv_addImages:
                if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("gallery"))
                    openImagePicker();
                else openCamera();
                break;
        }
    }

    public void doneCropping()
    {
        if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("gallery")&& Constants.limit > 1) {
            for (int i = 0; i < uriArrayListGAllery.size(); i++) {
                if (uriArrayListGAllery.get(i).getCropped() == 0) {
                    Toast.makeText(this, "All Images must be cropped", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (i == ((uriArrayListGAllery.size()) - 1) && uriArrayListGAllery.get(i).getCropped() == 1) {
                    sendIntent();
                }
            }
        }
        else if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("camera") && Constants.limit > 1) {

            for (int i = 0; i < uriArrayListCamera.size(); i++) {
                if (uriArrayListCamera.get(i).getCropped() == 0) {
                    Toast.makeText(this, "All Images must be cropped", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (i == ((uriArrayListCamera.size()) - 1) && uriArrayListCamera.get(i).getCropped() == 1) {
                    sendIntent();
                }
            }
        }
        else if(filePath.contains("cropped"))
            sendIntent();
        else Toast.makeText(this, " Image must be cropped", Toast.LENGTH_SHORT).show();

    }


    public void setDoneActive()
    {
        if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("gallery")&& Constants.limit > 1) {
            for (int i = 0; i < uriArrayListGAllery.size(); i++) {
                if (uriArrayListGAllery.get(i).getCropped() == 0) {
                    iv_done.setImageResource(R.drawable.tick);
//                    Toast.makeText(this, "All Images must be cropped", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (i == ((uriArrayListGAllery.size()) - 1) && uriArrayListGAllery.get(i).getCropped() == 1) {
//                    sendIntent();
                iv_done.setImageResource(R.drawable.done_icon_active);
                }
            }
        }
        else if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("camera") && Constants.limit > 1) {

            for (int i = 0; i < uriArrayListCamera.size(); i++) {
                if (uriArrayListCamera.get(i).getCropped() == 0) {
//                    Toast.makeText(this, "All Images must be cropped", Toast.LENGTH_SHORT).show();
                    iv_done.setImageResource(R.drawable.tick);
                    return;
                }
                if (i == ((uriArrayListCamera.size()) - 1) && uriArrayListCamera.get(i).getCropped() == 1) {
                    iv_done.setImageResource(R.drawable.done_icon_active);

                    //                    sendIntent();
                }
            }
        }
        else if(filePath.contains("cropped"))
            iv_done.setImageResource(R.drawable.done_icon_active);

            //            sendIntent();
        else
            iv_done.setImageResource(R.drawable.tick);

            //            Toast.makeText(this, " Image must be cropped", Toast.LENGTH_SHORT).show();

    }

    public void sendIntent() {
        Intent intent = new Intent();
        if ( Constants.limit > 1)
            intent.putStringArrayListExtra(Constants.IMAGE_PATH_LIST, filePathList);
        else
            intent.putExtra(Constants.IMAGE_PATH, filePath);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED)
        {
            if(filePathList.size()<1&&filePath==null)
            finish();
        } if(resultCode == RESULT_OK){
        if (requestCode == com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_IMAGES);
            getUriListGallery(images);
            filePath=images.get(0).path;
            position = 0;
            Glide.with(MultiImageActivity.this).load(uriArrayListGAllery.get(0).getImageUri()).into(iv_imageSelcet);
            setRecyclerViewGallery(uriArrayListGAllery);
        }
        else if (requestCode == CAMERA_REQUEST) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File savefile = new File(mCurrentPhotoPath);
            Uri contentUri = Uri.fromFile(savefile);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);

            if (Constants.limit > 1) {
                if (savefile.exists()) {
                    filePathList.add(savefile.getAbsolutePath());
                    Glide.with(MultiImageActivity.this).load(getImageContentUri(this, savefile.getAbsolutePath())).into(iv_imageSelcet);

//                    iv_imageSelcet.setImageURI(getImageContentUri(this, savefile.getAbsolutePath()));
                    setRecyclerViewCamera(getUriListCamera(savefile.getAbsolutePath()));
                }
            } else {
                if (savefile.exists()) {
                    filePath = savefile.getAbsolutePath();
                    filePathList.add(savefile.getAbsolutePath());
                    Glide.with(MultiImageActivity.this).load(getImageContentUri(this, savefile.getAbsolutePath())).into(iv_imageSelcet);

//                    iv_imageSelcet.setImageURI(getImageContentUri(this, savefile.getAbsolutePath()));
                    setRecyclerViewCamera(getUriListCamera(savefile.getAbsolutePath()));
                }
            }
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
            setDoneActive();
        }
        }
    }

    public ArrayList<GalleryImageUriModel> getUriListGallery(ArrayList<Image> images) {
        for (int i = 0; i < images.size(); i++) {
            GalleryImageUriModel uriModel = new GalleryImageUriModel();
            uriModel.setUri(getImageContentUri(MultiImageActivity.this, images.get(i).path));
//                    uriArrayListGAllery.add(getImageContentUri(MultiImageActivity.this,images.get(i).path));
            filePathList.add(images.get(i).path);
            uriArrayListGAllery.add(uriModel);
        }
        return uriArrayListGAllery;
    }

    public ArrayList<CameraImageUriModel> getUriListCamera(String images) {

        CameraImageUriModel uriModel = new CameraImageUriModel();
        uriModel.setUri(getImageContentUri(MultiImageActivity.this, images));
//                    uriArrayListGAllery.add(getImageContentUri(MultiImageActivity.this,images.get(i).path));
        uriArrayListCamera.add(uriModel);
        return uriArrayListCamera;
    }

    public void setRecyclerViewGallery(ArrayList<GalleryImageUriModel> uriList) {
        recyclerView = (RecyclerView) findViewById(R.id.rv_imageList);
        adapterForMultiImageGallery = new AdapterForMultiImageGallery(MultiImageActivity.this, uriList);
        layoutManager = new LinearLayoutManager(MultiImageActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setAdapter(adapterForMultiImageGallery);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setRecyclerViewCamera(ArrayList<CameraImageUriModel> uriList) {
        recyclerView = (RecyclerView) findViewById(R.id.rv_imageList);
        AdapterForMultiImageCamera adapterForMultiImageCamera = new AdapterForMultiImageCamera(MultiImageActivity.this, uriList);
        layoutManager = new LinearLayoutManager(MultiImageActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setAdapter(adapterForMultiImageCamera);
        recyclerView.setLayoutManager(layoutManager);
    }

    public static Uri getImageContentUri(Context context, String filePath) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (filePath != null) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "cropped"+System.currentTimeMillis() + ".jpg"));
        Crop.of(source, destination).asSquare().start(this);


    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("gallery")) {
                GalleryImageUriModel uriModel = new GalleryImageUriModel();
                uriModel.setUri(uri);
                String[] s = uri.toString().split("storage");
                if (Constants.limit > 1)
                    filePathList.set(position, "/storage"+s[1]);
                else filePath = "/storage"+s[1];
                Glide.with(MultiImageActivity.this).load(uri).into(iv_imageSelcet);

//                iv_imageSelcet.setImageURI(uri);
                uriArrayListGAllery.set(position, uriModel);
                setRecyclerViewGallery(uriArrayListGAllery);
            } else {
                CameraImageUriModel uriModel = new CameraImageUriModel();
                uriModel.setUri(uri);
                String[] s = uri.toString().split("storage");
                if (Constants.limit > 1)
                    filePathList.set(position,"/storage"+ s[1]);
                else filePath = "/storage"+s[1];
                Glide.with(MultiImageActivity.this).load(uri).into(iv_imageSelcet);

//                iv_imageSelcet.setImageURI(uri);
                uriArrayListCamera.set(position, uriModel);
                setRecyclerViewCamera(uriArrayListCamera);
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class AdapterForMultiImageGallery extends RecyclerView.Adapter<AdapterForMultiImageGallery.MyHolder> {

        Context context;
        ArrayList<GalleryImageUriModel> uriList;

        public AdapterForMultiImageGallery(Context context, ArrayList<GalleryImageUriModel> uriList) {
            this.context = context;
            this.uriList = uriList;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_image, parent, false);
            return new AdapterForMultiImageGallery.MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
//            holder.iv_galleryImage.setImageURI(uriList.get(position).getImageUri());
            Glide.with(MultiImageActivity.this).load(uriList.get(position).getImageUri()).into( holder.iv_galleryImage);

            if (String.valueOf(uriList.get(position).getImageUri()).contains("cropped")) {
                holder.iv_checkIcon.setVisibility(View.VISIBLE);
                uriList.get(position).setCropped(1);
            } else {
                holder.iv_checkIcon.setVisibility(View.GONE);
                uriList.get(position).setCropped(0);
            }
            System.gc();
        }

        @Override
        public int getItemCount() {
            return uriList.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            ImageView iv_galleryImage;
            ImageView iv_checkIcon;
            RelativeLayout rl_remove;

            public MyHolder(View itemView) {
                super(itemView);
                iv_galleryImage = (ImageView) itemView.findViewById(R.id.iv_galleryImage);
                iv_checkIcon = (ImageView) itemView.findViewById(R.id.iv_checkIcon);
                rl_remove = (RelativeLayout) itemView.findViewById(R.id.rl_remove);
                rl_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeAlertDialog(getAdapterPosition());
                    }
                });
                iv_galleryImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        position = getAdapterPosition();
//                        iv_imageSelcet.setImageURI(uriList.get(position).getImageUri());
                        Glide.with(MultiImageActivity.this).load(uriList.get(position).getImageUri()).into(iv_imageSelcet);

                        System.gc();
                    }
                });
            }
        }
    }

    public void removeAlertDialog(final int adapterPosition) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//           builder.setTitle("Are you sure ?");
        builder.setMessage("Do you want to remove this image ?");
        builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("gallery") && Constants.limit > 1) {
                    uriArrayListGAllery.remove(adapterPosition);
                    filePathList.remove(adapterPosition);
                    if (position == adapterPosition) {
                        if (adapterPosition < uriArrayListGAllery.size())
                            Glide.with(MultiImageActivity.this).load(uriArrayListGAllery.get(adapterPosition).getImageUri()).into(iv_imageSelcet);

//                            iv_imageSelcet.setImageURI(uriArrayListGAllery.get(adapterPosition).getImageUri());
                        else if (uriArrayListCamera == null || uriArrayListCamera.size() < 1) {
                            iv_imageSelcet.setImageBitmap(null);
                        }
                        else
                            Glide.with(MultiImageActivity.this).load(uriArrayListGAllery.get(0).getImageUri()).into(iv_imageSelcet);

//                            iv_imageSelcet.setImageURI(uriArrayListGAllery.get(0).getImageUri());
                    }
                    setRecyclerViewGallery(uriArrayListGAllery);
                } else if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("Camera") && Constants.limit > 1) {
                    uriArrayListCamera.remove(adapterPosition);
                    filePathList.remove(adapterPosition);
                    if (position == adapterPosition) {
                        if (adapterPosition < uriArrayListCamera.size())
                            Glide.with(MultiImageActivity.this).load(uriArrayListCamera.get(adapterPosition).getImageUri()).into(iv_imageSelcet);

//                            iv_imageSelcet.setImageURI(uriArrayListCamera.get(adapterPosition).getImageUri());
                        else if (uriArrayListCamera == null || uriArrayListCamera.size() < 1) {
                            iv_imageSelcet.setImageBitmap(null);
                        } else
                            Glide.with(MultiImageActivity.this).load(uriArrayListCamera.get(0).getImageUri()).into(iv_imageSelcet);

//                            iv_imageSelcet.setImageURI(uriArrayListCamera.get(0).getImageUri());
                    }
                    setRecyclerViewCamera(uriArrayListCamera);
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

    private class AdapterForMultiImageCamera extends RecyclerView.Adapter<AdapterForMultiImageCamera.MyHolder> {

        Context context;
        ArrayList<CameraImageUriModel> uriList;

        public AdapterForMultiImageCamera(Context context, ArrayList<CameraImageUriModel> uriList) {
            this.context = context;
            this.uriList = uriList;

        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_image, parent, false);
            return new AdapterForMultiImageCamera.MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
//            holder.iv_galleryImage.setImageURI(uriList.get(position).getImageUri());
            Glide.with(context)
                    .load(uriList.get(position).getImageUri())
                    .into(holder.iv_galleryImage);
            if (String.valueOf(uriList.get(position).getImageUri()).contains("/0/")) {
                holder.iv_checkIcon.setVisibility(View.VISIBLE);
                uriList.get(position).setCropped(1);
            } else {
                holder.iv_checkIcon.setVisibility(View.GONE);
                uriList.get(position).setCropped(0);
            }
            System.gc();
        }

        @Override
        public int getItemCount() {
            if (uriList != null)
                return uriList.size();
            else return 0;
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            ImageView iv_galleryImage;
            ImageView iv_checkIcon;
            RelativeLayout rl_remove;

            public MyHolder(View itemView) {
                super(itemView);
                iv_galleryImage = (ImageView) itemView.findViewById(R.id.iv_galleryImage);
                iv_checkIcon = (ImageView) itemView.findViewById(R.id.iv_checkIcon);
                rl_remove = (RelativeLayout) itemView.findViewById(R.id.rl_remove);
                rl_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeAlertDialog(getAdapterPosition());
                    }
                });
                iv_galleryImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        position = getAdapterPosition();
                        Glide.with(context).load(uriList.get(position).getImageUri()).into(iv_imageSelcet);
//                        iv_imageSelcet.setImageURI(uriList.get(position).getImageUri());
                        System.gc();
                    }
                });
            }
        }
    }
}
