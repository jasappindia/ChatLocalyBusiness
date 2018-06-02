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
import com.chatlocalybusiness.apiModel.CameraImageUriModel;
import com.chatlocalybusiness.apiModel.GalleryImageUriModel;
import com.chatlocalybusiness.imagecrop.Crop;
import com.chatlocalybusiness.utill.BasicUtill;
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
 * Created by windows on 1/29/2018.
 */
public class SingleImageSelectionActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int SELECT_PICTURE = 103;
    private ImageView iv_arrowBack, iv_crop, iv_done;
    private ImageView iv_imageSelcet, iv_addImages;
    private RecyclerView recyclerView;
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
        uriArrayListGAllery = new ArrayList<>();
        uriArrayListCamera = new ArrayList<>();
        imageBitmapList = new ArrayList<>();
        filePathList = new ArrayList<>();
        choosefunction();

    }

    public void choosefunction() {
        if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("gallery"))
            openImagePicker();
        else openCamera();
    }

    public void openImagePicker() {
        if (this.checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, AlbumSelectActivity.class);
            intent.putExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_LIMIT, Constants.limit);
            startActivityForResult(intent, com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE);
        } else {
            utill.requestMultiplePermissions(this);
        }
    }

    public void openCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkCallingOrSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
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
                utill.requestMultiplePermissions(this);
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
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            choosefunction();
        } else if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            choosefunction();

        } else finish();


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


    public void sendIntent() {
        Intent intent = new Intent();
        if (Constants.limit > 1)
            intent.putStringArrayListExtra(Constants.IMAGE_PATH_LIST, filePathList);
        else
            intent.putExtra(Constants.IMAGE_PATH, filePath);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void openCropActivity() {
        if (!EditBusinessOverviewActivity.chooserDialogTitle.equals("")) {
            if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("gallery")) {
                beginBannerCrop(uriArrayListGAllery.get(position).getImageUri());
            } else beginBannerCrop(uriArrayListCamera.get(position).getImageUri());

        } else {

            if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("gallery")) {
                beginCrop(uriArrayListGAllery.get(position).getImageUri());
            } else beginCrop(uriArrayListCamera.get(position).getImageUri());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            if (filePathList.size() < 1 && filePath == null)
                finish();
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                ArrayList<Image> images = data.getParcelableArrayListExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_IMAGES);
                getUriListGallery(images);
                filePath = images.get(0).path;
                position = 0;
                openCropActivity();
//                    Glide.with(this).load(uriArrayListGAllery.get(0).getImageUri()).into(iv_imageSelcet);

            } else if (requestCode == CAMERA_REQUEST) {
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File savefile = new File(mCurrentPhotoPath);
                Uri contentUri = Uri.fromFile(savefile);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                if (Constants.limit > 1) {
                    if (savefile.exists()) {
                        filePathList.add(savefile.getAbsolutePath());
                        getUriListCamera(savefile.getAbsolutePath());

                        openCropActivity();
                        //                            Glide.with(this).load(getImageContentUri(this, savefile.getAbsolutePath())).into(iv_imageSelcet);

                    }
                } else {
                    if (savefile.exists()) {
                        filePath = savefile.getAbsolutePath();
                        filePathList.add(savefile.getAbsolutePath());
                        getUriListCamera(savefile.getAbsolutePath());

//                            Glide.with(this).load(getImageContentUri(this, savefile.getAbsolutePath())).into(iv_imageSelcet);
                        openCropActivity();
                    }
                }
            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(resultCode, data);
            }
        }
    }

    public ArrayList<GalleryImageUriModel> getUriListGallery(ArrayList<Image> images) {
        for (int i = 0; i < images.size(); i++) {
            GalleryImageUriModel uriModel = new GalleryImageUriModel();
            uriModel.setUri(getImageContentUri(this, images.get(i).path));
//                    uriArrayListGAllery.add(getImageContentUri(MultiImageActivity.this,images.get(i).path));
            filePathList.add(images.get(i).path);
            uriArrayListGAllery.add(uriModel);
        }
        return uriArrayListGAllery;
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
        Uri destination = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "cropped" + System.currentTimeMillis() + ".jpg"));
        Crop.of(source, destination).asSquare().start(this);


    }

    private void beginBannerCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "cropped" + System.currentTimeMillis() + ".jpg"));
        Crop.of(source, destination).withAspect(1, 0.53f).start(this);

    }

    public ArrayList<CameraImageUriModel> getUriListCamera(String images) {

        CameraImageUriModel uriModel = new CameraImageUriModel();
        uriModel.setUri(getImageContentUri(SingleImageSelectionActivity.this, images));
//                    uriArrayListGAllery.add(getImageContentUri(MultiImageActivity.this,images.get(i).path));
        uriArrayListCamera.add(uriModel);
        return uriArrayListCamera;
    }

    public void setImage() {
        if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("gallery") && Constants.limit > 1) {
            for (int i = 0; i < uriArrayListGAllery.size(); i++) {
                if (uriArrayListGAllery.get(i).getCropped() == 0) {
                    Toast.makeText(this, "All Images must be cropped", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (i == ((uriArrayListGAllery.size()) - 1) && uriArrayListGAllery.get(i).getCropped() == 1) {
                    sendIntent();
                }
            }
        } else if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("camera") && Constants.limit > 1) {

            for (int i = 0; i < uriArrayListCamera.size(); i++) {
                if (uriArrayListCamera.get(i).getCropped() == 0) {
                    Toast.makeText(this, "All Images must be cropped", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (i == ((uriArrayListCamera.size()) - 1) && uriArrayListCamera.get(i).getCropped() == 1) {
                    sendIntent();
                }
            }
        } else if (filePath.contains("cropped"))
            sendIntent();
        else Toast.makeText(this, " Image must be cropped", Toast.LENGTH_SHORT).show();

    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            if (Constants.IMAGE_SELECT_CAPTURE.equalsIgnoreCase("gallery")) {
                GalleryImageUriModel uriModel = new GalleryImageUriModel();
                uriModel.setUri(uri);
                String[] s = uri.toString().split("storage");
                if (Constants.limit > 1)
                    filePathList.set(position, "/storage" + s[1]);
                else filePath = "/storage" + s[1];
                setImage();
                //                    Glide.with(this).load(uri).into(iv_imageSelcet);

//                iv_imageSelcet.setImageURI(uri);
                uriArrayListGAllery.set(position, uriModel);

            } else {
                CameraImageUriModel uriModel = new CameraImageUriModel();
                uriModel.setUri(uri);
                String[] s = uri.toString().split("storage");
                if (Constants.limit > 1)
                    filePathList.set(position, "/storage" + s[1]);
                else filePath = "/storage" + s[1];
                setImage();

//                    Glide.with(this).load(uri).into(iv_imageSelcet);

//                iv_imageSelcet.setImageURI(uri);
                uriArrayListCamera.set(position, uriModel);

            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}


