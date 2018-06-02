package com.chatlocalybusiness.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;

/**
 * Created by windows on 5/15/2018.
 */

public class ProfileImageActivity extends AppCompatActivity {

    ImageView iv_arrowBack,iv_profileImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);
        iv_profileImage=(ImageView)findViewById(R.id.iv_profileImage);
        iv_arrowBack=(ImageView)findViewById(R.id.iv_arrowBack);
        iv_arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Glide.with(this).load(new ChatBusinessSharedPreference(this).getUserImage()).into(iv_profileImage);


    }
}
