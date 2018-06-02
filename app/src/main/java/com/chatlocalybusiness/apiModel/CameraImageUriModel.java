package com.chatlocalybusiness.apiModel;

import android.net.Uri;

/**
 * Created by windows on 1/29/2018.
 */

   public class CameraImageUriModel
   {
    private Uri uri=null;
    private int pic=-1;
    public void setCropped(int pic)
    {
        this.pic=pic;
    }

    public int getCropped()
    {
        return pic;
    }
    public void setUri(Uri uri)
    {
        this.uri=uri;
    }
    public Uri getImageUri()
    {
        return uri;
    }

   }
